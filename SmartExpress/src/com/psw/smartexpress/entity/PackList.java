package com.psw.smartexpress.entity;
/**
 * ����
 * @author mac
 *
 */
public class PackList {
/*
 * <order_no>B14091616215955</order_no>������
<o_jName>������</o_jName>�ļ���
<o_jTel>15851812981</o_jTel>�ļ��绰
<jAddr>һ��Ժ</jAddr>�ļ���ַ
<o_sName>fff</o_sName>�ռ���
<o_sTel>13222222222</o_sTel>�ռ��绰
<sAddr>jjjjjjjjjjjjjjjjjjjjjjjjjj</sAddr>�ռ���ַ
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
