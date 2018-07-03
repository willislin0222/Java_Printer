package com.printer.demo;

import java.awt.print.PrinterException;
import java.io.IOException;

import javax.print.PrintService;

import com.printer.model.PrintUtil;

public class DEMO {

	public static void main(String[] args) throws IOException, PrinterException {
		
		PrintUtil printutil = new PrintUtil();
		
		//找出特定印表機並指定使用
		String printname = "Adobe PDF";
//		String printname = "HP LaserJet 200 color MFP M276 PCL 6";
		PrintService printService = printutil.findPrintServicesName(printname);
		printutil.setPrintService(printService);
		
		//列印圖片
		printutil.printImage("C:/images/vicky.jpg");
		
		//列印PDF
		printutil.printPdf("C:/pdf/test.pdf");
	}

}
