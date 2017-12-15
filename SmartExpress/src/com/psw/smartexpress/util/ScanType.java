package com.psw.smartexpress.util;

import com.ccb.yyps.utils.scaner.ScanerSerialPort;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

//扫描类型
public class ScanType {
	ScanerSerialPort scan ;
	private Handler mhandler ;
	public ScanType(int scantype ,Handler mhandler){
		scan = new ScanerSerialPort();
		readThread.start() ;
		this.mhandler = mhandler ;
		int openSt = scan.open();
		Log.e("", "open---->" + openSt);
		int powerSt = scan.scanerPoweron();
		Log.e("", "scanerPoweron---->" + powerSt);
	}
	
	public void scan(){
		
		scan.scanerTrigeron();
//		readThread.start() ;
	}
	
	public void stop (){
		readThread.interrupt() ;
	}
	
	//获取条码数据线程
	private Thread readThread = new Thread(new Runnable() {
		String barCode = null;
		@Override
		public void run() {
			while(!readThread.isInterrupted()){
				barCode = scan.getScanerData();
				if(barCode != null){
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("barcode", barCode);
					msg.setData(bundle);
					msg.what = 1000 ;
					mhandler.sendMessage(msg);
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	});
}
