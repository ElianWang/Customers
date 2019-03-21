/**      
 * @ PopeDomTreeObj.java Create on 2008-7-30 下午04:26:02      
 *      
 * Copyright (c) 2008 by xinlian.      
 */       
    
      
/**      
* @author chris    
*/ 
package com.kd.util;

import java.util.ArrayList;
import java.util.List;


public class TreeObj {
	public List<TreeObj> children = new ArrayList<TreeObj>() ;
	private String id;
	private String name;
	private String pId;
	
	
	public List<TreeObj> getChildren() {
		return children;
	}

	public void setChildren(List<TreeObj> children) {
		this.children = children;
	}

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

	public String getpId() {
		return pId;
	}

	public void setpId(String id) {
		pId = id;
	}
	
}
