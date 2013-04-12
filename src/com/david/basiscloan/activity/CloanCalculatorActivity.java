package com.david.basiscloan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import cn.domob.android.ads.DomobUpdater;

import com.david.cloancalculator.R;

public class CloanCalculatorActivity extends Activity implements OnClickListener{
	/** Called when the activity is first created. */
	private Button basiscloanBtn = null;//貸款計算器
	private Button prepayBtn = null;//提前還款計算器
	private int REQUEST_CODE = 1;
	public static final String PUBLISHER_ID = "56OJzUrYuNUl6vGcqG";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消页面标题  
        setContentView(R.layout.activity_cloancalculator);//定义画面对应的xml布局
        initView();
        DomobUpdater.checkUpdate(this, "Your Publisher ID.");
    }
    private void initView()//读取视图控件
	{
    	this.basiscloanBtn = (Button) findViewById(R.id.basiscloanbtn);//获取汽车保养按钮对象
    	basiscloanBtn.setOnClickListener(this);//添加点击事件
    	this.prepayBtn = (Button) findViewById(R.id.prepaybtn);//获取汽车信息注册按钮对象
    	prepayBtn.setOnClickListener(this);
	}   
    public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.basiscloanbtn:
			sendBasisCloan();
			break;
		case R.id.prepaybtn:
			sendPrepay();
			break;
		}
	}
    //跳转页面
    public void sendBasisCloan(){
    	Intent intent=new Intent();//申请传值
		intent.setClass(CloanCalculatorActivity.this, BasisCloanSetActivity.class);//画面跳转到knowledge_car_active 保养页面
		setResult(REQUEST_CODE,intent);
		startActivity(intent);
    }
    //跳轉頁面
    public void sendPrepay(){
    	Intent intent=new Intent();//申请传值
		intent.setClass(CloanCalculatorActivity.this, PrepaymentSetActivity.class);//画面跳转到knowledge_car_active 保养页面
		setResult(REQUEST_CODE,intent);
		startActivity(intent);
    }
}