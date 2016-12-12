package com.cqx.DubboDemo.DemoForMaven.Providers.Dao;

import com.cqx.DubboDemo.DemoForMaven.Commons.Model.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cqx on 2016/11/28.
 */
@Repository
@Transactional
public class RoleDaoHibImpl extends BaseDaoHibImpl<Role> implements RoleDao {
    public Role getRoleByName(String name){

        List<Role> roles = find("from Role role where role.name = ?",name);
        return roles.size() == 0 ? null : roles.get(0);
    }

    public Role getRoleById(int id) {
        List<Role> roles = find("from Role role where role.id = ?",id);
        return roles.size() == 0 ? null : roles.get(0);
    }
}
