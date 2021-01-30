package com.assistant.repos;

import com.assistant.domain.mysql.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepos extends JpaRepository<Menu,Integer> {
    @Query(value = "SELECT * FROM menu WHERE menu_id IN (SELECT menu_id FROM role_menu WHERE role_id = :roleId) AND menu_state = 1",nativeQuery=true)
    List<Menu> findAllRole(@Param("roleId") Integer roleId);


    List<Menu> findAllByParentId(Integer parentId);
}
