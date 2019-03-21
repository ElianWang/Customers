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

@Entity
@Table(name="docs")
public class Doc {
	
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
	@Column(name ="residence_type",length=255)
	private String residenceType;//户口性质
	
	
	@Excel(width=20,orderNum="7",name = "人员状态")
	@Column(name ="person_status",length=255)
	private String personStatus;//人员状态
	
	@Excel(width=20,orderNum="7",name = "职级")
	@Column(name ="rank",length=255)
	private String rank;//职级
	
	@Excel(width=20,orderNum="7",name = "档案存放情况")
	@Column(name ="doc_deposit",length=255)
	private String docDeposit;//档案存放情况
	
	@Excel(width=20,orderNum="7",name = "单位集体户")
	@Column(name ="unit_col_house",length=255)
	private String unitColhouse;//单位集体户
	
	@Excel(width=20,orderNum="7",name = "党员关系")
	@Column(name ="party_relation",length=255)
	private String partyRelation;//党员关系
	
	
//	@Excel(width=20,orderNum="7",name = "转入时间",exportFormat="yyyy-MM-dd")
	@Column(name ="create_time")
	private Date createTime;//创建时间
	
//	@Excel(width=20,orderNum="9",name = "转出时间",exportFormat = "yyyy-MM-dd")
	@Column(name ="update_time")
	private Date updateTime;//更新时间
	
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
	
	
	public String getResidenceType() {
		return residenceType;
	}

	public void setResidenceType(String residenceType) {
		this.residenceType = residenceType;
	}

	public String getPersonStatus() {
		return personStatus;
	}

	public void setPersonStatus(String personStatus) {
		this.personStatus = personStatus;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getDocDeposit() {
		return docDeposit;
	}

	public void setDocDeposit(String docDeposit) {
		this.docDeposit = docDeposit;
	}

	public String getUnitColhouse() {
		return unitColhouse;
	}

	public void setUnitColhouse(String unitColhouse) {
		this.unitColhouse = unitColhouse;
	}

	public String getPartyRelation() {
		return partyRelation;
	}

	public void setPartyRelation(String partyRelation) {
		this.partyRelation = partyRelation;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
