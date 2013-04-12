package com.david.basiscloan.model;

import android.test.AndroidTestCase;
import android.util.Log;

import com.david.cloancalculator.util.BaseDataEntities;
import com.david.cloancalculator.util.BaseDataEntity;

public class BasisCloanSetManagerImplTest extends AndroidTestCase {

	public void testCalculateCloan() {
	
	}

	public void testGetBusinessCloan() {
		BasisCloanSetManagerImpl bManager = new BasisCloanSetManagerImpl();
		BaseDataEntity result=bManager.getBusinessCloan(6.55, 500000.0, 360, 1.0);
		
		Log.i("dwtest",String.valueOf(result.gpdv("monthPayment")));
		Log.i("dwtest",String.valueOf(result.gpdv("repayMoney")));
		Log.i("dwtest",String.valueOf(result.gpdv("interest")));
	}

	public void testGetProvidentCloan() {
		BasisCloanSetManagerImpl bManager = new BasisCloanSetManagerImpl();
		BaseDataEntity result=bManager.getProvidentCloan(6.55, 500000.0, 360, 1.0);
		BaseDataEntities bes = (BaseDataEntities)result.gpov("monthPayment");
		for(int i = 0;i<bes.size();i++){
			BaseDataEntity row = bes.get(i);
			Log.i("dwtest",row.gpsv("monthPayment"));
		}
		Log.i("dwtest",String.valueOf(result.gpdv("repayMoney")));
		Log.i("dwtest",String.valueOf(result.gpdv("interest")));
		System.out.println("111");
	}

	public void testGetMontMnney() {
	
	}

	public void testGetMonthMoney2() {
		BasisCloanSetManagerImpl bManager = new BasisCloanSetManagerImpl();
		double result = bManager.getMonthMoney2(0.0655, 500000.0, 360,2);
		Log.i("dwtest",String.valueOf(result));
	}

}
