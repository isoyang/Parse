package work.excel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import tool.io.*;
import search.SearchDir.SearchDir;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;

public class ScanCell {

	
	public static void addRow(WritableSheet sheet, Cell [] cell,int row) throws Exception
	{
		CellType type;
		for(int i = 0 ; i < cell.length ; i++)
		{
			type = cell[i].getType();
			if( type == CellType.LABEL)
			{
				sheet.addCell(new Label(i,row,cell[i].getContents()));
			}
			
			else if(type == CellType.NUMBER)
			{
				sheet.addCell(new Number(i,row,Double.parseDouble(cell[i].getContents())));
			}
			else if(type == CellType.EMPTY)
			{
				sheet.addCell(new Label(i,row,""));
			}
		}
	}
	
	public static String toString(Cell [] cell)
	{
		StringBuffer sb = new StringBuffer();
		
		for(Cell singlecell : cell)
		{
			sb.append(singlecell.getContents());
		}
		
		return sb.toString();
	}
	
	public static boolean isContainDateUnit(int n, int size,String regDate,String regUnit,Sheet sheet)
	
	{
	   Cell cell;
	   String string;
	   boolean indicator = false;
	   int pointer = 0;
	   int pointer1 = 0;
	   
	   if (n >= size - 9)
	   {
		   return indicator;
	   }
		   

	   for (int i = n; i < n + 9 ; i++)
	   {
		   for (int j = 0; j < sheet.getColumns(); j++)
		   {
			      cell = sheet.getCell(j,i);

			      string = cell.getContents();

			     if(string.matches(regDate))
			     {
			    	 	
				        pointer++;
			    	 
			     }
			     else if(string.matches(regUnit))
			     {
			    	   pointer1++;
			     }

			}

		 }
	   
	   if (pointer != 0 && pointer1 != 0)
	   {
		   indicator  = true;
		   return indicator;
	   }  
	   else
	   {
		   return indicator;
	   }
	   
			
	}

	public static void main(String args[]) throws Exception
	{
		 String reg = "(?i).*?(condensed|consolidated).*?((balance\\ssheet)|(financial\\sposition)).*?";
		 
		 String reg1 = "(?i).*?(condensed|consolidated).*?(operation|(comprehensive\\sloss)|(comprehensive\\sincome)|(net\\sloss)|(net\\sincome)|(statements.*?loss)).*?";
		 
		 String reg2 = "(?i).*?(condensed|consolidated).*?(cash\\sflows).*?";
		 
		 String regDate = "(?i).*(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec).*";
		 
		 String regUnit = "(?i).*(dollar|usd|eur|\\$).*";
		 
		 String dir = "/home/yang/Documents/Work/Excel/";
		 String path = dir+"/reporterror/errortest.txt";
		 String foldername = "test";
		 SearchDir.search(dir+"/"+foldername);
		 List<String> list = SearchDir.getList();
		 List<String> namelist = new ArrayList<String>();
		 String [] temp;
		 String name;
					
		 WritableWorkbook newbook;
		 Workbook workbook;
		 Sheet sheet;
		 WritableSheet nsheet;
		 WritableSheet nsheet1;
		 WritableSheet nsheet2;
		 
		 int nrow;
		 Cell [] cell;
		 String string;
		 
		 
		 
		 for (String ls:list)
		 {
			 temp = ls.split("/");
			 name = temp[temp.length-1];
			 
			 workbook = Workbook.getWorkbook(new File(ls));
			 newbook = Workbook.createWorkbook(new File(dir+"/splittable/"+"/"+foldername+"/"+name));
		     
		     sheet = workbook.getSheet(0);
		     nsheet = newbook.createSheet("Financial Position", 0);
		     nsheet1 = newbook.createSheet("Opreration", 1);
		     nsheet2 = newbook.createSheet("Cash Flows", 2);
		     
		     nrow = sheet.getRows();
		     
		     int pointer = 0;
		     int pointer1 = 0;
		     int pointer2 = 0;
		     boolean indicator = false;
		     int rows = 0;
		     int size;
		     int select = 0;
		     int calculator = 0;
		     
		     
		     for (int i = 0 ; i < nrow ; i++)
		     {
		    	 cell = sheet.getRow(i);
		    	 size = cell.length;
		    	 string = toString(cell);
		    	 
		    	 if (string.matches(reg) && isContainDateUnit(i+1,nrow,regDate,regUnit,sheet) && i < nrow-9)
		    	 {
		    		 System.out.println(i);
		    		 calculator++;
		    		 pointer++;
		    		 rows = i;
		    		 indicator = true;
		    		 select = 1;
		    	 }
		    	 else if(string.matches(reg1) && isContainDateUnit(i+1,nrow,regDate,regUnit,sheet) && i < nrow-9)
		    	 {
		    		 System.out.println(i);
		    		 pointer1++;
		    		 calculator++;
		    		 rows = i;
		    		 indicator = true;
		    		 select = 2;
		    	 }
		    	 else if(string.matches(reg2) && isContainDateUnit(i+1,nrow,regDate,regUnit,sheet) && i < nrow-9)
		    	 {
		    		 System.out.println(i);
		    		 pointer2++;
		    		 calculator++;
		    		 rows = i;
		    		 indicator = true;
		    		 select = 3;
		    	 }
		    	 	    	 
		    	 if(indicator)
		    	 {
		    		 switch(select)
		    		 {
		    		   
		    		   case 1 :
		    			 addRow(nsheet,cell,pointer);
			    		 pointer++;
			    		 break;
		    		   case 2 :
		    			 addRow(nsheet1,cell,pointer1);
		  	    		 pointer1++;
		  	    		 break;
		    		   case 3 :
		    			 addRow(nsheet2,cell,pointer2);
		  	    		 pointer2++; 
		  	    		 break;
		    		 }    		 
		    	 }
		    	 
		    	 if(indicator && size == 0 && i - rows >10)
		    	 {
		    		 indicator = false;
		    		 switch(select)
		    		 {
		    		 case 1 :
		    			 nsheet.addCell(new Label(0,pointer,""));
		               	 pointer++;
		               	 break;
		    		 case 2 :
		    			 nsheet1.addCell(new Label(0,pointer1,""));
		               	 pointer1++;
		               	 break;
		    		 case 3 :
		    			 nsheet2.addCell(new Label(0,pointer2,""));
		               	 pointer2++;
		               	 break;
		    		 }
		    		  
		    	 }	
		    	 
		     }
		     if(calculator < 3 && calculator >= 1)
		     {
		    	 System.out.print(calculator);
		    	 System.out.println("     This File should be recorded !!");
		    	 namelist.add(name);	    	
		     }
		     
		     
		     newbook.write();
		     newbook.close();
		 }
		 
		 IOFile.writeFile(namelist, path, false);
		    
		 SearchDir.clearList();
		 
		
	}
}
