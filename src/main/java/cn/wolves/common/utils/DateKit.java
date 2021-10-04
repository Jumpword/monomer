package cn.wolves.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jumping
 */
public class DateKit {

    public static Date parse(String dateStr) {
        if (ChgDataKit.isEmptyStr(dateStr)) {
            return null;
        } else {
            dateStr = dateStr.replace("-", "").replace("/", "").replace(" ", "").replace(":", "");
        }
        SimpleDateFormat sdf = null;
        Date date = null;
        try {
            if (dateStr.length() >= 14) {
                sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                date = sdf.parse(dateStr);
                return new Date(date.getTime());
            }

        } catch (Exception e) {
        }

        try {
            if (dateStr.length() >= 12) {
                sdf = new SimpleDateFormat("yyyyMMddHHmm");
                date = sdf.parse(dateStr);
                return new Date(date.getTime());
            }

        } catch (Exception e) {
        }
        try {
            if (dateStr.length() >= 10) {
                sdf = new SimpleDateFormat("yyyyMMddHH");
                date = sdf.parse(dateStr);
                return new Date(date.getTime());
            }

        } catch (Exception e) {
        }
        try {
            if (dateStr.length() >= 8) {
                sdf = new SimpleDateFormat("yyyyMMdd");
                date = sdf.parse(dateStr);
                return new Date(date.getTime());
            }

        } catch (Exception e) {
        }
        return null;
    }

}
