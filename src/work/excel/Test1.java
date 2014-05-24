package work.excel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import tool.process.ToLog;
import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;
import search.SearchDir.*;




public class Test1 {
	
	
	
	public static void main(String args []) throws BiffException, FileNotFoundException, IOException, RowsExceededException, WriteException
	{
		 
		String dir = "/home/yang/Documents/Work/Excel";
		SearchDir.search(dir+"/newwork1");
		List<String> list = SearchDir.getList();
		String [] temp;
		String name;
		
		for (String ls : list)
		{
			
			System.out.println(ls);
		}
		
	
		
		SearchDir.clearList();
		
		
		
		
		
		
		
		
		 //WritableWorkbook workbook = Workbook.createWorkbook(new File("/home/yang/Documents/Workspace/Test/testadd.xls"),Workbook.getWorkbook(new File("/home/yang/Documents/Workspace/Test/add.xls")));
		 
		 //WritableSheet sheet = workbook.getSheet(0);
		 
		 // test function  insertAttribute
		/* CleanExcel.insertAttribute(sheet,"add",1);
		 CleanExcel.insertAttribute(sheet,"fen",2);
		 
		 for (int i =  0 ; i < sheet.getRows() ; i++)
		 {
			 Cell [] cell = sheet.getRow(i);
			 for(int j = 0 ; j < cell.length ; j++)
			 {
				 Cell c = sheet.getCell(j,i);
				 
				 System.out.print(c.getContents());
				 System.out.print(" ");
				 
			 }
			 System.out.println("");
		 }
		 */
		 
		 // test containString containStrings
		 
		 /*for (int i = 0 ; i < sheet.getRows() ; i++)
		 {
			 WritableCell cell = sheet.getWritableCell(0, i);
			 
			 System.out.println(CleanExcel.containString(cell, "test"));
		 }*/
		 
		 // test containAttribute
		 
		// System.out.println(CleanExcel.containAttribute(sheet, "containAttribut"));
		 
		 // test function containNumber
		 
		 /*for (int i = 0 ; i < sheet.getRows() ; i++)
		 {
			 System.out.println(CleanExcel.containNumber(sheet.getWritableCell(0, i)));
		 }*/
		 
		 // test function getRowNumber
		 
		 //System.out.println(CleanExcel.getRowNumber(sheet, "test"));
		 
		 // test function getrowoffirstnumber
		 
		// System.out.println(CleanExcel.getRowofFirstNumber(sheet));
		 
		/* List<Integer> list = new ArrayList<Integer>();
		 
		 list = CleanExcel.returnNumberofBlanks(sheet, 7, 13);
		 
		 for(Integer in:list)
		 {
			 System.out.println(in.intValue());
		 }
		 
		 
		 Cell [] c = sheet.getColumn(0);
		 
		 for(Cell cl:c)
		 {
			 System.out.println(cl.getType().toString());
		 }*/
		 
		 
		 // test function sumofcolumn
		 
         /* Cell [] c = sheet.getColumn(0);
		 
		 for(Cell cl:c)
		 {
			 System.out.println(cl.getType().toString());
		 }
		 
		 System.out.println(CleanExcel.sumofcolumn(sheet, 0, 13));
		 */
		 
		 // test function tackle
		 
	/*	 String netoperate = "Net Cash flow from operating activities";
		 String operate = "Cash flow from operating activities";
		 
		 String reg5 = "(?i).*?operating\\sactivities.*?";
		 int n = CleanExcel.getRowNumber(sheet,"Cash\\s(increase|decrease)\\sin\\scash\\sand\\scash\\sequivalents");
		 
		 CleanExcel.tackle(sheet,n,reg5,operate,netoperate);
		 
	 workbook.write();
	 workbook.close();*/

	}

}
