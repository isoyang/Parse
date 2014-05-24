package work.process;


import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.util.*;
import java.io.*;

import tool.io.IOFile;
import tool.process.*;



public class PreprocessCompany {
	
	public static List<String> readExcel(String dir,int n) throws BiffException, IOException
	{
		List<String> list = new ArrayList<String>();
		Workbook workbook = Workbook.getWorkbook(new File(dir));
		
		int number = workbook.getNumberOfSheets();
		
		for (int i = 0 ; i < number ; i++)
		{
			Sheet sheet = workbook.getSheet(i);
			
		    Cell [] column = sheet.getColumn(n-1); 
		    
		    for (int j = 1 ; j < column.length ; j++)
		    {
		    	
		    	Cell cell = column[j];
		    	String str = cell.getContents();
		    	
		    	list.add(str);
		    }
			
		}
			
		return list;
	}
	
	public static List<String> readExcel(String dir,int n, int m) throws BiffException, IOException
	{
		List<String> finallist = new ArrayList<String>();
		
		List<String> list = readExcel(dir,n);
		
		List<String> list1 = readExcel(dir,m);
		
		for (int i = 0 ; i < list.size() ; i++)
		{
			finallist.add(list.get(i)+"  "+list1.get(i));
		}
		
		return finallist;
		
	}
	
	public static List<String> merge(String add, String dir)
	{
		List<String> list = new ArrayList<String>();
		
		List<String> ticker = IOFile.readFile(dir);
		
		for (String string : ticker)
		{
			list.add(add+string);
		}
				
		return list;
	}
	
	public static List<String> mergeTXT(String dir1,String dir2)
	{
		List<String> orig = IOFile.readFile(dir1);
		List<String> added = IOFile.readFile(dir2);
		
		List<String> list = new ArrayList<String>();
		
		for (int i = 0 ; i < added.size() ; i++)
		{
			String temp [] = added.get(i).split("  ");
			list.add(temp[0]);
		}
		
		orig.addAll(list);
		
		return orig;
	}
	
	
	
	public static void main(String args[]) throws Exception
	{
		
		
		List<String> list =mergeTXT("C:\\Users\\YANG\\Desktop\\FileIE\\crawltmx\\finalinput.txt","C:\\Users\\YANG\\Desktop\\FileIE\\crawlremain\\remainwebsite.txt");
		
		IOFile.writeFile(list, "C:\\Users\\YANG\\Desktop\\FileIE\\finalaftermerge.txt", false);
		
		
		
		
		
		/*List<String> list = merge("http://web.tmxmoney.com/company.php?qm_symbol=","C:\\Users\\YANG\\Desktop\\FIleIE\\ticker.txt");
		
		IOFile.writeFile(list, "C:\\Users\\YANG\\Desktop\\FIleIE\\readyinput.txt", false);
		
		
		Tackle.printList(list);
		*/
		
		
		
		
		/*List<String> list = readExcel("C:\\Users\\YANG\\Desktop\\FIleIE\\MiningCompanies.xls",3);
		List<String> list1 = readExcel("C:\\Users\\YANG\\Desktop\\FIleIE\\MiningCompanies.xls",2);
		
		IOFile.writeFile(list, "C:\\Users\\YANG\\Desktop\\FileIE\\ticker.txt", false);
		IOFile.writeFile(list1, "C:\\Users\\YANG\\Desktop\\FileIE\\name.txt", false);
		
		
		Tackle.printList(list);*/
	}

}
