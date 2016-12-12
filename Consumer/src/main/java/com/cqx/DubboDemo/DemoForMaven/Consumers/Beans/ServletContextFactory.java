package com.cqx.DubboDemo.DemoForMaven.Consumers.Beans;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * Created by cqx on 2016/11/29.
 */

public class ServletContextFactory implements FactoryBean<ServletContext>,
        ServletContextAware {
    private ServletContext servletContext;

    public ServletContext getObject() throws Exception {
        return servletContext;
    }

    public Class<?> getObjectType() {
        return ServletContext.class;
    }

    public boolean isSingleton() {
        return true;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

}