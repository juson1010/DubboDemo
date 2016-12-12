package com.cqx.DubboDemo.DemoForMaven.Providers.Dao;

import com.cqx.DubboDemo.DemoForMaven.Commons.Model.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by cqx on 2016/11/28.
 */
@Repository
@Transactional
public class UserDaoHibImpl extends BaseDaoHibImpl<User> implements UserDao {

    public boolean checkUser(String username, String password) {
        return (find("from User user where user.name = ? and user.password = ?",username,password).size() == 1);
    }

    public Query getUserSQLQuery(){
       return getSession().createSQLQuery("SELECT * FROM USER ORDER BY  registerDate ASC ").setCacheable(true);
    }

    public List<User> getUsersQuery(String hql, Object... parms) {
        return find(hql,parms);
    }

    public Criteria getCriteria() {
       return getSession().createCriteria(User.class);
    }

    public User getUserQuery(String hql, Object... parms) {
        List<User> users = getUsersQuery(hql,parms);
        return users.size() == 0 ? null : users.get(0);
    }
}
