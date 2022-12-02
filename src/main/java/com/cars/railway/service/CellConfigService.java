package com.cars.railway.service;

import com.cars.railway.dao.oracle.IOracleDao;
import com.cars.railway.tools.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.cars.railway.common.CommonInfo.railLineMap;

/**
 * @author chenghao
 * @purpose：cellCOnfig
 * 表的查询，修改
 * @备注：
 * @data 2022年11月10日 11:05
 */
@Slf4j
@Service
public class CellConfigService {

    @Autowired
    private IOracleDao iOracleDao;
    /*
        获取小区表所有信息，或根据条件模糊查询
     */
    public String getAll(Map<String, String> paraMap) {
        String lineNum = paraMap.get("lineNum");
        for(Integer key : railLineMap.keySet()){
            String value = railLineMap.get(key);
            if (value.equals(lineNum)){
                String lineName = key.toString();
                System.out.println(key);
                paraMap.replace("lineNum",lineNum,lineName);
            }
        }
        ArrayList<Map<String, Object>> list = iOracleDao.selectAllCellConfig(paraMap);
        //查询到数据后再转换回去，就是线路number对应具体的名称
        for(Map<String,Object> map : list) {
            //从map中获取线路转换成字符串，并且判空
            String oldLineName =  map.get("LINENUM").toString();
                if (oldLineName != null){
                    int newlineNameInt = Integer.parseInt(oldLineName);
                    //初始化线路：使线路number对应线路名称
                    //从数据库房中拿到的线路number转换为int，找对应的线路name
                    String newLineNameString =  railLineMap.get(newlineNameInt);
                    //替换原始集合
                    boolean linenum = map.replace("LINENUM", map.get("LINENUM"), newLineNameString);
                    System.out.println(linenum);
                }
        }
        return Utils.listToJSON(list);
    }
    /*
    更改表中NCC开始往后的7个字段
     */
    public boolean updateRecord(Map<String,String> map) {
        boolean res = iOracleDao.updateCellInfoByCellName(map);
        return res;
    }
}
