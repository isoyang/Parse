package work.process;

import tool.io.*;
import tool.process.Tackle;

import java.io.*;
import java.util.*;

// this file is to check the websites of companies which will not be crawled due to some reasons

public class CheckFileName {

	public static void checkName(String str,String str1,String str2,String str3) throws InterruptedException
	{
		
		File file = new File(str);
		List<String> list = scanRead(str1);
		List<String> list1 = new ArrayList<String>();
		//List<String> list3 = new ArrayList<String>();
		String [] name = file.list();
		List<String> list2 = Tackle.toList(name);
		
		
		int i = 1;
			for (String li : list)
			{
				
				System.out.print("Company  ");
				System.out.print(i);
				System.out.println("  "+li);
                labelB:
				for(String string:list2)
				{
					String [] temp = string.split("\\.");
					String trim = "";
					if(temp.length > 2)
					{
						trim = temp[1];
					}
					else
					{
						trim = temp[0];
					}
					
					if(li.contains(trim))
					{
						
						System.out.println("");
						System.out.println(li+"  has been matched with  "+string);
						System.out.println("");
						System.out.println("********************************************************************");
						
						list1.add(li);
						list2.remove(string);
						//Thread.sleep(250);
						break labelB;
			
					}
					else
					{
						
						continue;
					}
				
				}
				
				i++;
				
			}
			
		list.removeAll(list1);
		
		
	
		IOFile.writeFile(list, str2, false);
		IOFile.writeFile(list2, str3, false);
	}
	
	public static List<String> scanRead(String str)
	{
		List<String> list = new ArrayList<String>();
		
		try {
			Scanner scan = new Scanner(new FileInputStream(new File(str)));
			scan.useDelimiter("\n");
			
			while(scan.hasNext())
			{
				list.add(scan.nextLine());
			}
			
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static void main(String args[]) throws Exception
	{
		for (int i = 1 ; i < 5 ; i++)
		{
			String number = String.valueOf(i);
			checkName("E:\\workspace\\HeritrixNew\\jobs\\newwork"+number+"\\mirror","E:\\Work\\Names\\500\\"+number+".txt","E:\\Work\\Check\\orign"+number+".txt","E:\\Work\\Check\\download"+number+".txt");
		}
		
		
		
		//List<String> list = scanRead("C:\\Users\\YANG\\Desktop\\1.txt");
		
		//Tackle.printList(list);
		
		
	}
}
