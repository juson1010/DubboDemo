package com.cqx.DubboDemo.DemoForMaven.Providers.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by cqx on 2016/11/30.
 */
public class Provider {
    private final static Logger logger = LoggerFactory.getLogger(Provider.class);
    public static void main(String[] args) throws Exception {
        try {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath*:provider.xml"});

        context.start();
        logger.info("dubbo se   rvice begin to start");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }
}
