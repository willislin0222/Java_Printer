package com.printer.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class ImagePrintable implements Printable{
	
	private double x , y , width;
	private int orientation;
	private BufferedImage image;
	private int pagesize=1;
	
	public ImagePrintable(PrinterJob printJob,BufferedImage image,int pagesize){
		PageFormat pageFormat = printJob.defaultPage();
		this.x = pageFormat.getImageableX();
		this.y = pageFormat.getImageableY();
		this.width = pageFormat.getImageableWidth();
		this.orientation = pageFormat.getOrientation();
		this.image = image;
		this.pagesize = pagesize;
	}
	
	public ImagePrintable(PrinterJob printJob,BufferedImage image){
		PageFormat pageFormat = printJob.defaultPage();
		this.x = pageFormat.getImageableX();
		this.y = pageFormat.getImageableY();
		this.width = pageFormat.getImageableWidth();
		this.orientation = pageFormat.getOrientation();
		this.image = image;
	}
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if(pageIndex < pagesize){
			int pWidth = 0;
			int pHeight = 0;
			if(orientation == PageFormat.PORTRAIT){ //直印
				
				pWidth = (int) Math.min(width,(double) image.getWidth());
				pHeight = pWidth * image.getHeight() / image.getWidth();
				
			}else { //橫印 LANDSCAPE
				
				pHeight = (int) Math.min(width, (double) image.getHeight());
				pWidth = pHeight * image.getWidth() / image.getHeight();
				
			}
			
			graphics.drawImage(image,(int) x,(int) y, pWidth, pHeight, null);
			return PAGE_EXISTS;
		}else{
			return NO_SUCH_PAGE;
		}
	}

}
