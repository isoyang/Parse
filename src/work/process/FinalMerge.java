package work.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import tool.io.*;
import work.excel.FileToExcel;
import search.SearchDir.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class FinalMerge {
	
	public static HSSFWorkbook ReadExcel(String name)
	{
		FileInputStream fis;
		HSSFWorkbook wb = null;
		try {
			
			fis = new FileInputStream(new File(name));
			
			wb = new HSSFWorkbook(fis);
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return wb;
	}
	
	public static void CopyRows(HSSFSheet from, HSSFSheet to, int firstrow, int lastrow,HSSFWorkbook wb)
	{
		
		if ((firstrow == -1) || (lastrow == -1) || lastrow < firstrow) {  
            return;  
        }  
		
		
		HSSFRow fromRow = null;  
        HSSFRow newRow = null;  
        HSSFCell newCell = null;  
        HSSFCell fromCell = null;  
        
        for (int i = 0; i < lastrow; i++) {  
            fromRow = from.getRow(i);  
            if (fromRow == null) {  
                continue;  
            }  
            newRow = to.createRow(i - firstrow);  
            newRow.setHeight(fromRow.getHeight());  
            for (int j = fromRow.getFirstCellNum(); j < fromRow.getPhysicalNumberOfCells(); j++) {  
                fromCell = fromRow.getCell(j);  
                if (fromCell == null) {  
                    continue;  
                }  
                newCell = newRow.createCell(j);  
                HSSFCellStyle cellstyle = wb.createCellStyle();
                cellstyle.cloneStyleFrom(fromCell.getCellStyle());
                newCell.setCellStyle(cellstyle);  
                int cType = fromCell.getCellType();  
                newCell.setCellType(cType);  
                switch (cType) {  
                case HSSFCell.CELL_TYPE_STRING:  
                    newCell.setCellValue(fromCell.getRichStringCellValue());  
                    break;  
                case HSSFCell.CELL_TYPE_NUMERIC:  
                    newCell.setCellValue(fromCell.getNumericCellValue());  
                    break;  
                case HSSFCell.CELL_TYPE_FORMULA:  
                    newCell.setCellFormula(fromCell.getCellFormula());  
                    break;  
                case HSSFCell.CELL_TYPE_BOOLEAN:  
                    newCell.setCellValue(fromCell.getBooleanCellValue());  
                    break;  
                case HSSFCell.CELL_TYPE_ERROR:  
                    newCell.setCellValue(fromCell.getErrorCellValue());  
                    break;  
                default:  
                    newCell.setCellValue(fromCell.getRichStringCellValue());  
                    break;  
                }  
            } 
        }
		
	}
	public static void CopySheet(HSSFSheet from, HSSFSheet to,HSSFWorkbook wb)
	{
		to.setMargin(HSSFSheet.TopMargin,from.getMargin(HSSFSheet.TopMargin));  
        to.setMargin(HSSFSheet.BottomMargin,from.getMargin(HSSFSheet.BottomMargin));  
        to.setMargin(HSSFSheet.LeftMargin,from.getMargin(HSSFSheet.LeftMargin) );  
        to.setMargin(HSSFSheet.RightMargin,from.getMargin(HSSFSheet.RightMargin));
        
        HSSFPrintSetup ps = to.getPrintSetup();  
        ps.setLandscape(false);  
        ps.setVResolution((short)600);  
        ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
        
        CopyRows(from, to, from.getFirstRowNum(),from.getLastRowNum(),wb);
        
		
	}
	
	public static void Merge (String balance,String cf, String is,String path)
	{
		HSSFWorkbook wb1 = ReadExcel(balance);
		HSSFWorkbook wb2 = ReadExcel(cf);
		HSSFWorkbook wb3 = ReadExcel(is);
		
		HSSFWorkbook wbnew  = new HSSFWorkbook();
		
		HSSFWorkbook [] book = {wb1,wb2,wb3}; 
		String sheetname [] = {"balance","cashflow","incomestatement"};
		HSSFSheet [] sheet = new HSSFSheet [3];
		
		for (int i = 0 ; i < 3 ; i ++)
		{
			sheet[i] = wbnew.createSheet(sheetname[i]);
			CopySheet(book[i].getSheetAt(0),sheet[i],wbnew);
		}
		
		FileToExcel.OutputExcel(path, wbnew);
	}
	
	public static String toStrings(List<String> list)
	{
        StringBuffer sb = new StringBuffer();
		
		
		for (String str:list)
		{
			sb.append(str+" ");
		}
	      
		return " "+sb.toString();
	}
	public static boolean Contains(String string,String candidate)
	{
		boolean judge = false;
		
		if (string.matches(".*?\\s"+candidate+"\\s.*?"))
		{
			judge = true;
		}
		return judge;
	}
	public static void main (String args [])
	{
		String path = "/home/yang/Documents/BusinessWeekProject";
		
		String time = "quarter";
		
		List<String> ba = IOFile.readFile(path+"/"+time+"_"+"balance");
		String sba = toStrings(ba);
		List<String> is = IOFile.readFile(path+"/"+time+"_"+"incomestatement");
	    String sis = toStrings(is);
		List<String> cf = IOFile.readFile(path+"/"+time+"_"+"cashflow");
		String scf = toStrings(cf);
		
		List<String> sticker = IOFile.readFile(path+"/"+"sticker");
		List<String> finallist = new ArrayList<String>();
		List<String> nolist = new ArrayList<String>();
		
		String excelpath = path+"/"+"excel"+"/"+time;
			
		for(String name:sticker)
		{
			String real = "";
			boolean judge = !Contains(sba,name) && !Contains(sis,name) && !Contains(scf,name); 
			System.out.println("   "+judge);
			if(judge)
			{
				System.out.println("     Output Excel   "+name);
				real = name+".xls";
				Merge(excelpath+"/"+"balance"+"/"+real,excelpath+"/"+"cashflow"+"/"+real,excelpath+"/"+"incomestatement"+"/"+real,path+"/"+"final"+"/"+time+"/"+real);
				finallist.add(name);
				
			}
			else
			{
				System.out.println("       "+name+"   Can not be Traced!!!");
				
				nolist.add(name);
				
			}
				
				
		}
		
		IOFile.writeFile(finallist, path+"/"+"finallist", false);
		IOFile.writeFile(nolist, path+"/"+"nolist", false);
			
	}

}
