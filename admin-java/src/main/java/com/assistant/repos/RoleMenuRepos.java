package com.assistant.repos;

import com.assistant.domain.mysql.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Title
 * @ClassName RoleMenuRepos
 * @Author jsb_pbk
 * @Date 2018/11/5
 */
public interface RoleMenuRepos extends JpaRepository<RoleMenu,Integer> {

    List<RoleMenu> findAllByMenuId(Integer menuId);
    List<RoleMenu> findAllByRoleId(Integer roleId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM role_menu WHERE role_id = :roleId",nativeQuery=true)
    int deleteRoleMenuListByRoleId(@Param("roleId") Integer roleId);
}
