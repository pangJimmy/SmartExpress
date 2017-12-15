package com.psw.smartexpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.psw.smartexpress.base.MBaseActivity;
/**
 * 主操作界面
 * @author mac
 *
 */
public class MainActivity extends MBaseActivity implements OnClickListener{

	
	private Button tvAdd ;
	private Button tvSend ;
	private Button tvQuery ;
	private Button btnSettings ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main) ;
		setTitle("主页") ;
		setBtnBackClickable(false) ;
		
		initView() ;
	}
	
	private void initView(){
		tvAdd = (Button) findViewById(R.id.textView_getpackage) ;
		tvSend = (Button) findViewById(R.id.textView_sendpackage) ;
		tvQuery = (Button) findViewById(R.id.textView_query_order) ;
		btnSettings = (Button) findViewById(R.id.textView_settings) ;
		tvAdd.setOnClickListener(this) ;
		tvSend.setOnClickListener(this) ;
		tvQuery.setOnClickListener(this) ;
		btnSettings.setOnClickListener(this) ;
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.textView_getpackage:
//			intent = new Intent(this, GetPackageActivity.class) ;
			intent = new Intent(this, LListActivity.class) ;
			break;
		case R.id.textView_sendpackage:
			intent = new Intent(this, SendPackageActivity.class) ;
			break;
		case R.id.textView_query_order:
			intent = new Intent(this, QueryOrderActivity.class) ;
			break;
		case R.id.textView_settings:
			intent = new Intent(this, SettingsActivity.class) ;
			break;

		}
		
		startActivity(intent) ;
	}
	
	long endTime = 0L ;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(System.currentTimeMillis() - endTime < 2000){
				finish() ;
			}
			endTime = System.currentTimeMillis()  ;
			ToastShow("再按一次返回键退出程序") ;
		}
		return false;
	}
	
}
