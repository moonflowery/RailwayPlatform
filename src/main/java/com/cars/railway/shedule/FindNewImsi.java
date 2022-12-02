package com.cars.railway.shedule;

import com.cars.railway.dao.oracle.IOracleDao;
import com.cars.railway.tools.ConfLocNumMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chenghao
 * @purpose：
 * @备注：
 * @data 2022年12月01日 16:40
 */
@Slf4j
@Component
public class FindNewImsi {
    @Autowired
    private IOracleDao ioracleDao;
    @Autowired
    ConfLocNumMap confLocNumMap;
   // @Scheduled(cron = "1-2 * 2 * * ? " )

    @Scheduled(cron = "0 0 2 * * ? " ) //每天凌晨两点开始
    public void createFindMobileId(){
        //创建开始时间,结束时间

        log.info("------------------开始查找是否有新的列车车次,并更新列车信息------------------");
        Date date = new Date(System.currentTimeMillis() - 1 * 86400 * 1000l);
        String startTime = new SimpleDateFormat("yyyy-MM-dd").format(date) + " 00:00:00";
        String endTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())) + " 23:59:59";
        confLocNumMap.study(startTime,endTime);
        log.info("'查询完毕");

    }
}
