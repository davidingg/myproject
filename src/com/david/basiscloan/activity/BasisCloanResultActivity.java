package com.david.basiscloan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.domob.android.ads.DomobAdEventListener;
import cn.domob.android.ads.DomobAdManager.ErrorCode;
import cn.domob.android.ads.DomobAdView;

import com.david.cloancalculator.R;
import com.david.cloancalculator.util.BaseDataEntity;
import com.david.cloancalculator.util.Utils;


public class BasisCloanResultActivity extends Activity implements OnClickListener{
	private Button login_reback_btn = null;
	private TextView tite = null;//标题
	private TextView house_tot_money = null; //房款总额
	private TextView cloan_tot_money = null; //贷款总额
	private TextView repay_money = null;//还款总额
	private TextView interest = null;//支付利息
	private TextView fist_pay = null;//首期付款
	private TextView month_cnt = null;//贷款月数
	private TextView month_payment = null;//月均还款
	private Utils utils = new Utils();
	RelativeLayout mAdContainer;
	DomobAdView mAdview320x50;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消页面标题  
		setContentView(R.layout.activity_basiscloanresult);
		initView();
		//calculateCloan();//计算贷款
		Intent intent = getIntent();
		String repayment_type = intent.getStringExtra("repayment_type");
		Bundle bundle=intent.getExtras();
		BaseDataEntity bd = (BaseDataEntity)bundle.get("bd");
		this.house_tot_money.setText(bd.gpsv("house_tot_money"));
		this.cloan_tot_money.setText(bd.gpsv("cloan_tot_money"));
		this.repay_money.setText(bd.gpsv("repayMoney"));
		this.interest.setText(bd.gpsv("interest"));
		this.fist_pay.setText(bd.gpsv("fist_pay"));
		this.month_cnt.setText(bd.gpsv("month_cnt"));
		this.month_payment.setText(bd.gpsv("monthPayment").replace("|", "\n"));
	}
	//获取ui控件对象
	private void initView()
	{
		this.login_reback_btn = (Button)findViewById(R.id.login_reback_btn);
		this.login_reback_btn.setOnClickListener(this);
		this.tite = (TextView)findViewById(R.id.tite);
		this.tite.setText("贷款计算器");
		this.house_tot_money = (TextView)findViewById(R.id.house_tot_money);
		this.cloan_tot_money = (TextView)findViewById(R.id.cloan_tot_money);
		this.repay_money = (TextView)findViewById(R.id.repay_money);
		this.interest = (TextView)findViewById(R.id.interest);
		this.fist_pay = (TextView)findViewById(R.id.fist_pay);
		this.month_cnt = (TextView)findViewById(R.id.month_cnt);
		this.month_payment = (TextView)findViewById(R.id.month_payment);
		this.month_payment.setMovementMethod(new ScrollingMovementMethod());
		//this.month_payment.setScrollbarFadingEnabled(false);
		//多盟广告平台
		mAdContainer = (RelativeLayout) findViewById(R.id.adcontainer);
		mAdview320x50 = new DomobAdView(this, CloanCalculatorActivity.PUBLISHER_ID, DomobAdView.INLINE_SIZE_320X50);
		mAdview320x50.setKeyword("game");
		mAdview320x50.setUserGender("male");
		mAdview320x50.setUserBirthdayStr("2000-08-08");
		mAdview320x50.setUserPostcode("123456");

		mAdview320x50.setAdEventListener(new DomobAdEventListener() {
			
			@Override
			public void onDomobAdReturned(DomobAdView adView) {
				Log.i("DomobSDKDemo", "onDomobAdReturned");				
			}
	
			@Override
			public void onDomobAdOverlayPresented(DomobAdView adView) {
				Log.i("DomobSDKDemo", "overlayPresented");
			}
	
			@Override
			public void onDomobAdOverlayDismissed(DomobAdView adView) {
				Log.i("DomobSDKDemo", "Overrided be dismissed");				
			}

			@Override
			public void onDomobAdClicked(DomobAdView arg0) {
				Log.i("DomobSDKDemo", "onDomobAdClicked");				
			}

			@Override
			public void onDomobAdFailed(DomobAdView arg0, ErrorCode arg1) {
				Log.i("DomobSDKDemo", "onDomobAdFailed");
			}

			@Override
			public void onDomobLeaveApplication(DomobAdView arg0) {
				Log.i("DomobSDKDemo", "onDomobLeaveApplication");
			}
		});
		mAdContainer.addView(mAdview320x50);
	}
	//定义控件点击事件
	public void onClick(View v) {
		if(v.getId()==R.id.login_reback_btn){
			finish();
		}	
	}
}
