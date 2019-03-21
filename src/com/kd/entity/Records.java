package com.kd.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="records")
public class Records implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4414017125741795920L;

	/**
	 * 
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	@Column(name ="id",nullable=false,length=11)
	private Integer id;
	
	@Column(name="customerId",nullable=false,length=11)
	private Integer customerId;//客户Id
	
	@Column(name="content",nullable=false,length=200)
	private String content;//记录
	
	@Column(name="createTime",nullable=false)
	private Date createTime;//创建时间
	
	@Column(name="createUser",nullable=false,length=20)
	private String createUser;//创建人

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Override
	public String toString() {
		return "Records [id=" + id + ", customerId=" + customerId + ", content=" + content + ", createTime="
				+ createTime + ", createUser=" + createUser + "]";
	}
	
	

	
	
}
