package com.psw.smartexpress.application;

import java.util.ArrayList;
import java.util.List;

import com.psw.smartexpress.entity.PackList;
import com.psw.smartexpress.util.ParaSave;
import com.psw.smartexpress.util.ScanType;

import android.app.Activity;
import android.app.Application;

public class Mapplication extends Application {

	//��½״̬
	private  boolean loginSuccess = false ;
	
	private String userName = null ;
	
    public void setUserName(String userName) {
		this.userName = userName;
	}

    
    //���ռ�
    private  PackList pack ;

    //ɨ�跽ʽ
    

	public PackList getPack() {
		return pack;
	}

	public void setPack(PackList pack) {
		this.pack = pack;
	}


	private List<Activity> listAc  = new ArrayList<Activity>();

	public boolean isLoginSuccess() {
		return loginSuccess;
	}

	public void setLoginSuccess(boolean loginSuccess) {
		this.loginSuccess = loginSuccess;
	}
	
	public String getURL(){
		ParaSave para = new ParaSave(this) ;
		return para.getIP() ;
	}
	
	
	   /**
     * add activity
     * @param activity
     */
    public void addActivity(Activity activity){
        if(!listAc.contains(activity)){
            listAc.add(activity) ;
        }

    }

    /**
     * kill all activity
     */
    public void exit(){
        try {
            for(Activity ac : listAc){
                if (ac != null)
                    ac.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }

    }
    
    

	public String getUserName() {
		// TODO Auto-generated method stub
		return this.userName;
	}
	
	
}
