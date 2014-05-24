package work.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import search.SearchDir.SearchDir;

public class ApplyMass {
	
	public static void main(String [] args) throws BiffException, IOException, RowsExceededException, WriteException
	{

		String dir = "/home/yang/Documents/Work/Excel";
		SearchDir.search(dir+"/n4");
		List<String> list = SearchDir.getList();
		String [] temp;
		String name;
				
		WritableWorkbook workbook;
		WritableSheet sheet;
		
		for (String ls : list)
		{
            temp = ls.split("/");
			
			name = temp[temp.length-1];
			workbook = Workbook.createWorkbook(new File(dir+"/CorrectDate/"+"/n4/"+name),Workbook.getWorkbook(new File(ls)));
			
			sheet = workbook.getSheet(0);
			
			CorrectDate.findDate(sheet);
			CorrectUnit.changeUnit(CorrectUnit.scanUnit(8,sheet),sheet);
			
			workbook.write();
			workbook.close();
			System.out.println(name);
		}
		
	
		
		SearchDir.clearList();
		
	}

}
