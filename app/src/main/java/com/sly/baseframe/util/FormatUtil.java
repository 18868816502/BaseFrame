package com.sly.baseframe.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {
    public static String encodeHeadInfo(String headInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, length = headInfo.length(); i < length; i++) {
            char c = headInfo.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", (int) c));
            } else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }

    public static String formatToSepara(double data) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(data);
    }

    public static String formatToSeparaYuan(double data) {
        DecimalFormat df = new DecimalFormat("#,###0.00");
        return df.format(data);
    }

    public static String formatToFull(double data) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(data);
    }

    public static String formatToInteger(double data) {
        DecimalFormat df = new DecimalFormat("#0");
        return df.format(data);
    }

    public static String formatDecimalsTwo(double data) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(data);
    }

    public static String formatDecimalsEight(double data) {
        if(data==0)
            return "0";
        DecimalFormat df = new DecimalFormat("#0.00000000");
        String str=df.format(data);
        //去除小数点后面多余的值
        String result=BigDecimal.valueOf(Double.parseDouble(str))
                .stripTrailingZeros().toPlainString();
        return result;
    }

    public static String formatMonthAndDay(long data) {
        if (data > 0) {
            SimpleDateFormat form = new SimpleDateFormat("MM-dd");
            Date date = new Date();
            form.format(date);
            String hh = form.format(data);
            return hh;
        } else {
            return "";
        }
    }
    public static String formatMonthAndDay2(long data) {
        if (data > 0) {
            SimpleDateFormat form = new SimpleDateFormat("MM/dd");
            Date date = new Date();
            form.format(date);
            String hh = form.format(data);
            return hh;
        } else {
            return "";
        }
    }

    public static String formatHourAndMinute(long data) {
        if (data > 0) {
            SimpleDateFormat form = new SimpleDateFormat("HH:mm");
            Date date = new Date();
            form.format(date);
            String hh = form.format(data);
            return hh;
        } else {
            return "";
        }
    }

    public static String formatToDayHourMinuteSec(long data) {
        if (data > 0) {
//            long day = data  / 3600 / 24;
            long day = data / 3600 / 24 / 1000;
            SimpleDateFormat form = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            form.format(date);
            String hh = form.format(data);
            if (day > 0) {
                hh = day + "天" + hh;
            }
            return hh;
        } else {
            return "";
        }
    }

    public static String formatToOffice(long data) {
        if (data > 0) {
            long day = data / 1000 / 3600 / 24;
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            form.format(date);
            String hh = form.format(data);
            return hh;
        } else {
            return "";
        }
    }
    public static String formatToOffice2(long data) {
        if (data > 0) {
            long day = data / 1000 / 3600 / 24;
            SimpleDateFormat form = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            form.format(date);
            String hh = form.format(data);
            return hh;
        } else {
            return "";
        }
    }
    public static String formatToOffice3(long data) {
        if (data > 0) {
            long day = data / 1000 / 3600 / 24;
            SimpleDateFormat form = new SimpleDateFormat("MM-dd HH:mm:ss");
            Date date = new Date();
            form.format(date);
            String hh = form.format(data);
            return hh;
        } else {
            return "";
        }
    }

    public static String formatToOfficeWithoutHour(long data) {
        if (data > 0) {
            long day = data / 1000 / 3600 / 24;
            SimpleDateFormat form = new SimpleDateFormat("yyyy年MM月dd日");
            Date date = new Date();
            form.format(date);
            String hh = form.format(data);
            return hh;
        } else {
            return "";
        }
    }

    public static String getEndFour(String data) {
        if (data.length() >= 4) {
            return data.substring(data.length() - 4);
        } else {
            return "";
        }
    }

    public static String secondsToLeftTime(long totalSeconds) {
        if (totalSeconds > 0) {
            long second = totalSeconds;
            long minute = 0;
            long hour = 0;
            long day = 0;
            if (second >= 60) {
                minute = second / 60;
                second = second % 60;
                if (minute >= 60) {
                    hour = minute / 60;
                    minute = minute % 60;
                    if (hour > 24) {
                        day = hour / 24;
                        hour = hour % 24;
                    }
                }
            }
            String hourStr = String.valueOf(hour);
            String minuteStr = String.valueOf(minute);
            String secondStr = String.valueOf(second);
            if (hourStr.length() == 1) {
                hourStr = "0" + hourStr;
            }
            if (minuteStr.length() == 1) {
                minuteStr = "0" + minuteStr;
            }
            if (secondStr.length() == 1) {
                secondStr = "0" + secondStr;
            }
            return hourStr + ":" + minuteStr + ":" + secondStr;
        }
        return "00:00:00";
    }

}
