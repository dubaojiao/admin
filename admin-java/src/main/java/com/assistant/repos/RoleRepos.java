package com.assistant.repos;

import com.assistant.domain.mysql.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Title
 * @ClassName RoleRepos
 * @Author jsb_pbk
 * @Date 2018/11/5
 */
public interface RoleRepos extends JpaRepository<Role,Integer>{
    List<Role> findAllByRoleState(Integer state);
}
