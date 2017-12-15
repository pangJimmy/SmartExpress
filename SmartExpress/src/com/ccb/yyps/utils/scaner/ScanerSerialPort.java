package com.ccb.yyps.utils.scaner;

import android.util.Log;

public class ScanerSerialPort {

	
	public ScanerSerialPort(){

	}
	
	
	
	static{
//		System.loadLibrary("devapi");
		System.loadLibrary("ScanSerialPort");
//		System.load("/system/lib/libdevapi.so");
//		System.load("/system/lib/libScanSerialPort.so");
	}
	/**
	  	功能说明：打开串口通信，厂家在此功能中封装好串口通信的参数，比如说串口号，波特率。
		返回值：0：成功；
		      1：失败；
	 */
	public native int open();
	/**
	 * 	功能说明：打开扫描电源
		返回值：0：成功；
			1：失败；
	 */
	public native int scanerPoweron();
	/**
	 * 	功能说明：关闭扫描电源
		返回值：0：成功；
			1：失败；
	 */
	public native int scanerPoweroff();
	/**
	 * 	功能说明：打开扫描，读取条码，写入数据
		返回值：0：成功；
			1：失败；
	 */
	public native int scanerTrigeron();

	/**
	 * 	功能说明：得到扫描数据
		返回值：返回扫描数据；
	 */
	public native String getScanerData();

	/**
	 * 	功能说明：关闭扫描
		返回值：0：成功；
			1：失败；
	 */
	public	native int scanerTrigeroff();

	/**
	 * 	功能说明：关闭串口通讯，并释放内存。
		返回值：0：成功；
			1：失败；
	 */
	public native int close();









}
