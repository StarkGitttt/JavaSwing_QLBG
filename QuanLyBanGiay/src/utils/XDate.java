/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author DELL
 */
public class XDate {
    static SimpleDateFormat formatter = new SimpleDateFormat();
    public static Date toDate(String date, String pattern) {
        try {
            formatter.applyPattern(pattern);
            return formatter.parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String toString(Date date, String pattern) {
        formatter.applyPattern(pattern);
        if(date == null) {
            date = XDate.now();
        }
        return formatter.format(date);
    }
    public static Date now() {
        return new Date();
    }
    public static Date addDay(Date date, long days) {
        date.setTime(date.getTime() + days*24*60*60*1000);
        return date;
    }
    public static java.sql.Date convertDate(String date, String pattern) {
        Date dateJava = XDate.toDate(date, pattern);
        java.sql.Date sqlDate = new java.sql.Date(dateJava.getTime());
        return sqlDate;
    }
    public static java.sql.Timestamp convertTimeStamp() {
        java.util.Date date = new java.util.Date();
        java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());
        return sqlTime;
    }
    public static long differentDays(String dateCreat, String openDay) {
        long totalDays = 0;
        Date parseDateCreat = XDate.toDate(dateCreat, "dd-MM-yyyy");
        Date parseOpenDay = XDate.toDate(openDay, "dd-MM-yyyy");
        long valueDateCreat = parseDateCreat.getTime();
        long valueOpenDay = parseOpenDay.getTime();
        long diff = (valueOpenDay - valueDateCreat);
        totalDays = diff / (24*60*60*1000);
        return totalDays;
    }
    public static void main(String[] args) {
//        java.sql.Timestamp st;
//        st = XDate.convertTimeStamp();
//        System.out.println(""+ st);
        System.out.println("" + XDate.differentDays("12-12-2021", "12-12-2021"));
    }
}
