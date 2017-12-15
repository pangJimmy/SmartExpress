package com.psw.smartexpress;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.psw.smartexpress.base.MBaseActivity;
import com.psw.smartexpress.web.HttpHelper;

public class SendPackageActivity extends MBaseActivity implements OnClickListener{

	private EditText editReceiver ;
	private EditText editOrder ;
	
	private Button btnCommit ;
	private HttpHelper http ; 
	private TextView tvResult ;
	private Handler handler = new Handler() ;
	
	private String orderStr = "";
	private String receiver = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendpackage) ;
		setTitle("派送") ;
		setCancelUnvisible() ;
		http = new HttpHelper(this) ;
		initView() ;
	}
	
	private void initView(){
		editReceiver = (EditText) findViewById(R.id.edit_receiver) ;
		editOrder = (EditText) findViewById(R.id.editText_order) ;
		btnCommit = (Button) findViewById(R.id.button_commit) ;
		tvResult = (TextView) findViewById(R.id.textView_result) ;
		btnCommit.setOnClickListener(this) ;
	}

	@Override
	public void onClick(View v) {
		orderStr = editOrder.getText().toString().trim() ;
		receiver = editReceiver.getText().toString().trim() ;
		if(orderStr == null || orderStr.length() == 0){
			ToastShow("请输入快递单号") ;
			return ;
		}
		if(receiver == null || receiver.length() == 0){
			ToastShow("请输入签收人姓名") ;
			return ;
		}
		
		new Thread(sendTask).start() ;
		
	}
	
	private Runnable sendTask = new Runnable() {
		
		@Override
		public void run() {
			final String resp = http.sendPackage(orderStr, receiver) ;
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					String msg = "单号：" + orderStr+ "; " ;
					if("0".equals(resp)){
						msg += "签收失败" ;
					}else if("1".equals(resp)){
						msg += "签收成功" ;
					}else if("2".equals(resp)){
						msg += "快件处于派送状态或已被签收，不能进行签收操作" ;
					}else if("3".equals(resp)){
						msg += "查询无果" ;
					}
					tvResult.setText(msg) ;
				}
			}) ;
		}
	};
}
