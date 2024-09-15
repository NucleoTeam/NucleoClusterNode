package com.nucleodb.cluster;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nucleodb.library.NucleoDB;
import com.nucleodb.library.database.utils.Serializer;
import com.nucleodb.spring.config.EnableNDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"com.nucleodb.cluster"})
@EnableNDBRepositories(
        dbType = NucleoDB.DBType.ALL,
        nodeFilterConnection = "com.nucleodb.cluster.distributedHash.ConnectionDistribution",
        nodeFilterDataEntry = "com.nucleodb.cluster.distributedHash.DataEntryDistribution",
        //mqsConfiguration = "com.nucleodb.library.mqs.local.LocalConfiguration",
        /*
        Feature: Read To Time, will read only changes equal to or before the date set.
         */
        //readToTime = "2023-12-17T00:42:32.906539Z",
        scanPackages = {
                "com.nucleodb.cluster.db.models"
        },
        basePackages = "com.nucleodb.cluster.db.repos"
)
@EnableAsync
public class NodeServer {
        public static void main(String[] args) {
                Serializer.getObjectMapper().getOm().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Serializer.getObjectMapper().getOm().configure(MapperFeature.USE_GETTERS_AS_SETTERS, false);

                Serializer.getObjectMapper().getOmNonType().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                Serializer.getObjectMapper().getOmNonType().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Serializer.getObjectMapper().getOmNonType().configure(MapperFeature.USE_GETTERS_AS_SETTERS, false);
                SpringApplication.run(NodeServer.class);
        }
}
