package test.work;

import tool.io.*;
import tool.process.*;
import search.SearchDir.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// this file is to target one word or consecutive multiple words with first uppercase character in one sentence


public class TestSeg {
	
	
	public static List<String> extractWord(String ss,String ss1,String ss2) throws Exception
	{
		List<String> list = new IOFile().readFile(ss);
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile("\\b[A-Z][a-z]+\\b");
		
		
		String str = "";
		String sss = "";
		
		for (String s : list)
		{
			str = str +" "+s.trim();
		}
		
		String [] newlist = str.split("\\.");
		
		for(String string : newlist)
		{
			if(string.matches(".*\\b"+"("+ss1+"|"+ss2+")"+"\\b.*"))
				
			{
				System.out.println(string);
				string = string.trim();
				Matcher matcher = pattern.matcher(string);
				
				while(matcher.find())
					
				{
				
				   list1.add(matcher.group());	
																			
				}			
			
				for(int i = 1; i < list1.size(); i++)
				{
					
						String temp1 = list1.get(i);
						
						String temp = list1.get(i-1);
						
						int dis = string.indexOf(temp1)-string.indexOf(temp)-temp.length();
					    
					    if(dis == 1)
					    {
					    	if(sss.equalsIgnoreCase(""))
					    	{
					    		sss = temp+" "+temp1;
					    	}
					    	else
					    	{
					    		sss = sss +" "+temp1;
					    	}
					    	
					    }
					    
					    else
					    {

					    	if(sss.equalsIgnoreCase(""))
					    	{
					    		list2.add(temp);
					    		sss = temp1;
					    	}
					    	else
					    	{
					    		list2.add(sss);	
					    		sss = temp1;
					    	}
					    }
						
					    if(i == list1.size()-1)
					    {
						    	list2.add(sss);		    
					    }				    
				}
			}
			
		}
		return list2;
	}

	public static void main(String [] args) throws Exception
	{
		
		SearchDir.search("E:\\Test\\work_searchdir");
		List<String> lis = SearchDir.getList();
		int pointer = 1;
		String s = Tackle.extractDomain(lis.get(0));
		for (String ss : lis)
		{
			List<String> list  = extractWord(ss,"project","mine");

			Tackle.printList(list);
			new IOFile().writeFile(list,"E:\\Test\\work_searchdir\\importantword"+"\\"+s+".txt",false);
			System.out.println("-------------------------------------The Data has been created!!!!--------------------------------------------------------");
			System.out.print("------------------------------Finish File ");
			System.out.print(pointer);
			System.out.println("  !!!------------------------------------------");
			pointer++;
		}
		
	}

}
