/**
 * 
 */
package com.david.cloancalculator.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Date;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * @author david
 * 項目公用方法
 * 2011/10/8 下午4:02:35
 */
public class Utils {
	public static final String TAG_RETDATA = "PAGE_RETDATA";//後臺頁面返回結果
	public static final String TAG_FORM = "PAGE_FORM";
	/**
	 * @author david
	 * 判断是否为数字
	 */
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	/**
     * 四捨五入.
     * 
     * @param var 傳入值.
     * @param dot 小數幾位.
     * @return 回傳值.
     */
    public double round(double var, int dot) {
        return Math.round(var * Math.pow(10, dot)) / Math.pow(10, dot);
    }
    public final static int YEAR = Calendar.YEAR;
    public final static int MONTH = Calendar.MONTH;
    public final static int DATE = Calendar.DATE;
	public double diffDate(String str,String str1,String str2) throws ParseException {
		double result = 0;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");  				
			Date date1 = formatter.parse(str1);
			Date date2 = formatter.parse(str2);
			if(str.equals("HH")) {
				result = (date2.getTime() - date1.getTime()) / (1000 * 3600.0);
			}
			if(str.equals("MM")){
				Calendar c1 = Calendar.getInstance();
				Calendar c2 = Calendar.getInstance();

				c1.setTime(date1);
				c2.setTime(date2);
				result = (c2.get(Calendar.YEAR)*12+c2.get(Calendar.MONTH)) - (c1.get(Calendar.YEAR)*12+c1.get(Calendar.MONTH));
				return result == 0 ? 1 : Math.abs(result);
			}		
		return result;
	}
	
	 /**
     * 格式化日期(yyyy/MM/dd).
     * 
     * @param date 傳入的日期
     * @return 格式化後的日期
     */
    public String formatDate(Date date) {
        String result = "";
        if (date != null) {
            result = new SimpleDateFormat("yyyy/MM/dd").format(date);
            if (result.equals("1900/01/01")) {
                result = "";
            }
        }
        return result;
    }
	
	public String formatDate(Date date, int field, int dateDiff) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, dateDiff);
        return this.formatDate(cal.getTime());
    }
}