package com.cqx.DubboDemo.DemoForMaven.Providers.Dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cqx on 2016/11/28.
 */
@Repository
@Transactional
public class BaseDaoHibImpl<T> implements BaseHibDao<T> {
    // DAO组件进行持久化操作底层依赖的SessionFactory组件
    // private ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    @Autowired
    protected SessionFactory sessionFactory ;
    protected int rowsCount;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // spring 建议调用sessionFactory.getCurrentSession()方法得到方式
    // 还有sessionFactory.openSession()
    public Session getSession() {

        return sessionFactory.getCurrentSession();
    }

    // 根据ID加载实体
    @SuppressWarnings("unchecked")
    public T get(Class<T> entityClazz, Serializable id) {
        return (T) getSession().get(entityClazz, id);
    }

    // 保存实体
    public Serializable save(T entity) {
        return getSession().save(entity);
        // 不要考虑事务，不需要关闭
    }

    // 更新实体
    public void update(T entity) {
        getSession().saveOrUpdate(entity);
    }

    // 删除实体
    public void delete(T entity) {
        getSession().delete(entity);
    }

    // 根据ID删除实体
    public void delete(Class<T> entityClazz, Serializable id) {
        getSession()
                .createQuery(
                        "delete " + entityClazz.getSimpleName()
                                + " en where en.id = :id").setParameter("id", id)
                .executeUpdate();
    }

    // 获取所有实体
    public List<T> findAll(Class<T> entityClazz) {
        return find("select en from " + entityClazz.getSimpleName() + " en");
    }

    // 获取实体总数
    public long findCount(Class<T> entityClazz) {
        List<?> l = find("select count(*) from " + entityClazz.getSimpleName());
        // 返回查询得到的实体总数
        if (l != null && l.size() == 1) {
            return (Long) l.get(0);
        }
        return 0;
    }

    // 根据HQL语句查询实体
    @SuppressWarnings("unchecked")
    protected List<T> find(String hql) {
        return (List<T>) getSession().createQuery(hql).list();
    }

    // 根据带占位符参数HQL语句查询实体
    @SuppressWarnings("unchecked")
    protected List<T> find(String hql, Object... params) {
        // 创建查询
        Query query = getSession().createQuery(hql);
        // 为包含占位符的HQL语句设置参数
        for (int i = 0, len = params.length; i < len; i++) {
            query.setParameter(i, params[i]);
        }
        return (List<T>) query.list();
    }

    /**
     * 使用hql 语句进行分页查询操作
     *
     * @param hql
     *            需要查询的hql语句
     * @param pageNo
     *            查询第pageNo页的记录
     * @param pageSize
     *            每页需要显示的记录数
     * @return 当前页的所有记录
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByPage(String hql, int pageNo, int pageSize) {
        // 创建查询
        return getSession().createQuery(hql)
                // 执行分页
                .setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize).list();
    }

    /**
     * 使用hql 语句进行分页查询操作
     *
     * @param hql
     *            需要查询的hql语句
     * @param params
     *            如果hql带占位符参数，params用于传入占位符参数
     * @param pageNo
     *            查询第pageNo页的记录
     * @param pageSize
     *            每页需要显示的记录数
     * @return 当前页的所有记录
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByPage(String hql, int pageNo, int pageSize,
                                 Object... params) {
        // 创建查询
        Query query = getSession().createQuery(hql);
        // 为包含占位符的HQL语句设置参数
        for (int i = 0, len = params.length; i < len; i++) {
            query.setParameter(i + "", params[i]);
        }
        // 执行分页，并返回查询结果
        return query.setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize).list();
    }

    public int getRowsCount() {
        // TODO Auto-generated method stub
        return rowsCount;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }
}
