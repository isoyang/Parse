package work.process;

import java.util.*;
import tool.io.*;

public class FindName {
	
	public static void main(String args[])
	{
		List<String> number = IOFile.readFile("C:\\Users\\YANG\\Desktop\\FileIE\\canotandnoweb.txt");
		
		List<String> name = IOFile.readFile("C:\\Users\\YANG\\Desktop\\FileIE\\nameandticker.txt");
		
		List<String> output = new ArrayList<String>();
		
		for (String str : number)
		{
			String target = name.get(Integer.parseInt(str)-1);
			System.out.println(target);
			output.add(target);
			
		}
		
		IOFile.writeFile(output, "C:\\Users\\YANG\\Desktop\\FileIE\\remain.txt", false);
	}

}
