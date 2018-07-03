package com.printer.controller;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Collection;

import javax.print.PrintService;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.printer.model.PrintUtil;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5* 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class PrinterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		Part part = req.getPart("image"); // Servlet3.0新增了Part介面，讓我們方便的進行檔案上傳處理
		String header = part.getHeader("Content-Disposition");
		String filename = header.substring(header.indexOf("filename=\"") + 10 , header.lastIndexOf("\""));
		
		System.out.println(filename);
		
		PrintUtil printutil = new PrintUtil();
		
//		找出特定印表機並指定使用
		String printname = "Adobe PDF";
//		String printname = "HP LaserJet 200 color MFP M276 PCL 6";
		PrintService printService = printutil.findPrintServicesName(printname);
		printutil.setPrintService(printService);
		
		//列印圖片
		try {
			printutil.printImage(filename);
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
