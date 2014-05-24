package work.excel;

import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

import java.io.*;

public class CorrectUnit {
	
	public static void printSheet(WritableSheet sheet)
	{
		Cell cell;
		for (int i = 0 ; i < sheet.getRows() ; i++)
		{
			for (int j = 0 ; j < sheet.getColumns() ; j++)
			{
				cell = sheet.getCell(j, i);
				
				System.out.print(cell.getContents());
				System.out.print(" ");
				
			}
			System.out.println("");
		}
	}
	
	public static void printType(WritableSheet sheet)
	{
		Cell cell;
		for (int i = 0 ; i < sheet.getRows() ; i++)
		{
			for (int j = 0 ; j < sheet.getColumns() ; j++)
			{
				cell = sheet.getCell(j, i);
				CellType type = cell.getType();
				System.out.print(type);
				System.out.print(" ");
				
			}
			System.out.println("");
		}
	}
	
	public static int scanUnit(int number,WritableSheet sheet)
	{
		int indicator = 0;
		Cell cell;
		String reg = "(!?)((.*thousand.*)|(.*1000.*dollar.*))";
		
		for (int i = 0 ; i <= number; i++)
		{
			cell = sheet.getCell(0, i);
			
			System.out.println(cell.getContents());
			
			if(cell.getContents().matches(reg))
			{
				System.out.println("unit should be changed!!!");
				indicator = 1;				
				break;
			}		
		}
	
		return indicator;
	}
	
	public static void changeUnit(int indicator,WritableSheet sheet) throws RowsExceededException, WriteException
	{
		switch(indicator)
		{
		case 0 :
			break;
		case 1 :
			Cell cell;
			Number n;
			Double number;
			for (int i = 0 ; i < sheet.getRows() ; i++)
			{
				for (int j = 0 ; j < sheet.getColumns() ; j++)
				{
					cell = sheet.getCell(j, i);
					if(cell.getType()==CellType.NUMBER)
					{
						n = (Number)cell;
											
						number = n.getValue();
											    
					    n.setValue(number*1000);
					}
				}
			}
			break;
		}
	}

	public static void main(String[] args) throws BiffException, IOException, WriteException {
		// TODO Auto-generated method stub
		
	WritableWorkbook workbook = Workbook.createWorkbook(new File("/home/yang/Documents/Workspace/Test/CorrectUnitRes.xls"),Workbook.getWorkbook(new File("/home/yang/Documents/Workspace/Test/TestCorrectUnit.xls")));
		 
	WritableSheet sheet = workbook.getSheet(0);
	
	printType(sheet);
	
	
	//System.out.println(scanUnit(8,sheet));
	CorrectDate.findDate(sheet);
	changeUnit(scanUnit(8,sheet),sheet);
	
	
	workbook.write();
	workbook.close();

	}

}
