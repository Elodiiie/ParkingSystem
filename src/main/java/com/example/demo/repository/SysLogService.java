package com.example.demo.repository;

import com.example.demo.vo.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: Elodie
 * @Date: 2021/11/7 18:07
 */

@Slf4j
@Service
public class SysLogService {
    public boolean save(SysLog sysLogBO){
        log.info(sysLogBO.getRemark());
        log.info(sysLogBO.getCreateDate());
        log.info(sysLogBO.getClassName());
        log.info(sysLogBO.getMethodName());
        log.info(sysLogBO.getParams());
        return true;
    }

}
