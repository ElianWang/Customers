package com.kd.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 业务办理表
 * @author Administrator
 *
 */
@Entity
@Table(name="business")
public class Business {
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	@Column(name ="ID",nullable=false,length=32)
	private String id;
	
	@Excel(width=20,orderNum="1",name = "业务编码")
	@Column(name ="code",length=255)
	private String code;//业务编码
	
	@Excel(width=20,orderNum="2",name = "所属单位")
	@Column(name ="company",length=255)
	private String company;//所属单位
	
	@Excel(width=20,orderNum="3",name = "业务大类")
	@Column(name ="big_business",length=255)
	private String bigBusiness;//业务大类
	
	@Excel(width=20,orderNum="4",name = "业务小类")
	@Column(name ="small_business",length=255)
	private String smallBusiness;//业务小类
	
	@Excel(width=20,orderNum="5",name = "业务对象")
	@Column(name ="service_target",length=255)
	private String serviceTarget;//业务对象
	
	@Excel(width=20,orderNum="6",name = "联系方式")
	@Column(name ="contact",length=255)
	private String contact;//联系方式
	
	@Excel(width=20,orderNum="7",name = "申报人")
	@Column(name ="apply_man",length=255)
	private String applyMan;//申请人
	
	@Excel(width=20,orderNum="8",name = "业务记录")
	@Column(name ="business_content",length=400)
	private String businessContent;//业务记录
	
	@Excel(width=20,orderNum="9",name = "备注")
	@Column(name ="remarks",length=400)
	private String remarks;//备注
	
	@Excel(width=20,orderNum="10",name = "操作类型")
	@Column(name ="operate_type",length=255)
	private String operateType;//操作类型
	
	@Excel(width=20,orderNum="11",name = "办理状态")
	@Column(name ="operate_status",length=255)
	private String operateStatus;//办理状态
	
	@Excel(width=20,orderNum="12",name = "操作时间",exportFormat="yyyy-MM-dd")
	@Column(name ="operate_time")
	private Date operateTime;//操作时间
	
	@Excel(width=20,orderNum="13",name = "操作人")
	@Column(name ="operate_user",length=100)
	private String operateUser;//操作人
	
	@Column(name ="begin_time")
	private String beginTime;//任务开始时间
	
	@Column(name ="type",length=255)
	private String type;//01：主任务，02：子任务
	
	
	@Column(name ="parent_code",length=255)
	private String parentCode;//如果是子任务，对应的主任务编码
	
	
	@Column(name ="update_time",length=255)
	private String updateTime;//更新时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBigBusiness() {
		return bigBusiness;
	}

	public void setBigBusiness(String bigBusiness) {
		this.bigBusiness = bigBusiness;
	}

	public String getSmallBusiness() {
		return smallBusiness;
	}

	public void setSmallBusiness(String smallBusiness) {
		this.smallBusiness = smallBusiness;
	}

	public String getServiceTarget() {
		return serviceTarget;
	}

	public void setServiceTarget(String serviceTarget) {
		this.serviceTarget = serviceTarget;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getApplyMan() {
		return applyMan;
	}

	public void setApplyMan(String applyMan) {
		this.applyMan = applyMan;
	}

	public String getBusinessContent() {
		return businessContent;
	}

	public void setBusinessContent(String businessContent) {
		this.businessContent = businessContent;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	
}
