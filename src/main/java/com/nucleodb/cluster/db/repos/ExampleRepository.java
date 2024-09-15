package com.nucleodb.cluster.db.repos;

import com.nucleodb.cluster.db.entries.ExampleDE;
import com.nucleodb.cluster.db.models.Example;
import com.nucleodb.spring.types.NDBDataRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "examples")
@Repository
public interface ExampleRepository extends NDBDataRepository<ExampleDE, String> {

}
