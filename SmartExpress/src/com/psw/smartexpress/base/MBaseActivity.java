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
 * activity���࣬���ú����͹��ó�Ա����
 * @author mac
 *
 */
public class MBaseActivity extends Activity {

	private TextView tvTitle ;  //����
	private Button tvBack ;	//����
	private TextView tvCancel ;//ע��
	
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
	
	
	//����ע�����ɼ�
	public void setCancelUnvisible(){
//		tvCancel.setVisibility(View.GONE) ;
		tvCancel.setText("") ;
	}
	
	//���ñ���
	public void setTitle(String title){
		tvTitle.setText(title) ;
	}
	
	//���ñ���
	public void setTitle(int stringR){
		tvTitle.setText(stringR) ;
	}
	
	//���÷��ؼ��Ƿ����
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
	
	//ע��ѯ�ʽӿ�
	private void createCancelDialog(){
		Builder  builder = new Builder(this) ;
		builder.setTitle("ע��") ;
//		builder.setMessage("�Ƿ�ע����ǰ�û���" +  mapp.getLoginResult().getUserName() + "��") ;
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(MBaseActivity.this, LoginActivity.class) ;
				startActivity(intent) ;
				finish() ;
				
			}
			}) ;
		
		builder.setNegativeButton("ȡ��", null) ;
		
		
		dialogLoading = builder.create() ;
		dialogLoading.show() ;
	}
	
	
	
	//��ʾ��Ϣ
	public void ToastShow(String info){
		
		Toast.makeText(this, info	, 0).show()  ;
	}
	
	//��ʾ��Ϣ
		public void ToastShow(int resId){
			
			Toast.makeText(this, resId	, 0).show()  ;
		}
		
		/**
		 * �ж����������Ƿ����
		 * @param context
		 * @return
		 */
		public  boolean isNetworkAvailable(Context context) {   
	        ConnectivityManager cm = (ConnectivityManager) context   
	                .getSystemService(Context.CONNECTIVITY_SERVICE);   
	        if (cm == null) {   
	        } else {//��������������ж���������
	        	//�����ʹ�� cm.getActiveNetworkInfo().isAvailable();  
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
