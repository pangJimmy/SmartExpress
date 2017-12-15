package com.psw.smartexpress.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.psw.smartexpress.entity.PackList;
import com.psw.smartexpress.entity.PackStatus;
import com.psw.smartexpress.util.Loger;
import com.psw.smartexpress.util.ParaSave;

import android.content.Context;
import android.util.Xml;

/**
 * ���������
 * @author mac
 *
 */
public class HttpHelper {

	public  String URL_M = "http://123.56.138.171:2001/Services/pda_Services.asmx" ;//��������ַ
	
	/**�û���½***/
	private final String LOGIN = "userLoad" ;
	
	/**���Ա�ռ�***/
	private final String ADD_EXPRESS = "addExpress" ;
	
	/**���Ա�ռ�***/
	private final String GET_STATUES = "getStatus" ;
	
	/**���Ա�ռ�***/
	private final String PAI_EXPRESS = "paiExpress" ;
	
	/**���Ա***/
	private final String GET_LLIST = "getLList" ;
	
	
	private List<String> listPara ;
	private List<String> listValues ;
	
	public  HttpHelper(Context context){
		ParaSave para = new ParaSave(context) ;
		URL_M = "http://" + para.getIP() + ":" + para.getPort() + "/Services/pda_Services.asmx" ;
	}
	
	/**
	 * 1 ��½
	 * @param user
	 * @param password
	 * @return
	 */
	public String login(String user, String password){
		listPara = new ArrayList<>() ;
		listValues = new ArrayList<>() ;
		listPara.add("username") ;
		listPara.add("password") ;
		listValues.add(user) ;
		listValues.add(password) ;
		return resolveResp(sendRequest(listPara, listValues, LOGIN)) ;
	}
	
	/**
	 * �ռ�
	 * @param order  ����
	 * @param goods  ��Ʒ
	 * @param goodsCount ��Ʒ����
	 * @param poster �ļ���
	 * @param posterTel �ļ��˵绰
	 * @param posterAddr �ļ��˵�ַ
	 * @param temp �¶�
	 * @param receiver  �ռ���
	 * @param receiverTel �ռ��˵绰
	 * @param receiverAddr �ռ��˵�ַ
	 * @param payType ֧����ʽ
	 * @param payment �˷�
	 * @param user  ������
	 * @return
	 */
	public String addPackage(String order , String goods, int goodsCount , String poster, String posterTel , String posterAddr,
			float temp, String receiver, String receiverTel , String receiverAddr, int payType, float payment, String user ){
		listPara = new ArrayList<>() ;
		listValues = new ArrayList<>() ;
		listPara.add("o_codeNum") ;
		listPara.add("o_name") ;
		listPara.add("o_num") ;
		listPara.add("o_temp") ;
		listPara.add("o_jName") ;
		listPara.add("o_jTel") ;
		listPara.add("jAddr") ;
		listPara.add("o_sName") ;
		listPara.add("o_sTel") ;
		listPara.add("sAddr") ;
		listPara.add("o_payType") ;
		listPara.add("o_payMoney") ;
		listPara.add("o_lName") ;
		listValues.add(order) ;
		listValues.add(goods) ;
		listValues.add(goodsCount + "") ;
		listValues.add(temp+"") ;
		listValues.add(poster) ;
		listValues.add(posterTel) ;
		listValues.add(posterAddr) ;
		listValues.add(receiver) ;
		listValues.add(receiverTel) ;
		listValues.add(receiverAddr) ;
		listValues.add(payType + "") ;
		listValues.add(payment + "") ;
		listValues.add(user) ;
		
		return resolveResp(sendRequest(listPara, listValues, ADD_EXPRESS)) ;
	}
	
	/**
	 * ��ѯ
	 * @param order
	 * @return
	 */
	public PackStatus queryOrder(String order){
		listPara = new ArrayList<>() ;
		listValues = new ArrayList<>() ;
		listPara.add("o_codeNum") ;
		listValues.add(order) ;
		return resolveStatus(sendRequest(listPara, listValues, GET_STATUES)) ;
	}
	
	/**
	 * ����
	 * @param order
	 * @param receiver
	 * @return
	 */
	public String sendPackage(String order, String receiver){
		listPara = new ArrayList<>() ;
		listValues = new ArrayList<>() ;
		listPara.add("o_codeNum") ;
		listPara.add("o_qName") ;
		listValues.add(order) ;
		listValues.add(receiver) ;
		return resolveResp(sendRequest(listPara, listValues, PAI_EXPRESS)) ;
	}
	
	/**
	 *����֪ͨ
	 */
	public List<PackList> getLList(){
		List<PackList> list = resolveLList(sendRequest(null, null, GET_LLIST) );
		return list ;
	}
	
	/**
	 * ��������
	 * @return
	 */
	private String sendRequest(List<String> paras , List<String> paraValues, String method) {
		String result = null;
		try{
		
		String url = URL_M + "/" +method + "?" ;
		String paraStr = "" ;
		if(paras != null && paraValues != null){
			for(int i = 0; i < paras.size(); i++){
				if(i != (paras.size() - 1)){
					paraStr += paras.get(i) + "=" + paraValues.get(i) + "&";
				}else{
					paraStr += paras.get(i) + "=" + paraValues.get(i) ;
				}
			}
		}
		url += paraStr ;
		HttpClient httpCient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url) ;
		HttpResponse httpResponse = httpCient.execute(httpGet);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity,"utf-8");//��entity���е�����ת��Ϊ�ַ���
//			Loger.e("resp", response) ;
			//��������
//			result = resolveResp(response);
			result = response ;
		}
		}catch(Exception e){
			result = e.toString() ;
		}
		return result  ;
	}
	
	/**
	 * �������״̬
	 * @param response
	 * @return
	 */
	private PackStatus resolveStatus(String response){
		//��������
		ByteArrayInputStream bi = new ByteArrayInputStream(response.getBytes()) ;
		XmlPullParser parser = Xml.newPullParser();
		String result = "" ;
		PackStatus pack = null ;
		try {
			parser.setInput(bi, "UTF-8") ;
			int eventType = parser.getEventType(); 
			
			while (eventType != XmlPullParser.END_DOCUMENT) { 
				
	            switch (eventType) {  
	            case XmlPullParser.START_DOCUMENT:  
//	                books = new ArrayList<Book>();  
	                break;  
	            case XmlPullParser.START_TAG:  
	            	
	            	//ds
	            	String tag = parser.getName() ;
	            	if("ds".equals(tag)){
	            		pack = new PackStatus() ;
	            	}else if("o_codeNum".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_codeNum = parser.getText() ;
	            	}else if("o_status".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_status = parser.getText() ;
	            	}else if("o_lName".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_lName = parser.getText() ;
	            	}else if("o_lTime".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_lTime = parser.getText() ;
	            	}else if("o_zName".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_zName = parser.getText() ;
	            	}else if("o_zLocaltion".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_zLocaltion = parser.getText() ;
	            	}else if("o_zTime".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_zTime = parser.getText() ;
	            	}else if("o_carNum".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_carNum = parser.getText() ;
	            	}else if("o_carTime".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_carTime = parser.getText() ;
	            	}else if("o_pName".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_pName = parser.getText() ;
	            	}else if("o_pLocaltion".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_pLocaltion = parser.getText() ;
	            	}else if("o_pTime".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_pTime = parser.getText() ;
	            	}else if("o_kName".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_kName = parser.getText() ;
	            	}else if("o_ksTime".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_ksTime = parser.getText() ;
	            	}else if("o_qName".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_qName = parser.getText() ;
	            	}else if("o_qTime".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.o_qTime = parser.getText() ;
	            		Loger.e("pack", pack.toString()) ;
	            	}
	            	
//	            	if(pack != null){
//	            		Loger.e("pack", pack.toString()) ;
//	            	}
	            	//
//	            	if(result != null)
//	            	Loger.e("result", result) ;
//	            	int index = parser.next() ;
	                break;  
	            case XmlPullParser.END_TAG:  
	                break;  
	        } 
	            
	            eventType = parser.next();
			}
		} catch (XmlPullParserException | IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return pack ;
	}
	
	/**
	 * ��������֪ͨ
	 * @param response
	 * @return
	 */
	private List<PackList> resolveLList(String response){
		List<PackList> list = new ArrayList<>() ;
		//��������
		ByteArrayInputStream bi = new ByteArrayInputStream(response.getBytes()) ;
		XmlPullParser parser = Xml.newPullParser();
		String result = "" ;
		try {
			parser.setInput(bi, "UTF-8") ;
			int eventType = parser.getEventType(); 
			PackList pack = null ;
			while (eventType != XmlPullParser.END_DOCUMENT) { 
				
	            switch (eventType) {  
	            case XmlPullParser.START_DOCUMENT:  
//	                books = new ArrayList<Book>();  
	                break;  
	            case XmlPullParser.START_TAG:  
	            	
	            	//ds
	            	String tag = parser.getName() ;
	            	if("ds".equals(tag)){
	            		pack = new PackList() ;
	            	}else if("order_no".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.orderNo = parser.getText() ;
	            	}else if("o_jName".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.jName = parser.getText() ;
	            	}else if("o_jTel".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.jTel = parser.getText() ;
	            	}else if("jAddr".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.jAddr = parser.getText() ;
	            	}else if("o_sName".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.sName = parser.getText() ;
	            	}else if("o_sTel".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.sTel = parser.getText() ;
	            	}else if("sAddr".equals(tag)){
	            		parser.next() ;//ָ��name����
	            		pack.sAddr = parser.getText() ;
	            		list.add(pack) ;
	            		Loger.e("pack", pack.toString()) ;
	            	}
	            	
//	            	if(pack != null){
//	            		Loger.e("pack", pack.toString()) ;
//	            	}
	            	//
//	            	if(result != null)
//	            	Loger.e("result", result) ;
//	            	int index = parser.next() ;
	                break;  
	            case XmlPullParser.END_TAG:  
	                break;  
	        } 
	            
	            eventType = parser.next();
			}
		} catch (XmlPullParserException | IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		

		
		return list ;
	}
	
	private String resolveResp(String response){
		//��������
		ByteArrayInputStream bi = new ByteArrayInputStream(response.getBytes()) ;
		XmlPullParser parser = Xml.newPullParser();
		String result = "" ;
		try {
			parser.setInput(bi, "UTF-8") ;
			int eventType = parser.getEventType(); 
			while (eventType != XmlPullParser.END_DOCUMENT) { 
				
	            switch (eventType) {  
	            case XmlPullParser.START_DOCUMENT:  
//	                books = new ArrayList<Book>();  
	                break;  
	            case XmlPullParser.START_TAG:  
	            	String tag = parser.getName() ;
	            	
	            	Loger.e("tag", tag) ;
	            	int index = parser.next() ;
	            	result = parser.getText() ;
	            	if(result != null)
	            	Loger.e("result", result) ;
//	            	int index = parser.next() ;
	                break;  
	            case XmlPullParser.END_TAG:  
	                break;  
	        } 
	            
	            eventType = parser.next();
			}
		} catch (XmlPullParserException | IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		

		
		return result ;
	}
}
