package com.psw.smartexpress;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.psw.smartexpress.adapter.PackAdapter;
import com.psw.smartexpress.application.Mapplication;
import com.psw.smartexpress.base.MBaseActivity;
import com.psw.smartexpress.entity.PackList;
import com.psw.smartexpress.web.HttpHelper;
//���ռ�����
public class LListActivity extends MBaseActivity  implements OnClickListener, OnItemClickListener{
	private ListView lv ;
	private Button btnFlush ;
	private Button btnGetPack ;

	private HttpHelper http ;
	private Mapplication mapp ;
	
	private Handler handler = new Handler()  ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_l_list) ;
		setTitle("�ռ�") ;
		mapp = (Mapplication) getApplication() ;
		http = new HttpHelper(this) ;
		createLoaddingDialog("�������ش��ռ���Ϣ") ;
		new Thread(getListTask).start() ;
		initView() ;
	}
	
	private void initView(){
		lv = (ListView) findViewById(R.id.listView_llist) ;
		lv.setOnItemClickListener(this) ;
		btnFlush = (Button) findViewById(R.id.button_flush) ;
		btnGetPack = (Button) findViewById(R.id.button_getpackage) ;
		btnFlush.setOnClickListener(this) ;
		btnGetPack.setOnClickListener(this) ;
	}
	
	List<PackList> listPack  = null ;
	//��ȡ�б���Ϣ�߳�
	private Runnable getListTask = new Runnable() {
		
		@Override
		public void run() {
			
			listPack = http.getLList() ;
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					dialogLoading.cancel() ;
					if(listPack != null){
						PackAdapter adapter = new PackAdapter(LListActivity.this, listPack) ;
						lv.setAdapter(adapter) ;
						
					}else{
						ToastShow("�޴��ռ�") ;
					}
					
				}
			}) ;
		}
	};
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_flush:
			createLoaddingDialog("�������ش��ռ���Ϣ") ;
			new Thread(getListTask).start() ;
			break;
		case R.id.button_getpackage:
			mapp.setPack(null) ;
			Intent intent = new Intent(this, GetPackageActivity.class) ;
			startActivity(intent) ;
			break;

		default:
			break;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(listPack != null & !listPack.isEmpty()){
			mapp.setPack(listPack.get(position)) ;
			Intent intent = new Intent(this, GetPackageActivity.class) ;
			startActivity(intent) ;
		}
		
	}
	
	
}
