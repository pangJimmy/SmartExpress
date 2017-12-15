package com.psw.smartexpress.util;

import android.util.Log;
/**
 * 日志打印控制
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
