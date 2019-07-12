package com.ts.springboot.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class FileIOUtils {
	public static byte[] createZip(String srcSource) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		// 将目标文件打包成zip导出
		File file = new File(srcSource);
		a(zip, file, "");
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
	
	public static String randomName2() {
		Date dt = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
		return sdf.format(dt);
	}
	
	public static boolean delFile(File file) {
		if (file.exists()&&file.isDirectory()) {
			File[] files = file.listFiles();
			if(files!=null&&files.length>0){
				for (File f : files) {
					if (f.exists()&&f.isDirectory()) {
						delFile(f);
					}else{
						f.delete();
					}
				}
			}
			//file.delete();
			return true;
		}
		return false;
	}
	public static void a(ZipOutputStream zip, File file, String dir)
			throws Exception {
		// boolean temp =true;
		// 如果当前的是文件夹，则进行进一步处理
		if (file.isDirectory()) {
			// 得到文件列表信息
			File[] files = file.listFiles();
			// 将文件夹添加到下一级打包目录
			/*
			 * if(!temp){ zip.putNextEntry(new ZipEntry(dir + "/")); dir =
			 * dir.length() == 0 ? "" : dir + "/"; }
			 */
			// 循环将文件夹中的文件打包
			for (int i = 0; i < files.length; i++) {
				a(zip, files[i], dir + files[i].getName()); // 递归处理
			}
		} else { // 当前的是文件，打包处理
			// 文件输入流
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			ZipEntry entry = new ZipEntry(dir);
			zip.putNextEntry(entry);
			zip.write(FileUtils.readFileToByteArray(file));
			IOUtils.closeQuietly(bis);
			zip.flush();
			zip.closeEntry();
		}
	}
}
