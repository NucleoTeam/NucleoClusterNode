package com.nucleodb.cluster.db.controllers;

import com.nucleodb.cluster.db.common.DataController;
import com.nucleodb.cluster.db.entries.ExampleDE;
import com.nucleodb.cluster.db.models.Example;
import com.nucleodb.cluster.db.repos.ExampleRepository;
import com.nucleodb.cluster.raft.Node;
import org.springframework.web.bind.annotation.RestController;

@RestController("/examples/")
public class ExampleController extends DataController<ExampleDE, String, Example> {

    public ExampleController(ExampleRepository exampleRepository, Node node) {
        super(exampleRepository, node);
    }
}