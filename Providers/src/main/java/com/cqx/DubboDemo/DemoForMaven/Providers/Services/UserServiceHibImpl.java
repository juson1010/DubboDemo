package com.cqx.DubboDemo.DemoForMaven.Providers.Services;

import com.cqx.DubboDemo.DemoForMaven.Commons.IServices.UserService;
import com.cqx.DubboDemo.DemoForMaven.Commons.Model.QueryUserBean;
import com.cqx.DubboDemo.DemoForMaven.Commons.Model.Role;
import com.cqx.DubboDemo.DemoForMaven.Commons.Model.User;
import com.cqx.DubboDemo.DemoForMaven.Commons.Model.User_Role;
import com.cqx.DubboDemo.DemoForMaven.Providers.Dao.RoleDao;
import com.cqx.DubboDemo.DemoForMaven.Providers.Dao.UserDao;
import com.cqx.DubboDemo.DemoForMaven.Providers.Dao.User_RoleDao;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cqx on 2016/11/28.
 */
@Service
public class UserServiceHibImpl implements UserService {

    @Autowired
    private UserDao userDao ;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private User_RoleDao user_roleDao;

    public void save(User user) {

        if(user.getId() != 0){
            update(user);
        }
        else
        {
            createUserRolesByUser(user);

            if (user.getPassword() == null || user.getPassword().length() == 0){
                savePassword(user, "123456");
            }
            userDao.save(user);
        }
    }


    public void update(User user) {
        createUserRolesByUser(user);
        userDao.update(user);
    }

    private void createUserRolesByUser(User user){
        List roles = user.getRoles();
        user_roleDao.deleteUserRolesByUserId(user.getId());
        if(roles == null || roles.size() == 0 ) return;
        for (int i = 0; i < roles.size(); i++) {
            User_Role user_Role = new User_Role();
            Role roleQ = roleDao.getRoleByName(roles.get(i) + "");
            user_Role.setRoleId(roleQ.getId());
            user_Role.setUserId(user.getId());

            user_roleDao.save(user_Role);
        }
    }

    public void delete(User user) {
        List<User_Role> user_Roles = user_roleDao.getUser_RolesByUserId(user.getId());

        for (int i = 0;i < user_Roles.size();i++){
            user_roleDao.delete(user_Roles.get(i));
        }
        userDao.delete(user);
    }

    public User getUserById(int id) {

        User user = userDao.get(User.class,id);

        List<User_Role> roles = user_roleDao.getUser_RolesByUserId(id);

        List rolesStr = new ArrayList();

        for (int i = 0; i < roles.size(); i++) {
            User_Role user_role =(User_Role) roles.get(i);

            Role role = roleDao.getRoleById(user_role.getRoleId());
            rolesStr.add(role.getName());
        }
        user.setRoles(rolesStr);
        return user;
    }

    public List<User_Role> getUserRoles(int userId) {
        return user_roleDao.getUser_RolesByUserId(userId);
    }

    public void savePassword(User user, String password) {
        String salt = RandomStringUtils.randomAlphanumeric(4);
        String hashedPassword = DigestUtils.md5Hex(password + salt);
        user.setSalt(salt);
        user.setPassword(hashedPassword);

        userDao.save(user);
    }
    public Boolean verifyPassword(User user, String password) {
       return DigestUtils.md5Hex(password + user.getSalt()).equals(user.getPassword()) ? true : false;
    }

    public Boolean isExistMobile(User postUser) {

        User user = userDao.getUserQuery("from User user where user.mobile = ?",postUser.getMobile());
        return (user != null && user.getId() != postUser.getId()) ;
    }

    public Boolean isExistEmail(User postUser) {

        User user = userDao.getUserQuery("from User user where user.email = ?",postUser.getEmail());
        return (user != null && user.getId() != postUser.getId());
    }

    public List<User> getUsers() {
        return userDao.getUsersQuery("from User");
    }

    public List<User> getUsersByRoleName(String name) {
        if (name.equals("admin")) name = "管理员";
        else if (name.equals("user")) name = "注册用户";
        else  if(name.equals("data")) name = "数据录入";
        System.out.println("name is "+name);

        Role role = roleDao.getRoleByName(name);
        List<User_Role> user_Roles = user_roleDao.getUser_RolesQuery("from User_Role user_Role where user_Role.roleId = ?",role.getId());
        List<User> users = new ArrayList<User>();

        for(int i = 0;i < user_Roles.size();i++){
            User_Role user_role = user_Roles.get(i);
            User user = userDao.get(User.class,user_role.getUserId());
            users.add(user);
        }
        return users;
    }

    public User getUserByMobile(String mobile) {
        return userDao.getUserQuery("from User user where user.mobile = ?" , mobile);
    }

    public List<User> getUsersByUserBean(QueryUserBean userBean) {
        if (userBean == null) userBean = new QueryUserBean();
        Criteria criteria = userDao.getCriteria();

        if (userBean.getGender() != null && !userBean.getGender().equals("请选择") && userBean.getGender().length() != 0)
            criteria.add(Restrictions.eq("gender", userBean.getGender()));

        if (userBean.getEmail() != null && userBean.getEmail().length() > 0)
            criteria.add(Restrictions.eq("email", userBean.getEmail()));

        if (userBean.getMobile() != null && userBean.getMobile().length() > 0)
            criteria.add(Restrictions.eq("mobile", userBean.getMobile()));

        if (userBean.getName() != null && userBean.getName().length() > 0)
            criteria.add(Restrictions.eq("name", userBean.getName()));

        if (userBean.getMobile_email_name() != null && userBean.getMobile_email_name().length() > 0) {
            String search = userBean.getMobile_email_name();
            criteria.add(Restrictions.eq("mobile", search)).add(Restrictions.eq("email", search)).add(Restrictions.eq("name", search));
        }

        criteria.addOrder(Order.desc("registerDate"));
        int size = criteria.list().size();
        int allPages = (size + 9)/ userBean.getLimit();
        userBean.setAllPages(allPages);

        criteria.setFirstResult(userBean.getOffset());
        criteria.setMaxResults(userBean.getLimit());

        List<User> users = getUsersWithRoles(criteria.list());
        List<User> aUsers = new ArrayList<User>();

        if (userBean.getRole() != null && !userBean.getRole().equals("未归类") && userBean.getRole().length() != 0){

            for(int i = 0;i < users.size();i++){
                User user = users.get(i);
                if (user.getRoles() == null || user.getRoles().size() == 0) continue;
                else {
                    for(int j = 0; j < user.getRoles().size();j++){
                        if (userBean.getRole().equals(user.getRoles().get(j))){

                            aUsers.add(user);
                            break;
                        }
                    }
                }

            }

        }else{
            aUsers = users;
        }
        return aUsers;
    }
    /*输入用户列表,为列表内每个用户的roles属性赋值(如果有分类的话)。*/
    private List<User> getUsersWithRoles(List<User> users){

        for (User user: users) {
            List<User_Role> roles = user_roleDao.getUser_RolesByUserId(user.getId());
            List rolesStr = new ArrayList();

            for (int i = 0; i < roles.size(); i++) {
                User_Role user_role = roles.get(i);
                Role role = roleDao.getRoleById(user_role.getRoleId());
                rolesStr.add(role.getName());
            }
            user.setRoles(rolesStr);
        }
        return users;
    }


    public User getUserQuery(String hql, Object... parms) {
        return userDao.getUserQuery(hql,parms);
    }


    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public User_RoleDao getUser_roleDao() {
        return user_roleDao;
    }

    public void setUser_roleDao(User_RoleDao user_roleDao) {
        this.user_roleDao = user_roleDao;
    }

    public String initDb(){
        Role role  = new Role();
        role.setName("管理员");
        roleDao.save(role);

        Role role2 = new Role();
        role2.setName("注册用户");
        roleDao.save(role2);

        Role role3 = new Role();
        role3.setName("数据录入");
        roleDao.save(role3);

        return "Table role init!";
    }


}
