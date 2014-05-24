package work.excel;

import java.io.*;

import jxl.*;
import jxl.write.*;
import jxl.write.Number;


public class Test {
	
	private static String reg = "[12][0-9]{3}[^0-9]*|.*(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec).*";
	
	public static String [] toString(Cell [] cell)
	{
		int size = cell.length;
		String [] string = new String [size]; 
		
		for(int i = 0 ; i < size ; i++)
		{
			string[i] = cell[i].getContents();
		}
		
		return string;
	}

	
	public static void printArray(String [] string)
	{
		for (String str : string)
		{
			System.out.print(str);
			System.out.print("  ");
		}
	}
	
	public static CellType[] getCellType(Cell [] cell)
	{
		int size= cell.length;
		CellType [] type = new CellType[size];
		
		for(int i = 0 ; i < size ; i++)
		{
			type[i] = cell[i].getType();
		}
		
		return type;
	}
	
	public static Cell [] copyRow(Cell [] cell, int size)
	{
		Cell [] newcell = new Cell [size];
		
		for (int i = 0 ; i < size ; i++)
		{
			newcell[i] = cell[i];
		}
			
		return newcell;
	}
	
	public static void delete(WritableWorkbook ww)
	{
		Sheet sheet = ww.getSheet(0);
		
		
		
		
	}
	
	public static String getReg()
	{
		return reg;
	}
	
	

	public static void main (String args[]) throws Exception
	{
        Workbook workbook = Workbook.getWorkbook(new FileInputStream("/home/yang/Documents/Workplace/Test/2q2013.xls"));
		
        WritableWorkbook newbook = Workbook.createWorkbook(new File("/home/yang/Desktop/2q2013.xls"));
        
        String reg = getReg();
        
            
        Sheet sheet = workbook.getSheet(0);
        WritableSheet nsheet = newbook.createSheet("test", 0);
        int nrow = sheet.getRows();
        
        System.out.println(nrow);
        int pointer = 0;
        int ncellbefore = 0;
        int ncellafter = 0;
        
        CellType [] type;
        Cell [] cell;
        String [] cellstr;
        
        Cell [] cellbefore = null;
        String [] cellbeforestr = null;
        Cell [] cellafter;
       
        for (int i = 0 ; i < nrow ; i++)
        {
        	
        	cell = sheet.getRow(i);
        	
        	type = getCellType(cell);
        	
        	cellstr = toString(cell);
        	
        	
        	int ncell = cell.length;
        	       	
        	if(i > 0)
        	{
        		cellbefore = sheet.getRow(i-1);
        		cellbeforestr = toString(cellbefore);
            	ncellbefore = cellbefore.length;
        	}
        	    	
        	
        	if(i < nrow - 1)
        	{
        		cellafter = sheet.getRow(i+1);
        		ncellafter = cellafter.length;
        		
        	}
            
            
            if(ncell == 1 && ncellbefore > 1 && ncellafter >1)
            {
            	nsheet.addCell(new Label(0,pointer,cellstr[0]));
            	pointer++;
            }
            
           
        	if (ncell > 2)
        	{
        		boolean  empty = type[0] == CellType.EMPTY && type[1] == CellType.EMPTY;
        		
        		if( (empty && type[2] == CellType.EMPTY) || (empty && !cellstr[2].matches(reg)))
                {
                	
                	continue;
                }
        		      		
        		
        		 if(cellstr[2].matches(reg) && (ncellbefore == 1 || (ncellbefore > 2 && !cellbeforestr[2].matches(reg) && (cellbeforestr[0].matches(reg)||cellbeforestr[0].matches("")))))
                 {
        			 
        			 
        				nsheet.addCell(new Label(0,pointer,""));
                      	pointer++;
           	
                 }
                 
        		
        		
        		for (int j = 0 ; j < ncell ; j++)
            	{
        			
        			if(type[j] == CellType.LABEL)
        			{
        				Label label = new Label(j,pointer,cellstr[j]);
        				nsheet.addCell(label);
        			}
        			else if(type[j] == CellType.NUMBER)
        			{
        				Number num = new Number(j,pointer,Double.parseDouble(cellstr[j]));
        				nsheet.addCell(num);
        			}
            		
            	}
        		
        		pointer++;
                     	
        	}
        	  	
        }
        
        newbook.write();
        newbook.close();
		
	}
}
