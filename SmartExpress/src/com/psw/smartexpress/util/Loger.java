package com.psw.smartexpress.util;

import android.util.Log;
/**
 * ��־��ӡ����
 * @author mac
 *
 */
public class Loger {

	
	public static  boolean debugFlag = true ;
	
	public static void e(String tag, String info){
		if(debugFlag){
			Log.e(tag, info) ;
		}
	}
}
