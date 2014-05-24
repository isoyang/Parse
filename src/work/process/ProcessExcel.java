package work.process;

import java.util.*;
import java.io.*;

import jxl.*;
import search.SearchDir.*;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ProcessExcel {
	
	public static void convertXlsx(String oldpath,String newpath) throws FileNotFoundException, IOException
	{
		Workbook oldwb = new XSSFWorkbook(new FileInputStream(oldpath));
		Workbook newwb = new HSSFWorkbook();
		
		int sizeofsheet = oldwb.getNumberOfSheets();
		
		for (int i = 0 ; i < sizeofsheet ; i++)
		{
			Sheet sheet = oldwb.getSheetAt(i);
			Sheet nsheet = newwb.createSheet(sheet.getSheetName());
			
			for (Row row : sheet)
			{
				Row nrow = nsheet.createRow(row.getRowNum());
				
				for (Cell cell : row)
				{
					Cell ncell = nrow.createCell(cell.getColumnIndex(),cell.getCellType());
					
					//ncell.setCellStyle(HSSFCellStyle)cell.getCellStyle());    
	                 
					
					switch (cell.getCellType()) {
				
					
					case Cell.CELL_TYPE_BLANK:
                        break;
					
	                case Cell.CELL_TYPE_STRING:
	                    ncell.setCellValue(cell.getRichStringCellValue().getString());
	                    break;
	                    
	                case Cell.CELL_TYPE_NUMERIC:
	                	if (DateUtil.isCellDateFormatted(cell)) {
	                        ncell.setCellValue(cell.getDateCellValue());
	                    } 
	                	else
	                    {
	                		
	                		ncell.setCellValue(cell.getNumericCellValue());
	                    }
	                    
	                    break;
	                    
	                case Cell.CELL_TYPE_BOOLEAN:
	                    ncell.setCellValue(cell.getBooleanCellValue());
	                    break;
	                    
	                case Cell.CELL_TYPE_FORMULA:
	                    ncell.setCellFormula(cell.getCellFormula());
	                    break;
	                    
	                case Cell.CELL_TYPE_ERROR:
                        ncell.setCellValue(cell.getErrorCellValue());
                        break;
					}
					
				    
					//ncell.setCellStyle(style);
	               
				}
				
			}
	    }
		
		File file = new File(newpath);
		FileOutputStream out = new FileOutputStream(file);
		
		newwb.write(out);
		out.close();
	}

	public static void  main(String args[]) throws Exception
	{
		String dir = "/home/yang/Documents/Work/Excel";
		SearchDir.search(dir+"/newwork2");
		List<String> list = SearchDir.getList();
		String [] temp;
		String name;
		
		for (String ls : list)
		{
			//temp = ls.split("\\\\");
            temp = ls.split("/");
			
			name = temp[temp.length-1];
					
			convertXlsx(ls,dir+"/n2"+"/"+name+".xls");
			
			System.out.println(name+".xls");
		}
		
	
		
		SearchDir.clearList();
		
		
	    /*jxl.Workbook workbook = jxl.Workbook.getWorkbook(new File("C:\\Users\\YANG\\Desktop\\1.xls"));
		
		jxl.Sheet sheet1 = workbook.getSheet(0);
		
		jxl.Cell cell = sheet1.getCell(1, 71);
	
		
		if(cell.getType() == CellType.NUMBER)
		{
			System.out.println("I am a NUmber!!");
			NumberCell nc = (NumberCell) cell;
			
		    Double   number = nc.getValue();
			
			System.out.println(number);
		}*/
				
	}
}
