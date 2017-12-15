package com.psw.smartexpress.entity;


public class PackStatus {

	public String o_codeNum;//快递单号
	public String o_status;//快递状态
	public String o_lName;//揽件人
	public String o_lTime;//揽件时间
	public String o_zName;//中转站入库操作人
	public String o_zLocaltion;//中转站存放位置
	public String o_zTime;//中转站入库时间
	public String o_carNum;//运输车编号
	public String o_carTime;//运输时间
	public String o_pName;//配送站入库操作人
	public String o_pLocaltion;//配送站存放位置
	public String o_pTime;//配送站入库时间
	public String o_kName;//配送员
	public String o_ksTime;//配送时间
	public String o_qName;//签收人
	public String o_qTime;//签收时间
	@Override
	public String toString() {
		return "PackStatus [o_codeNum=" + o_codeNum + ", o_status=" + o_status
				+ ", o_lName=" + o_lName + ", o_lTime=" + o_lTime
				+ ", o_zName=" + o_zName + ", o_zLocaltion=" + o_zLocaltion
				+ ", o_zTime=" + o_zTime + ", o_carNum=" + o_carNum
				+ ", o_carTime=" + o_carTime + ", o_pName=" + o_pName
				+ ", o_pLocaltion=" + o_pLocaltion + ", o_pTime=" + o_pTime
				+ ", o_kName=" + o_kName + ", o_ksTime=" + o_ksTime
				+ ", o_qName=" + o_qName + ", o_qTime=" + o_qTime + "]";
	}
	
	
	
	
}
