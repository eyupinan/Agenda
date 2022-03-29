package com.kartaca.challenge.repository;

import com.kartaca.challenge.Enums.Roles;
import com.kartaca.challenge.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUsername(String name);
    Long deleteByUsername(String name);
    List<User> findByRole(Roles role);
}