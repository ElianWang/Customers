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
 * 业务流程
 * @author Administrator
 *
 */
@Entity
@Table(name="progress")
public class Progress {

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	@Column(name ="ID",nullable=false,length=32)
	private String id;
	
	@Excel(width=20,orderNum="1",name = "流程编号")
	@Column(name ="code",length=255)
	private String code;//流程编号
	
	@Excel(width=20,orderNum="2",name = "适用单位")
	@Column(name ="company",length=255)
	private String company;//适用单位
	
	@Excel(width=20,orderNum="3",name = "发布机构")
	@Column(name ="publish_org",length=255)
	private String publishOrg;//发布机构
	
	@Excel(width=20,orderNum="4",name = "适用年度")
	@Column(name ="apply_year",length=255)
	private String applyYear;//适用年度
	
	@Excel(width=20,orderNum="5",name = "流程标题")
	@Column(name ="title",length=255)
	private String title;//流程标题
	
	@Excel(width=20,orderNum="6",name = "流程内容")
	@Column(name ="content",length=400)
	private String content;//流程内容
	
	@Excel(width=20,orderNum="7",name = "流程备注")
	@Column(name ="remarks",length=400)
	private String remarks;//流程备注
	
	@Excel(width=20,orderNum="8",name = "操作人员")
	@Column(name ="operate_user",length=255)
	private String operateUser;//操作人员
	
	@Excel(width=20,orderNum="9",name = "操作时间",exportFormat="yyyy-MM-dd")
	@Column(name ="operate_time")
	private Date operateTime;//操作时间

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

	public String getPublishOrg() {
		return publishOrg;
	}

	public void setPublishOrg(String publishOrg) {
		this.publishOrg = publishOrg;
	}

	public String getApplyYear() {
		return applyYear;
	}

	public void setApplyYear(String applyYear) {
		this.applyYear = applyYear;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
}
