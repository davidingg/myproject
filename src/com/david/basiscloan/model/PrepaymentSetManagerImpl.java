package com.david.basiscloan.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;

import com.david.cloancalculator.R;
import com.david.cloancalculator.util.BaseDataEntity;
import com.david.cloancalculator.util.Utils;

public class PrepaymentSetManagerImpl implements PrepaymentSetManager {
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
	public BaseDataEntity calculateCloan(Context context,String cloan_type,double cloan_money,int cloanYearsValueIndex,int rateValueIndex,int rebateValueIndex,String first_repay,String ahead_repay,double aheadRepayMethodMoney,int processModeIndex){
		BaseDataEntity bd=new BaseDataEntity();//返回数据集合
		cloan_years_values = context.getResources().getStringArray(R.array.cloan_years_value);
		rate_bs_low_values = context.getResources().getStringArray(R.array.rate_bs_low_value);
		rate_bs_long_values = context.getResources().getStringArray(R.array.rate_bs_long_value);
		rate_gj_low_values = context.getResources().getStringArray(R.array.rate_bs_low_value);
		rate_gj_long_values = context.getResources().getStringArray(R.array.rate_gj_long_value);
		rebate_values = context.getResources().getStringArray(R.array.rebate_value);
		int cloan_years = Integer.parseInt(cloan_years_values[cloanYearsValueIndex]);//原贷款年数
		int cloan_months = cloan_years*12;//原贷款月数
		double tot_money = cloan_money*10000;//原贷款总额
		double rebate = Double.parseDouble(rebate_values[rebateValueIndex]);//贷款折扣
		double rate = 0;//利率
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
		
		//已经还款期数
		double alreadyPayMonths =0;
		try {
			alreadyPayMonths = utils.diffDate("MM", first_repay, ahead_repay);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bd = getCloan(cloan_years,first_repay,ahead_repay,cloan_months,tot_money,rate,rebate,alreadyPayMonths,aheadRepayMethodMoney*10000,processModeIndex);
		return bd;
	}
	/**
	 * @param first_repay
	 * @param cloan_months
	 * @param tot_money
	 * @param rate
	 * @param rebate
	 * @param alreadyPayMonths
	 * @return
	 */
	public BaseDataEntity getCloan(int cloan_years,String first_repay,String ahead_repay,int cloan_months,double tot_money,double rate,double rebate,double alreadyPayMonths,double aheadRepayMethodMoney,int processModeIndex){
		BaseDataEntity row = new BaseDataEntity();
		//原每月还款
		double originalMonthPayment = getMontMnney(utils.round(rate/100*rebate,4),tot_money,cloan_months);
		double monthRate = utils.round(rate/100*rebate,4)/12;
		//原最后还款期
		String originalLastDate = getLastDate(first_repay,cloan_months-1);
		//已经还款金额
		double alreaddyPayMoney = originalMonthPayment*alreadyPayMonths;
		
		//已还利息额
		double alreaddyPayRate = 0;
		//已换本金
		double alreaddyPayBenjing = 0;
		for(int i = 1; i <= alreadyPayMonths; i++){
			//已还利息=之前已还利息+（总贷款金额-已经还本金）*利息
			alreaddyPayRate = alreaddyPayRate + (tot_money - alreaddyPayBenjing) * monthRate;
			//已经还本金=之前已还本金+原月还款-（总贷款金额-已经还本金）*利息
			alreaddyPayBenjing = alreaddyPayBenjing + originalMonthPayment-(tot_money-alreaddyPayBenjing)*monthRate;
		}
		//计算结果提示：
		String remark = ""; 
		//该月一次还款金额
		double nowPayMoney = 0;
		//节省利息
		double saveRate = 0;	
		//新最后还款期
		String newLastPayDate = ""; 
		//下月起还款额
		double nowMonthPayment = 0;
		//一次还清
		if(aheadRepayMethodMoney+originalMonthPayment>=(tot_money-alreaddyPayBenjing)*(1-monthRate)){
			//该有一次还款额=（总贷款金额-已经还本金）*（1+利息）
			nowPayMoney = (tot_money - alreaddyPayBenjing) * (1 + monthRate);
			//下月起月还款额=0
			nowMonthPayment = 0;
			//节省利息=原月还款*贷款月数-已经还款金额-该有一次还款额
			saveRate = originalMonthPayment * cloan_months - alreaddyPayMoney - nowPayMoney;
			//最新最后还款期
			newLastPayDate = getLastDate(ahead_repay,0);			
		} else {
			//已还本金 = 已还本金 + 原月还款
			alreaddyPayBenjing = alreaddyPayBenjing + originalMonthPayment;
			//该月一次还款金额 = 原月还款 + 提前还款
			nowPayMoney = originalMonthPayment + aheadRepayMethodMoney;
			if(processModeIndex==0){//缩短还款期限，月还款额基本不变
				double alreaddyPayBenjing_temp = alreaddyPayBenjing + aheadRepayMethodMoney;
				//现在贷款期数
				int xdkqs = 0;
				while(alreaddyPayBenjing_temp <= tot_money){
					alreaddyPayBenjing_temp = alreaddyPayBenjing_temp + originalMonthPayment - (tot_money - alreaddyPayBenjing_temp) * monthRate;
					xdkqs++;
				} 
				xdkqs = xdkqs -1;
				nowMonthPayment = getMontMnney(utils.round(rate/100*rebate,4),(tot_money-alreaddyPayBenjing-aheadRepayMethodMoney),xdkqs);
				saveRate = originalMonthPayment * cloan_months - alreaddyPayMoney - nowPayMoney - nowMonthPayment * xdkqs;
				//最新最后还款期
				newLastPayDate = getLastDate(ahead_repay,xdkqs-1);
			} else {
				nowMonthPayment = getMontMnney(utils.round(rate/100*rebate,4),(tot_money-alreaddyPayBenjing-aheadRepayMethodMoney),(cloan_months-(int)alreadyPayMonths));
				saveRate  = originalMonthPayment * cloan_months - alreaddyPayMoney - nowPayMoney - nowMonthPayment * (cloan_months-(int)alreadyPayMonths);
				newLastPayDate = originalLastDate;
			}
		}
		row.spdv("originalMonthPayment", utils.round(originalMonthPayment,2));
		row.spsv("originalLastDate", originalLastDate);
		row.spdv("alreaddyPayMoney", utils.round(alreaddyPayMoney,2));
		row.spdv("alreaddyPayRate", utils.round(alreaddyPayRate,2));
		row.spdv("nowPayMoney", utils.round(nowPayMoney,2));
		row.spdv("nowMonthPayment", utils.round(nowMonthPayment,2));
		row.spdv("saveRate", utils.round(saveRate,2));
		row.spsv("newLastPayDate",newLastPayDate);
		row.spsv("remark", "");
		return row;
	}
	//计算最后还款日期
	public String getLastDate(String first_repay,int cloan_months){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");  				
		Date date1=null;
		try {
			date1 = formatter.parse(first_repay);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String lastDate = utils.formatDate(date1, utils.MONTH, cloan_months);
		return lastDate.substring(0, 4)+"年"+lastDate.substring(5, 7)+"月";
	}
	
	/**
	 * 计算月还款额（本息）
	 */
	public double getMontMnney(double rate, double tot_money, int cloan_months){
		double rate_month = rate / 12;//月利率
		//return utils.round(tot_money * rate_month * Math.pow(1 + rate_month, cloan_months) / ( Math.pow(1 + rate_month, cloan_months) -1 ),2); 
		return tot_money * rate_month * Math.pow(1 + rate_month, cloan_months)/ ( Math.pow(1 + rate_month, cloan_months) -1 );
	}
}
