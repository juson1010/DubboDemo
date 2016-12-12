package com.cqx.DubboDemo.DemoForMaven.Commons.IServices;
import com.cqx.DubboDemo.DemoForMaven.Commons.Model.QueryUserBean;
import com.cqx.DubboDemo.DemoForMaven.Commons.Model.User;
import com.cqx.DubboDemo.DemoForMaven.Commons.Model.User_Role;

import java.util.List;

/**
 * Created by cqx on 2016/11/28.
 */
public interface UserService {

    public void save(User user) ;
    public void update(User user);
    public void delete(User user);
    public User getUserById(int id);
    public List<User_Role> getUserRoles(int userId) ;

    public void savePassword(User user, String password) ;
    public Boolean verifyPassword(User user, String password) ;

    public Boolean isExistMobile(User postUser);
    public Boolean isExistEmail(User postUser);

    /*直接返回数据库所有用户*/
    public List<User> getUsers();
    /*输入Role 名字,返回所有该角色的用户*/
    public List<User> getUsersByRoleName(String name);
    public User getUserByMobile(String mobile);
    public List<User> getUsersByUserBean(QueryUserBean userBean);

    public User getUserQuery(String hql, Object... parms);
    public String initDb();
}