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
	  	����˵�����򿪴���ͨ�ţ������ڴ˹����з�װ�ô���ͨ�ŵĲ���������˵���ںţ������ʡ�
		����ֵ��0���ɹ���
		      1��ʧ�ܣ�
	 */
	public native int open();
	/**
	 * 	����˵������ɨ���Դ
		����ֵ��0���ɹ���
			1��ʧ�ܣ�
	 */
	public native int scanerPoweron();
	/**
	 * 	����˵�����ر�ɨ���Դ
		����ֵ��0���ɹ���
			1��ʧ�ܣ�
	 */
	public native int scanerPoweroff();
	/**
	 * 	����˵������ɨ�裬��ȡ���룬д������
		����ֵ��0���ɹ���
			1��ʧ�ܣ�
	 */
	public native int scanerTrigeron();

	/**
	 * 	����˵�����õ�ɨ������
		����ֵ������ɨ�����ݣ�
	 */
	public native String getScanerData();

	/**
	 * 	����˵�����ر�ɨ��
		����ֵ��0���ɹ���
			1��ʧ�ܣ�
	 */
	public	native int scanerTrigeroff();

	/**
	 * 	����˵�����رմ���ͨѶ�����ͷ��ڴ档
		����ֵ��0���ɹ���
			1��ʧ�ܣ�
	 */
	public native int close();









}
