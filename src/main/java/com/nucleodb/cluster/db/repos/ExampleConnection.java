package com.nucleodb.cluster.db.repos;

import com.nucleodb.cluster.db.models.ExampleConn;
import com.nucleodb.cluster.db.entries.ExampleDE;
import com.nucleodb.spring.types.NDBConnRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExampleConnection extends NDBConnRepository<ExampleConn, String, ExampleDE, ExampleDE> {
}
