package com.marufhassan.userinfo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.marufhassan.userinfo.exceptions.ResourceNotFoundException;
import com.marufhassan.userinfo.models.Parent;
import com.marufhassan.userinfo.repository.ParentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class ParentController {
    @Autowired
    private ParentRepository parentRepository;

    @GetMapping("/parents")
    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    @GetMapping("/parents/{id}")
    public ResponseEntity<Parent> getParentsById(@PathVariable(value = "id") int parentId) throws ResourceNotFoundException {
        Parent parent = parentRepository.findById(parentId).
                                orElseThrow(() -> new ResourceNotFoundException("Parent " + parentId + " not found"));
        return ResponseEntity.ok().body(parent);
    }

    /**
     * POST Method for Create operation
     */
    @PostMapping("/parents")
    public Parent create(@Valid @RequestBody Parent parent) {
        return parentRepository.save(parent);
    }

    /**
     * PUT Method for Update operation
     */
    @PutMapping("/parents/{id}")
    public ResponseEntity<Parent> update(
        @PathVariable(value = "id") int parentId, @Valid @RequestBody Parent parentDetails){

        Parent oldParent = parentRepository.findById(parentId).get();
        oldParent.setFirstname(parentDetails.getFirstname());
        oldParent.setLastname(parentDetails.getLastname());
        oldParent.setAddress(parentDetails.getAddress());

        final Parent updatedParent = parentRepository.save(oldParent);
        return ResponseEntity.ok(updatedParent);
    }

    /**
     * DELETE Method for Delete operation
     */
    @DeleteMapping("/parents/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") int parentId) throws ResourceNotFoundException {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent " + parentId + " not found"));

        parentRepository.delete(parent);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
