package com.marufhassan.userinfo.repository;

import com.marufhassan.userinfo.models.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends JpaRepository<Child, Integer> {
    
}
