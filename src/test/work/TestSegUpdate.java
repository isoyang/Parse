package test.work;

import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import search.SearchDir.SearchDir;
import tool.io.*;
import tool.process.Tackle;
import tool.filter.*;

public class TestSegUpdate {

	public static List<String> getKeyWord(String dir,String select) throws Exception
	{
		
		List<String> list = new IOFile().readFile(dir);
		
		
		String reg = ".*\\b"+select+"\\b.*";
		String reg1 = "(\\b[A-Z][a-z]+\\b\\s)+\\b"+select+"\\b";
		String reg2 = "\\s"+select;
        
		StringBuffer sb = new StringBuffer();
		for (String s : list)
		{
			sb.append(" "+s.trim());
		}
		
		String [] tem= sb.toString().split("\\.");		
		Pattern pattern = Pattern.compile(reg1);
	    List<String> finalist = new ArrayList<String>();
	    
	    for (String str : tem)
	    {
	    	if(str.matches(reg))
	    	{
	    		str = str.trim();
	    		//System.out.println(str);
	    		Matcher matcher = pattern.matcher(str);
	    		
	    		while(matcher.find())
	    		{
	    			 //System.out.println(matcher.group());
	    			 String temp = matcher.group();
	    			 temp = temp.replaceAll(reg2, "");
	    			 if (temp.length() > 2 && temp.length() < 20)
	    			 {
	    				 finalist.add(temp);
	    				 
	    			 }
	    			 
	    		}
	    		
	    		//list3.add("one search finished!!!");
	    																	
	    				
	    	    finalist = Tackle.removeDuplicate(finalist);
	    	    //Tackle.printList(finalist);	    	    
	    	    
	    	 }
	    	
	     }	
	    		
		
		return finalist;
	}
	
	public static void main(String [] args) throws Exception
	{
		
		// get current name of class
		String classname = new Object()
		{
			public String getClassName() {  
	            String classname = this.getClass().getName();  
	            return classname.substring(0, classname.lastIndexOf('$'));  
	        }  
		
		}.getClassName();
		
	    // output log file
		File log = new File("E:\\Test\\Log\\"+classname+".txt");
		if(!(log.exists()))
		{
			log.createNewFile();
		}
		PrintStream original = System.out;
		FileOutputStream  ps = new FileOutputStream(log,false);
		IOStream both = new IOStream(original,new PrintStream(ps));
		System.setOut(new PrintStream(both));
		
		String select = "([Pp]roject|[Mm]ine|[Dd]eposit|[Pp]roperty)";
		String dir = "E:\\Test\\work_searchdir";
		String select1 = "[Mm]ine";
		String select2 = "mine";
		
		File file = new File(dir);
		String [] dirname = file.list();
		List<List<String>> li;
		List<String> lis ;
		for (String sss : dirname)
		{
			
			SearchDir.search(dir+"\\"+sss);
			lis = SearchDir.getList();
			int pointer = 1;
			
			
			li = new ArrayList<List<String>>();
			System.out.println("--------------------------------------Start  Extracting  "+sss+"  ----------------------------------------");
			System.out.println();
			List<String> list;
			for (String ss : lis)
			{   
				
				File f = new File(ss);
				if(f.length() >= 1024000)
				{
					pointer++;
					System.out.println("");
					System.out.println("--------------The size of this File is too large to process for saving computation time!!!!---------------");
					System.out.println("");
					f=null;
					continue;
					
				}
				String [] temp = ss.split("\\\\");
				String name = temp[temp.length-1];
				System.out.print("------------------------------Start  File ");
				System.out.print(pointer);
				System.out.println("  "+sss+"  "+name+"  !!!------------------------------------------");
				
				list  = getKeyWord(ss,select2);
               
				Tackle.printList(list);
				
				//new IOFile().writeFile(list,"E:\\Test\\keyword"+"\\"+s+"one"+".txt",false);
				li.add(list);
				System.out.print("------------------------------Finish File ");
				System.out.print(pointer);
				System.out.println("  !!!------------------------------------------");
				System.out.println("");
				pointer++;
				f = null;
			}
			
			System.out.println("--------------------------------------strat writing data to file----------------------------------------");
			
			new IOFile().writeFile(Tackle.removeDuplicate(Filter.filterWord(Tackle.toList(li))),"E:\\Test\\keyword"+"\\"+sss+".txt",false);
			
			System.out.println("--------------------------------------END  Extracting  "+sss+"  ----------------------------------------");
			li = null;
			SearchDir.clearList();
		}

	}
}
