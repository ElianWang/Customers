package com.kd.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 参数实体类模板
 * @author Administrator
 *
 */
@Entity
@Table(name="params")
public class Params implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4663449972303359583L;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	@Column(name ="ID",nullable=false,length=32)
	private String id;
	
	@Column(name ="code",length=20)
	private String code;
	
	@Column(name ="name",length=100)
	private String name;
	
	@Column(name ="type",length=10)
	private String type;//所属单位：01，办理状态：02，操作类型：04，大类别：03，小类别：05,档案参数：06
	
	@Column(name ="parent_id",length=32)
	private String parentCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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


	public enum paramEnum{
		company("所属单位","01"),operateStatus("办理状态","02"),operateType("操作类型","03"),
		bigBisiness("大类别","04"),smallBusiness("小类别","05"),residenceType("户口性质","0601"),
		personStatus("人员状态","0602"),rank("职级","0603"),docDeposit("档案现存放情况","0604"),
		unitColhouse("单位集体户","0605"),partyRelation("党员关系","0606"),docParams("档案参数","06"),
		docCompany("档案所属单位","0607");
		
		private String name;  
	    private String code;  
	    // 构造方法  
	    private paramEnum(String name, String code) {  
	        this.name = name;  
	        this.code = code;  
	    }  
	    // 普通方法  
	    public static String getName(String code) {  
	        for (paramEnum c : paramEnum.values()) {  
	            if (c.getCode() == code) {  
	                return c.name;  
	            }  
	        }  
	        return null;  
	    }
	    
	    public static List<String> getAllCodes(){
	    	List<String> codes = new ArrayList<String>();
	    	for(paramEnum p:paramEnum.values()){
	    		codes.add(p.getCode());
	    	}
	    	return codes;
	    }
	    // get set 方法  
	    public String getName() {  
	        return name;  
	    }  
	    public void setName(String name) {  
	        this.name = name;  
	    }  
	    public String getCode() {  
	        return code;  
	    }  
	    public void setCode(String code) {  
	        this.code = code;  
	    }
	}

}
