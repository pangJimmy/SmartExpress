package com.psw.smartexpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.psw.smartexpress.base.MBaseActivity;

public class AddSuccessActivity extends MBaseActivity implements OnClickListener{

	private TextView tvTip ;
	private Button btnAddAgain ;
	private Button btnBackMain ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addsuccess) ;
		setTitle("Ìí¼Ó³É¹¦") ;
		initView() ;
		
	}
	
	private void initView(){
		tvTip = (TextView) findViewById(R.id.textView_tips) ;
		btnAddAgain = (Button) findViewById(R.id.button_back_add) ;
		btnBackMain = (Button) findViewById(R.id.button_back_main) ;
		
		btnAddAgain.setOnClickListener(this) ;
		btnBackMain.setOnClickListener(this) ;
	}
	@Override
	public void onClick(View v) {
		Intent intent = null ;
		switch (v.getId()) {
		case R.id.button_back_add:
			intent = new Intent(this, GetPackageActivity.class) ;
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
			break;
		case R.id.button_back_main:
			intent = new Intent(this, MainActivity.class) ;
			break;

		default:
			break;
		}
		
		startActivity(intent) ;
		finish() ;
	}
}
