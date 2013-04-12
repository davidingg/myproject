/**
 * 
 */
package com.david.cloancalculator.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author david
 * 集合數據
 * 2011/9/28 上午8:37:50
 */
public class BaseDataEntity implements Serializable {

	private Hashtable paramList = null;
	protected Utils utils = null;
	// protected DataEntityState dataEntityState = null;

	public BaseDataEntity()
	{
		// dataEntityState = OnCreateStateObject();
	}

	/**
	 * 重置数据实体
	 */
	public void Reset()
	{
		if (paramList != null)
			paramList.clear();

		OnReset();
	}

	/**
	 * 在重置实体中调用
	 */
	protected void OnReset()
	{

	}
	
	
	/**
	 * 从MAP中初始化实体对象
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public boolean FromMap(Map map,boolean bReset)
	{		
		if(bReset)
			Reset();

		if (map == null)
			return false;

		for (Object objKey : map.keySet())
		{
			Object objValue = map.get(objKey);
			if (objValue == null)
				continue;
			
			this.SetParamValue(objKey.toString(), objValue);
		}

		return OnFromMap(map);
	}
	
	protected boolean OnFromMap(Map map) 
	{
		return true;
	}

	public void FillMap(Map map)
	{
		if (paramList != null)
		{
			Enumeration keys = paramList.keys();
			while (keys.hasMoreElements())
			{
				Object objKey = keys.nextElement();
				Object objValue = paramList.get(objKey);
				map.put(objKey, objValue);
			}
		}
	}
	
	/**
	 * 从JSON 字符串中导出实体对象
	 * @param jsonObject
	 * @return
	 */
	public static BaseDataEntity FromJSONString(String strJSONString)
	{
		JSONObject jsonObject = JSONObject.fromString(strJSONString);
		return FromJSONObject(jsonObject);
	}
	
	/**
	 * 从JSONObject导出实体对象
	 * @param jsonObject
	 * @return
	 */
	public static BaseDataEntity FromJSONObject(JSONObject jsonObject)
	{
		BaseDataEntity baseDataEntity = new BaseDataEntity();
		java.util.Iterator it=jsonObject.keys();
		while(it.hasNext())
		{
			Object objKey = it.next();
			Object objValue = jsonObject.get(objKey.toString());
			if(objValue instanceof JSONArray)
			{
				JSONArray ay = ((JSONArray)objValue);
				if(ay.length() == 0)continue;
				if(ay.get(0) instanceof JSONObject){
					baseDataEntity.SetParamValue(objKey.toString(), BaseDataEntities.FromJSONArray((JSONArray)objValue));
				}else{
					List list = new ArrayList();
					for (int i = 0; i < ay.length(); i++) {
						list.add(ay.getString(i));
					}
					baseDataEntity.SetParamValue(objKey.toString(), list.toArray(new String[]{}));
				}
			}
			else
			{
				if(objValue instanceof String){
					baseDataEntity.SetParamValue(objKey.toString(), StringHelper.getValue(objValue).replaceAll("<rn>", "\r\n").replaceAll("<dyh>", "'"));
				}else{
					baseDataEntity.SetParamValue(objKey.toString(), objValue);
				}
			}
		}
		return baseDataEntity;
	}
	
	
	
	/**
	 * 获取参数�?
	 * 
	 * @param strParamName
	 * @return
	 */
	public Object gpov(String strParamName){
		return GetParamValue(strParamName);
	}
	
	public BaseDataEntity gpde(String strParamName){
		Object obj = this.gpov(strParamName);
		if(obj == null) return null;
		if(obj instanceof BaseDataEntity) return (BaseDataEntity)obj;
		return null;
	}
	
	public BaseDataEntities gpdes(String strParamName){
		Object obj = this.gpov(strParamName);
		if(obj == null) return null;
		if(obj instanceof BaseDataEntities) return (BaseDataEntities)obj;
		return null;
	}
	
	public ResultSet gprs(String strParamName){
		Object obj = this.gpov(strParamName);
		if(obj == null) return null;
		if(obj instanceof ResultSet) return (ResultSet)obj;
		return null;
	}
	
	public Object GetParamValue(String strParamName)
	{
		String strParamName2 = strParamName.toUpperCase();
		if (paramList == null)
		{
			return null;
		}

		//Hashtable paramList = form.getParamList();
		if (paramList.containsKey(strParamName2))
		{
			Object objValue = paramList.get(strParamName2);
			return objValue;
		}
		
		else
		{
			return null;
		}
	}
	
	/**
	 * 此方法为脚本引擎使用
	 * 
	 * @param strParamName
	 * @return
	 */
	public Object get(String strParamName)
	{
		return this.GetParamValue(strParamName);
	}
	

	/**
	 * 设置参数�?
	 * 
	 * @param strParamName
	 * @param objParamValue
	 */
	public void spov(String strParamName, Object paramvalue){
		SetParamValue(strParamName, paramvalue);
	}
	public void SetParamValue(String strParamName, Object objParamValue)
	{
		if (StringHelper.IsNullOrEmpty(strParamName))
			return;

		strParamName = strParamName.toUpperCase();
		if (objParamValue == null)
		{
			if (paramList != null)
			{
				if (paramList.containsKey(strParamName))
					paramList.remove(strParamName);

			}
		}
		else
		{
			if (paramList == null)
				paramList = new Hashtable();

			paramList.put(strParamName, objParamValue);
		}
	}
	
	public void spiv(String strParamName, int paramvalue){
		SetParamValue(strParamName, paramvalue);
	}
	public void SetParamValue(String strParamName, int paramvalue)
	{
		SetParamValue(strParamName,(Object)paramvalue);
	}
	
	public void spbv(String strParamName, boolean paramvalue){
		SetParamValue(strParamName, paramvalue);
	}
	public void SetParamValue(String strParamName, boolean paramvalue)
	{
		SetParamValue(strParamName,(Object)paramvalue);
	}
	
	public void spsv(String strParamName, String paramvalue){
		SetParamValue(strParamName, paramvalue);
	}
	public void SetParamValue(String strParamName, String paramvalue)
	{
		SetParamValue(strParamName,(Object)paramvalue);
	}
	
	public void spdv(String strParamName, double paramvalue){
		SetParamValue(strParamName, paramvalue);
	}
	public void SetParamValue(String strParamName, double paramvalue)
	{
		SetParamValue(strParamName,(Object)paramvalue);
	}
	
	public void splv(String strParamName, long paramvalue){
		SetParamValue(strParamName, paramvalue);
	}
	public void SetParamValue(String strParamName, long paramvalue)
	{
		SetParamValue(strParamName,(Object)paramvalue);
	}
	
	public int gpiv(String strParamName){
		return this.gpiv(strParamName,0);
	}
	
	public int gpiv(String strParamName, int nDefault){
		return this.GetParamIntValue(strParamName,nDefault);
	}
	
	public int[] gpivs(String strParamName){
		return this.GetParamIntValues(strParamName);
	}
	
	public int[] GetParamIntValues(String strParamName){
		int[] result = new int[0];
        String[] temp = gpsvs(strParamName);
        if (temp != null && temp.length != 0) {
            result = new int[temp.length];
            for (int i = 0; i < temp.length; i++) {
                try {
                    result[i] = new Integer(temp[i]).intValue();
                } catch (Exception ex) {
                    result[i] = 0;
                }
            }
        }
        return result;
	}
	
	/**
	 * 获取参数的整数型�?
	 * 
	 * @param strParamName
	 * @param nDefault
	 * @return
	 */
	public int GetParamIntValue(String strParamName, int nDefault)
	{
		Object objValue = GetParamValue(strParamName);
		if (StringHelper.IsNullOrEmpty(StringHelper.getValue(objValue)))
		{
			return nDefault;
		}
		try
		{
			return Integer.parseInt(objValue.toString());
		}
		catch (Exception ex)
		{
			return nDefault;
		}
	}

	/**
	 * 获取参数浮点�?
	 * 
	 * @param strParamName
	 * @param fDefault
	 * @return
	 */
	public float GetParamFloatValue(String strParamName, float fDefault)
	{
		Object objValue = GetParamValue(strParamName);
		if (StringHelper.IsNullOrEmpty(StringHelper.getValue(objValue)))
		{
			return fDefault;
		}
		try
		{
			return Float.parseFloat(objValue.toString());
		}
		catch (Exception ex)
		{
			return fDefault;
		}
	}
	
	public BigDecimal GetParamBigDecimalValue(String strParamName, BigDecimal fDefault)
	{
		Object objValue = GetParamValue(strParamName);
		if (StringHelper.IsNullOrEmpty(StringHelper.getValue(objValue)))
		{
			return fDefault;
		}
		try
		{
			return BigDecimal.valueOf(Double.parseDouble(objValue.toString()));
		}
		catch (Exception ex)
		{
			return fDefault;
		}
	}

	public double gpdv(String strParamName){
		return this.GetParamDoubleValue(strParamName,0.0);
	}
	
	public double gpdv(String strParamName,int dot){
		return Math.round(this.gpdv(strParamName) * Math.pow(10, dot)) / Math.pow(10, dot);
	}
	
	public double[] gpdvs(String strParamName){
		return this.GetParamDoubleValues(strParamName);
	}
	
	public double[] GetParamDoubleValues(String strParamName){
		 double[] result = new double[0];
	        String[] temp = this.gpsvs(strParamName);
	        if (temp != null && temp.length != 0) {
	            result = new double[temp.length];
	            for (int i = 0; i < temp.length; i++) {
	                try {
	                    result[i] = new Double(temp[i]).doubleValue();
	                } catch (Exception ex) {
	                    result[i] = 0;
	                }
	            }
	        }
	        return result;
	}
	
	
	/**
	 * 获取参数Double�?
	 * 
	 * @param strParamName
	 * @param fDefault
	 * @return
	 */
	public double GetParamDoubleValue(String strParamName, double fDefault)
	{
		Object objValue = GetParamValue(strParamName);
		if (StringHelper.IsNullOrEmpty(StringHelper.getValue(objValue)))
		{
			return fDefault;
		}
		try
		{
			return Double.parseDouble(objValue.toString());
		}
		catch (Exception ex)
		{
			return fDefault;
		}
	}

	public long GetParamLongValue(String strParamName, long nDefault)
	{
		Object objValue = GetParamValue(strParamName);
		if (StringHelper.IsNullOrEmpty(StringHelper.getValue(objValue)))
		{
			return nDefault;
		}
		try
		{
			return Long.parseLong(objValue.toString());
		}
		catch (Exception ex)
		{
			return nDefault;
		}
	}
	
	public boolean gpbv(String strParamName, boolean bDefault){
		return this.GetParamBoolValue(strParamName,bDefault);
	}
	
	/**
	 * 获取参数的布尔型�?
	 * 
	 * @param strParamName
	 * @param bDefault
	 * @return
	 */
	public boolean GetParamBoolValue(String strParamName, boolean bDefault)
	{
		Object objValue = GetParamValue(strParamName);
		if (StringHelper.IsNullOrEmpty(StringHelper.getValue(objValue)))
		{
			return bDefault;
		}
		try
		{
			return Boolean.parseBoolean(objValue.toString());
		}
		catch (Exception ex)
		{
			return bDefault;
		}
	}
	
	/**
	 * @param strParamName
	 * @return
	 * 获取字符型参�?簡寫
	 */
	public String gpsv(String strParamName){
		return GetParamStringValue(strParamName,"");
	}
	
	/**
	 * @param strParamName
	 * @param strDefault
	 * @return
	 * 获取字符型参�?簡寫
	 */
	public String gpsv(String strParamName,String strDefault){
		return GetParamStringValue(strParamName,strDefault);
	}
	
	/**
	 * 获取字符型参�?
	 * 
	 * @param strParamName
	 * @param strDefault
	 * @return
	 */
	public String GetParamStringValue(String strParamName, String strDefault)
	{
		Object objValue = GetParamValue(strParamName);
		if (StringHelper.IsNullOrEmpty(StringHelper.getValue(objValue)))
		{
			return strDefault;
		}
		try
		{
			if(objValue instanceof String[] && ((String[])objValue).length > 0){
				return ((String[])objValue)[0].trim(); 
			}else if(objValue instanceof Date)
				return GetFormatDate(objValue);
			return objValue.toString().trim();
		}
		catch (Exception ex)
		{
			return strDefault;
		}
	}
	
	public BaseDataEntities getrecords(String strParamName){
		if(this.ContainesParam(strParamName+"_temprecords"))
			return (BaseDataEntities)this.GetParamValue(strParamName+"_temprecords");
		BaseDataEntities records = new BaseDataEntities();
		String arys[] = this.gpsvs(strParamName);
		if(arys != null && arys.length >0){
			for (int i = 0; i < arys.length; i++) {
				BaseDataEntity record = BaseDataEntity.FromJSONString(arys[i].replaceAll("\r\n","<rn>").replaceAll("'","<dyh>"));
				records.add(record);
			}
		}
		this.SetParamValue(strParamName+"_temprecords",records);
		return records ;
	}
	
	public BaseDataEntity getrecord(String strParamName){
		if(this.ContainesParam(strParamName+"_temprecord"))
			return (BaseDataEntity)this.GetParamValue(strParamName+"_temprecord");
		BaseDataEntity record = null;
		String strValue = this.gpsv(strParamName);
		if(!StringHelper.IsNullOrEmpty(strValue)){
			record = BaseDataEntity.FromJSONString(strValue);
		}else{
			record = new BaseDataEntity();
		}
		this.SetParamValue(strParamName+"_temprecord",record);
		return record;
	}
	/**
	 * @param strParamName
	 * @return
	 * 获取字符型参�?[数组] 簡寫
	 */
	public String[] gpsvs(String strParamName){
		return GetParamStringValues(strParamName,new String[0]);
	}
	
	/**
	 * @param strParamName
	 * @param strDefault
	 * @return
	 * 获取字符型参�?[数组]簡寫
	 */
	public String[] gpsvs(String strParamName, String[] strDefault){
		return GetParamStringValues(strParamName,strDefault);
	}
	
	/**
	 * 获取字符型参�?[数组]
	 * @param strParamName
	 * @param strDefault
	 * @return
	 */
	public String[] GetParamStringValues(String strParamName, String[] strDefault)
	{
		if(!this.ContainesParam(strParamName) && paramList != null && strParamName.startsWith("^")) {//
			List list = new ArrayList();
			String strRealParamName = strParamName.substring(1,strParamName.length()).toUpperCase();
			Object objValue = null;
			Enumeration keys = paramList.keys();
			while (keys.hasMoreElements())
			{				
				Object objKey = keys.nextElement();
				if(objKey instanceof String && objKey != null){
					String key = (String)objKey;
					if(key.startsWith(strRealParamName)){
						objValue = paramList.get(objKey);	
						if(objValue instanceof String){
							list.add((String)objValue);
						}
					}
				}
			}
			
			String[] ay = new String[list.size()];
			list.toArray(ay);
			this.SetParamValue(strParamName,ay);
			return ay;
			
		} else {
			Object objValue = GetParamValue(strParamName);
			try
			{
				if(objValue instanceof String[])
					return (String[])objValue;
				else if(objValue instanceof String && objValue != null){
					String arg[] = {(String)objValue};
					return arg;
				}else if(objValue != null && !StringHelper.IsNullOrEmpty(String.valueOf(objValue))){
					String arg[] = {String.valueOf(objValue)};
					return arg;
				}
				else  
					return strDefault;
			}
			catch (Exception ex)
			{
				return strDefault;
			}
		}
	}

	
	public String GetFormatDate(Object objParamName){
		return GetFormatDate(objParamName,"yyyy/MM/dd");
	}
	
	public String GetFormatDate(Object objParamName,String strFormat){
		Date date = null;
		if(objParamName instanceof String){
			date = GetParamDateValue((String)objParamName, null,strFormat);
		}else if(objParamName instanceof Date){
			date = (Date)objParamName;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		if(date == null || "1900/01/01".equals(df.format(date)))
			return "";
		
		df.applyPattern(strFormat);
		return df.format(date);
	}
	
	public Date GetParamDateValue(String strParamName, Date dtDefault){
		return GetParamDateValue(strParamName,dtDefault,"yyyy/MM/dd");
	}
	/**
	 * 获取时间类型
	 * 
	 * @param strParamName
	 * @param dtDefault
	 * @return
	 */
	public Date GetParamDateValue(String strParamName, Date dtDefault,String strFormat)
	{
		Object objValue = GetParamValue(strParamName);
		if (StringHelper.IsNullOrEmpty(StringHelper.getValue(objValue)))
		{
			return dtDefault;
		}
		try
		{
			if (objValue instanceof Date)
				return (Date) objValue;
			
			try
			{
				SimpleDateFormat df = new SimpleDateFormat(strFormat);
				return df.parse(objValue.toString());
			}
			catch (Exception ex)
			{
				return dtDefault;
			}
			
		}
		catch (Exception ex)
		{
			return dtDefault;
		}
	}

	
	/**
	 * 获取类型为实体集合的属�?�?
	 * @param strParamName
	 * @return
	 */
	public BaseDataEntities GetParamDataEntitiesValue(String strParamName)
	{
		Object objValue = GetParamValue(strParamName);
		if (objValue == null)
		{
			return null;
		}
		if(objValue instanceof BaseDataEntities)
			return (BaseDataEntities)objValue;
		return null;
	}
	
	/**
	 * 将�?填充至JSON对象�?
	 * @param objJSON
	 */
	public void FillJSONObject(JSONObject objJSON)
	{
		FillJSONObject(objJSON,true);
	}
	
	/**
	 * 填充到JSON对象�?
	 * 
	 * @param objJSON
	 */
	public void FillJSONObject(JSONObject objJSON,boolean bIncludeEmpty)
	{
		OnFillJSONObject(objJSON,bIncludeEmpty);
	}

	/**
	 * 在填充到JSON对象中调�?
	 * 
	 * @param objJSON
	 */
	protected void OnFillJSONObject(JSONObject objJSON,boolean bIncludeEmpty)
	{
		if (paramList != null)
		{
			FillJSONObject(paramList,objJSON,bIncludeEmpty);
		}
	}


	/**
	 * 将属性列表填充至JSON对象�?
	 * @param paramList
	 * @param bEmptyParamList
	 * @param objJSON
	 */
	private static void FillJSONObject(Hashtable paramList,JSONObject objJSON,boolean bIncludeEmpty)
	{
		if (paramList != null)
		{
			Enumeration keys = paramList.keys();
			while (keys.hasMoreElements())
			{
				Object objKey = keys.nextElement();
				Object objValue = paramList.get(objKey);

				if (objKey == null || objValue == null)
					continue;

				try
				{
					if(objValue instanceof BaseDataEntities)
					{
						objJSON.put(objKey.toString().toLowerCase(), ((BaseDataEntities)objValue).ToJSONArray(bIncludeEmpty));
					}else if(objValue instanceof BaseDataEntity){
						objJSON.put(objKey.toString().toLowerCase(), ((BaseDataEntity)objValue).ToJSONString());
					}else
					{
						if(objValue instanceof Date)
						{
							JSONObject dt = new JSONObject();
							dt.put("time", ((Date)objValue).getTime());
							objJSON.put(objKey.toString().toLowerCase(), dt);
						}
						else
							objJSON.put(objKey.toString().toLowerCase(), objValue);
					}
					
				}
				catch (Exception ex)
				{
					StringHelper.Format("Object[%1$s][%2$s] to JSON Error", objKey, objValue);
					objJSON.put(objKey.toString().toLowerCase(), objValue.toString());
				}
			}
		}
	}
	
	/**
	 * 导出为JSON字符
	 * 
	 * @return
	 */
	public String ToJSONString()
	{
		return ToJSONString(this,false);
	}

	
	
	/**
	 * 导出为JSON字符
	 * 
	 * @return
	 */
	public static String ToJSONString(BaseDataEntity dataEntity,boolean bIncludeEmpty)
	{
		JSONObject jsonObj = new JSONObject();
		dataEntity.FillJSONObject(jsonObj,bIncludeEmpty);
		return jsonObj.toString();
	}
	
	/**
	 * @param dataEntity
	 * @param bReset
	 * @param bOverride
	 */
	public void CopyTo(BaseDataEntity dataEntity, boolean bReset,boolean bOverride){
		if (bReset)
			dataEntity.Reset();

		if (paramList != null)
		{
			CopyTo(paramList,false,dataEntity,bOverride);
		}
	}
	
	/**
	 * 将当前拷贝至其它数据对象
	 * 
	 * @param dataEntity
	 * @param bReset
	 */
	public void CopyTo(BaseDataEntity dataEntity, boolean bReset)
	{
		CopyTo(dataEntity,bReset,true);
	}

	private static void CopyTo(Hashtable paramList,boolean bEmptyParamList,BaseDataEntity dataEntity,boolean bOverride)
	{
		Enumeration keys = paramList.keys();
		while (keys.hasMoreElements())
		{
			
			Object objKey = keys.nextElement();
			if(!bOverride && dataEntity.ContainesParam((String)objKey))
				continue;
			
			if(bEmptyParamList)
			{
				dataEntity.SetParamValue((String) objKey, null);
			}
			else
			{
				Object objValue = paramList.get(objKey);
				if(objValue instanceof BaseDataEntities)
				{
					dataEntity.SetParamValue((String) objKey,((BaseDataEntities)objValue).clone());
				}
				else
					dataEntity.SetParamValue((String) objKey, objValue);
			}
			
		}
	}
	
	/**
	 * 指定属�?复制至指定数据对象（支持多个参数进行复制，参数之间可使用 ';'�?,'�?|' 进行分割
	 * 
	 * @param dataEntity
	 * @param strParamNames
	 * @param bReset
	 */
	public void CopyTo(BaseDataEntity dataEntity, String strParamNames, boolean bReset)
	{
		
		if (bReset)
			dataEntity.Reset();
		strParamNames = strParamNames.replace(';', '|');
		strParamNames = strParamNames.replace(',', '|');
		String strParamName[] = strParamNames.split("[|]");
		int nCount = strParamName.length;
		for (int i = 0; i < nCount; i++)
		{
			if (this.ContainesParam(strParamName[i]))
			{
				dataEntity.SetParamValue(strParamName[i], this.GetParamValue(strParamName[i]));
			}
		}
	}
	
	/**
	 * 判断实体中是否包含指定参�?
	 * 
	 * @param strParamName
	 * @return
	 */
	public boolean ContainesParam(String strParamName)
	{
		if (strParamName == null)
			return false;

		strParamName = strParamName.toUpperCase();

		if (paramList != null)
		{
			if (paramList.containsKey(strParamName))
				return true;
		}
		
		return false;
	}
	
	public boolean isNullOrEmpty(String key){
		if(!this.ContainesParam(key))
			return true;
		
		if(this.GetParamValue(key) == null)
			return true;
		
		if(this.GetParamStringValue(key, "").length() == 0)
			return true ;
		
		return false;
	}
	
	public boolean isInt(String srtParam){
		return this.isNumber(srtParam,"dot:0");
	}
	
	/**
	 * @param srtParam
	 * @return 是不是大�?的整�?
	 */
	public boolean isIntGZ(String srtParam){
		return this.isNumber(srtParam,"dot:0,min:'0'");
	}
	
	/**
	 * @param srtParam
	 * @return 是不是大于等�?的整�?
	 */
	public boolean isIntGEZ(String srtParam){
		return this.isNumber(srtParam,"dot:0,min:0");
	}
	
	public boolean isNumber(String srtParam,String strconfig){
		return StringHelper.isNumber(this.gpsv(srtParam),strconfig);
	}
	
	public boolean isNullOrEmpty(){
		return this.paramList == null || this.paramList.size() == 0;
	}
	
	public Utils getUtils() {
		return utils;
	}

	public void setUtils(Utils utils) {
		this.utils = utils;
	}
	
	
	public static BaseDataEntity getConfig(String strConfig){
		BaseDataEntity temConfig = null;
		if(!StringHelper.IsNullOrEmpty(strConfig)){
			try {
				temConfig = BaseDataEntity.FromJSONString(StringHelper.Format("{%1$s}",strConfig));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	
		if(temConfig == null) temConfig = new BaseDataEntity();
		return temConfig;
	}
	
}
