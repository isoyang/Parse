package work.process;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tool.io.IOFile;

public class GetDataFromBussinessWeek {
	
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
	
	
	public static List<String> extractWeb(String string,String reg,int number)
	{
		
		
		List<String> str = new ArrayList<String>();
		
		
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		
		Matcher matcher = pattern.matcher(string);
		
		while(matcher.find())
		{
			
			str.add(matcher.group(number));
			
		}
			
		return str;
	}
	
	public static String cleanString (String string,String reg)
	{
		String r;
		String [] temp = string.split(reg);
		
		r = temp[0]+" "+temp[1];
		
		return r;
	}
	
	public static void removeList(List<String> list, String str,String str1,String str2)
	{
		ListIterator<String> al = list.listIterator();
		String t [];
		
		while(al.hasNext())
		{
			String temp = al.next();
			
			if (temp.indexOf(str)!= -1 || temp.indexOf(str1) != -1 || temp.indexOf("As of:")!= -1 || temp.indexOf("&nbsp;")!=-1 || temp.equals(""))
			{
				al.remove();
				
			}
			else if (temp.indexOf(str2) != -1)
			{
				t = temp.split("<br\\s/>");
				al.set(t[0]+" "+t[1]);
			}
		}
	}
	public static void main (String args [])
	{
		/*List<String> list = IOFile.readFile("/home/yang/Documents/BusinessWeekProject/sticker");
		List<String> balance = new ArrayList<String>();
		
		String typeof = "balanceSheet";
		String time = "Q";
		String start = "http://investing.businessweek.com/research/stocks/financials/financials.asp?ticker=";
		String end = ":CN&dataset="+typeof+"&period="+time+"&currency=native";
		
		for (int i = 0 ; i < list.size();i++)
		{
			balance.add(start+list.get(i)+end);			
		}
		
		for (int i = 0 ; i < balance.size() ; i++)
		{
			System.out.println(balance.get(i));
		}
		
		IOFile.writeFile(balance, "/home/yang/Documents/BusinessWeekProject/balance", false);*/
		
		List<String> result = new ArrayList<String>();
		List<String> result2 = new ArrayList<String>();
		List<String> outlier = new ArrayList<String>();
		
		
		String path = "/home/yang/Documents/BusinessWeekProject";
		String stk;
		String time = "quarter";
		String type = "balance";
		String out = time+"_"+type;
		
		
		String reg = "<tr><td\\s.*?>Currency\\sin(.*?)id=\"currency\"\\svalue=\"native\"";
		
		String reg2 = "<td.*?>(.*?)</td>";
				
		/*result = extractWeb(get("http://investing.businessweek.com/research/stocks/financials/financials.asp?ticker=ELG:CN&dataset=balanceSheet&period=A&currency=native"),reg,0);
		
		result2 = extractWeb(result.get(0),reg2,1);
		
		removeList(result2,"Trend","<img","<br />");
		
		for (String s:result2)
		{
			System.out.println(s);
		}*/
		List<String> url = IOFile.readFile(path+"/"+"orig_"+out);
		List<String> sticker = IOFile.readFile(path+"/"+"sticker");
		
		for (int i = 0 ; i < url.size() ; i++)
		{
			stk = sticker.get(i);
			result = extractWeb(url.get(i),reg,0);
			if (result.size() == 0)
			{
				System.out.print("         "+"Cannot Trace"+"  ");
				System.out.println(stk+"  "+(int)(i+1)+"          ");
                outlier.add(stk+" ");
                continue;
			}
			result2 = extractWeb(result.get(0),reg2,1);
			removeList(result2,"Trend","<img","<br />");
			
			IOFile.writeFile(result2, path+"/"+"data"+"/"+time+"/"+type+"/"+stk+".txt", false);
			
			System.out.print("         "+"Finnish"+"  ");
			System.out.println(stk+"  "+(int)(i+1)+"          ");
		}
		
		IOFile.writeFile(outlier, path+"/"+out, false);
			
	}

}
