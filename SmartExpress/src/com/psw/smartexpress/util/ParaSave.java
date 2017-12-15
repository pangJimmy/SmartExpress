package com.psw.smartexpress.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ParaSave {

	private Context context ;
	
	public ParaSave(Context context){
		this.context = context ;
	}
	
	public void setIp(String ip){
		SharedPreferences shared = context.getSharedPreferences("para", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putString("ip", ip) ;
		editor.commit() ;
	}
	
	public String getIP(){
		SharedPreferences shared = context.getSharedPreferences("para", Context.MODE_PRIVATE) ;
		return shared.getString("ip", "123.56.138.171") ;
	}
	
	public void setPort(String port){
		SharedPreferences shared = context.getSharedPreferences("para", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putString("port", port) ;
		editor.commit() ;
	}
	
	public String getPort(){
		SharedPreferences shared = context.getSharedPreferences("para", Context.MODE_PRIVATE) ;
		return shared.getString("port", "2001") ;
	}
	
	public void setUser(String user){
		SharedPreferences shared = context.getSharedPreferences("para", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putString("user", user) ;
		editor.commit() ;
	}
	
	
	public String getUser(){
		SharedPreferences shared = context.getSharedPreferences("para", Context.MODE_PRIVATE) ;
		return shared.getString("user", "") ;
	}
	
	
	public void setPassword(String pass){
		SharedPreferences shared = context.getSharedPreferences("para", Context.MODE_PRIVATE) ;
		Editor editor = shared.edit() ;
		editor.putString("pass", pass) ;
		editor.commit() ;
	}
	
	
	public String getPassword(){
		SharedPreferences shared = context.getSharedPreferences("para", Context.MODE_PRIVATE) ;
		return shared.getString("pass", "") ;
	}
	
}
