package work.process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestAlg {
	
	public static void main (String args[]) throws FileNotFoundException
	{
		/*StringBuffer sb = new StringBuffer();
		
		FileReader file = new FileReader("/home/yang/Documents/BusinessWeekProject/testalg");
		
		Scanner scan = new Scanner(file);
		
		scan.useDelimiter("\n");
		
		while(scan.hasNext())
		{
			sb.append(scan.nextLine().trim());
		}
		
		scan.close();
		
		System.out.println(sb.toString());*/
		
		String s = "<tr><td class=\"headerLite\" style=\"text-align:left\" valign=\"top\">Currency in<br />Millions of US Dollars</td><td class=\"headerLite\" style=\"width:40px; font-size:11px\">4 Year<br />Trend:</td>";
		String s1 = "Nov 30<br />2009<br />thsdj";
		String s2 = "sss";
		String re = "<td.*?>(((?!trend).)+)</td>";
		String re1= "(.*?)<br\\s/>(.*?)<br\\s/>.*";
		String re2 = "\\d+\\.?\\d*";
		
		
		
        Pattern pattern = Pattern.compile(re2, Pattern.CASE_INSENSITIVE);
		
		Matcher matcher = pattern.matcher(s2);
		
		String web = "";
		
		while(matcher.find())
		{
			
			web = matcher.group();
			System.out.println(web);
		}
		
		String [] ary = s1.split("<br\\s/>");
		
		
		
		System.out.println(ary[0]+" "+ary[1]);
		
		
		
		
	}

}
