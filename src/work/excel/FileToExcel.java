package work.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFCell;

import tool.io.*;
import search.SearchDir.*;

public class FileToExcel {
	
	public static void OutputExcel(String string,HSSFWorkbook wb)
	{
		FileOutputStream fos = null;
		
		try
		{
			fos = new FileOutputStream(new File(string));
			wb.write(fos);
			fos.close();
		}
		
		catch(Exception e){}
						
	}
	
	public static void main (String [] args)
	{ 
		String time = "quarter";
		String type = "balance";
		String path = "/home/yang/Documents/BusinessWeekProject";
		
		List<String> content = new ArrayList<String>();
				
		String url;
		String name;
		String judge;
		String value;
		String []temp1;
		String [] temp;
				
		SearchDir.search(path+"/"+"data"+"/"+time+"/"+type);
		List<String> list = SearchDir.getList();
		
		HSSFWorkbook wb;
		HSSFSheet sheet;
		HSSFRow  row;
		
		int rows;
		
		
		
		for (String s:list)
		{
			int arrow = 0;
			int pointer = 0;
			
			temp = s.split("/");
			temp1 = temp[temp.length-1].split("\\.");
			name = temp1[0];
			
			System.out.println("    Begin Output "+"  "+name);
			content = IOFile.readFile(s);
			
			 wb = new HSSFWorkbook();
			 sheet = wb.createSheet(type);
			 
			 HSSFCell cell;
			 
			 if (type.equals("balance"))
			 {
				 rows = (content.size()-2)/5 + 2;
			 }
			 else
			 {
				 rows = content.size()/5;
			 }
			 
			 System.out.println("    Total"+"  "+(int)rows);
			
		     while (pointer < rows)
		     {
		    	 row = sheet.createRow(pointer);
		    	 
		    	 judge = content.get(arrow);
		    	 
		    	 if (judge.equals("Assets"))
		    	 {
		    		 System.out.println("    Assets Found");
		    		 cell = row.createCell(0);
		    		 cell.setCellValue("Assets");
		    		 arrow++;
		    	 }
		    	 else if (judge.equals("LIABILITIES &amp; EQUITY"))
		    	 {
		    		 System.out.println("    Liability Found");
		    		 cell = row.createCell(0);
		    		 cell.setCellValue("LIABILITIES AND EUITY");
		    		 arrow++;
		    	 }
		    	 
		    	 else
		    	 {
		    		 System.out.println("    Records Found");
		    		 for (int i = arrow ; i < arrow + 5 ; i++)
		    		 {
		    			 cell = row.createCell(i-arrow);
		    			 value = content.get(i);
		    			 
		    			 if (value.matches("-?\\d+\\.?\\d*"))
		    			 {
		    				 cell.setCellValue(Double.parseDouble(value));
		    			 }
		    			 else
		    			 {
		    				 cell.setCellValue(value);
		    			 }
		    		 }
		    		 
		    		 arrow = arrow + 5;
		    	 }
		    	 pointer++;
		    	 	    	 
		     }
		     
		     OutputExcel(path+"/"+"excel"+"/"+time+"/"+type+"/"+name+".xls",wb);
					
		}
		SearchDir.clearList();
	}

}