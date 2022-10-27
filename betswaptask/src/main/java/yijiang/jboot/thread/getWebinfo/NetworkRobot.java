///*
//*<p>file: NetworkRobot.java
//*
//*
//*/
//package yijiang.jboot.thread.getWebinfo;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//import java.util.Vector;
//
//import javax.swing.text.BadLocationException;
//import com.heaton.bot.HTTPSocket;
//import com.heaton.bot.Link;
//
//public class NetworkRobot {
//
//
//	public void createIndex(String []urls) throws Exception{
//		Map urlMap = this.serch(urls);
//		//Index _index = new Index();
//		if(urlMap!=null){
//			Set keySet = urlMap.keySet();
//			Iterator _it = keySet.iterator();
//			 int n = 0;
//		      while (_it.hasNext()) {
//		    	String key = (String)_it.next();
//		        Link _link = (Link)urlMap.get(key);
//		        String _herf = _link.getHREF().trim();
//		        if(key!=null && !key.equals("")){
//		        	//_index.AddNews(_herf, key);
//		        	n++;
//		        }
//		      }
//		      //_index.close();
//		}
//	}
//
//
//
//	  public static String input(String str,String encoding) {
//		 if(str==null){
//				str = "";
//		}
//		str = str.trim();
//		String temp = "";
//		    if (str != null) {
//		      try {
//		    	temp = new String(str.getBytes("ISO8859_1"),encoding);
//		      }
//		      catch (Exception e) {
//		      }
//		    }
//	    temp = temp.replaceAll("\\<.*?\\>","");
//  	  	temp = temp.replaceAll("\\<.*?\\>","");//delete <b> </b> tags
//  	  	temp = temp.replaceAll("!","");//delete the signs that won't be indexed
//	  	temp = temp.replaceAll("\\?","");
//	  	temp = temp.replaceAll("\\."," ");
//	  	temp = temp.replaceAll(","," ");
//	  	temp = temp.replaceAll(":"," ");
//	  	temp = temp.replaceAll("&nbsp;"," ");
//	  	temp = temp.replaceAll(";"," ");
//	  	temp = temp.replaceAll(">>","");
//	  	temp = temp.replaceAll(">","");
//	  	temp = temp.replaceAll("<<","");
//	  	temp = temp.replaceAll("<","");
//	    return temp;
//	  }
//
//
//	public String getencoding(HTTPSocket http){
//		String str_body= http.getBody();
//		String encoding = "gb2312";
//		if(http!=null){
//			 if(str_body!=null && !"".equals(str_body)){
//			  	 int en_index = str_body.indexOf("charset");
//			  	 if(en_index>-1){
//			  		  if((str_body.substring(en_index, en_index+20).toUpperCase()).indexOf("GBK")>-1){
//			  			  encoding = "gbk";
//			  		  }else if((str_body.substring(en_index, en_index+20).toUpperCase()).indexOf("GB2312")>-1){
//			  			  encoding = "gb2312";
//			  		  }else if((str_body.substring(en_index, en_index+20).toUpperCase()).indexOf("UTF-8")>-1){
//			  			  encoding = "utf-8";
//			  		  }
//			  	  }
//			 }
//		 }
//		return encoding;
//	}
//
//
//	public Map serch(String[] url)throws IOException,BadLocationException{
//		Map linkall = new HashMap();
//		try {
//		      for(int index = 0;index<url.length;index++){
//		    	  HTTPSocket http = new HTTPSocket();
//			      HTMLPage page = new HTMLPage(http);
//		    	  page.open(url[index],null);
//		    	  String encoding = null;
//		    	  encoding = this.getencoding(http);
//		    	  Vector links = null;
//		    	  links = page.getLinks();
//		    	  if ( links.size()>0 ) {
//			          for ( int i=0;i<links.size();i++ ){
//			        	   Link link = (Link)(links.elementAt(i));
//			        	   String key = null;
//			        	   key = this.input(link.getPrompt(), encoding);
//			        	   if(key!=null && !"null".equals(key) && !"".equals(key) ){
//			        		  // System.out.println("һ������:" + key+" "+page.getURL());
//			        		   linkall.put(this.input(link.getPrompt().trim(), encoding), link);
//			        	   }
//			        	  //下追一层
//			        	  /* if(link.getHREF()!=null && !"null".equals(link.getHREF().trim()) &&!"".equals(link.getHREF().trim())){
//			        		   HTTPSocket http_temp = null;
//			     		       HTMLPage page_temp = null;
//			        		   http_temp = new HTTPSocket();
//			     		       page_temp = new HTMLPage(http_temp);
//			     		       page_temp.open(link.getHREF(),null);
//			     		       Vector linksons = null;
//			     		       linksons = page_temp.getLinks();
//			     		       String encoding_temp = null;
//			     		       encoding_temp = this.getencoding(http_temp);
//			        		   if(linksons !=null && linksons.size()>0){
//			        			   for(int k = 0; k<linksons.size();k++){
//				        			   Link linkson = (Link)(linksons.elementAt(k));
//				        			   String key_temp = "";
//				        			   key_temp = this.input(linkson.getPrompt(), encoding_temp);
//				        			   if(key_temp !=null && !"null".equals(key_temp) &&!"".equals(key_temp)){
//				        				  linkall.put(this.input(linkson.getPrompt().trim(), encoding_temp), linkson);
//						        	   }
//				        		   }
//			        		   }
//			        	   }*/
//			           }
//			       }
//		      }
//		    } catch ( Exception e ) {
//		       e.printStackTrace();
//		    }
//		    return linkall;
//	}
//}
