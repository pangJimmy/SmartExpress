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
		setTitle("����") ;
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
			ToastShow("�������ݵ���") ;
			return ;
		}
		if(receiver == null || receiver.length() == 0){
			ToastShow("������ǩ��������") ;
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
					String msg = "���ţ�" + orderStr+ "; " ;
					if("0".equals(resp)){
						msg += "ǩ��ʧ��" ;
					}else if("1".equals(resp)){
						msg += "ǩ�ճɹ�" ;
					}else if("2".equals(resp)){
						msg += "�����������״̬���ѱ�ǩ�գ����ܽ���ǩ�ղ���" ;
					}else if("3".equals(resp)){
						msg += "��ѯ�޹�" ;
					}
					tvResult.setText(msg) ;
				}
			}) ;
		}
	};
}
