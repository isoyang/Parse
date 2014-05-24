package work.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;


// this file is to standardize the titles and add titles to total calculations

public class CleanExcel {
	
	public static boolean containStrings(WritableCell cell,String reg)
	{
		return cell.getContents().matches(reg);
	}
	
	public static boolean containAttribute(WritableSheet sheet,String string)
	{
		Cell [] cell = sheet.getColumn(0);
		
		boolean indicator = false;
		
		for (Cell c : cell)
		{
			if (c.getContents().equals(string))
			{
				indicator = true;
				break;
			}
		}
		
		return indicator;
	}
	
	public static boolean containString(WritableCell cell,String string)
	{
		return cell.getContents().indexOf(string) != -1;
	}
	public static boolean containNumber(WritableCell cell)
	{
		CellType type = cell.getType();
		
		if(type == CellType.NUMBER)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean isEmpty(WritableCell cell)
	{
        CellType type = cell.getType();
		
		if(type == CellType.EMPTY)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static int getRowNumber(WritableSheet sheet,String reg)
	{
		Cell [] cell = sheet.getColumn(0);
		int row = 0;
		
		for (int i = 0 ; i < cell.length ; i++)
		{
			if(cell[i].getContents().matches(reg))
			{
				row = i ;
			}
		}
		
		return row;
	}
	
	public static int getRowofFirstNumber(WritableSheet sheet)
	{
		int row = 0 ;
		
		WritableCell cell;
		
		for (int i = 0 ; i < sheet.getRows() ; i++)
		{
			cell = sheet.getWritableCell(0, i);
			if(cell.getType() == CellType.NUMBER)
			{
				row = i;
				break;
			}
		}	
		return row;
	}
	
	public static void insertAttribute(WritableSheet sheet, String string,int in) throws RowsExceededException, WriteException
	{
		int row = sheet.getRows();
		WritableCell cbefore;
		
		Label add = new Label(0,row,sheet.getCell(0, row-1).getContents());
		
		for (int i = row-1 ; i > in; i--)
		{
			
			cbefore = sheet.getWritableCell(0, i-1);		
			sheet.addCell(new Label(0,i,cbefore.getContents()));			
		}
		
		sheet.addCell(new Label(0,in,string));
		sheet.addCell(add);
	}
	
	public static void tackle(WritableSheet sheet,int in,String reg,String str1,String str2) throws RowsExceededException, WriteException
	{
		WritableCell cell1;
		WritableCell cell2;
		
		int test = 0;
		int number = 0;
		int first = 0;
		for (int j = 0 ; j < sheet.getRows() ; j++)
		{
			cell1 = sheet.getWritableCell(0, j);
			cell2 = sheet.getWritableCell(1, j);
			
			
			if(containStrings(cell1,reg) && isEmpty(cell2))
			{
				test = 1;
				number = j;
				
			}
			else if(!containStrings(cell1,reg) && !containAttribute(sheet,str2))
			{
				test = 2;				
			}
			else if(!containStrings(cell1,reg) && containAttribute(sheet,str2))
			{
				test = 3;				
			}
		}
		
		switch(test)
		{
		case 0:			
			System.out.println("there is nothing to do with this sheet!");
			break;
		case 1:
			sheet.addCell(new Label(0,number,str1));
			break;
		case 2:
			insertAttribute(sheet,str1,in);
			insertAttribute(sheet,str2,in+1);
			break;
		case 3:
		    first = getRowofFirstNumber(sheet);
		    insertAttribute(sheet,str2,first);
		    break;
		}	
		
	}
	
	public static  List<Integer> returnNumberofBlanks(WritableSheet sheet,int i ,int j)
	{
		List<Integer> list = new ArrayList<Integer>();
		
		
		
		WritableCell cell;
		
		for (int m = i ; m < j+1 ; m++)
		{
			cell = sheet.getWritableCell(0, m);
			
			if(cell.getType() == CellType.EMPTY)
			{
				list.add(Integer.valueOf(m));
			}
		}
		
		return list;
	}
	
	public static int sumofcolumn(WritableSheet sheet,int i, int j)
	{
		int sum = 0;
		WritableCell cell;
		
		
			for (int m = i ; m < j+1 ; m++)
			{
				cell = sheet.getWritableCell(1,m);
				
				if(cell.getType() == CellType.NUMBER)
				{
					sum = sum + Integer.valueOf(cell.getContents());
				}
			}
		
		
		
		return sum;
	}
	
	public static void makeUp(List<Integer> list,WritableSheet sheet,String str1,String title,String str2,String str3) throws RowsExceededException, WriteException
	{
		
		int i;
		int j;
		int size;
		if(!containAttribute(sheet,str1))
		{
			i = getRowNumber(sheet,str2);
			
			j = getRowNumber(sheet,str3);
			
			list  = returnNumberofBlanks(sheet,i,j);
			
		    size = list.size();
			
			if(size!=0)
			{
				sheet.addCell(new Label(0, list.get(size-1).intValue(),title));
			}
			
			else
			{
				insertAttribute(sheet,title,j-1);
				int num;
				for (int m = 1; m < sheet.getColumns(); i++)
				{
					num = sumofcolumn(sheet,i,j);
					sheet.addCell(new Number(m,j-1,num));
				}
			}
			
		}
		
		list = null;
	}
		
	public static void processCashFlow(WritableSheet sheet) throws RowsExceededException, WriteException
	{
		WritableCell cell1;
		WritableCell cell2;
		
		//regular expression
		String reg = "(?i).*?cash.*(begin|end).*";
		String reg1 = "(?i).*?cash.*(increase|decrease).*";	
		String reg2 = "(?i).*?cash.*?flow.*operat.*";
		String reg3 = "(?i).*?cash.*?flow.*financ.*";
		String reg4 = "(?i).*?cash.*?flow.*invest.*";
		String reg5 = "(?i).*?operating\\sactivities.*?";
		String reg6 = "(?i).*?financing\\sactivities.*?";
		String reg7 = "(?i).*?investing\\sactivities.*?";
		
		//attribute
		String netoperate = "Net Cash flow from operating activities";
		String operate = "Cash flow from operating activities";
		String netfinance = "Net Cash flow from financing activities";
		String finance = "Cash flow from financing activities";
		String netinvest = "Net Cash flow from investing activities";
		String invest = "Cash flow from investing activities";
		String cashc = "Cash increase/decrease in cash and cash equivalents";
		
		boolean total = false;
		boolean cashchange = false;
		
		for (int i = 0 ; i < sheet.getRows() ; i++)
		{
			System.out.println(i);
			cell1 = sheet.getWritableCell(0, i);
			cell2 = sheet.getWritableCell(1, i);
			
			
			if(containStrings(cell1,reg) && containNumber(cell2))
			{
				total = true;
				
				if(containString(cell1,"begin"))
				{
					sheet.addCell(new Label(0,i,"Cash and cash equivalent beginning of the period"));
				}
				else
				{
					sheet.addCell(new Label(0,i,"Cash and cash equivalent end of the period"));
				}
			}
			
			if(containStrings(cell1,reg1) && containNumber(cell2))
			{
				cashchange = true;
				
				if(containString(cell1,"increase") || containString(cell1,"decrease"))
				{
					sheet.addCell(new Label(0,i,cashc));
				}
			}
			
			if(containStrings(cell1,reg2) && containNumber(cell2))
			{
				sheet.addCell(new Label(0,i,netoperate));
			}
			else if(containStrings(cell1,reg3) && containNumber(cell2))
			{
				sheet.addCell(new Label(0,i,netfinance));
			}
			else if(containStrings(cell1,reg4) && containNumber(cell2))
			{
				sheet.addCell(new Label(0,i,netinvest));
			}		
			
		}
		
		if(!cashchange)
		{
			System.out.println("add attribute");
			sheet.addCell(new Label(0,sheet.getRows(),"Cash increase/decrease in cash and cash equivalents" ));
		}
		
		System.out.println("Begin to insert attribute!!!!");
		// to insert attributes.
		int n = getRowNumber(sheet,"Cash\\s(increase|decrease)\\sin\\scash\\sand\\scash\\sequivalents");
		
		tackle(sheet,n,reg5,operate,netoperate);
		tackle(sheet,n,reg6,finance,netfinance);
		tackle(sheet,n,reg7,invest,netinvest);
		
		System.out.println("make up some stuff like add numbers!!!!!");
		// to make up 
		List<Integer> list = new ArrayList<Integer>();
		makeUp(list,sheet,operate,netoperate,operate,finance);
		makeUp(list,sheet,finance,netfinance,finance,invest);
		makeUp(list,sheet,invest,netinvest,invest,cashc);	
	}
	
	public static void processBalanceSheet(WritableSheet sheet)
	{
		
	}
	
	public static void processIncomeAndLoss(WritableSheet sheet)
	{
		
	}
	
	public static void processChangeEquity(WritableSheet sheet)
	{
		
	}
	
	
	
	public static void main (String args []) throws BiffException, IOException, RowsExceededException, WriteException
	{
		WritableWorkbook copy = Workbook.createWorkbook(new File("C:\\Users\\Yang\\Desktop\\FileIE\\cashnew.xls"), Workbook.getWorkbook(new File("C:\\Users\\Yang\\Desktop\\FileIE\\cashflow.xls")));
		
		WritableSheet sheet = copy.getSheet(0);
		
		processCashFlow(sheet);
		
		copy.write();
		copy.close();
		
		
	}

}
