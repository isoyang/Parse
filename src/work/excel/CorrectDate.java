package work.excel;

import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

import java.io.*;

public class CorrectDate {
	
	static String reg = "(!?).*?(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec).*?";
	
	public static void findDate(WritableSheet sheet)
	{
		int row = 0;
		Cell cell;
		Cell nextcell;
		Label label;
		
		for (int i = 0 ; i < sheet.getRows() ; i++)
		{
			for (int j = 0 ; j < sheet.getColumns(); j++)
			{
				cell = sheet.getCell(j, i);
				
				if(cell.getType() == CellType.LABEL)
				{
					label = (Label) cell;
					System.out.println(label.getString());
					System.out.println(label.getString().matches(reg));
					if(label.getString().matches(reg) && i < sheet.getRows()-1)
					{
						System.out.println("Date attribute has been found!!!");
						row = i + 1;
						nextcell = sheet.getCell(j, row);
						label.setString(label.getString()+nextcell.getContents());
					}
				}
			}
		}
		
		sheet.removeRow(row);
	}

	public static void main(String[] args) throws BiffException, IOException, WriteException {
		// TODO Auto-generated method stub

		WritableWorkbook workbook = Workbook.createWorkbook(new File("/home/yang/Documents/Workspace/Test/CorrectUnitRes.xls"),Workbook.getWorkbook(new File("/home/yang/Documents/Workspace/Test/TestCorrectUnit.xls")));
		 
		WritableSheet sheet = workbook.getSheet(0);
		
		findDate(sheet);

		workbook.write();
		workbook.close();
		
		System.out.println("Dec".matches(reg));
	}

}
