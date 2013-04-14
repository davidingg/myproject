package com.david.incomtax.model;

import android.content.Context;

import com.david.cloancalculator.util.BaseDataEntity;

/**
 * @author david
 * @date 2013-4-14 下午9:23:53
 */
public interface IncomTaxSetManager {

	/**
	 * @author david
	 * @param context
	 * @param monthIncome1	月收入
	 * @param insure1	三险一金
	 * @param StartTax1  起征额
	 * @return
	 * @throws Exception
	 */
	BaseDataEntity calculateTax(Context context,double monthIncome1, double insure1, double StartTax1);
}
