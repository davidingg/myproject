package com.david.basiscloan.model;

import junit.framework.TestCase;

import android.util.Log;

import com.david.cloancalculator.util.BaseDataEntity;
import com.david.incomtax.model.IncomTaxSetManagerImpl;

public class IncomTaxSetManagerImplTest extends TestCase {

	public void testCalculateTax() {
		IncomTaxSetManagerImpl iManager = new IncomTaxSetManagerImpl();
	
	}

	public void testGetIncomeTaxTierIndex() {
		IncomTaxSetManagerImpl iManager = new IncomTaxSetManagerImpl();
		int i = iManager.getIncomeTaxTierIndex(5200);
		Log.i("dwtest",String.valueOf(i));
	}

}
