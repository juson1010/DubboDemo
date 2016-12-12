package com.cqx.DubboDemo.DemoForMaven.Providers.Dao;
import com.cqx.DubboDemo.DemoForMaven.Commons.Model.User_Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cqx on 2016/11/28.
 */
@Repository
@Transactional
public class User_RoleDaoHibImpl extends BaseDaoHibImpl<User_Role> implements User_RoleDao {

    public List<User_Role> getUser_RolesByUserId(int userId) {
        return  find("from User_Role user_role where user_role.userId = ?",userId);
    }

    public List<User_Role> getUser_RolesQuery(String hql, Object... parms) {
        return find(hql,parms);
    }

    public void deleteUserRolesByUserId(int userId) {
        List<User_Role> user_roles = getUser_RolesByUserId(userId);
        for (User_Role user_role:user_roles) {
            delete(user_role);
        }
    }
}
