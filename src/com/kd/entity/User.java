package com.kd.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Table(name="users")
@Entity
public class User implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 6568038572059663938L;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	@Column(name ="ID",nullable=false,length=11)
	private Integer id;
	@Column(name="account",nullable=false,length=20)
	private String account;//账号
	
	@Column(name="name",nullable=false,length=20)
	private String name;//姓名
	
	@Column(name="password",nullable=false,length=50)
	private String password;
	
	@Column(name="is_admin",length=10)
	private String isAdmin;//判断是否是超级管理员，超级管理员不能删除

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}
}
