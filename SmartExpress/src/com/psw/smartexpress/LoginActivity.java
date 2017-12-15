package com.psw.smartexpress;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.psw.smartexpress.application.Mapplication;
import com.psw.smartexpress.base.MBaseActivity;
import com.psw.smartexpress.entity.PackList;
import com.psw.smartexpress.util.Loger;
import com.psw.smartexpress.util.ParaSave;
import com.psw.smartexpress.web.HttpHelper;
import com.psw.smartexpress.web.WebCallBack;
import com.psw.smartexpress.web.WebService;

public class LoginActivity extends MBaseActivity implements OnClickListener, OnCheckedChangeListener{
	
	private EditText editUser ;
	private EditText editPassword ;
	private Button btnLogin ; 
	private Button btnSettings; 
	private CheckBox checkSave ;//是否记住密码
	
	
	private Mapplication mapp ;
	private WebService web ;
	
	private Handler handler = new Handler() ;
	private ParaSave paraSave ;
	private boolean isSave = false ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login) ;
		setTitle("电商物流智能手持应用系统") ;
		setBtnBackClickable(false) ;
		setCancelUnvisible() ;
		mapp = (Mapplication) getApplication() ;
		web = new WebService(mapp) ;
		paraSave = new ParaSave(this) ;
		initView() ;
	}
	
	private void initView(){
		editUser = (EditText) findViewById(R.id.editText_user) ;
		editPassword = (EditText) findViewById(R.id.editText_password) ;
		btnLogin = (Button) findViewById(R.id.button_login) ;
		btnSettings = (Button) findViewById(R.id.button_settings) ;
		checkSave = (CheckBox) findViewById(R.id.checkBox_save) ;
		editUser.setText(paraSave.getUser()) ;
		editPassword.setText(paraSave.getPassword()) ;
		
		btnLogin.setOnClickListener(this) ;
		btnSettings.setOnClickListener(this) ;
		checkSave.setChecked(true) ;
		checkSave.setOnCheckedChangeListener(this) ;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_login:
			new Thread(loginTask2).start() ;
			break;
		case R.id.button_settings:
			Intent i = new Intent(this, SettingsActivity.class) ;
			startActivity(i) ;
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		isSave = isChecked ;
		
	}

	
	String user ;
	String password ;
	private void login(){
		user = editUser.getText().toString() ;
		password = editPassword.getText().toString() ;
		if(user == null || user.length() == 0){
			ToastShow("请输入用户名") ;
			editUser.setFocusable(true) ;
			return ;
		}
		
		if(password == null || password.length() == 0){
			ToastShow("请输入密码") ;
			editUser.setFocusable(true) ;
			return ;
		}
		web.login(user, password, new WebCallBack() {
			
			@Override
			public void call(final Object obj, final String errMsg) {
				if(errMsg != null && "true".equals(errMsg)){
					updateInfo("", true) ;
				}else{
					updateInfo(errMsg, false) ;
				}
				
			}
		}) ;
		
	}
	
	//根据登陆flag提示信息
	private void updateInfo(final String msg, final boolean flag) {
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				if(flag){
					//成功
//					ToastShow("登陆成功") ;
					mapp.setUserName(user) ;
					mapp.setLoginSuccess(true) ;
					LoginActivity.this.finish() ;
					Intent intent = new Intent(LoginActivity.this, MainActivity.class) ;
					startActivity(intent) ;
				}else{
					//失败
					ToastShow("登陆失败" + msg) ;
					mapp.setLoginSuccess(false) ;
				}
				
			}
		}) ;

	}
	
	private Runnable loginTask2 = new Runnable() {
		@Override
		public void run() {
			
			user = editUser.getText().toString() ;
			password = editPassword.getText().toString() ;
			if(user == null || user.length() == 0){
				ToastShow("请输入用户名") ;
				editUser.setFocusable(true) ;
				return ;
			}
			
			if(password == null || password.length() == 0){
				ToastShow("请输入密码") ;
				editUser.setFocusable(true) ;
				return ;
			}

			HttpHelper http = new HttpHelper(LoginActivity.this) ;
//			List<PackList> list = http.getLList() ;
//			if(list != null){
//				for(PackList p : list){
//					Loger.e("+++++++", p.toString()) ;
//				}
//			}
			String resp = http.login(user, password) ;
			resp = resp.trim() ;
			Loger.e("", resp) ;
			if("true".equals(resp)){
				updateInfo("", true) ;
				
				paraSave.setUser(user) ;
				paraSave.setPassword(password);
			}else{
				updateInfo("", false) ;
			}
			
		}
	};
	
	private Runnable loginTask = new Runnable() {
		
		@Override
		public void run() {
			//登陆
//			login() ;
			
			//第一步：创建HttpClient对象
			try{
			HttpClient httpCient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet("http://123.56.138.171:2001/Services/pda_Services.asmx/userLoad?username=bbbbbb&password=123456");
			HttpResponse httpResponse = httpCient.execute(httpGet);
			 if (httpResponse.getStatusLine().getStatusCode() == 200) {
				 HttpEntity entity = httpResponse.getEntity();
				String response = EntityUtils.toString(entity,"utf-8");//将entity当中的数据转换为字符串
				//解析数据
				ByteArrayInputStream bi = new ByteArrayInputStream(response.getBytes()) ;
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput(bi, "UTF-8") ;
				
				int eventType = parser.getEventType(); 
				while (eventType != XmlPullParser.END_DOCUMENT) { 
					
		            switch (eventType) {  
	                case XmlPullParser.START_DOCUMENT:  
//	                    books = new ArrayList<Book>();  
	                    break;  
	                case XmlPullParser.START_TAG:  
	                	String tag = parser.getName() ;
	                	int index = parser.next() ;
	                	String dataString = parser.getText() ;
	                    break;  
	                case XmlPullParser.END_TAG:  
	                    break;  
	            } 
		            
		            eventType = parser.next();
				}
			 }
			}catch(Exception e){
				
			}
		}
	}; 
}
