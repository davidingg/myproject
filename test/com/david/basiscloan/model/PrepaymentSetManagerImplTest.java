package com.david.basiscloan.model;

import com.david.cloancalculator.util.BaseDataEntity;

import android.util.Log;
import junit.framework.TestCase;

public class PrepaymentSetManagerImplTest extends TestCase {
	PrepaymentSetManagerImpl pmanager = new PrepaymentSetManagerImpl();
	public void testCalculateCloan() {
		
		
		//fail("Not yet implemented");
	}

	public void testGetCloan() {
		
		BaseDataEntity result = pmanager.getCloan(30, "2012/04/05", "2013/04/05", 360, 266000, 6.55, 1, 12, 260000, 0);
		//fail("Not yet implemented");
	}

	public void testGetLastDate() {
		String lastdate = pmanager.getLastDate("2012/03/01", 360);
		Log.i("dwtest",String.valueOf(lastdate));
		//fail("Not yet implemented");
	}


	public void testGetMontMnney() {
		//fail("Not yet implemented");
	}



}
