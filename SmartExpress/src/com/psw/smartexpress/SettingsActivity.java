package com.psw.smartexpress;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.psw.smartexpress.base.MBaseActivity;
import com.psw.smartexpress.util.ParaSave;
/**
 * ���ý��棬����ip��ַ����Ϣ
 * @author mac
 *
 */
public class SettingsActivity extends MBaseActivity implements OnClickListener{

	private EditText editIp ;
	private EditText editPort ;
	private Button btnSave ;
	String ip = null ;
	String port = null ;
	ParaSave para ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings) ;
		setTitle("����") ;
		para =  new ParaSave(this) ;
		initView() ;
	}
	
	private void initView(){
		editIp = (EditText) findViewById(R.id.editText_ip) ;
		editPort = (EditText) findViewById(R.id.edit_port) ;
		btnSave = (Button) findViewById(R.id.button_save_settings) ;
		editIp.setText(para.getIP()) ;
		editPort.setText(para.getPort()) ;
		btnSave.setOnClickListener(this) ;
	}

	@Override
	public void onClick(View v) {
		ip = editIp.getText().toString().trim() ;
		port = editPort.getText().toString().trim() ;
		if(ip == null || ip.length() == 0){
			 ToastShow("������ip") ;
			 editIp.setFocusable(true) ;
			 return ;
		}
		
		if(port == null || port.length() == 0){
			 ToastShow("������˿ں�") ;
			 editIp.setFocusable(true) ;
			 return ;
		}
		para.setIp(ip) ;
		para.setPort(port)  ;
		ToastShow("���óɹ�") ;
	}
}
