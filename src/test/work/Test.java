package test.work;

import java.io.*;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.*;

public class Test{
	
    

	public static void Transfer(String str,String str1,String str2,String domain)
	{

	    String [] temp = str1.split("\\\\");
	    
	    String suffix = temp[temp.length-1];
		
		String dir = str2 + "\\"+ domain;
	    String newdir = dir + "\\"+suffix+"." + str;
	    
	    PDFParser parser = new PDFParser();
	   
	    File file = new File(dir);
	    
	    if(!(file.exists()))
	    {
	    	file.mkdir();
	    }
	    
	    File f = new File(str1);
	    
	    InputStream iStream;
	    
	    try
	    {
	    	// check whether file is broken;
		    if(!(f.length() <= 2048))
		    {
		    	iStream = new BufferedInputStream(new FileInputStream(f));
		    }
		    
		    else
		    {
		    	return;
		    }
		    
		    OutputStream oStream = new BufferedOutputStream(new FileOutputStream(new File(newdir)));
		    
		    
		    
		    
		    Metadata metadata = new Metadata();
		    metadata.add(Metadata.CONTENT_ENCODING, "utf-8");
		    
		   
		    if(str.equalsIgnoreCase("html"))
		    {
		    	ToHTMLContentHandler handler = new ToHTMLContentHandler(oStream,"utf-8");
		    	 
			    handler.startDocument();
			    handler.endDocument();
			   
			    parser.parse(iStream, handler, metadata, new ParseContext());
		    }
		    else if(str.equalsIgnoreCase("xml"))
		    {
		    	 ToXMLContentHandler handler1 = new ToXMLContentHandler(oStream,"utf-8");
		    	 handler1.startDocument();
		 	    handler1.endDocument();
		 	    
			    parser.parse(iStream, handler1, metadata, new ParseContext());
		    }
		    
		    else if(str.equalsIgnoreCase("txt"))
		    {
		    	ContentHandler handler2 = new BodyContentHandler(oStream);
		    	
		    	parser.parse(iStream, handler2, metadata, new ParseContext());
		    }
		   
	    }
	    catch(IOException | SAXException | TikaException e)
	    {
	    	e.printStackTrace();
	    }
	    
	    
	 
	}
	
	public static void main(String [] args) 
	{
		Transfer("html","C:\\Users\\YANG\\Desktop\\bin64\\4.pdf","E:","IE");
		
	}
}
