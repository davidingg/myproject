package com.david.incomtax.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.david.cloancalculator.R;
import com.david.cloancalculator.util.BaseDataEntity;
import com.david.cloancalculator.util.Utils;
import com.david.incomtax.model.IncomTaxSetManager;
import com.david.incomtax.model.IncomTaxSetManagerImpl;

public class IncomeTaxSetActivity extends Activity implements OnClickListener{
	private Button login_reback_btn = null;
	private Button save_btn = null; //���水ť
	private TextView tite = null;//����
	private Button delete_btn = null;//ɾ����ť
	private EditText month_income = null;//������
	private EditText insure = null;//����һ��
	private EditText start_tax = null;//������
	private TextView month_income_notcontain_tax = null;//˰��������
	private TextView incom_tax = null;//Ӧ�ɸ�˰
	private Utils utils = new Utils();
	private IncomTaxSetManager iManager=new IncomTaxSetManagerImpl();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ȡ��ҳ�����  
		setContentView(R.layout.activity_incomtaxset);
		initView();
	}	
	//��ȡui�ؼ�����
	private void initView()
	{
		this.login_reback_btn = (Button)findViewById(R.id.login_reback_btn);
		this.login_reback_btn.setOnClickListener(this);
		this.tite = (TextView)findViewById(R.id.tite);
		this.tite.setText("��˰������");
		this.save_btn = (Button)findViewById(R.id.save_button);
		this.delete_btn = (Button)findViewById(R.id.delete_button);
		this.save_btn.setOnClickListener(this);
		this.delete_btn.setOnClickListener(this);
		this.month_income=(EditText)findViewById(R.id.month_income);
		this.month_income.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		this.insure=(EditText)findViewById(R.id.insure);
		this.insure.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		this.start_tax=(EditText)findViewById(R.id.start_tax);
		this.start_tax.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		this.start_tax.setText(getResources().getString(R.string.start_tax_value));
		this.start_tax.setInputType(InputType.TYPE_NULL);
		this.month_income_notcontain_tax=(TextView)findViewById(R.id.month_income_notcontain_tax);
		this.incom_tax=(TextView)findViewById(R.id.incom_tax);
		
	}
	//����ؼ�����¼�
	public void onClick(View v) {
		if(v.getId()==R.id.login_reback_btn){
			finish();
		}
		if(v.getId()==R.id.save_button){
			calculateTax();	
		}
	}
	public void calculateTax(){
		String monthIncome = this.month_income.getText().toString();//ȡ��������
		String insure = this.insure.getText().toString();	//����һ��
		String StartTax = this.start_tax.getText().toString();//������
		if("".equals(monthIncome)||!utils.isNum(monthIncome)){
			Toast.makeText(this, "������û��!", Toast.LENGTH_LONG).show();
			return;
		}
		if("".equals(insure)){
			insure = "0";
		}
		if(!utils.isNum(insure)){
			Toast.makeText(this, "����һ������������!", Toast.LENGTH_LONG).show();
			return;
		}
		double monthIncome1 = Double.parseDouble(monthIncome);
		double insure1 = Double.parseDouble(insure);
		double StartTax1 = Double.parseDouble(StartTax);
		BaseDataEntity bd=iManager.calculateTax(this, monthIncome1, insure1, StartTax1);
		month_income_notcontain_tax.setText(bd.gpsv("monthIncomeNotcontainTax"));
		incom_tax.setText(bd.gpsv("incomTax"));
	}
}
