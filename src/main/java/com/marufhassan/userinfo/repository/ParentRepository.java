package com.marufhassan.userinfo.repository;

import com.marufhassan.userinfo.models.Parent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Integer> {
    
}
