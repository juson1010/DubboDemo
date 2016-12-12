package com.cqx.DubboDemo.DemoForMaven.Providers.Dao;

import com.cqx.DubboDemo.DemoForMaven.Commons.Model.Role;

/**
 * Created by cqx on 2016/11/28.
 */
public interface RoleDao  extends BaseHibDao<Role>  {
    public Role getRoleByName(String name);
    public Role getRoleById(int id);
}
