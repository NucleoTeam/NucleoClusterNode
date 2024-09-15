package com.nucleodb.cluster.raft;

import com.nucleodb.cluster.raft.common.Status;
import com.nucleodb.cluster.raft.leadership.LeaderRequest;
import com.nucleodb.library.NucleoDB;
import com.nucleodb.spring.NDBRepositoryFactory;
import com.nucleodb.spring.NDBRepositoryFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.event.ConsumerStartedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.nucleodb.cluster.communication.KafkaConfiguration.RaftTopic;

@Service
@KafkaListener(topics = { RaftTopic })
public class Node {

    private static final Logger logger = LoggerFactory.getLogger(Node.class);

    @Value("${server.port}")
    int port;

    Status status;
    Timer leaderExpirationTimer = new Timer();
    ScheduledExecutorService leaderSchedule = Executors.newScheduledThreadPool(1);

    private KafkaTemplate<Object, Object> template;
    private NucleoDB nucleoDB;




    public Node(NucleoDB nucleoDB, KafkaTemplate<Object, Object> template) {
        this.nucleoDB = nucleoDB;
        this.template = template;

        status = new Status(port);
        this.nucleoDB.addTableEvent(table->status.getTables().add(table.getConfig().getTable()));
        this.nucleoDB.addConnectionEvent(connection->status.getConnections().add(connection.getConfig().getLabel()));
    }

    @KafkaHandler
    public void status(Status status) {
        if(status.getLeaderId().equals("")){
            // bad data
            return;
        }
        if(this.status.getId().equals(status.getId())){
            // self leadership election not allowed.
            if(this.status.getLeaderId().equals(this.status.getId())){
                logger.info("I AM STILL THE LEADER!!");
            }
            return;
        }
        if(!status.getLeaderId().equals(this.status.getLeaderId()) && status.getLeaderId().equals(this.status.getId())){
            logger.info("I AM NOW THE LEADER!!");
            stopTimer();
            this.status.setLeaderId(this.status.getId());
            startLeaderLoop();
        }else if(status.getLeaderId().equals(this.status.getLeaderId()) && status.getLeaderId().equals(this.status.getId())){
            stopTimer();
        }else{
            //logger.info("I AM NOT THE LEADER!!");
            this.status.setLeaderId(status.getLeaderId());
            stopLeaderLoop();
        }
    }

    @KafkaHandler
    public void leadershipRequest(LeaderRequest leaderRequest) {
        if(!status.getLeaderId().equals(status.getId())) restartTimer();
        Status tmpStatus = new Status(status);
        tmpStatus.setLeaderId(leaderRequest.getNode());
        tmpStatus.setId(status.getId());
        template.send(RaftTopic, tmpStatus);
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        logger.info("Received unknown: " + object);
    }

    @EventListener
    public void consumerStarted(ConsumerStartedEvent event) {
        restartTimer();
    }

    public void restartTimer(){
        try {
            leaderExpirationTimer.cancel();
            leaderExpirationTimer.purge();
        }catch (Exception e){
            // ignore if already cancelled
        }
        leaderExpirationTimer = new Timer();
        leaderExpirationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                template.send(RaftTopic, new LeaderRequest(status.getId(), false));
            }
        }, 5000);
    }
    public void stopTimer(){
        try {
            leaderExpirationTimer.cancel();
            leaderExpirationTimer.purge();
        }catch (Exception e){
            // ignore if already cancelled
        }
    }

    public void startLeaderLoop(){
        leaderSchedule = Executors.newScheduledThreadPool(1);
        leaderSchedule.scheduleAtFixedRate(()->{
            template.send(RaftTopic, new LeaderRequest(status.getId(), true));
        }, 0, 2000, TimeUnit.MILLISECONDS);
    }
    public void stopLeaderLoop(){
        try{
            leaderSchedule.close();
        }catch (Exception e){}
    }
}
