package work.process;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import tool.io.IOFile;

public class StoreFile {
	
	public static String get(String path)
	{
		StringBuffer sb = new StringBuffer();
		
		try
		{
			URL url = new URL(path);
			
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			
			con.setRequestProperty("User-Agent", "MSIE 7.0");
			
			Scanner scan = new Scanner(con.getInputStream());
			
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
	
	public static void main (String args [])
	{
		String path = "/home/yang/Documents/BusinessWeekProject";
		String stk;
		String time = "quarter";
		String type = "incomestatement";
		String season = "Q";
		String out = time+"_"+type;
		
		String address = "http://investing.businessweek.com/research/stocks/financials/financials.asp?ticker=";
		
		String end = ":CN&dataset=incomeStatement&period="+season+"&currency=native";
		
		
		//List<String> url = IOFile.readFile(path+"/"+time+"/"+type);
		List<String> sticker = IOFile.readFile(path+"/"+"sticker");
		
		List<String> li = new ArrayList<String>();
		List<String> outlier = new ArrayList<String>();
		
		for (int i = 0 ; i < sticker.size() ; i++)
		{
			
			stk = sticker.get(i);
			String url = address+stk+end;
			
			String content = get(url);
			
			if (content.length() == 0)
			{
				outlier.add(stk);
				System.out.println(stk+"  cannot be traced  "+(int)i);
				continue;
			}
			
			li.add(content);
			System.out.println(stk+"  "+(int)i);
		}
		
		IOFile.writeFile(li, path+"/"+"clean"+"/"+"orig_"+out, false);
		
		IOFile.writeFile(outlier, path+"/"+out, false);
	}

}
