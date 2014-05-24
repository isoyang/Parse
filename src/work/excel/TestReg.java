package work.excel;

import java.io.File;
import java.io.IOException;

import jxl.*;
import jxl.read.biff.BiffException;



public class TestReg {

	public static void main (String args []) throws BiffException, IOException
	{
		
		Workbook workbook = Workbook.getWorkbook(new File("/home/yang/Documents/Work/Excel/test/2q2013.xls"));
		
		Sheet sheet = workbook.getSheet(0);
		
		int nrow = sheet.getRows();
		int ncol = sheet.getColumns();
		
		 String regUnit = "(?i).*(dollar|usd|eur|\\$).*";
		
		 String regDate = "(?i).*(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec).*";
	
		System.out.println(ScanCell.isContainDateUnit(59, nrow,regDate, regUnit, sheet));
		
		/*String string= "(EXPRESSED IN CANADIAN DOLLARS)";
		 String regUnit = "(?i).*(dollar|usd|eur|\\$).*";
		 
		 System.out.println(string.matches(regUnit));*/
		
	}
}
