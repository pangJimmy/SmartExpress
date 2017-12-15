package com.psw.smartexpress;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.pda.scan.ScanThread;

import com.psw.smartexpress.base.MBaseActivity;
import com.psw.smartexpress.entity.PackStatus;
import com.psw.smartexpress.util.ScanType;
import com.psw.smartexpress.util.Util;
import com.psw.smartexpress.web.HttpHelper;

public class QueryOrderActivity extends MBaseActivity implements OnClickListener{

	private EditText editOrder ;
	private Button btnQuery ;
	private TextView tvResult ;
	private String order ;
	private HttpHelper http ;
	
	
	
	private TextView tv_o_codeNum ;
	private TextView tv_o_status ;
	private TextView tv_o_lName ;
	private TextView tv_o_lTime ;
	private TextView tv_o_zName ;
	private TextView tv_o_zLocaltion ;
	private TextView tv_o_zTime ;
	private TextView tv_o_carNum ;
	private TextView tv_o_carTime ;
	private TextView tv_o_pName ;
	private TextView tv_o_pLocaltion;
	private TextView tv_o_pTime ;
	private TextView tv_o_kName ;
	private TextView tv_o_ksTime ;
	private TextView tv_o_qName ;
	private TextView tv_o_qTime ;
	private LinearLayout layout ;
	
	private ScanType scan ;
	
	private ScanThread scanThread ;
	
	private Handler hander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 1001){
				String bar = msg.getData().getString("data") ;
				bar = bar.replaceAll("\r\n", "") ;
				bar = bar.replaceAll("\n", "") ;
				editOrder.setText(bar) ;
				query() ;
				Util.play(1, 0) ;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_queryorder);
		setTitle("查询订单") ;
		setCancelUnvisible() ;
		http = new HttpHelper(this) ;
//		scan = new ScanType(1, hander) ;
		Util.initSoundPool(this) ;
		initView();
	}
	
	@Override
	protected void onResume() {
		IntentFilter filter = new IntentFilter() ;
		filter.addAction("android.rfid.FUN_KEY") ;
		registerReceiver(receiver, filter) ;
		try {
			scanThread = new ScanThread(hander) ;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scanThread.start() ;
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		unregisterReceiver(receiver) ;
		scanThread.close() ;
		super.onPause();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == 133 || keyCode == 134 || keyCode == 135 ||
				keyCode == 136){
			scanThread.scan() ;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	private void initView(){
		editOrder = (EditText) findViewById(R.id.editText_order) ;
		btnQuery = (Button) findViewById(R.id.button_query) ;
		tvResult = (TextView) findViewById(R.id.textView_result) ;
		layout = (LinearLayout) findViewById(R.id.layout_all) ;
		layout.setVisibility(View.GONE) ;
		tv_o_codeNum = (TextView) findViewById(R.id.textView_o_codeNum) ;
		tv_o_status = (TextView) findViewById(R.id.textView_o_status) ;
		tv_o_lName = (TextView) findViewById(R.id.textView_o_lName) ;
		tv_o_lTime = (TextView) findViewById(R.id.textView_o_lTime) ;
		tv_o_zName = (TextView) findViewById(R.id.textView_o_zName) ;
		tv_o_zLocaltion = (TextView) findViewById(R.id.textView_o_zLocaltion) ;
		tv_o_zTime = (TextView) findViewById(R.id.textView_o_zTime) ;
		tv_o_carNum = (TextView) findViewById(R.id.textView_o_carNum) ;
		tv_o_carTime = (TextView) findViewById(R.id.textView_o_carTime) ;
		tv_o_pName = (TextView) findViewById(R.id.textView_o_pName) ;
		tv_o_pLocaltion = (TextView) findViewById(R.id.textView_o_pLocaltion) ;
		tv_o_pTime = (TextView) findViewById(R.id.textView_o_pTime) ;
		tv_o_kName = (TextView) findViewById(R.id.textView_o_kName) ;
		tv_o_ksTime = (TextView) findViewById(R.id.textView_o_ksTime) ;
		tv_o_qName = (TextView) findViewById(R.id.textView_o_qName) ;
		tv_o_qTime = (TextView) findViewById(R.id.textView_o_qTime) ;
		btnQuery.setOnClickListener(this) ;
	}

	
	@Override
	public void onClick(View v) {

		query() ;
	}
	
	private void query(){
		order = editOrder.getText().toString().trim() ;
		if(order == null || order.length() == 0){
			ToastShow("请输入单号") ;
			return ;
		}
		new Thread(queryTask).start() ;
	}
	
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			int keyCode = intent.getIntExtra("keyCode", 0) ;
			if(keyCode == 0){//����H941
				keyCode = intent.getIntExtra("keycode", 0) ;
			}
			boolean keyDown = intent.getBooleanExtra("keydown", false) ;
			Log.e("", "KEYcODE = " + keyCode + ", Down = " + keyDown);
			if(keyDown){
//				scan.scan() ;
				scanThread.scan() ;
			}
			
		}
	};
	
	
	private Runnable queryTask = new Runnable() {
		
		@Override
		public void run() {
			final PackStatus pack = http.queryOrder(order) ;
			hander.post(new Runnable() {
				
				@Override
				public void run() {
					if(pack != null){
						layout.setVisibility(View.VISIBLE) ;
						tv_o_codeNum.setText(pack.o_codeNum) ;
						tv_o_status.setText(pack.o_status) ;
						tv_o_lName.setText(pack.o_lName) ;
						tv_o_lTime.setText(pack.o_lTime) ;
						tv_o_zName.setText(pack.o_zName) ;
						tv_o_zLocaltion.setText(pack.o_zLocaltion) ;
						tv_o_zTime.setText(pack.o_zTime) ;
						tv_o_carNum.setText(pack.o_carNum) ;
						tv_o_carTime.setText(pack.o_carTime) ;
						tv_o_pName.setText(pack.o_pName) ;
						tv_o_pLocaltion.setText(pack.o_pLocaltion) ;
						tv_o_pTime.setText(pack.o_pTime) ;
						tv_o_kName.setText(pack.o_kName) ;
						tv_o_ksTime.setText(pack.o_ksTime) ;
						tv_o_qName.setText(pack.o_qName) ;
						tv_o_qTime.setText(pack.o_qTime) ;
					}else{
						layout.setVisibility(View.GONE) ;
						ToastShow("无此单信息") ;
					}

				}
			}) ;
		}
	};
	
}
