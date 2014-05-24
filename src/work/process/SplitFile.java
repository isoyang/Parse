package work.process;


import tool.io.*;
import java.util.*;
import java.io.*;



/**
 * @author YANG
 * 
 * Split one file into several small files
 * 
 * dir: the address of source file
 * 
 * dest; the path of target files
 * 
 * interval; the size of small file
 *
 */
public class SplitFile {

	public static void split(String dir,String dest,int interval)
	{
		List<String> list = IOFile.readFile(dir);
	
		File file = new File(dest);
		if(!(file.exists()))
		{
	         file.mkdirs();
		}
		int pointer = 1;
		
		System.out.println(list.size());
		
		for ( int i = list.size()-1 ; i >= 0 ; i = i - interval)
		{
			List<String> newlist = null;
			if(i > interval)
			{
				newlist = list.subList(i-interval+1, i+1);
				
			}
			
			else
			{
				newlist = list.subList(0, i+1);
					
			}
			
            if(!(file.exists()))
            {
            	file.mkdirs();
            }
			IOFile.writeFile(newlist, dest+"\\"+(int)pointer+".txt", false);
			pointer++;	
						
		}
	}
	
	public static void main(String args [])
	{
		split("C:\\Users\\YANG\\Desktop\\FileIE\\finalaftermerge.txt","E:\\Work\\Names\\400",400);
	}
}
