package com.david.incomtax.model;

import android.content.Context;

import com.david.cloancalculator.R;
import com.david.cloancalculator.util.BaseDataEntity;
import com.david.cloancalculator.util.Utils;


/**
 * @author david
 * @date 2013-4-14 ����9:21:47
 */
public class IncomTaxSetManagerImpl implements IncomTaxSetManager {
	private String incomeTaxTiers[]=null;//ȫ��Ӧ��˰���ö�
	private String incomeTaxRatios[]=null;//˰��
	private String incomeTaxReduces[]=null;//����۳���
	private Utils utils = new Utils();
	/**
	 * @author david
	 * @param context
	 * @param monthIncome1	������
	 * @param insure1	����һ��
	 * @param StartTax1  ������
	 * @return
	 * @throws Exception
	 */
	@Override
	public BaseDataEntity calculateTax(Context context, double monthIncome1,
			double insure1, double StartTax1) {
		BaseDataEntity bd=new BaseDataEntity();//�������ݼ���
		incomeTaxTiers = context.getResources().getStringArray(R.array.Income_tax_tier);
		incomeTaxRatios = context.getResources().getStringArray(R.array.Income_tax_ratio);
		incomeTaxReduces = context.getResources().getStringArray(R.array.Income_tax_reduce);
		double payTaxMoney = monthIncome1-insure1-StartTax1;//Ӧ��˰��
		double incomTax = 0;// Ӧ�ɸ�˰
		double monthIncomeNotcontainTax = 0;//˰��������
		int index  = 0;//��˰���
		if(payTaxMoney>0){
			index = getIncomeTaxTierIndex(payTaxMoney);
			double incomeTaxRatio = Double.parseDouble(incomeTaxRatios[index]);
			double incomeTaxReduce = Double.parseDouble(incomeTaxReduces[index]);
			incomTax = utils.round(payTaxMoney*incomeTaxRatio/100-incomeTaxReduce,2);
			monthIncomeNotcontainTax = monthIncome1-insure1-incomTax;
		} else {
			incomTax = 0;
			monthIncomeNotcontainTax = monthIncome1-insure1-incomTax;
		}
		bd.spdv("incomTax", incomTax);
		bd.spdv("monthIncomeNotcontainTax", monthIncomeNotcontainTax);
		
		return bd;
	}
	
	/**
	 * @author david
	 * @param payTaxMoney  Ӧ��˰��
	 * @return ����
	 */
	private int getIncomeTaxTierIndex(double payTaxMoney){
		double lastIncomeTaxTiers = 0;//�ϴμ������
		int index = 0;
		for(int i = 0;i<incomeTaxTiers.length;i++){//ѭ�����Լ���
			if(payTaxMoney>=lastIncomeTaxTiers){//Ӧ��˰��>=��һ����
				if(i==incomeTaxTiers.length){
					index = i;
					break;
				} else {
					if(payTaxMoney<Double.parseDouble(incomeTaxTiers[i])){
						index = i;
						break;
					}
				}
			}
			lastIncomeTaxTiers = Double.parseDouble(incomeTaxTiers[i]);
		} 
		return index;
	}
}
