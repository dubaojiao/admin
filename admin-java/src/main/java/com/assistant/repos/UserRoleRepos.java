package com.assistant.repos;

import com.assistant.domain.mysql.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepos extends JpaRepository<UserRole,Integer> {

    UserRole findFirstByUserId(Integer uid);
}
