package com.printer.model;

import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.PrintRequestAttributeSet;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PrintUtil {
	private PrintService printService;
	private boolean hasUI =  false;
	
	 /**
     * 只印一張圖
     * @param filepath
     * @throws IOException
     * @throws PrinterException
     */
	
	public void printImage(String filepath) throws IOException,PrinterException{
		BufferedImage image = ImageIO.read(new File(filepath));
		this.printImage(image);
	}
	
	  /**
     * 只印一張圖
     * @param image
     * @throws PrinterException
     */
	
	public void printImage(BufferedImage image) throws PrinterException{
		PrinterJob printJob = PrinterJob.getPrinterJob();
		if(this.printService != null){
			printJob.setPrintService(printService);
		}
		
		printJob.setPrintable(new ImagePrintable(printJob,image));
		if(hasUI == true){
			if(printJob.printDialog()){
				printJob.print();
			}
		}else{
			printJob.print();
		}
	}
	
	/**
     * 印整個PDF
     * @param filepath
     * @throws IOException
     * @throws PrinterException
     */
    public void printPdf(String filepath) throws IOException, PrinterException{
        PDDocument doc = PDDocument.load(filepath);
        this.printPdf(doc);
    }
	
	/**
     * 印整個PDF
     * @param doc
     * @throws IOException
     * @throws PrinterException
     */
	public void printPdf(PDDocument doc) throws IOException, PrinterException{
        PrinterJob printJob = PrinterJob.getPrinterJob();
        Book book = new Book();
        List pages = doc.getDocumentCatalog().getAllPages();
        for(int i=0;i<pages.size();i++){
            PDPage page = (PDPage) pages.get(i);
            BufferedImage image = page.convertToImage();
            book.append(new ImagePrintable(printJob, image, pages.size()), printJob.defaultPage());
        }
        printJob.setPageable(book);
        if(this.printService != null){
            printJob.setPrintService(printService);
        }
        if(hasUI == true){
            if (printJob.printDialog()) {
                printJob.print();
            }
        }else{
            printJob.print();
        }
    }
	
	/**
     * 找出一樣的印表機名稱
     * @param printname
     * @return
     */
	public PrintService findPrintServicesName(String printname){
        PrintService printservice = null;
        PrintService services[] = this.findPrintServices(null, null);
        if (services.length == 0){
            return null;
        }
        for(int i = 0 ; i < services.length ; i++){
            PrintService ps = services[i];
            if(printname.equalsIgnoreCase(ps.getName())){
                printservice = ps;
            }
        }
        return printservice;
    }
	
	
	/**
     * 依照條件找出可用的印表機
     * @param docflavor
     * @param pras
     * @return
     */
	  public PrintService[] findPrintServices(DocFlavor docflavor, PrintRequestAttributeSet pras){
	        PrintService[] services = PrintServiceLookup.lookupPrintServices(docflavor, pras);
	        return services;
	  
	 }

	public PrintService getPrintService() {
		return printService;
	}

	public void setPrintService(PrintService printService) {
		this.printService = printService;
	}

	public boolean isHasUI() {
		return hasUI;
	}

	public void setHasUI(boolean hasUI) {
		this.hasUI = hasUI;
	}
	  
	  
	
	
	
	
	
	
}
