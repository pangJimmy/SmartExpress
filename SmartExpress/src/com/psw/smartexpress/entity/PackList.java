package com.psw.smartexpress.entity;
/**
 * 揽件
 * @author mac
 *
 */
public class PackList {
/*
 * <order_no>B14091616215955</order_no>订单号
<o_jName>便利店</o_jName>寄件人
<o_jTel>15851812981</o_jTel>寄件电话
<jAddr>一号院</jAddr>寄件地址
<o_sName>fff</o_sName>收件人
<o_sTel>13222222222</o_sTel>收件电话
<sAddr>jjjjjjjjjjjjjjjjjjjjjjjjjj</sAddr>收件地址
 */
	public String orderNo ; 
	public String jName ;
	public String jTel ;
	public String jAddr ;
	public String sName ;
	public String sTel ;
	public String sAddr ;
	@Override
	public String toString() {
		return "PackList [orderNo=" + orderNo + ", jName=" + jName + ", jTel="
				+ jTel + ", jAddr=" + jAddr + ", sName=" + sName + ", sTel="
				+ sTel + ", sAddr=" + sAddr + "]";
	}
	
	
}
