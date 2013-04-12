package com.david.basiscloan.model;

import android.content.Context;

import com.david.cloancalculator.R;
import com.david.cloancalculator.util.BaseDataEntity;
import com.david.cloancalculator.util.Utils;

public class BasisCloanSetManagerImpl implements BasisCloanSetManager {
	private String cloan_yearss[]=null;//所有贷款年限
	private String cloan_years_values[]=null;//所有贷款年限值
	private String rates[]=null;//所有贷款利率
	private String rate_bs_low_values[]=null;//利率商业低于五年利率
	private String rate_bs_long_values[]=null;//利率商业高于五年利率
	private String rate_gj_low_values[]=null;//利率公积金低于五年利率
	private String rate_gj_long_values[]=null;//利率公积金高于五年利率
	private String rebates[]=null;//利率折扣
	private String rebate_values[]=null;//利率折扣值
	private Utils utils = new Utils();
	public BaseDataEntity calculateCloan(Context context,String repayment_type,String cloan_type,int cloanYearsValueIndex,int rateValueIndex,int rebateValueIndex,double cloan_money){
		BaseDataEntity bd=new BaseDataEntity();//返回数据集合
		cloan_years_values = context.getResources().getStringArray(R.array.cloan_years_value);
		rate_bs_low_values = context.getResources().getStringArray(R.array.rate_bs_low_value);
		rate_bs_long_values = context.getResources().getStringArray(R.array.rate_bs_long_value);
		rate_gj_low_values = context.getResources().getStringArray(R.array.rate_bs_low_value);
		rate_gj_long_values = context.getResources().getStringArray(R.array.rate_gj_long_value);
		rebate_values = context.getResources().getStringArray(R.array.rebate_value);
		int cloan_years = Integer.parseInt(cloan_years_values[cloanYearsValueIndex]);//贷款年数
		int cloan_months = cloan_years*12;//贷款月数
		double tot_money = cloan_money*10000;//贷款总额
		double rebate = Double.parseDouble(rebate_values[rebateValueIndex]);//贷款年数
		double rate = 0;//利率折扣
		if("商业贷款".equals(cloan_type)){
			if(cloan_years<=5){
				rate = Double.parseDouble(rate_bs_low_values[rateValueIndex]);
			} else {
				rate = Double.parseDouble(rate_bs_long_values[rateValueIndex]);
			}
		} else {
			if(cloan_years<=5){
				rate = Double.parseDouble(rate_gj_low_values[rateValueIndex]);
			} else {
				rate = Double.parseDouble(rate_gj_long_values[rateValueIndex]);
			}
		}
		if("等额本息".equals(repayment_type)){
			bd=getBusinessCloan(rate,tot_money,cloan_months,rebate);
		} else if("等额本金".equals(repayment_type)){
			bd=getProvidentCloan(rate,tot_money,cloan_months,rebate);
		}
		return bd;
	}
	/**
	 * 等额本息
	 * @return
	 */
	public BaseDataEntity getBusinessCloan(double rate, double tot_money, int cloan_months,double rebate){
		//月还款额
		double monthPayment = getMontMnney(utils.round(rate/100*rebate,4),tot_money,cloan_months);
		//还款总额
		double repayMoney = monthPayment * cloan_months;
		//支付的利息
		double interest = repayMoney - tot_money;
		BaseDataEntity row = new BaseDataEntity();
		row.spsv("house_tot_money","略");
		row.spdv("cloan_tot_money", tot_money);
		row.spdv("repayMoney", utils.round(repayMoney,2));
		row.spdv("interest", utils.round(interest,2));
		row.spdv("fist_pay", 0);
		row.spsv("month_cnt",cloan_months+"(月)");
		row.spdv("monthPayment", utils.round(monthPayment,2));
		return row;
	}
	/**
	 * 公等额本金
	 */
	
	public BaseDataEntity getProvidentCloan(double rate, double tot_money, int cloan_months,double rebate){
		BaseDataEntity row = new BaseDataEntity();
		StringBuffer monthPaymentStr = new StringBuffer("");
		//还款总额
		double repayMoney = 0;
		for(int i = 0; i < cloan_months; i++){
			//调用函数计算: 本金月还款额
			double monthPayment = getMonthMoney2(utils.round(rate/100*rebate,4), tot_money, cloan_months,i);
			repayMoney += monthPayment;
			if(i!=0){
				monthPaymentStr.append("|" + (i+1)+"月  "+utils.round(monthPayment,2));
			} else {
				monthPaymentStr.append((i+1)+"月  "+utils.round(monthPayment,2));
			}
			
		} 
		//支付的利息
		double interest = utils.round(repayMoney - tot_money,2);
		row.spsv("house_tot_money","略");
		row.spdv("cloan_tot_money", tot_money);
		row.spdv("repayMoney", utils.round(repayMoney,2));
		row.spdv("interest", interest);
		row.spdv("fist_pay", 0);
		row.spsv("month_cnt",cloan_months+"(月)");
		row.spsv("monthPayment", monthPaymentStr.toString());
		return row;
	} 
	/**
	 * 计算月还款额（本息）
	 */
	public double getMontMnney(double rate, double tot_money, int cloan_months){
		double rate_month = rate / 12;//月利率
		//return utils.round(tot_money * rate_month * Math.pow(1 + rate_month, cloan_months) / ( Math.pow(1 + rate_month, cloan_months) -1 ),2); 
		return tot_money * rate_month * Math.pow(1 + rate_month, cloan_months)/ ( Math.pow(1 + rate_month, cloan_months) -1 );
	}
	
	/**
	 * 计算月还款额（本金）
	 */
	
	//本金还款的月还款额(参数: 年利率 / 贷款总额 / 贷款总月份 / 贷款当前月0～length-1)
	public double getMonthMoney2(double rate, double tot_money, int cloan_months,int mm){
		double rate_month = rate / 12;//月利率
		double benjin_money = tot_money / cloan_months;
		return (tot_money - benjin_money * mm) * rate_month + benjin_money;
	} 
}
