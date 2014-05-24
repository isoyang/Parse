package work.process;

import search.SearchDir.*;
import tool.io.IOFile;

import java.util.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class ChangeName {

	@SuppressWarnings("resource")
	public static void change(String dir,String dest,String small,String amount) throws IOException
	{
		File top = new File(dir);
		String [] dm = top.list();
		long t = System.currentTimeMillis();
		List<String> list;
		List<String> smallfile = new ArrayList<String>();
		List<String> amountfile = new ArrayList<String>();
		for (String domain:dm)
		{
			int i = 1;
			SearchDir.search(dir+"\\"+domain);
			list = SearchDir.getList();
			
			for (String string:list)
			{
				System.out.println(string);
				
				File file = new File(dest);
								
				String dirname = dest+"\\"+domain+"_"+(int)i+".pdf";
				
				
				File file1 = new File(string);
				
				if(file1.length() < 2048)
				{
					smallfile.add(string);
					continue;
				}
				
				
				if(!(file.exists()))
				{
					file.mkdirs();
				}
				
				FileChannel in = new RandomAccessFile(file1,"rw").getChannel();
				
				FileChannel out = new RandomAccessFile(new File(dirname),"rw").getChannel();
				
				/*MappedByteBuffer mbb = in.map(FileChannel.MapMode.READ_ONLY, 0, in.size());
				MappedByteBuffer mbb1 = out.map(FileChannel.MapMode.READ_WRITE, 0, in.size());
				
				while(mbb.hasRemaining())
				{
					mbb1.put(mbb.get());
					
				}*/
				
				/*MappedByteBuffer mbb = in.map(FileChannel.MapMode.READ_ONLY, 0, in.size());
				
				out.write(mbb);*/
			
				ByteBuffer bf = ByteBuffer.allocate((int)in.size());
				
				while(in.read(bf)!=-1)
				{
					bf.flip();
					out.write(bf);
					bf.clear();
				}
				
				//mbb.clear();
				in.close();
				out.close();
				
				
				i++;
			}
			
			if(i < 8)
			{
				amountfile.add(domain);
			}
			SearchDir.clearList();
		}
		
		IOFile.writeFile(smallfile, small, false);
		IOFile.writeFile(amountfile, amount, false);
		long t2 = System.currentTimeMillis();
		long tt = t2-t;
		
		System.out.println(tt);
		
		
	}
	
	public static void main(String [] args) throws Exception
	{
		for (int i = 1 ; i < 5 ; i++)
		{
			String number = String.valueOf(i);
			change("E:\\workspace\\HeritrixNew\\jobs\\newwork"+number+"\\mirror","E:\\Work\\File\\newwork"+number,"E:\\Work\\Report\\smallfile\\"+number+".txt","E:\\Work\\Report\\amount\\"+number+".txt");
		}
		
	}
}
