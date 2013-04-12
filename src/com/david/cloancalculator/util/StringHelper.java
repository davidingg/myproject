package com.david.cloancalculator.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
public class StringHelper {
    public StringHelper()
    {

    }

    public static String Format(String strFormat)
    {
        return strFormat;
    }

    public static String Format(String strFormat, Object obj1)
    {
        return String.format(strFormat, obj1);
    }

    public static String Format(String strFormat, Object obj1, Object obj2)
    {
        return String.format(strFormat, obj1, obj2);
    }

    public static String Format(String strFormat, Object obj1, Object obj2, Object obj3)
    {
        return String.format(strFormat, obj1, obj2, obj3);
    }

    public static String Format(String strFormat, Object obj1, Object obj2, Object obj3, Object obj4)
    {
        return String.format(strFormat, obj1, obj2, obj3, obj4);
    }

    public static String Format(String strFormat, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5)
    {
        return String.format(strFormat, obj1, obj2, obj3, obj4, obj5);
    }

    public static String Format(String strFormat, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6)
    {
        return String.format(strFormat, obj1, obj2, obj3, obj4, obj5, obj6);
    }

    public static String Format(String strFormat, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7)
    {
        return String.format(strFormat, obj1, obj2, obj3, obj4, obj5, obj6, obj7);
    }

    public static String Format(String strFormat, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8)
    {
        return String.format(strFormat, obj1, obj2, obj3, obj4, obj5, obj6, obj7, obj8);
    }

    public static String Format(String strFormat, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9)
    {
        return String.format(strFormat, obj1, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9);
    }

    public static String Format(String strFormat, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Object obj9, Object obj10)
    {
        return String.format(strFormat, obj1, obj2, obj3, obj4, obj5, obj6, obj7, obj8, obj9, obj10);
    }


    public static String Format(String strFormat, Object[] arrList)
    {
        int nArrCount = arrList.length;

        switch (nArrCount)
        {
        case 0:
            return strFormat;
        case 1:
            return String.format(strFormat, arrList[0]);
        case 2:
            return String.format(strFormat, arrList[0], arrList[1]);
        case 3:
            return String.format(strFormat, arrList[0], arrList[1], arrList[2]);
        case 4:
            return String.format(strFormat, arrList[0], arrList[1], arrList[2],
                                 arrList[3]);
        case 5:
            return String.format(strFormat, arrList[0], arrList[1], arrList[2],
                                 arrList[3]
                                 , arrList[4]);
        case 6:
            return String.format(strFormat, arrList[0], arrList[1], arrList[2],
                                 arrList[3]
                                 , arrList[4], arrList[5]);
        case 7:
            return String.format(strFormat, arrList[0], arrList[1], arrList[2],
                                 arrList[3]
                                 , arrList[4], arrList[5], arrList[6]);
        case 8:
            return String.format(strFormat, arrList[0], arrList[1], arrList[2],
                                 arrList[3]
                                 , arrList[4], arrList[5], arrList[6],
                                 arrList[7]);
        case 9:
            return String.format(strFormat, arrList[0], arrList[1], arrList[2],
                                 arrList[3]
                                 , arrList[4], arrList[5], arrList[6],
                                 arrList[7], arrList[8]);
        case 10:
            return String.format(strFormat, arrList[0], arrList[1], arrList[2],
                                 arrList[3]
                                 , arrList[4], arrList[5], arrList[6],
                                 arrList[7], arrList[8], arrList[9]);

        }

        return strFormat;
    }

    public static int StringLength(String strValue)
    {
        if (strValue == null)
        {
            return 0;
        }
        return strValue.length();
    }

    public static int Length(String strValue)
    {
        if (strValue == null)
        {
            return 0;
        }
        return strValue.trim().length();
    }

    /**
     * 是否为空�?
     * @param strValue
     * @return
     */
    public static boolean IsNullOrEmpty(String strValue)
    {
    	return (Length(strValue) == 0) || "null".equals(strValue);
    }

    /**
     * 对比两个字符�?
     *
     * @param strValue1 String
     * @param strValue2 String
     * @param bIgnoreCase boolean
     * @return int
     */
    public static int Compare(String strValue1, String strValue2, boolean bIgnoreCase)
    {
        if (strValue1 == null || strValue2 == null)
        {
            return -1;
        }

        if (bIgnoreCase)
        {
            return strValue1.compareToIgnoreCase(strValue2);
        }
        else
        {
            return strValue1.compareTo(strValue2);
        }
    }
    
  
    public static String[] Split(String strValue,char chSeperator)
    {
        if(StringHelper.Length(strValue) == 0)
            return null;

        ArrayList arrList = new ArrayList();
        while (true)
        {
            int nPos = strValue.indexOf(chSeperator);
            if (nPos != -1)
            {
                String strPartA = strValue.substring(0, nPos);
                arrList.add(strPartA);
                strValue = strValue.substring(nPos + 1);
            }
            else
            {
                arrList.add(strValue);
                break;
            }
        }

        String[] strList = new String[arrList.size()];
        arrList.toArray(strList);
        return strList;

    }

    /**
     * 剔除字符串左侧空�?
     * @param strValue
     * @return
     */
    public static String TrimLeft(String strValue)
    {
    	return TrimLeft(strValue,' ');
    }
    
    /**
     * 剔除字符串左侧指定字�?
     * @param strValue
     * @param ch
     * @return
     */
    public static String TrimLeft(String strValue,char ch)
    {
    	while(strValue.length()>0)
		{
			if(strValue.charAt(0)==ch)
			{
				strValue = strValue.substring(1);
			}
			else
				break;
		}
		return strValue;
    }
    
    public static String getValue(boolean obj){
		return String.valueOf(obj);
	}
    
	public static String getValue(Object obj){
		return getValue(obj, "");
	}
	
	public static String getValue(Object obj,String defval){
		if(obj == null || "null".equals(String.valueOf(obj)) || "".equals(String.valueOf(obj))){
			return defval ;
		}else{
			return String.valueOf(obj).trim();
		}
	}
	
	public static boolean getValue(Object obj,boolean defval){
		if(obj == null || "null".equals(String.valueOf(obj)) || "".equals(String.valueOf(obj))){
			return defval ;
		}else{
			return Boolean.parseBoolean((String)obj);
		}
	}
	
	/**
	 * @param strValue
	 * @param bDefault
	 * @return
	 */
	public static boolean GetValue(String strValue, boolean bDefault)
	{
		try
		{
			return Boolean.parseBoolean(strValue);
		}
		catch (Exception ex)
		{
			return bDefault;
		}

	}

	/**
	 * @param strValue
	 * @param fDefault
	 * @return
	 */
	public static double GetValue(String strValue, double fDefault)
	{
		try
		{
			if (strValue.indexOf("%") == (strValue.length() - 1))
			{
				strValue = strValue.replaceAll("%", "");
				return Double.parseDouble(strValue) / 100;
			}
			else
				return Double.parseDouble(strValue);
		}
		catch (Exception ex)
		{
			return fDefault;
		}
	}

	public static float GetValue(String strValue, float fDefault)
	{
		try
		{
			return Float.parseFloat(strValue);
		}
		catch (Exception ex)
		{
			return fDefault;
		}
	}

	// / <summary>
	// / 获取数字
	// / </summary>
	// / <param name="strValue"></param>
	// / <param name="bDefault"></param>
	// / <returns></returns>
	public static int GetValue(String strValue, int nDefault)
	{
		try
		{
			return Integer.parseInt(strValue);
		}
		catch (Exception ex)
		{
			return nDefault;
		}
	}
	
	public static boolean isChinese(char c) {  
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A 
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        
        return false;  
  
    }  
	
	/**
	 * @param arrays
	 * @param split
	 * @return
	 */
	public static String arrayToString(String[] arrays,String strSeprate,String strSplit){
		
		return arrayToString(arrays,strSeprate, strSplit, 0);
	}
	
	
	/**
	 * @param arrays 數組
	 * @param strSeprate 元素外面包得分隔�?
	 * @param strSplit 元素之前的分隔符
	 * @param begin  起始位置
	 * @return
	 */
	public static String arrayToString(String[] arrays,String strSeprate,String strSplit,int begin){
		StringBuffer sb = new StringBuffer();
		for (int i = begin; i < arrays.length; i++) {
			sb.append(i == begin ? "" : strSplit);
			sb.append(strSeprate);
			sb.append(arrays[i]);
			sb.append(strSeprate);
		}
		return sb.toString();
	}
	
	public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }  
        }
        return false;
    }
	
	public static boolean isDate(String strDate){
		return isDate(strDate, null);
	}
	
	public static boolean isDate(String strDate,String format){
		try{
			if(format == null || IsNullOrEmpty(format))
				format = "yyyy/MM/dd";
			strDate = strDate.trim();
			String oldDate = strDate;
			SimpleDateFormat df = new SimpleDateFormat(format);
			Date date = df.parse(strDate);
			return oldDate.equals(df.format(date));
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static boolean isInt(String value){
		return StringHelper.isNumber(value,"dot:0");
	}
	
	/**
	 * @param srtParam
	 * @return 是不是大�?的整�?
	 */
	public static boolean isIntGZ(String value){
		return StringHelper.isNumber(value,"dot:0,min:'0'");
	}
	
	/**
	 * @param srtParam
	 * @return 是不是大于等�?的整�?
	 */
	public static boolean isIntGEZ(String value){
		return StringHelper.isNumber(value,"dot:0,min:0");
	}
	
	/**
	 * @param value
	 * @param strConfig
	 * @return 
	 */
	public static boolean isNumber(String value,String strConfig){
		if(StringHelper.IsNullOrEmpty(value))return false;
		Object dot = null;
		Object min = null;
		Object max = null;
		
		if(!StringHelper.IsNullOrEmpty(strConfig)){
			BaseDataEntity config = BaseDataEntity.getConfig(strConfig);
			if(config.ContainesParam("dot"))
				dot = config.get("dot");
			if(config.ContainesParam("min"))
				min = config.get("min");
			if(config.ContainesParam("max"))
				max = config.get("max");
		}
		
		
		boolean ret = Pattern.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?$").matcher(value).matches();
		if(ret && dot instanceof Integer && ((Integer)dot) >=0){
			if(((Integer)dot) == 0){
				ret = Pattern.compile("^-?\\d+$").matcher(value).matches();
			}else{
				ret = Pattern.compile("^-?\\d+(\\.\\d{1,"+dot+"})?$").matcher(value).matches();
			}
		}
		
		if(ret && !StringHelper.IsNullOrEmpty(String.valueOf(min))){
			boolean bminself = true;
			if(min instanceof String){
				min = Double.parseDouble(String.valueOf(min));
				bminself = false;
			}
			
			if(bminself)
				ret =  Double.parseDouble(value) >=  Double.parseDouble(String.valueOf(min));
			else
				ret = Double.parseDouble(value) >  Double.parseDouble(String.valueOf(min));
		}
		
		if(ret && !StringHelper.IsNullOrEmpty(String.valueOf(max))){
			boolean bmaxself = true;
			if(ret && max instanceof String){
				max = Double.parseDouble(String.valueOf(max));
				bmaxself = false;
			}
			
			if(bmaxself)
				ret = Double.parseDouble(value) <=Double.parseDouble(String.valueOf(max));
			else
				ret = Double.parseDouble(value) < Double.parseDouble(String.valueOf(max));
		}
		
		return ret;
	}
	
	public static String encode(String strValue){
		if(StringHelper.IsNullOrEmpty(strValue))return "";
		try {
			return URLEncoder.encode(strValue,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return strValue;
		}
	}
	
	public static String decode(String strValue){
		if(StringHelper.IsNullOrEmpty(strValue))return "";
		try {
			return URLDecoder.decode(strValue,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return strValue;
		}
	}
}
