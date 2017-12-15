package cn.pda.scan;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.pda.serialport.SerialPort;

public class ScanThread extends Thread {
	
	private SerialPort mSerialPort ;
	private InputStream is ;
	private OutputStream os ;
	/*serialport parameter*/
	private int port = 0;
	private int baudrate = 9600;
	private int flags = 0;
	
	private Handler handler ;
	
	
	public static int SCAN = 1001;  //messege recv mode
	
	/**
	 * if throw exception, serialport initialize fail.
	 * @throws SecurityException
	 * @throws IOException
	 */
	public ScanThread(Handler handler) throws SecurityException, IOException{
		this.handler = handler;
		mSerialPort = new SerialPort(port, baudrate, flags);
		
		is = mSerialPort.getInputStream();
		os = mSerialPort.getOutputStream();
		
		mSerialPort.scaner_poweron();
		mSerialPort.scaner_trigoff() ;
		
//		
//
//		
//		mSerialPort.setGPIOlow(145) ;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/** clear useless data **/
		byte[] temp = new byte[128];
		is.read(temp);
		
	}
	
	@Override
	public void run() {
		try {
			int size = 0;
			byte[] buffer = new byte[4096];
			int available = 0;
			while(!isInterrupted()){
				available = is.available();
				if(available > 0){
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					size = is.read(buffer);
					if(size > 0){
						sendMessege(buffer, size, SCAN);
						if(mtimer != null){
							mtimer.cancel();
						}
					}
				}
			}
		} catch (IOException e) {
			//返回错误信息
			e.printStackTrace();
		}
		super.run();
	}
	
	
	private final byte START = 0x02 ;
	private final byte END = 0x03 ;
	private void sendMessege(byte[] data, int dataLen, int mode){
//		if(data[0] == START && data[dataLen-1] == END){
			String dataStr = new String(data,0, dataLen );
//			String dataStr = "";
//			try {
//				dataStr = new String(data, 0 , dataLen, "GBK");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Log.e("barcode", dataStr);
			Bundle bundle = new Bundle();
			bundle.putString("data", dataStr);
			Message msg = new Message();
			msg.what = mode;
			msg.setData(bundle);
			handler.sendMessage(msg);
//		}

	}
	
	Timer mtimer = null;
	
	public void scan(){
//		mSerialPort.setGPIOhigh(145) ;
		if(mtimer != null){
			mtimer.cancel();
		}
		mtimer = new Timer();
		mtimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				mSerialPort.scaner_trigoff();
//				mSerialPort.setGPIOlow(145) ;
			}
		}, 5000);
		if(mSerialPort.scaner_trig_stat() == true){
			mSerialPort.scaner_trigoff();
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		mSerialPort.scaner_trigon();
	}
	
	public void close(){
		if(mSerialPort != null){
//			mSerialPort.setGPIOlow(145) ;
			mSerialPort.scaner_poweroff();
//	mSerialPort.rfidPoweroff();
//			mSerialPort.psam_poweroff();
			mSerialPort.zigbeepoweroff();
			
			try {
			if(is != null){
				is.close();
			}
			if(os != null){
				os.close();
			}
			} catch (IOException e) {
				e.printStackTrace();
			}
			mSerialPort.close(port);
		}
	}

}
