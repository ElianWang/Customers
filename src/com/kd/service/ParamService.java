package com.kd.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kd.entity.Params;
import com.kd.util.Pagination;

public interface ParamService {

	/**
	 * 根据查询条件对日志信息进行分页显示
	 * @param request
	 * @param pageIndex 第几页
	 * @param pageSize 每页大小
	 * @return
	 */
	public Pagination<Params> queryList(HttpServletRequest request,int pageIndex,int pageSize);
	
	/**
	 * 记录参数信息
	 * @param param 参数信息
	 */
	public void saveParam(Params param);
	
	/**
	 * 更新参数信息
	 * @param param 参数信息
	 */
	public void updateParam(Params param);
	
	/**
	 * 删除参数信息
	 * @param id 参数信息id
	 * @param paramType 参数类型
	 */
	public void deleteParam(String id,String paramType);
	
	/**
	 * 根据id获取相应的参数数据
	 * @param id 参数id
	 * @param paramType 参数类型
	 * @return
	 */
	public Params getByIdAndType(String id,String paramType);
	
	
	/**
	 * 根据参数类型获取参数集合
	 * @param paramType 参数类型
	 * @return 对应的参数集合
	 */
	public List<Params> getByType(String paramType);
	
	/**
	 * 初始化参数，将参数放到缓存中
	 * @param paramType 具体的参数类型
	 */
	public void initParams(String paramType);
	
	/**
	 * 根据code获取参数对象
	 * @param code
	 * @return
	 */
	public Params getByCode(String code);
	
	/**
	 * 获取某一类型参数的下一个code
	 * @param paramType 参数类型
	 * @param parentCode 父code，如果不为空，说明是要获取大类的小类的写一个code
	 * @param num 需要截取的位数
	 * @return
	 */
	public String getNextCode(String paramType,String parentCode,int num);
	
	/**
	 * 根据id获取对应的对象
	 * @param id
	 * @return
	 */
	public Params getById(String id);

	/**
	 * 根据type和父节点获取对应的参数集合
	 * @param parentCode 父节点
	 * @param type 参数类型
	 * @return
	 */
	public List<Params> getByPidAndType(String parentCode, String type);

	/**
	 * 根据当前节点的code获取父节点对象
	 * @param childCode 当前节点
	 * */
	public Params getParentByChildCode(String childCode);
}
