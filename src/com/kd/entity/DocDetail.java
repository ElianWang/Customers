package com.kd.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * 记录档案创建和更新的记录信息
 * @author WTD
 *
 */
@Entity
@Table(name="doc_details")
public class DocDetail {
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	@Column(name ="ID",nullable=false,length=32)
	private String id;
	
	@Excel(width=20,orderNum="1",name = "档案编码")
	@Column(name ="code",length=255)
	private String code;//档案编码
	
	@Excel(width=20,orderNum="2",name = "姓名")
	@Column(name ="full_name",length=255)
	private String fullName;//姓名
	
	@Excel(width=20,orderNum="3",name = "身份证号")
	@Column(name ="id_num",length=255)
	private String idNum;//身份证号
	
	@Excel(width=20,orderNum="5",name = "所属单位")
	@Column(name ="company",length=255)
	private String company;//所属单位
	
	@Excel(width=20,orderNum="6",name = "户口性质")
	@Column(name ="business_cause",length=255)
	private String businessCause;//业务起因
	
	
	@Excel(width=20,orderNum="7",name = "人员状态")
	@Column(name ="business_type",length=255)
	private String businessType;//业务类型
	
	@Excel(width=20,orderNum="7",name = "职级")
	@Column(name ="business_operation",length=255)
	private String businessOper;//业务操作
	
	@Excel(width=20,orderNum="7",name = "档案存放情况")
	@Column(name ="oper_content",length=255)
	private String operContent;//操作内容
	
	@Excel(width=20,orderNum="7",name = "单位集体户")
	@Column(name ="unit_col_house",length=255)
	private String docCode;//所记录明细的档案编码
	
	@Excel(width=20,orderNum="7",name = "党员关系")
	@Column(name ="party_relation",length=5)
	private String newest = "0";//是否是最新
	
	
//	@Excel(width=20,orderNum="7",name = "转入时间",exportFormat="yyyy-MM-dd")
	@Column(name ="create_time")
	private Date createTime;//创建时间
	
	@Excel(width=20,orderNum="10",name = "备注")
	@Column(name ="remarks",length=400)
	private String remarks;//备注
	
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
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBusinessCause() {
		return businessCause;
	}

	public void setBusinessCause(String businessCause) {
		this.businessCause = businessCause;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessOper() {
		return businessOper;
	}

	public void setBusinessOper(String businessOper) {
		this.businessOper = businessOper;
	}

	public String getOperContent() {
		return operContent;
	}

	public void setOperContent(String operContent) {
		this.operContent = operContent;
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getNewest() {
		return newest;
	}

	public void setNewest(String newest) {
		this.newest = newest;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
