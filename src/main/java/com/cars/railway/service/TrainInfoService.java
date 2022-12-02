package com.cars.railway.service;

import com.cars.railway.dao.oracle.IOracleDao;
import com.cars.railway.tools.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Map;

/**
 * @author chenghao
 * @purpose：
 * @备注：
 * @data 2022年11月29日 11:24
 */
@Slf4j
@Service
public class TrainInfoService {
    @Autowired
    IOracleDao oracleDao;

    public String getAll(Map<String, String> paraMap){
        ArrayList<Map<String, Object>> list = oracleDao.selectTrainInfo(paraMap);
        return Utils.listToJSON(list);
    }



}
