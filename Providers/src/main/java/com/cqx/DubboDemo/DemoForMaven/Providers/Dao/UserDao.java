package com.cqx.DubboDemo.DemoForMaven.Providers.Dao;

import com.cqx.DubboDemo.DemoForMaven.Commons.Model.User;
import org.hibernate.Criteria;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by cqx on 2016/11/28.
 */
public interface UserDao extends BaseHibDao<User> {
    public boolean checkUser(String username, String password);
    public Query getUserSQLQuery();
    public Criteria getCriteria();
    public List<User> getUsersQuery(String hql, Object... parm);
    public User getUserQuery(String hql, Object... parms);
}
