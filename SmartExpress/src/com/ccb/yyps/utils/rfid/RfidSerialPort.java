package com.ccb.yyps.utils.rfid;

import java.util.List;

import android.util.Log;

public class RfidSerialPort {

	
	public RfidSerialPort(){
//		open();
	}
	
	
	
	static{
//		System.loadLibrary("devapi");
//		System.loadLibrary("RFIDSerialPort");
//		System.load("/system/lib/libdevapi.so");
//		System.load("/system/lib/libRFIDSerialPort.so");
		
	}

	

	public native int open();

	public native int setOutputPower(int value);

	public native int uhfPoweron();
	
	public native int uhfPoweroff();
	
	public native int uhfTrigeron();
	
	public native List<String> getUhfData();
	
	public native int uhfTrigeroff();
	
	public native int uhfSetFrequency(int freMode, int freBase, int baseFre, 
			int channNum, int channSpc, int freHop);
	
	public native int uhfWriteData (int bank, int addr, int cnt, String writeData);

	public native int uhfWriteEPC (int cnt, String newEpc);
	
	public native int uhfSleepMode ();
	
	public native int close();

}
