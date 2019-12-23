package com.swinginwind.iknowu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.swinginwind.core.pager.JSONResponse;
import com.swinginwind.core.utils.ApplicationPropsUtil;
import com.swinginwind.core.utils.FileUtil;
import com.swinginwind.core.utils.Identities;
import com.swinginwind.core.utils.MediaUtil;
import com.swinginwind.iknowu.model.BaseFile;
import com.swinginwind.iknowu.model.SysUser;
import com.swinginwind.iknowu.service.BaseFileService;

@Controller
@RequestMapping("file")
public class BaseFileController {

	public static final int FILE_BUFFER_SIZE = 10240;

	@Autowired
	BaseFileService baseFileService;

	@RequestMapping("getFile")
	public void getFile(HttpServletRequest request, HttpServletResponse response, int id, String thumbnail) {
		
		String uploadDir = ApplicationPropsUtil.getPropsValue(ApplicationPropsUtil.ATTACHMENT_UPLOADDIR);
		if ("".equals(uploadDir)) {
			uploadDir = new File(request.getSession().getServletContext().getRealPath("/")).getParentFile()
					.getParentFile().getAbsolutePath();
		}
		if (id != 0) {
			BaseFile file = baseFileService.selectById(id);
			if (file != null) {
				String filePath = file.getFilePath();
				if("1".equals(thumbnail) && "img".equals(file.getFileType())) {
					int index = filePath.lastIndexOf(".");
					filePath = filePath.substring(0, index) + ".thumbnail." + filePath.substring(index + 1);
					if(!new File(uploadDir + filePath).exists())
						MediaUtil.thumbnailImage(uploadDir + file.getFilePath(), uploadDir + filePath);
				}
				this.downLoadFile(uploadDir + filePath, file.getFileName(), null, request, response,
						file.getFileType());
			}
		}
		response.setStatus(204);
	}
	
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public JSONResponse uploadFile(HttpServletRequest request, HttpServletResponse response, MultipartFile file, String fileType) {
		JSONResponse res = new JSONResponse();
		String uploadDir = ApplicationPropsUtil.getPropsValue(ApplicationPropsUtil.ATTACHMENT_UPLOADDIR);
		if ("".equals(uploadDir)) {
			uploadDir = new File(request.getSession().getServletContext().getRealPath("/")).getParentFile()
					.getParentFile().getAbsolutePath();
		}
		if (file != null && fileType != null) {
			String fileName = file.getOriginalFilename();
			BaseFile baseFile = new BaseFile();
			baseFile.setFileName(fileName);
			baseFile.setFileSize(new Double(file.getSize()));
			baseFile.setFileSuffix(FileUtil.getFileExtension(fileName));
			String newFileName = FileUtil.getFilenameWithoutExtension(fileName) + "-" + Identities.uuid() + "." + baseFile.getFileSuffix();
			try {
				baseFile.setFileMd5(FileUtil.getFileMD5(FileUtil.saveFile(file, uploadDir + newFileName)));
			} catch (IOException e) {
				res.setStatusAndMsg(false, "文件存储异常");
				return res;
			}
			baseFile.setFilePath(newFileName);
			baseFile.setFileType(fileType);
			baseFile.setUpdateTime(new Date());
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			if(user != null)
				baseFile.setUpdateUser(user.getId());
			baseFileService.insert(baseFile);
			res.put("fileId", baseFile.getId());
		}
		else
			res.setStatusAndMsg(false, "文件或文件类型不能为空");
		return res;
	}

	/**
	 * 文件下载通用方法, 可以被其他action调用
	 * 
	 * @param filePath
	 *            文件完整路径
	 * @param fileName
	 *            下载显示的文件名
	 * @param contentType
	 *            内容类型
	 * @return page
	 */
	public String downLoadFile(String filePath, String fileName, String contentType, HttpServletRequest request,
			HttpServletResponse response, String type) {
		File destFile = new File(filePath);
		if (destFile.exists()) {
			try {
				InputStream bais;
				bais = new FileInputStream(destFile);
				byte[] buffer = new byte[FILE_BUFFER_SIZE];

				if (fileName == null || fileName.equals(""))
					fileName = destFile.getName();
				// fileName = new String(fileName.getBytes("gbk"),
				// "iso-8859-1");
				response.reset();
				if (contentType == null)
					contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

				if (type != null && "img".equals(type)) {
					String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
					if (!"".equals(prefix)) {
						prefix = prefix.toLowerCase();
						if ("jpg".equals(prefix) || "jpeg".equals(prefix)) {
							contentType = MediaType.IMAGE_JPEG_VALUE;
						} else if ("png".equals(prefix)) {
							contentType = MediaType.IMAGE_PNG_VALUE;
						} else if ("gif".equals(prefix)) {
							contentType = MediaType.IMAGE_GIF_VALUE;
						} else {
							response.setHeader("Content-disposition", "filename=\"" + fileName + "\"");
						}
					} else {
						response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
					}
				} else {
					response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
				}
				response.setContentType(contentType);

				// 设置文件大小, 客户端可以读取文件大小显示进度
				response.setContentLength((int) destFile.length());
				OutputStream out = response.getOutputStream();
				int length = 0;
				while ((length = bais.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				out.flush();
				bais.close();
				out.close();
				return null;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "";
	}

}
