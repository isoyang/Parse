package test.work;

import java.io.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfReader;

import org.apache.tika.*;


public class TestCheck {

	public static void main(String args[]) throws Exception
	{
       String file ="E:\\1.pdf";
       
       File f = new File(file);
       long type = f.length();
       
       
       System.out.println(type);
       
      
    /*
     Document document = new Document(new PdfReader(file).getPageSize(1));
     document.open();
     PdfReader pdf = new PdfReader(file);
     
     int num = pdf.getNumberOfPages();
       
       if (num != 0)
       {
    	   System.out.println("The file can be read!!");
    	   
    	   document.close();
       }
       
       else
       {
    	   System.out.println("This File is broken!!!");
       }*/
		
	  
	}
}
