package com.cqx.DubboDemo.DemoForMaven.Providers.Dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cqx on 2016/11/28.
 */

public interface BaseHibDao<T> {
    // 根据ID加载实体
    T get(Class<T> entityClazz, Serializable id);

    // 保存实体
    Serializable save(T entity);

    // 更新实体
    void update(T entity);

    // 删除实体
    void delete(T entity);

    // 根据ID删除实体
    void delete(Class<T> entityClazz, Serializable id);

    // 获取所有实体
    List<T> findAll(Class<T> entityClazz);

    // 获取实体总数
    long findCount(Class<T> entityClazz);

    // 获取记录总数
    int getRowsCount();
}
