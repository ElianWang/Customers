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
@Table(name="orders")
public class Orders implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	@Column(name ="ID",nullable=false,length=20)
	private int id;
	
	@Column(name ="orderTime",length=20)
	private Date orderTime;//订单日期
	
	@Column(name ="product",length=30)
	private String product;//产品
	
	@Column(name ="type",length=50)
	private String type;//类型
	
	@Column(name ="area",length=32)
	private Float area;//面积
	
	@Column(name ="price",length=32)
	private Float price;//单价
	
	@Column(name ="totalAmount",length=32)
	private Float totalAmount;//总金额
	
	@Column(name ="remarks",length=32)
	private String remarks;//备注
	
	@Column(name ="customerId",length=11)
	private int customerId;//备注
	

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Float getArea() {
		return area;
	}

	public void setArea(Float area) {
		this.area = area;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", orderTime=" + orderTime + ", product=" + product + ", type=" + type + ", area="
				+ area + ", price=" + price + ", totalAmount=" + totalAmount + ", remarks=" + remarks + "]";
	}
	
}
