package com.david.basiscloan.activity;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.david.basiscloan.model.PrepaymentSetManager;
import com.david.basiscloan.model.PrepaymentSetManagerImpl;
import com.david.cloancalculator.R;
import com.david.cloancalculator.util.BaseDataEntity;
import com.david.cloancalculator.util.Utils;


public class PrepaymentSetActivity extends Activity implements OnClickListener{
	private Button login_reback_btn = null;
	private Button save_btn = null; //保存按钮
	private TextView tite = null;//标题
	private Button delete_btn = null;//删除按钮
	private EditText cloan_type = null; //贷款类别
	private EditText cloan_money = null;//原贷款总额
	private EditText cloan_years = null;//按揭年数
	private EditText rate = null; //利        率
	private EditText first_repay = null;//第一次还款时间
	private EditText ahead_repay = null;//提前还款时间
	private EditText ahead_repay_money = null;//提前还款方式
	private EditText process_mode = null;//处理方式
	private int cloanYearsValueIndex = 0;//年度选择index
	private int rateValueIndex = 0;//利率选择index
	private int rebateValueIndex = 0;//折扣选择index
	private int processModeIndex=0;//处理方式
	private EditText rebate = null;//折扣
	private PrepaymentSetManager pManager=new PrepaymentSetManagerImpl();
	private Utils utils = new Utils();
	private int REQUEST_CODE = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消页面标题  
		setContentView(R.layout.activity_prepaymentcloanset);
		initView();
	}
	//获取ui控件对象
	private void initView()
	{
		this.login_reback_btn = (Button)findViewById(R.id.login_reback_btn);
		this.login_reback_btn.setOnClickListener(this);
		this.tite = (TextView)findViewById(R.id.tite);
		this.tite.setText("提前还款计算器");
		this.cloan_type = (EditText)findViewById(R.id.cloan_type);
		this.cloan_type.setOnClickListener(this);
		this.cloan_type.setText("商业贷款");
		this.cloan_type.setInputType(InputType.TYPE_NULL);
		
		this.cloan_money = (EditText)findViewById(R.id.cloan_money);
		this.cloan_money.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		this.cloan_years = (EditText)findViewById(R.id.cloan_years);
		this.cloan_years.setOnClickListener(this);
		this.cloan_years.setInputType(InputType.TYPE_NULL);
		
		this.rate = (EditText)findViewById(R.id.rate);
		this.rate.setOnClickListener(this);
		this.rate.setText("12年7月6日利率");
		this.rate.setInputType(InputType.TYPE_NULL);
		
		this.rebate = (EditText)findViewById(R.id.rebate);
		this.rebate.setOnClickListener(this);
		this.rebate.setText("基准利率");
		this.rebate.setInputType(InputType.TYPE_NULL);
		
		this.first_repay = (EditText)findViewById(R.id.first_repay);
		this.first_repay.setOnClickListener(this);
		this.first_repay.setInputType(InputType.TYPE_NULL);
		
		this.ahead_repay = (EditText)findViewById(R.id.ahead_repay);
		this.ahead_repay.setOnClickListener(this);
		this.ahead_repay.setInputType(InputType.TYPE_NULL);
		
		this.ahead_repay_money = (EditText)findViewById(R.id.ahead_repay_money);
		this.ahead_repay_money.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL); 
		
		this.process_mode = (EditText)findViewById(R.id.process_mode);
		this.process_mode.setOnClickListener(this);
		this.process_mode.setInputType(InputType.TYPE_NULL);
		
		this.save_btn = (Button)findViewById(R.id.save_button);
		this.delete_btn = (Button)findViewById(R.id.delete_button);
		this.save_btn.setOnClickListener(this);
		this.delete_btn.setOnClickListener(this);
	}
	//定义控件点击事件
	public void onClick(View v) {
		if(v.getId()==R.id.repayment_type 
				||v.getId()==R.id.cloan_type 
				||v.getId()==R.id.cloan_years
				||v.getId()==R.id.rate 
				||v.getId()==R.id.process_mode
				||v.getId()==R.id.rebate
				){
			showDateDialog(v);
		}
		
		switch(v.getId())
		{
		case R.id.login_reback_btn:
			finish();
			break;
		case R.id.first_repay:
			showDateDialog(v);
			break;	
		case R.id.ahead_repay:
			showDateDialog(v);
			break;	
			
		}
		if(v.getId()==R.id.save_button){
			calculateCloan();	
		}
	}
	
	/**
	 * 彈出日期輸入控件
	 */
	public void showDateDialog(View v){
		Calendar myCalendar = Calendar.getInstance(Locale.CHINA);
        Date myDate=new Date();                //获取当前日期Date对象
        myCalendar.setTime(myDate);              //为Calendar对象设置时间为当前日期
        int year=myCalendar.get(Calendar.YEAR);                //获取Calendar对象中的年
        int month=myCalendar.get(Calendar.MONTH);
        int day=myCalendar.get(Calendar.DAY_OF_MONTH);         //获取这个月的第几天
        if(v.getId()==R.id.first_repay){
	        DatePickerDialog dpd = new DatePickerDialog(PrepaymentSetActivity.this,new OnDateSetListener(){
				 public void onDateSet(DatePicker view, int myyear,int monthOfYear,int dayOfMonth) {
						 first_repay.setText(myyear+"/"+(monthOfYear+1)+"/"+dayOfMonth); 
	         }},year,month,day);
	        dpd.show(); 
        } 
        if(v.getId()==R.id.ahead_repay) {
        	 DatePickerDialog dpd = new DatePickerDialog(PrepaymentSetActivity.this,new OnDateSetListener(){
				 public void onDateSet(DatePicker view, int myyear,int monthOfYear,int dayOfMonth) {
					 	ahead_repay.setText(myyear+"/"+(monthOfYear+1)+"/"+dayOfMonth); 
	         }},year,month,day);
	        dpd.show(); 
		}	
        if(v.getId()==R.id.cloan_type){
			//贷款类别弹出单选选择框
			new AlertDialog.Builder(this).setTitle("贷款类别").setIcon(
					android.R.drawable.ic_dialog_info).setSingleChoiceItems(
					R.array.cloan_type, 0,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							cloan_type.setText(getResources().getStringArray(R.array.cloan_type)[which]);
							dialog.dismiss();
						}		    
	          }).setNegativeButton("取消", null).show();
		}
		if(v.getId()==R.id.cloan_years){
			//按揭年数弹出单选选择框
			new AlertDialog.Builder(this).setTitle("按揭年数").setIcon(
					android.R.drawable.ic_dialog_info).setSingleChoiceItems(
					R.array.cloan_years, 0,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							cloan_years.setText(getResources().getStringArray(R.array.cloan_years)[which]);
							cloanYearsValueIndex=which;
							dialog.dismiss();
						}		    
	          }).setNegativeButton("取消", null).show();
		}
		if(v.getId()==R.id.rate){
			//按揭年数弹出单选选择框
			new AlertDialog.Builder(this).setTitle("利率").setIcon(
					android.R.drawable.ic_dialog_info).setSingleChoiceItems(
					R.array.rate, 0,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							rate.setText(getResources().getStringArray(R.array.rate)[which]);
							rateValueIndex=which;
							dialog.dismiss();
						}		    
	          }).setNegativeButton("取消", null).show();
		}
		if(v.getId()==R.id.rebate){
			//按揭年数弹出单选选择框
			new AlertDialog.Builder(this).setTitle("折扣").setIcon(
					android.R.drawable.ic_dialog_info).setSingleChoiceItems(
					R.array.rebate, 0,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							rebate.setText(getResources().getStringArray(R.array.rebate)[which]);
							rebateValueIndex=which;
							dialog.dismiss();
						}		    
	          }).setNegativeButton("取消", null).show();
		}
		if(v.getId()==R.id.process_mode){
			//按揭年数弹出单选选择框
			new AlertDialog.Builder(this).setTitle("处理方式").setIcon(
					android.R.drawable.ic_dialog_info).setSingleChoiceItems(
					R.array.process_mode, 0,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							process_mode.setText(getResources().getStringArray(R.array.process_mode)[which]);
							processModeIndex=which;
							dialog.dismiss();
						}		    
	          }).setNegativeButton("取消", null).show();
		}
	}
	public void calculateCloan(){
		String cloan_type = this.cloan_type.getText().toString();
		String cloan_years =  this.cloan_years.getText().toString();
		String rate = this.rate.getText().toString();
		String rebate = this.rebate.getText().toString();
		String cloan_money = this.cloan_money.getText().toString();
		String firstRepay = this.first_repay.getText().toString();
		String aheadRepay = this.ahead_repay.getText().toString();
		String ahead_repay_money = this.ahead_repay_money.getText().toString();
		String processMode = this.process_mode.getText().toString();
		if("".equals(cloan_type)){
			Toast.makeText(this, "还款类别没填!", Toast.LENGTH_LONG).show();
			return;
		}	
		if("".equals(cloan_money)){
			Toast.makeText(this, "原贷款金额没填!", Toast.LENGTH_LONG).show();
			return;
		}
		if(!utils.isNum(cloan_money)){
			Toast.makeText(this, "原贷款金额请输入数字!", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(cloan_years)){
			Toast.makeText(this, "按揭年数没填!", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(rate)){
			Toast.makeText(this, "利率没填!", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(rebate)){
			Toast.makeText(this, "折扣没填!", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(firstRepay)){
			Toast.makeText(this, "第一次还款时间没填!", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(aheadRepay)){
			Toast.makeText(this, "提前还款时间没填!", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(ahead_repay_money)||"0".equals(ahead_repay_money)){
			Toast.makeText(this, "提前还款金额没填!", Toast.LENGTH_LONG).show();
			return;
		}
		if(!utils.isNum(ahead_repay_money)){
			Toast.makeText(this, "提前还款金额请输入数字!", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(processMode)){
			Toast.makeText(this, "处理方式没填!", Toast.LENGTH_LONG).show();
			return;
		}
		//已经还款期数
		double alreadyPayMonths =0;
		try {
			alreadyPayMonths = utils.diffDate("MM", firstRepay, aheadRepay);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(alreadyPayMonths<0){
			Toast.makeText(this, "提前还款日期必须大于第一次还款日期!", Toast.LENGTH_LONG).show();
			return;
		}
		double cloan_money1 = Double.parseDouble(cloan_money);
		double ahead_repay_money1 = Double.parseDouble(ahead_repay_money);
		BaseDataEntity bd;
		try {
			bd = pManager.calculateCloan(this, cloan_type, cloan_money1, cloanYearsValueIndex, rateValueIndex, rebateValueIndex, firstRepay, aheadRepay, ahead_repay_money1, processModeIndex);
			Bundle mBundle = new Bundle(); 
			Intent intent=new Intent();
			intent.setClass(PrepaymentSetActivity.this,PrepaymentResultActivity.class);
			mBundle.putSerializable("bd", bd) ;
			intent.putExtras(mBundle);
			setResult(REQUEST_CODE,intent);
			startActivity(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
