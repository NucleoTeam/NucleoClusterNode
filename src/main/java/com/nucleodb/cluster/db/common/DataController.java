package com.nucleodb.cluster.db.common;

import com.nucleodb.cluster.raft.Node;
import com.nucleodb.cluster.raft.common.AssignedResource;
import com.nucleodb.library.database.tables.table.DataEntry;
import com.nucleodb.library.database.utils.exceptions.ObjectNotSavedException;
import com.nucleodb.spring.types.NDBDataRepository;
import javassist.tools.web.BadHttpRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public abstract class DataController<T extends DataEntry<X>, ID, X> {

    private final NDBDataRepository<T, ID> repository;
    private final Node node;

    public DataController(NDBDataRepository<T, ID> repository, Node node) {
        this.repository = repository;
        this.node = node;
    }

    @GetMapping("/data")
    @ResponseBody
    public X get(ID id) {
        T byId = repository.findById(id);
        if(byId==null) return null;
        return byId.getData();
    }
    @PutMapping("/data")
    @ResponseBody
    public ResponseEntity get(ID id, X data) {
        T byId = repository.findById(id);
        if(byId==null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        try {
            T copy = byId.copy(true);
            copy.setData(data);
            repository.saveForget(copy);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (ObjectNotSavedException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/entry")
    @ResponseBody
    public T getDE(ID id) {
        T byId = repository.findById(id);
        return byId;
    }

    @PostMapping("/entry")
    @ResponseBody
    public ResponseEntity<T> post(ID id, X entry) {
        AssignedResource assignedResource = node.getAssignedTables().get(entry.getClass().getSimpleName());
        if(assignedResource==null) return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
        if(assignedResource.isUUIDInRange(UUID.fromString((String)id))) return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        if(repository.findById(id)!=null) return new ResponseEntity(HttpStatus.CONFLICT);
        return ResponseEntity.ok(repository.save((T)new DataEntry(entry)));
    }

    @GetMapping("/list")
    @ResponseBody
    public List<T> list() {
        return repository.findAll();
    }

}