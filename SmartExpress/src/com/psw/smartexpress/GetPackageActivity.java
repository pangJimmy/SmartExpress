package com.psw.smartexpress;

import java.io.IOException;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import cn.pda.scan.ScanThread;

import com.psw.smartexpress.application.Mapplication;
import com.psw.smartexpress.base.MBaseActivity;
import com.psw.smartexpress.entity.PackList;
import com.psw.smartexpress.util.Util;
import com.psw.smartexpress.web.HttpHelper;
import com.psw.smartexpress.web.WebService;

public class GetPackageActivity extends MBaseActivity implements OnClickListener ,OnCheckedChangeListener{

	private EditText editOrder ;
	private EditText editGoods ;
	private EditText editGoodsCount ;
	private EditText editPoster ;
	private EditText editPosterTel ;
	private EditText editPosterAddr ;
	private EditText editWendu ;
	private EditText editReceiver;
	private EditText editReceiverTel ;
	private EditText editReceiverAddr ;
	private EditText editPayment ;
	
	private String order ;//订单一定不能为空
	private String goods ;//物品不能为空
	private int goodsCount ;//数量不能为空
	private String poster ;//寄件人不能为空
	private String posterTel ;//电话不能为空
	private String posterAddr ;
	private float temp ;//
	private String receiver ;// 收件人不能为空
	private String receiverTel ;//收件人电话不能为空
	private String receiverAddr ;//收件人地址不能为空
	private int payType = 0;
	private float payment  ;//运费不能为空
	
	private RadioGroup radio ;
	private Button btnCommit ;
	
	private boolean isMonth ;//月付
	private boolean isGet ;//到付
	private boolean isPost ;//寄付
	

	private RadioButton rbMonth ;
	private RadioButton rbGet ;
	private RadioButton rbPost ;
	private Mapplication mapp ;
	private WebService web ;
	HttpHelper helper  ;
	
	private PackList pack ;
	
	private ScanThread scanThread ;
	
	private Handler hander = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 1001){
				String bar = msg.getData().getString("data") ;
				bar = bar.replaceAll("\r\n", "") ;
				bar = bar.replaceAll("\n", "") ;
				editOrder.setText(bar) ;
				Util.play(1, 0) ;
			}
		};
	};
	
	private Handler handler = new Handler() ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getpackage) ;
		setTitle("收件") ;
		setCancelUnvisible() ;
		mapp = (Mapplication) getApplication() ;
		helper = new HttpHelper(this) ;
		pack = mapp.getPack() ;
		Util.initSoundPool(this) ;
		initView() ;
	}
	
	@Override
	protected void onResume() {
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
	
	private void initView() {
		editOrder = (EditText) findViewById(R.id.editText_order) ;
		editGoods = (EditText) findViewById(R.id.editText_goods_name) ;
		editGoodsCount = (EditText) findViewById(R.id.editText_goods_count) ;
		editPoster = (EditText) findViewById(R.id.editText_send_person) ;
		editPosterTel = (EditText) findViewById(R.id.editText_send_person_tel) ;
		editPosterAddr = (EditText) findViewById(R.id.editText_send_person_addr) ;
		editWendu = (EditText) findViewById(R.id.editText_wendu) ;
		editReceiver = (EditText) findViewById(R.id.editText_getpackage_person) ;
		editReceiverTel = (EditText) findViewById(R.id.editText_getpackage_person_tel) ;
		editReceiverAddr = (EditText) findViewById(R.id.editText_getpackage_person_addr) ;
		editPayment = (EditText) findViewById(R.id.editText_payment) ;
		radio = (RadioGroup) findViewById(R.id.radioGroup) ;
		radio.setOnCheckedChangeListener(this) ;
		btnCommit = (Button) findViewById(R.id.button_commit) ;
		rbMonth = (RadioButton) findViewById(R.id.radio_month_pay) ;
		rbGet = (RadioButton) findViewById(R.id.radio_geter_pay) ;
		rbPost = (RadioButton) findViewById(R.id.radio_poster_pay) ;
		rbMonth.setChecked(true) ;
		payType = 0 ;
		btnCommit.setOnClickListener(this) ;
		if(pack != null){
			editOrder.setText(pack.orderNo) ;
			editPoster.setText(pack.jName) ;
			editPosterTel.setText(pack.jTel) ;
			editPosterAddr.setText(pack.jAddr) ;
			editReceiver.setText(pack.sName) ;
			editReceiverTel.setText(pack.sTel) ;
			editReceiverAddr.setText(pack.sAddr) ;
			
		}
	}
	
	@Override
	public void onClick(View v) {
		order = editOrder.getText().toString().trim() ;
		goods = editGoods.getText().toString().trim() ;
		String goodsCountStr = editGoodsCount.getText().toString().trim() ;
		poster = editPoster.getText().toString().trim() ;
		posterTel = editPosterTel.getText().toString().trim() ;
		posterAddr = editPosterAddr.getText().toString().trim() ;
		String tempStr = editWendu.getText().toString().trim() ;
		receiver = editReceiver.getText().toString().trim() ;
		receiverTel = editReceiverTel.getText().toString().trim() ;
		receiverAddr = editReceiverAddr.getText().toString().trim() ;
		String paymentStr  = editPayment.getText().toString().trim() ;
		if(order == null || order.length() == 0){
			ToastShow("请输入快递单号") ;
			return ;
		}
		if(goods == null || goods.length() == 0){
			ToastShow("请输入物品名称") ;
			return ;
		}
		if(goodsCountStr == null || goodsCountStr.length() == 0){
			ToastShow("请输入物品数量") ;
			return ;
		}
		goodsCount = Integer.valueOf(goodsCountStr) ;
		if(poster == null || poster.length() == 0){
			ToastShow("请输入寄件人姓名") ;
			return ;
		}
		if(posterTel == null || posterTel.length() == 0){
			ToastShow("请输入寄件人电话") ;
			return ;
		}
		if(receiverAddr == null || receiverAddr.length() == 0){
//			ToastShow("请输入快递单号") ;
//			return ;
			receiverAddr = "" ;
		}
		if(tempStr == null || tempStr.length() == 0){
			ToastShow("请输入存储温度") ;
			return ;
		}
		temp = Float.valueOf(tempStr) ;
		if(receiver == null || receiver.length() == 0){
			ToastShow("请输入收件人姓名") ;
			return ;
		}
		if(receiverTel == null || receiverTel.length() == 0){
			ToastShow("请输入收件人电话") ;
			return ;
		}
		if(receiverAddr == null || receiverAddr.length() == 0){
			ToastShow("请输入收件人地址") ;
			return ;
		}
		if(paymentStr == null || paymentStr.length() == 0){
			ToastShow("请输入运费金额") ;
			return ;
		}
		payment = Float.valueOf(paymentStr) ;
		new Thread(commitTask).start() ;
		
	}
	
	private Runnable commitTask  = new Runnable() {
		
		@Override
		public void run() {
//			order = "1111222233334444" ;
//			goods = "以" ;
//			goodsCount = 1 ;
//			poster = "的" ;
//			posterTel = "111122223333444" ;
//			posterAddr = "1111222233334444" ;
//			temp = (float)(Math.round(20*100))/100;
//			receiver = "王" ;
//			receiverTel = "111122223334444" ;
//			receiverAddr = "ww" ;
//			payType = 1 ;
//			payment = (float)(Math.round(10*100))/100; ;
//			
			
			final String resp = helper.addPackage(order, goods, 
					goodsCount, poster, 
					posterTel, posterAddr,
					temp, receiver, 
					receiverTel, receiverAddr, 
					payType, payment, mapp.getUserName()) ;
			
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					if("0".equals(resp)){
						ToastShow("添加失败") ;
					}else if("1".equals(resp)){
						ToastShow("该快递单号已存在") ;
					}else if("2".equals(resp)){
						ToastShow("添加成功") ;
						Intent i = new Intent(GetPackageActivity.this, AddSuccessActivity.class) ;
						startActivity(i) ;
						finish() ;
					}
				}
			}) ;
		}
	};
	
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		isMonth = false ;
		isGet = false ;
		isPost = false ;
		switch (group.getCheckedRadioButtonId() ) {
		case R.id.radio_month_pay:
			if(rbMonth.isChecked()){
				payType = 0 ;
			}
			break;
		case R.id.radio_geter_pay:
			if(rbGet.isChecked()){
				payType = 1 ;
			}
			break;
		case R.id.radio_poster_pay:
			if(rbPost.isChecked()){
				payType = 2 ;
			}
			break;

		default:
			break;
		}
		
	}
	
}
