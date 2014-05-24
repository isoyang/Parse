package test.work;

import tool.process.*;

import search.SearchDir.*;

import java.util.*;

import java.io.*;

public class TestTransfer {
	
	public static void main(String [] args) throws Exception
	{
		ToLog.generateLog(new TestTransfer());
		String string = "E:\\Test\\test_searchdir";
		String string1 = "E:\\Test\\work_searchdir";
		List<String> list;
		
		File file = new File(string);
		String [] dir = file.list();
		
		Tackle.printArray(dir);
		
		for (String s :dir)
		{
			System.out.println("------------------------------------------START TRANSFER "+s+" !  ------------------------------------------------");
			SearchDir.search(string+"\\"+s);
			
			list = SearchDir.getList();
			
		    //Tackle.printList(list);
		
			for (String ss:list)
			{
				System.out.println(ss);
				
				
					Test.Transfer("txt",ss, string1,s);
				
			}
			SearchDir.clearList();
			System.out.println("---------------------------------------------FINISH         "+s+"! ------------------------------------------------");
		    
		}
		
	}

}
