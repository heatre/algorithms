package com.swin.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Swin on 2016/8/31.
 */
public class StringUtils {
    /**
     * 去掉字符串中空格，水平制表符，回车、换行符
     *
     * @param s 传入字符串
     * @return 去掉空格等后的字符串
     */
    public static String replaceBlank(String s) {
        String rnt = null;
        if(s!=null){
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(s);
            rnt  = m.replaceAll("");
        }
        return rnt;
    }
}
