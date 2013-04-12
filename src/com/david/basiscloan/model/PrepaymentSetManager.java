package com.david.basiscloan.model;

import android.content.Context;

import com.david.cloancalculator.util.BaseDataEntity;

public interface PrepaymentSetManager {
	/**
	 * 
	 * @param context 上下文
	 * @param cloan_type 还款类型
	 * @param cloan_money 原贷款金额
	 * @param cloanYearsValueIndex 贷款年限
	 * @param rateValueIndex 汇率
	 * @param rebateValueIndex 折扣
	 * @param first_repay 第一次还款日期
	 * @param ahead_repay 提前还款日期
	 * @param aheadRepayMethodIndex 提前还款方式
	 * @param aheadRepayMethodMoney 提前还款金额
	 * @param processModeIndex 处理方式
	 * @return
	 * @throws Exception
	 */
	public BaseDataEntity calculateCloan(Context context,String cloan_type,double cloan_money,int cloanYearsValueIndex,int rateValueIndex,int rebateValueIndex,String first_repay,String ahead_repay,double aheadRepayMethodMoney,int processModeIndex)throws Exception;
		
}
