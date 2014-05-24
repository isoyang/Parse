package work.process;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.net.*;

import tool.io.IOFile;

public class GetHtml {
	
	public static String get(String path)
	{
		StringBuffer sb = new StringBuffer();
		
		try
		{
			URL url = new URL(path);
			
			Scanner scan = new Scanner(url.openStream());
			
			scan.useDelimiter("\n");
			
			while(scan.hasNext())
			{
				sb.append(scan.nextLine().trim());
			}
						
			scan.close();
			
		    
			
		}
		catch(MalformedURLException e)
		{
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return sb.toString();
	}
	
	public static String extractWeb(String string)
	{
		String web = "";
		
		String reg = "<td\\s.*?>Website:</td><td\\s.*?><a\\shref=\"([^\"]+)\".*?>";
		
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		
		Matcher matcher = pattern.matcher(string);
		
		while(matcher.find())
		{
			
			web = matcher.group(1);
		}
			
		return web;
	}
	
	public static void main (String args [])
	
	{
		List<String> list = IOFile.readFile("C:\\Users\\YANG\\Desktop\\FIleIE\\readyinput.txt");
		
		List<String> canot = new ArrayList<String>();
		List<String> noweb = new ArrayList<String>();
		List<String> useful = new ArrayList<String>();
		List<String> total = new ArrayList<String>();
		
		
		for (int i = 0 ; i < list.size() ; i++)
		{
			String string = list.get(i);
			String web = extractWeb(get(string));
			
			if(web.equalsIgnoreCase(""))
			{
							
				canot.add(String.valueOf(i+1));
			}
			
			else if(web.equalsIgnoreCase("http://"))
			{
				noweb.add(String.valueOf(i+1));
			}
			
			else 
			{
				useful.add(web);
			}
				
			total.add(web);
			System.out.println(web);
		}
		
		IOFile.writeFile(canot, "C:\\Users\\YANG\\Desktop\\FileIE\\cannotfind.txt", false);
		IOFile.writeFile(noweb, "C:\\Users\\YANG\\Desktop\\FileIE\\noweb.txt", false);
		IOFile.writeFile(useful, "C:\\Users\\YANG\\Desktop\\FileIE\\finalinput.txt", false);
		IOFile.writeFile(total, "C:\\Users\\YANG\\Desktop\\FileIE\\total.txt", false);
		
		
		
	}	

}
