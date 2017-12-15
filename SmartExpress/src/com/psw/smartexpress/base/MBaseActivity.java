package com.psw.smartexpress.base;


import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.psw.smartexpress.LoginActivity;
import com.psw.smartexpress.R;
/**
 * activity基类，公用函数和公用成员变量
 * @author mac
 *
 */
public class MBaseActivity extends Activity {

	private TextView tvTitle ;  //标题
	private Button tvBack ;	//返回
	private TextView tvCancel ;//注销
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		super.setContentView(layoutResID);
		tvTitle = (TextView) findViewById(R.id.textView_title) ;
		tvBack = (Button) findViewById(R.id.button_back) ;
		tvCancel = (TextView) findViewById(R.id.textView_cancel) ;
		tvCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				createCancelDialog() ;
				
			}
		}) ;
		tvBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish() ;
			}
		});
	}
	
	
	//设置注销不可见
	public void setCancelUnvisible(){
//		tvCancel.setVisibility(View.GONE) ;
		tvCancel.setText("") ;
	}
	
	//设置标题
	public void setTitle(String title){
		tvTitle.setText(title) ;
	}
	
	//设置标题
	public void setTitle(int stringR){
		tvTitle.setText(stringR) ;
	}
	
	//设置返回键是否可用
	public void setBtnBackClickable(boolean flag){
		tvBack.setClickable(flag) ;
		if(!flag){
			tvBack.setBackgroundColor(getResources().getColor(R.color.title_color));
		}
	}
	
	public 	Dialog dialogLoading ;
	public TextView tvMessage  ;
	//create loading dialog
	public void createLoaddingDialog(final String message){
		Builder  builder = new Builder(this) ;
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_loading, null) ;
		tvMessage = (TextView) view.findViewById(R.id.textView_dialog_message) ;
		tvMessage.setText(message) ;
		builder.setView(view) ;
		dialogLoading = builder.create() ;
		dialogLoading.setCancelable(false) ;
		dialogLoading.show() ;
	}
	
	//注销询问接口
	private void createCancelDialog(){
		Builder  builder = new Builder(this) ;
		builder.setTitle("注销") ;
//		builder.setMessage("是否注销当前用户：" +  mapp.getLoginResult().getUserName() + "？") ;
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(MBaseActivity.this, LoginActivity.class) ;
				startActivity(intent) ;
				finish() ;
				
			}
			}) ;
		
		builder.setNegativeButton("取消", null) ;
		
		
		dialogLoading = builder.create() ;
		dialogLoading.show() ;
	}
	
	
	
	//提示信息
	public void ToastShow(String info){
		
		Toast.makeText(this, info	, 0).show()  ;
	}
	
	//提示信息
		public void ToastShow(int resId){
			
			Toast.makeText(this, resId	, 0).show()  ;
		}
		
		/**
		 * 判断网络连接是否可用
		 * @param context
		 * @return
		 */
		public  boolean isNetworkAvailable(Context context) {   
	        ConnectivityManager cm = (ConnectivityManager) context   
	                .getSystemService(Context.CONNECTIVITY_SERVICE);   
	        if (cm == null) {   
	        } else {//如果仅仅是用来判断网络连接
	        	//则可以使用 cm.getActiveNetworkInfo().isAvailable();  
	            NetworkInfo[] info = cm.getAllNetworkInfo();   
	            if (info != null) {   
	                for (int i = 0; i < info.length; i++) {   
	                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
	                        return true;   
	                    }   
	                }   
	            }   
	        }   
	        return false;   
	    } 

}
