package com.vosouq.scoringcommunicator.infrastructures.utils;

import com.vosouq.scoringcommunicator.infrastructures.Constants;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static com.vosouq.scoringcommunicator.infrastructures.Constants.ONE_INT;
import static com.vosouq.scoringcommunicator.infrastructures.Constants.ZERO_INT;

public class CreditScoringUtil {

    public static Date getDate(int year, int month, int day) {
        return java.sql.Date.valueOf(LocalDate.of(year, month, day));
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static boolean isTrue(int i) {
        return i == ONE_INT;
    }

    public static int getIntValue(boolean b) {
        return b ? ONE_INT : ZERO_INT;
    }

    public static Date calcLastTimeOfFutureDateByDelta(Date d, int deltaDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, deltaDay);
        c.set(Calendar.HOUR, Constants.TWENTY_THREE);
        c.set(Calendar.MINUTE, Constants.FIFTY_NINE);
        c.set(Calendar.SECOND, Constants.FIFTY_NINE);
        return c.getTime();
    }

}
