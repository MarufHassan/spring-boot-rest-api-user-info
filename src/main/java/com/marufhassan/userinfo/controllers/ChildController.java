package com.marufhassan.userinfo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.marufhassan.userinfo.exceptions.ResourceNotFoundException;
import com.marufhassan.userinfo.models.Child;
import com.marufhassan.userinfo.repository.ChildRepository;

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
public class ChildController {
    @Autowired
    private ChildRepository childRepository;

    @GetMapping("/childs")
    public List<Child> getAllChilds() {
        return childRepository.findAll();
    }

    @GetMapping("/childs/{id}")
    public ResponseEntity<Child> getChildById(@PathVariable(value = "id") int childId) throws ResourceNotFoundException {
        Child child = childRepository.findById(childId).
            orElseThrow(() -> new ResourceNotFoundException("Child " + childId + " not found"));
        return ResponseEntity.ok().body(child);
    }

    /**
     * POST Method for Create operation
     */
    @PostMapping("/childs")
    public Child create(@Valid @RequestBody Child child) {
        return childRepository.save(child);
    }

    /**
     * PUT Method for Update operation
     */
    @PutMapping("/childs/{id}")
    public ResponseEntity<Child> update(
        @PathVariable(value = "id") int childId, @Valid @RequestBody Child childDetails) throws ResourceNotFoundException {

        Child oldChild = childRepository.findById(childId).
            orElseThrow(() -> new ResourceNotFoundException("Child " + childId + " not found"));
        oldChild.setFirstname(childDetails.getFirstname());
        oldChild.setLastname(childDetails.getLastname());
        oldChild.setParent(childDetails.getParent());

        final Child updatedChild = childRepository.save(oldChild);
        return ResponseEntity.ok(updatedChild);
    }

    /**
     * DELETE Method for Delete operation
     */
    @DeleteMapping("/childs/{id}")
    public Map<String, Boolean> delete(@PathVariable(value = "id") int childId) throws ResourceNotFoundException {
        Child child = childRepository.findById(childId).
            orElseThrow(() -> new ResourceNotFoundException("Child " + childId + " not found"));

        childRepository.delete(child);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
