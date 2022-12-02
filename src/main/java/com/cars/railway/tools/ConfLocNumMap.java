package com.cars.railway.tools;

import com.cars.railway.dao.oracle.IOracleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenghao
 * @purpose：
 * @备注：
 * @data 2022年11月30日 09:07
 */
@Component
public class ConfLocNumMap {
    @Autowired
    private IOracleDao oracleDao;
    private Map map = new HashMap<>();
    private int studyDays = 1;



    /*
     * @Description:    1. 经过Abit表和Amon表的联合查询，查询条件：
                         a. 限制时间
                         b. msisdn
                        2. 上一步查处有没有对应关系，如果有的话需要在rails库的   LOCNUMMSISDN表中 按照MISISDN字段为条件进行更新
                        3. 但是LOCNUMMSISDN表中，LOCONUM（列车号）大部分记录都没有值，所以在第二步的时候可以顺带更新，当然也可一次更新（使用工具类）
     * @return: void
     **/
    public void study(String startTime,String endTime) {

        //查询条件模糊匹配mobileid
        String mobileIdLike = "46020%";
        //在将mobileIdLike修改为35%
        findRecord( startTime,  endTime,  mobileIdLike);
        mobileIdLike = "35%";
        findRecord( startTime,  endTime,  mobileIdLike);


    }
    public void findRecord(String startTime, String endTime, String mobileIdLike) {

        ArrayList<Map<String, Object>> listMap = oracleDao.selectTrainInfo(null);
        String imeisTr = "";
        String s = "";
        for (Map map : listMap) {
            String msisdn = null == map.get("MSISDN") ? "" : map.get("MSISDN").toString();
            String imsi = null == map.get("IMSI") ? "" : map.get("IMSI").toString();
            String ctcsid = null == map.get("CTCSID") ? "" : map.get("CTCSID").toString();
            String loconnum = null == map.get("LOCONUM") ? "" : map.get("LOCONUM").toString();

            if (msisdn != "") {
                List<String> list = oracleDao.selectMobileIdByAbisAndAmon(msisdn, startTime, endTime, mobileIdLike);
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    imeisTr = iterator.next().toString();
                }
                //imeis号大于0,说明找到了
                if (imeisTr.length() > 0) {
                    //如果以前没有相等的IMESI号，就修改，顺带着看一下列车号是否为空，为空的话修改
                    if (!imeisTr.equals(imsi)) {
                        oracleDao.updateImsiByMsi(imeisTr, msisdn);
                        if (loconnum ==  "") {
                            String loconum = CtcsidToJiCheHao.ctcsIdToJiCheHao(ctcsid);
                            oracleDao.updateLocon(loconum, msisdn.toString());
                        }
                    }
                }

            }
        }


    }
}