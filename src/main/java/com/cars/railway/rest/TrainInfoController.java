package com.cars.railway.rest;

import com.cars.railway.service.TrainInfoService;
import com.cars.railway.tools.ConfLocNumMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Map;

/**
 * @author chenghao
 * @purpose：
 * @备注：
 * @data 2022年11月29日 11:23
 */
@RestController
@RequestMapping("trainInfo")
public class TrainInfoController {
    @Autowired
    ConfLocNumMap confLocNumMap;

    @Autowired
    TrainInfoService trainInfoService;
    @RequestMapping(method = RequestMethod.POST,path = "/getAll" )
    public String getAll(@RequestBody Map<String, String> paraMap ) throws SQLException {
        String all = trainInfoService.getAll(paraMap);
        return all;
    }

}
