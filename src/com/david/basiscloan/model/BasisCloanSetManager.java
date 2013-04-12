package com.david.basiscloan.model;

import android.content.Context;

import com.david.cloancalculator.util.BaseDataEntity;

public interface BasisCloanSetManager {
	public BaseDataEntity calculateCloan(Context context,String repayment_type,String cloan_type,int cloanYearsValueIndex,int rateValueIndex,int rebateValueIndex,double cloan_money)throws Exception;
		
}
