package com.david.basiscloan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.david.basiscloan.model.BasisCloanSetManager;
import com.david.basiscloan.model.BasisCloanSetManagerImpl;
import com.david.cloancalculator.R;
import com.david.cloancalculator.util.BaseDataEntity;
import com.david.cloancalculator.util.Utils;


public class BasisCloanSetActivity extends Activity implements OnClickListener{
	private Button login_reback_btn = null;
	private Button save_btn = null; //保存按钮
	private TextView tite = null;//标题
	private Button delete_btn = null;//删除按钮
	private EditText repayment_type = null; //还款方式
	private EditText cloan_type = null;//贷款类别
	private EditText cloan_money = null;//贷款总额
	private EditText cloan_years = null;//按揭年数
	private EditText rate = null;//汇率
	private EditText rebate = null;//折扣
	private Utils utils = new Utils();
	private BasisCloanSetManager bManager=new BasisCloanSetManagerImpl();
	private int cloanYearsValueIndex=0;//按揭年数选择第几个值
	private int rateValueIndex=0;//汇率选择第几个值
	private int rebateValueIndex=0;//折扣选择第几个值
	private int REQUEST_CODE = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消页面标题  
		setContentView(R.layout.activity_basiscloanset);
		initView();
		//calculateCloan();//计算贷款
	}
	//获取ui控件对象
	private void initView()
	{
		this.login_reback_btn = (Button)findViewById(R.id.login_reback_btn);
		this.login_reback_btn.setOnClickListener(this);
		this.tite = (TextView)findViewById(R.id.tite);
		this.tite.setText("贷款计算器");
		this.save_btn = (Button)findViewById(R.id.save_button);
		this.delete_btn = (Button)findViewById(R.id.delete_button);
		this.save_btn.setOnClickListener(this);
		this.delete_btn.setOnClickListener(this);
		this.repayment_type = (EditText)findViewById(R.id.repayment_type);
		this.repayment_type.setOnClickListener(this);
		this.repayment_type.setText("等额本息");
		this.repayment_type.setInputType(InputType.TYPE_NULL);
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
		
	}
	//定义控件点击事件
	public void onClick(View v) {
		if(v.getId()==R.id.repayment_type ||v.getId()==R.id.cloan_type ||v.getId()==R.id.cloan_years||v.getId()==R.id.rate||v.getId()==R.id.rebate){
			showDateDialog(v);
		}
		if(v.getId()==R.id.save_button){
			calculateCloan();	
		}
		if(v.getId()==R.id.login_reback_btn){
			finish();
		}
	}
	public void showDateDialog(View v){
		
		Builder builder=new Builder(this);
		if(v.getId()==R.id.repayment_type){
			//还款方式弹出单选选择框
			new AlertDialog.Builder(this).setTitle("还款方式").setIcon(
				android.R.drawable.ic_dialog_info).setSingleChoiceItems(
				R.array.repayment_type, 0,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						repayment_type.setText(getResources().getStringArray(R.array.repayment_type)[which]);
						dialog.dismiss();
					}		    
          }).setNegativeButton("取消", null).show();
			
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
	}
	public void calculateCloan(){
		Log.i("dwtest", "11111");
		//获得控件值
		String repayment_type = this.repayment_type.getText().toString();
		String cloan_type = this.cloan_type.getText().toString();
		String cloan_years =  this.cloan_years.getText().toString();
		String rate = this.rate.getText().toString();
		String rebate = this.rebate.getText().toString();
		String cloan_money = this.cloan_money.getText().toString();
		if("".equals(repayment_type)){
			Toast.makeText(this, "还款方式没填!", Toast.LENGTH_LONG).show();
			return;
		}	
		if("".equals(cloan_type)){
			Toast.makeText(this, "贷款类别没填!", Toast.LENGTH_LONG).show();
			return;
		}	
		if("".equals(cloan_years)){
			Toast.makeText(this, "按揭年数没填!", Toast.LENGTH_LONG).show();
			return;
		}	
		if("".equals(cloan_money)){
			Toast.makeText(this, "贷款总额没填!", Toast.LENGTH_LONG).show();
			return;
		}
		if(!utils.isNum(cloan_money)){
			Toast.makeText(this, "贷款总额请输入数字!", Toast.LENGTH_LONG).show();
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
		double cloan_money1 = Double.parseDouble(cloan_money);
		try {
			Log.i("dwtest", "3333");
			BaseDataEntity bd=bManager.calculateCloan(this, repayment_type, cloan_type, cloanYearsValueIndex, rateValueIndex, rebateValueIndex,cloan_money1);
			Bundle mBundle = new Bundle(); 
			Intent intent=new Intent();
			intent.setClass(BasisCloanSetActivity.this,BasisCloanResultActivity.class);
			mBundle.putSerializable("bd", bd) ;
			intent.putExtras(mBundle);
			intent.putExtra("repayment_type", repayment_type);
			setResult(REQUEST_CODE,intent);
			startActivity(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
