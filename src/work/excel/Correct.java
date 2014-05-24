package work.excel;

import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

import java.io.*;

public class Correct {
	
	// process sheet1
	public static void correctWriting1(WritableSheet sheet) throws RowsExceededException, WriteException
	{
		String reg = "(?i)current.*";
		WritableCell cell1;
		WritableCell cell1before = null;
		WritableCell cell2;
		
		for(int n = 0 ; n < sheet.getRows() ; n++)
		{
		while(n != 0)
		{
			cell1before = sheet.getWritableCell(0,n-1);
		}
		cell1 = sheet.getWritableCell(0, n);
		cell2 = sheet.getWritableCell(1, n);
		
		if(n!=0 && cell1before.getContents().equalsIgnoreCase("assets") && cell1.getContents().matches(reg))
		{
			sheet.addCell(new Label(0,n,"Current Assets"));
		}
		
		if(n!=0 && cell1before.getContents().equalsIgnoreCase("liabilities") && cell1.getContents().matches(reg))
		{
			sheet.addCell(new Label(0,n,"Current Liabilities"));
		}
		
		if(cell1.getType() == CellType.EMPTY && cell2.getType() == CellType.NUMBER)
		{
			
		}
	 }
	}
	
  
	public static void correctCell(Workbook wb) throws IOException, WriteException
	{
		WritableWorkbook copy = Workbook.createWorkbook(new File("C:\\Users\\Yang\\Desktop\\wrong1.xls"), wb);
		WritableSheet sheet;
		
		WritableCell cell1;
		WritableCell cell2;
			
		Label label1;
		Label label2;
		
		boolean indicator = false;
		
		
		
		for (int i = 0 ; i < copy.getNumberOfSheets() ; i++)
		{
			sheet = copy.getSheet(i);
			for (int m = 0 ; m < sheet.getRows() ; m++)
			{
				
				System.out.println(m);
				cell1 = sheet.getWritableCell(0, m);
				cell2 = sheet.getWritableCell(1, m);
				
				if(cell1.getType() == CellType.EMPTY && cell2.getType() == CellType.LABEL)
				{
					indicator = true;
				    label1 = new Label(0,m,cell2.getContents());
				    sheet.addCell(label1);
				    
				    label2 = new Label(1,m,"");
				    sheet.addCell(label2);
				    					
				}
				
			}
			
			if(indicator)
			{
				sheet.removeColumn(1);
				
			}
		
		}
			
		copy.write();
		copy.close();
	}
	
	
	public static void main(String args[]) throws BiffException, FileNotFoundException, IOException, WriteException
	{
		correctCell(Workbook.getWorkbook(new FileInputStream("C:\\Users\\Yang\\Desktop\\wrong.xls")));
		
		
	}

}
