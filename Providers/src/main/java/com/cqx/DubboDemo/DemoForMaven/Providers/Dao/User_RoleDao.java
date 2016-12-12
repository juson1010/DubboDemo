package com.cqx.DubboDemo.DemoForMaven.Providers.Dao;


import com.cqx.DubboDemo.DemoForMaven.Commons.Model.User_Role;

import java.util.List;

/**
 * Created by cqx on 2016/11/28.
 */
public interface User_RoleDao extends BaseHibDao<User_Role> {

    public List<User_Role> getUser_RolesByUserId(int userId);
    public List<User_Role> getUser_RolesQuery(String hql, Object... parms);
    public void deleteUserRolesByUserId(int userId);
}
