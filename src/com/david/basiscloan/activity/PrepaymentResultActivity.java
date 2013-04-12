package com.david.basiscloan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.domob.android.ads.DomobAdEventListener;
import cn.domob.android.ads.DomobAdView;
import cn.domob.android.ads.DomobAdManager.ErrorCode;

import com.david.cloancalculator.R;
import com.david.cloancalculator.util.BaseDataEntity;
import com.david.cloancalculator.util.Utils;


public class PrepaymentResultActivity extends Activity implements OnClickListener{
	private Button login_reback_btn = null;
	private TextView tite = null;//标题
	private TextView original_month_payment = null; //原月还款额
	private TextView original_last_payment_date = null; //原最后还款期
	private TextView already_repay_money = null;//已还款总额
	private TextView already_interest = null;//已还利息额
	private TextView now_tot_repay_money = null;//该月一次还款额
	private TextView next_fist_pay = null;//下月起月还款额
	private TextView save_interest = null;//节省利息支出
	private TextView new_last_payment_date = null;//新的最后还款期
	private Utils utils = new Utils();
	RelativeLayout mAdContainer;
	DomobAdView mAdview320x50;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消页面标题  
		setContentView(R.layout.activity_prepaymentcloanresult);
		initView();
		//calculateCloan();//计算贷款
		Intent intent = getIntent();
		Bundle bundle=intent.getExtras();
		BaseDataEntity bd = (BaseDataEntity)bundle.get("bd");
		this.original_month_payment.setText(bd.gpsv("originalMonthPayment"));
		this.original_last_payment_date.setText(bd.gpsv("originalLastDate"));
		this.already_repay_money.setText(bd.gpsv("alreaddyPayMoney"));
		this.already_interest.setText(bd.gpsv("alreaddyPayRate"));
		this.now_tot_repay_money.setText(bd.gpsv("nowPayMoney"));
		this.next_fist_pay.setText(bd.gpsv("nowMonthPayment"));
		this.save_interest.setText(bd.gpsv("saveRate"));
		this.new_last_payment_date.setText(bd.gpsv("newLastPayDate"));
	}
	//获取ui控件对象
	private void initView()
	{
		this.login_reback_btn = (Button)findViewById(R.id.login_reback_btn);
		this.login_reback_btn.setOnClickListener(this);
		this.tite = (TextView)findViewById(R.id.tite);
		this.tite.setText("提前还款计算器");
		this.original_month_payment = (TextView)findViewById(R.id.original_month_payment);
		this.original_last_payment_date = (TextView)findViewById(R.id.original_last_payment_date);
		this.already_repay_money = (TextView)findViewById(R.id.already_repay_money);
		this.already_interest = (TextView)findViewById(R.id.already_interest);
		this.now_tot_repay_money = (TextView)findViewById(R.id.now_tot_repay_money);
		this.next_fist_pay = (TextView)findViewById(R.id.next_fist_pay);
		this.save_interest = (TextView)findViewById(R.id.save_interest);
		this.new_last_payment_date = (TextView)findViewById(R.id.new_last_payment_date);
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
