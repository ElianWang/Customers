package com.kd.util;

import java.io.File;
import java.util.ResourceBundle;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Level;

import com.alibaba.fastjson.JSONObject;

public class OperateFiles {

	private static String savePath = "";
	private static String tempPath = "";
	
	static{
		ResourceBundle resource = ResourceBundle.getBundle("configSystem");
		try {
			savePath = resource.getString("savePath");
			File file = new File(savePath);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("savePath获取失败！");
		}
		try {
			tempPath = resource.getString("tempPath");
			File file = new File(tempPath);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("tempPath获取失败！");
		}
		System.out.println("文件路径：" + savePath);
	}
	
	/**
	 * 保存文件
	 * @param item 文件对象，只包含文件名和文件内容
	 * @return 返回文件全路径，如果保存失败，则返回"FALSE"
	 */
	public static String  saveFiles(FileItem item,String vehicleNum,String type){
		String filePath = savePath + File.separator + vehicleNum;
		String editPath = tempPath + File.separator + vehicleNum;
		boolean editFlag = false;//标记是否为编辑信息时的保存照片，false标识新建，true标识编辑
		if (type != null && !type.isEmpty() && type.equals("edit")) {
			editFlag = true;
		}
		String result = "FALSE";
		File tempFile = new File(item.getName());
		try {
			//如果是编辑车辆信息时
			if (editFlag) {
				File folder = new File(editPath);
				if (!folder.exists()) {
					folder.mkdirs();
				}
				File folder1 = new File(filePath);
				if (!folder1.exists()) {
					folder1.mkdirs();
				}
				//上传文件的保存路径
				File file = new File(editPath, tempFile.getName());
				item.write(file);
				//虽然是编辑，但是返回的文件路径还是新建状态的文件路径，因为带有_edit的文件夹会去掉edit标识
				File file1 = new File(filePath,tempFile.getName());
				result = file1.getAbsolutePath();
			}else{
				File folder = new File(filePath);
				if (!folder.exists()) {
					folder.mkdir();
				}
				//上传文件的保存路径
				File file = new File(filePath, tempFile.getName());
				item.write(file);
				result = file.getAbsolutePath();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据文件路径删除文件，失败的返回文件名包括路径，成功的返回文件名
	 * @param filePath
	 * @return
	 */
	public static JSONObject deleteFile(String vehicleNum,String type){
		JSONObject json = new JSONObject();
		json.put("success", "true");
		String filePath = savePath + File.separator + vehicleNum;
		if (type != null && type.equals("edit")) {
			filePath = tempPath + File.separator + vehicleNum;
		}
		File file = new File(filePath);
		if (file.exists()) {
			try {
				deleteFile(file);
			} catch (Exception e) {
				e.printStackTrace();
				LogUtil.log("文件" + filePath + "删除失败", Level.ERROR, e);
				json.put("success", "false");
				json.put("message", "文件" + filePath + "删除失败");
			}
		}
		return json;
	}
	
	/**
	 * 删除vehicleNum对应的所有上传照片
	 * @param vehicleNum
	 * @return
	 */
	public static boolean deleteFileByVehicleNum(String vehicleNum,String type){
		String filePath = savePath + File.separator + vehicleNum;
		if (type != null && !type.isEmpty() && type.equals("edit")) {
			filePath += "_edit";
		}
		File file = new File(filePath);
		if (file.exists()) {
			try {
				deleteFile(file);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}else{
			return false;
		}
		return true;
	}
	
	//删除文件夹或者文件
	private static  void deleteFile(File file) {  
        if (file.exists()) {//判断文件是否存在   
             if (file.isFile()) {//判断是否是文件   
                 file.delete();//删除文件    
             }else if(file.isDirectory()){//否则如果它是一个目录   
                 File[] files = file.listFiles();//声明目录下所有的文件 files[];  
                 for(int i = 0;i < files.length;i ++){//遍历目录下所有的文件   
                     deleteFile(files[i]);//把每个文件用这个方法进行迭代   
                 }  
                 file.delete();//删除文件夹   
             }else{
            	System.out.println("所删除的文件不存在");  
            }
        }  
     }
	
	/**
	 * 修改文件名称
	 * @param vehicleNum
	 */
	public static void renameFile(String vehicleNum){
		String oldName = tempPath + File.separator + vehicleNum;
		String newName = savePath + File.separator + vehicleNum;
		File oldFile = new File(oldName);
		if (!oldFile.exists()) {
			oldFile.mkdir();
		}
	    boolean isOk = oldFile.renameTo(new File(newName));
	    System.out.println(isOk);
	}
	
	/**
	 * 判断车辆信息的编辑上传照片是否存在
	 * @param vehicleNum
	 * @return
	 */
	public static boolean existEditFile(String vehicleNum) {
		String editFilePath = tempPath + File.separator + vehicleNum;
		File editFile = new File(editFilePath);
		if (editFile.exists()) {
			return true;
		}
		return false;
	}
}
