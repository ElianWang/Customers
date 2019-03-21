package com.kd.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

@Entity
@Table(name="customers")
public class Customers implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	@Column(name ="id",nullable=false,length=11)
	private int id;
	
	@Excel(width=20,orderNum="1",name = "客户姓名")
	@Column(name="name",nullable=false,length=10)
	private String name;//客户姓名
	
	@Excel(width=20,orderNum="2",name = "电话号码")
	@Column(name="phone",nullable=false,length=20)
	private String phone;//电话号码
	
	@Excel(width=20,orderNum="3",name = "省市")
	@Column(name="city",nullable=false,length=20)
	private String city;//省市
	
	@Excel(width=20,orderNum="4",name = "地址")
	@Column(name="address",nullable=false,length=50)
	private String address;//地址
	
	@Excel(width=20,orderNum="5",name = "公司")
	@Column(name="company",nullable=false,length=50)
	private String company;//公司
	
	@Excel(width=20,orderNum="6",name = "创建时间")
	@Column(name="createTime",nullable=false)
	private Date createTime;//创建时间
	
	@Excel(width=20,orderNum="7",name = "更新时间")
	@Column(name="updateTime",nullable=false)
	private Date updateTime;//更新时间
	
	@Excel(width=20,orderNum="8",name = "备注")
	@Column(name="remarks",nullable=false,length=200)
	private String remarks;//备注
	
	@Excel(width=20,orderNum="10",name = "创建人")
	@Column(name="createUser",nullable=false,length=10)
	private String createUser;//创建人
	
	@Excel(width=20,orderNum="9",name = "客户类型")
	@Column(name="isVip",nullable=false,length=10)
	private String isVip;//

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	
	@Override
	public String toString() {
		return "Customers [name=" + name + ", phone=" + phone + ", city=" + city + ", address=" + address + ", company="
				+ company + ", createTime=" + createTime + ", updateTime=" + updateTime + ", remarks=" + remarks
				+ ", createUser=" + createUser + ", isVip=" + isVip + "]";
	}
}
