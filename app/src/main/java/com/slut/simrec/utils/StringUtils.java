package com.slut.simrec.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 七月在线科技 on 2016/12/14.
 */

public class StringUtils {

    public static boolean isURL(String text) {
        Pattern pattern = Pattern
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        return pattern.matcher(text).matches();
    }

    public static boolean isInteger(String input){
        Matcher mer = Pattern.compile("^[0-9]+$").matcher(input);
        return mer.find();
    }

}
