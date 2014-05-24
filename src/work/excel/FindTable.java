package work.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;

public class FindTable {
	
	public static void find() throws Exception
	{
		Workbook workbook = new  HSSFWorkbook(new FileInputStream("C:\\Users\\YANG\\Dexktop\\1.xls"));
		
		int number = workbook.getNumberOfSheets();
		
		for (int i = 0 ; i < number ; i++)
		{
			Sheet sheet = workbook.getSheetAt(i);
			
			for (Row row : sheet)
			{
				
			}
		}
		
		
	}
	
	public static void main(String ags[])
	{
		
	}

}
