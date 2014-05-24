package work.process;

import java.io.*;
import java.util.*;

import tool.io.IOFile;
import search.SearchDir.SearchDir;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class ReadMultiThread {
	
	private static int totalfile = 0;
	
	private static String destPath;
	
	public static List<File> getFileList(List<String> list)
	{
		List<File> file = new ArrayList<File>();
		
		for(String str:list)
		{
			file.add(new File(str));
		}
		
		return file;
	}

	public static int getTotalFile()
	{
		return totalfile;
	}
	
	public static String getDestPath()
	{
		return destPath;
	}
	public static void main(String args[])
	{
		
		// create file pool
	    String string = "E:\\Test\\tempstore\\aubullion.com";
	    destPath = "E:\\thread";
	    SearchDir.search(string);
	    List<File> pool = getFileList(SearchDir.getList());
	    totalfile = pool.size();
	    SearchDir.clearList();
	    
	    Thread [] thread = new Thread[4];
	    
	    for (Thread th : thread)
	    {
	    	th = new ReadThread(pool);
	    	
	    	th.start();
	    }
	
	
	}
}


class ReadThread extends Thread
{
	private List<File> list;
	
	private static int done = 0;
	
	public ReadThread(List<File> list)
	{
		this.list = list;
	}
	
	@SuppressWarnings("resource")
	public void run()
	{
		File input = null;
		while(done != ReadMultiThread.getTotalFile())
		{
			synchronized(list)
			{
				while(list.isEmpty())
				{
					if(done == ReadMultiThread.getTotalFile())
					{
						return;
					}
					
					try
					{
						list.wait();
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				
				input = list.remove(list.size()-1);
				increment();
			}
			
			
			try {
				String [] temp = input.getName().split("\\\\");
				String name = temp[temp.length-1];
				
				FileChannel in = new RandomAccessFile(input,"rw").getChannel();
				FileChannel out = new RandomAccessFile(new File(ReadMultiThread.getDestPath()+"\\"+name),"rw").getChannel();
				
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
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	private static synchronized  void  increment()
	{
		done++;
	}
	
}