package com.psw.smartexpress.adapter;

import java.util.List;

import com.psw.smartexpress.R;
import com.psw.smartexpress.entity.PackList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PackAdapter extends BaseAdapter {

	Context context ;
	private List<PackList> list = null ;
	
	public PackAdapter(Context context , List<PackList> list){
		this.list = list ;
		this.context = context ; 
	}
	
	@Override
	public int getCount() {
		if(list != null){
			return list.size() ;
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if(list != null && !list.isEmpty()){
			list.get(position) ;
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null ;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item_pack, null) ;
			holder = new ViewHolder() ;
			holder.tvId = (TextView) convertView.findViewById(R.id.textView_id) ;
			holder.tvName = (TextView) convertView.findViewById(R.id.textView_name) ;
			holder.tvTel = (TextView) convertView.findViewById(R.id.textView_tel) ;
			convertView.setTag(holder) ;
		}else{
			holder = (ViewHolder) convertView.getTag() ;
		}
		if(list != null && !list.isEmpty()){
			int id = position + 1 ;
			holder.tvId.setText(id + "") ;
			holder.tvName.setText(list.get(position).jName) ;
			holder.tvTel.setText(list.get(position).jTel) ;
		}
		return convertView;
	}
	
	private class ViewHolder{
		TextView tvId ;
		TextView tvName ;
		TextView tvTel ;
		
	}

}
