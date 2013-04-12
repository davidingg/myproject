/**
 * 
 */
package com.david.cloancalculator.util;

import java.sql.ResultSet;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
/**
 * @author david
 * 数据对象集合
 * 2011/9/28 上午8:43:53
 */
public class BaseDataEntities  extends java.util.Vector<BaseDataEntity> {


	/**
	 * 序列标识
	 */
	private static final long serialVersionUID = -6002618898839162833L;

	
	/**
	 * 将数据对象集合导出为JSON数组
	 * @param bIncludeEmpty
	 * @return
	 */
	public JSONArray ToJSONArray(boolean bIncludeEmpty)
	{
		Vector<JSONObject> arr = new Vector<JSONObject>();
		for(BaseDataEntity dataEntity:this)
		{
			JSONObject jsonObject = new JSONObject();
			dataEntity.FillJSONObject(jsonObject, bIncludeEmpty);
			arr.add(jsonObject);
		}
		return JSONArray.fromArray(arr.toArray());
	}
	
	
	public String TOJSONString(){
		JSONArray json = null;
		try{
			json =  this.ToJSONArray(true);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(json == null)
			json = new JSONArray();
		
		return json.toString();
	}

	/* (non-Javadoc)
	 * @see java.util.Vector#clone()
	 */
	@Override
	public synchronized Object clone()
	{
		BaseDataEntities dataEntities = new BaseDataEntities();
		
		for(BaseDataEntity dataEntity:this)
		{
			BaseDataEntity temp = new BaseDataEntity();
			dataEntity.CopyTo(temp,false);
			dataEntities.add(temp);
		}
		return dataEntities;
	}
	
	public static BaseDataEntities FromJSONArrayString(String strJSONArrayString)
	{
		return FromJSONArray(JSONArray.fromString(strJSONArrayString));
	}
	
	public static BaseDataEntities FromJSONArray(JSONArray jsonArray)
	{
		BaseDataEntities dataEntities = new BaseDataEntities();
		
		for(int i=0;i<jsonArray.length();i++)
		{
			JSONObject jsonobject = jsonArray.getJSONObject(i);
			BaseDataEntity dataEntity = BaseDataEntity.FromJSONObject(jsonobject);
			dataEntities.add(dataEntity);
		}
		return dataEntities;
	}

}
