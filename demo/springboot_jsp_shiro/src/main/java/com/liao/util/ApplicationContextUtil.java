package com.liao.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 上下文工具
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
    }

    /**
     * 根据拿到Bean
     */
    public static Object getBean(Object object){
        if(object instanceof String){
            return applicationContext.getBean(object.toString());
        }else if(object instanceof Class){
            return applicationContext.getBean((Class)object);
        }
        return null;
    }
}
