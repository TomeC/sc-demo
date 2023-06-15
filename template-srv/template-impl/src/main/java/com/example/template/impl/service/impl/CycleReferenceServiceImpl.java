package com.example.template.impl.service.impl;

import com.example.template.impl.service.TemplateService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CycleReferenceServiceImpl implements ApplicationContextAware {
    @Resource
    private TemplateService templateService;

    @Async
    public void get() {
        templateService.deleteTemplate(1L);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
