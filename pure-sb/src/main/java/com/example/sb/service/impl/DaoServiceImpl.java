package com.example.sb.service.impl;

import com.example.sb.service.DaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 王锟
 * @description
 * @date 2023/7/7
 */
@Service
@Slf4j
public class DaoServiceImpl implements DaoService {
    @Override
    public int save() {
        System.out.println("dao save");
        return 0;
    }

    @Override
    public String query() {
        log.info("dao query");
        return "success";
    }
}
