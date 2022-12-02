package com.cars.railway.rest;

import com.cars.railway.service.CellConfigService;
import com.cars.railway.tools.ConfLocNumMap;
import com.cars.railway.tools.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author chenghao
 * @purpose：
 * @备注：小区表的操作
 * @data 2022年11月08日 23:14
 */
@RestController
@RequestMapping("cellConfig")
public class CellConfigController {
    @Autowired
    CellConfigService cellConfigService;
    @Autowired
    ConfLocNumMap confLocNumMap;

    /*
     * @Description: 获取全部小区信息，或按条件模糊查询小区信息
     * @Author: chenghao
     * @Date: 2022/11/16 17:23
     * @param paraMap
     * @return: java.lang.String
     **/

    @RequestMapping(method = RequestMethod.POST,path = "/getAll" )
    public String getAll(@RequestBody Map<String, String> paraMap ){
        String all = cellConfigService.getAll(paraMap);
        return all;
    }
    /*
     * @Description: 小区信息的7个字段的修改
     * @Author: chenghao
     * @Date: 2022/11/16 17:24
     * @param map
     * @return: java.lang.String
     **/
    @RequestMapping(method = RequestMethod.POST,path = "/updateByCellName" )
    public String updateRecord(@RequestBody Map<String,String> map){
        boolean result = cellConfigService.updateRecord(map);
        //前端需要success字符串判断是否成功
        if (!result){
            return null;
        }
        return "success";
    }




}
