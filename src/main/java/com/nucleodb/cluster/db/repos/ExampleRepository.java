package com.nucleodb.cluster.db.repos;

import com.nucleodb.cluster.db.entries.ExampleDE;
import com.nucleodb.cluster.db.models.Example;
import com.nucleodb.spring.types.NDBDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends NDBDataRepository<ExampleDE, String> {

}
