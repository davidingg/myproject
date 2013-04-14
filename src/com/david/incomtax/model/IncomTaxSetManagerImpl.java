package com.david.incomtax.model;

import android.content.Context;

import com.david.cloancalculator.R;
import com.david.cloancalculator.util.BaseDataEntity;
import com.david.cloancalculator.util.Utils;


/**
 * @author david
 * @date 2013-4-14 下午9:21:47
 */
public class IncomTaxSetManagerImpl implements IncomTaxSetManager {
	private String incomeTaxTiers[]=null;//全月应纳税所得额
	private String incomeTaxRatios[]=null;//税率
	private String incomeTaxReduces[]=null;//速算扣除数
	private Utils utils = new Utils();
	/**
	 * @author david
	 * @param context
	 * @param monthIncome1	月收入
	 * @param insure1	三险一金
	 * @param StartTax1  起征额
	 * @return
	 * @throws Exception
	 */
	@Override
	public BaseDataEntity calculateTax(Context context, double monthIncome1,
			double insure1, double StartTax1) {
		BaseDataEntity bd=new BaseDataEntity();//返回数据集合
		incomeTaxTiers = context.getResources().getStringArray(R.array.Income_tax_tier);
		incomeTaxRatios = context.getResources().getStringArray(R.array.Income_tax_ratio);
		incomeTaxReduces = context.getResources().getStringArray(R.array.Income_tax_reduce);
		double payTaxMoney = monthIncome1-insure1-StartTax1;//应纳税额
		double incomTax = 0;// 应缴个税
		double monthIncomeNotcontainTax = 0;//税后月收入
		int index  = 0;//纳税额级数
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
	 * @param payTaxMoney  应纳税额
	 * @return 级数
	 */
	private int getIncomeTaxTierIndex(double payTaxMoney){
		double lastIncomeTaxTiers = 0;//上次级数金额
		int index = 0;
		for(int i = 0;i<incomeTaxTiers.length;i++){//循环所以级数
			if(payTaxMoney>=lastIncomeTaxTiers){//应纳税额>=上一级数
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
