package com.swpu.uchain.community.util;

import java.util.Random;

/**
 * @ClassName KeyUtil
 * @Description 生成签到表主键编号
 * @Author LZH
 */
public class KeyUtil {

    /**
     * 生成主键
     * 时间+2位随机数
     * @return
     */
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(90)+10;
        return System.currentTimeMillis()+String.valueOf(number);
    }






}
