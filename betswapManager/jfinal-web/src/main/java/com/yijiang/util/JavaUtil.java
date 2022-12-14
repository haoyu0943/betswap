package com.yijiang.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * 
 * @author chenc@inetcop.com.cn
 * @date 2009-08-06
 */
public class JavaUtil {
	private static Logger logger = Logger.getLogger(JavaUtil.class);

	private static String filePath = "/doc/";

	/**
	 * 
	 * @param is
	 * @param fileName
	 */
	public static void writeFile(InputStream is, String fileName) {
		try {
			URL path = JavaUtil.class.getResource("/");
			String dir = URLDecoder.decode(path.getPath(), "utf-8");
			FileOutputStream fs = new FileOutputStream(dir + filePath
					+ fileName);
			byte[] buf = new byte[1024];
			int len = is.read(buf);
			while (len != -1) {
				fs.write(buf, 0, len);
				len = is.read(buf);
			}
			fs.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
	}
	
	public static String encodeURL(String url, String encode) {
		String ret = url;
		String invalidURLchars = "[^abcdefghijklmnopqrstuvwxyz0123456789%\\-\\._~:/\\?#\\[\\]@!\\$&'\\(\\)\\*\\+,;=]";
		// if (JavaUtil.isAllMatch(ret, invalidURLchars)) {
		Pattern p = Pattern.compile(invalidURLchars);
		Matcher m = p.matcher(ret);
		while (m.find()) {
			String replacechar = m.group();
			try {
				ret = ret.replace(replacechar, URLEncoder.encode(replacechar,
						encode));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return ret;
	}

	public static String encodeURL(String url) {
		return encodeURL(url, "utf-8");
	}


	public static void writeFile(String src, String path) {
		try {
			FileOutputStream fs = new FileOutputStream(path);
			fs.write(src.getBytes("utf-8"));// ??????????????????????????????????????????????????????????????????????????????????????????
			fs.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	/**
	 * 
	 * @param src
	 * @param charset
	 * @param fileName
	 */
	public static void writeFile(String src, String charset, String fileName) {
		try {
			URL path = JavaUtil.class.getResource("/");
			String dir = URLDecoder.decode(path.getPath(), "utf-8");
			FileOutputStream fs = new FileOutputStream(dir + filePath
					+ fileName);
			fs.write(src.getBytes(charset));
			fs.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
	}

	/**
	 * ??????????????????
	 * 
	 * @param src
	 * @param fileName
	 */
	public static void writeFileAppend(String src, String fileName) {
		try {
			URL path = JavaUtil.class.getResource("/");
			String dir = URLDecoder.decode(path.getPath(), "utf-8");
			FileOutputStream fs = new FileOutputStream(dir + filePath
					+ fileName, true);
			fs.write(src.getBytes("iso-8859-1"));// ??????????????????????????????????????????????????????????????????????????????????????????
			fs.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	/**
	 * ????????????
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readFile(String fileName) {
		URL path = JavaUtil.class.getResource("/");
		FileInputStream fs;
		String content = "";
		try {
			String dir = URLDecoder.decode(path.getPath(), "utf-8");
			fs = new FileInputStream(dir + filePath + fileName);
			byte data[] = new byte[1024];
			int len = fs.read(data);
			while (len != -1) {
				content = content + new String(data, 0, len, "iso-8859-1");
				len = fs.read(data);
			}
			fs.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		return content;
	}

	/**
	 * ????????????
	 * 
	 * @param fileName
	 * @return
	 */
	public static String readFile(File file, String enc) {
		FileInputStream fs;
		String content = "";
		try {
			fs = new FileInputStream(file.getAbsolutePath());
			byte data[] = new byte[1024];
			int i = 0;
			int len = fs.read(data);
			while (len != -1) {
				content = content + new String(data, 0, len, enc);
				len = fs.read(data);
				i++;
			}
			fs.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		return content;
	}

	public static String readUTF8File(File file) {
		FileInputStream fs;
		String content = "";
		try {
			fs = new FileInputStream(file.getAbsolutePath());
			byte data[] = new byte[1024];
			int i = 0;
			int len = fs.read(data);
			while (len != -1) {
				if (i == 0 && (data[0] == -17 && data[1] == -69 && data[2] == -65))
					content = content + new String(data, 3, len - 3, "utf-8");
				else
					content = content + new String(data, 0, len, "utf-8");
				len = fs.read(data);
				i++;
			}
			fs.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		return content;
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @param fileName
	 * @param charset
	 * @return
	 */
	public static String readFile(String fileName, String charset) {
		URL path = JavaUtil.class.getResource("/");
		FileInputStream fs;
		String content = "";
		try {
			String dir = URLDecoder.decode(path.getPath(), "utf-8");
			fs = new FileInputStream(dir + filePath + fileName);
			byte data[] = new byte[1024];
			int len = fs.read(data);
			while (len != -1) {
				content = content + new String(data, 0, len, charset);
				len = fs.read(data);
			}
			fs.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		return content;
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @param fileName
	 * @param charset
	 * @return
	 */
	public static String getMailModelFile(String fileName, String charset) {
		URL path = JavaUtil.class.getResource("/");
		FileInputStream fs;
		String content = "";
		try {
			String dir = URLDecoder.decode(path.getPath(), "utf-8");
			fs = new FileInputStream(dir + "/config/" + fileName);
			byte data[] = new byte[1024];
			int len = fs.read(data);
			while (len != -1) {
				content = content + new String(data, 0, len, charset);
				len = fs.read(data);
			}
			fs.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
		return content;
	}

	

	/**
	 * ????????????????????????????????????
	 * 
	 * @param is
	 * @param charset
	 * @return
	 */
	public static String readStream(InputStream is, String charset) {
		StringBuilder sb = new StringBuilder();
		// String content="";
		try {
			byte data[] = new byte[1024];
			for (int n; (n = is.read(data)) != -1;) {
				sb.append(new String(data, 0, n, charset));
			}

			is.close();
		} catch (IOException e) {

		}
		return sb.toString();
		// return content;
	}

	/**
	 * ????????????
	 * 
	 * @param s
	 * @param pattern
	 * @return
	 */
	public static String[] match(String s, String pattern) {
		Matcher m = Pattern.compile(pattern).matcher(s);

		while (m.find()) {
			int n = m.groupCount();
			String[] ss = new String[n + 1];
			for (int i = 0; i <= n; i++) {
				ss[i] = m.group(i);
			}
			return ss;
		}
		return null;
	}

	/**
	 * ????????????
	 * 
	 * @param s
	 * @param pattern
	 * @return
	 */
	public static List<String[]> matchAll(String s, String pattern) {
		Matcher m = Pattern.compile(pattern).matcher(s);
		List<String[]> result = new ArrayList<String[]>();

		while (m.find()) {
			int n = m.groupCount();
			String[] ss = new String[n + 1];
			for (int i = 0; i <= n; i++) {
				ss[i] = m.group(i);
			}
			result.add(ss);
		}
		return result;
	}

	/**
	 * 
	 * @param s
	 * @param pattern
	 * @return
	 */
	public static String[] firstMatch(String s, String pattern, int startIndex) {
		Matcher m = Pattern.compile(pattern).matcher(s);

		if (m.find(startIndex)) {
			int n = m.groupCount();
			String[] ss = new String[n + 1];
			for (int i = 0; i <= n; i++) {
				ss[i] = m.group(i);
			}
			return ss;
		}
		return null;
	}

	/**
	 * ????????????
	 * 
	 * @param s
	 * @param pattern
	 * @return
	 */
	public static boolean isMatch(String s, String pattern) {
		return s.matches(pattern);
	}

	public static boolean isAllMatch(String s, String pattern) {
		Matcher m = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(
				s);

		while (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param s
	 * @param pattern
	 * @return
	 */
	public static String[] matchWeak(String s, String pattern) {
		Matcher m = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(
				s);

		while (m.find()) {
			int n = m.groupCount();
			String[] ss = new String[n + 1];
			for (int i = 0; i <= n; i++) {
				ss[i] = m.group(i);
			}
			return ss;
		}
		return null;
	}

	public static String unescape(String s) {
		s = s.replaceAll("<pre>[\\s]*", "");
		s = s.replaceAll("</pre>.*$", "\n");
		// cdmackie: sometimes we get only "</"
		s = s.replaceAll("</$", "\n");

		// Clean up the end of line, and replace HTML tags
		s = s.replaceAll("&#13;&#10; &#13;&#10;", "\n");
		s = s.replaceAll("&#9;", "\t");
		s = s.replaceAll("&#09;", "\t");
		s = s.replaceAll("&#10;", "\n");
		s = s.replaceAll("&#13;", "");
		// s = s.replaceAll("&#27;", "\27");
		s = s.replaceAll("&#32;", " ");
		s = s.replaceAll("&#33;", "!");
		s = s.replaceAll("&#35;", "#");
		s = s.replaceAll("&#36;", "\\$");
		// cdmackie: this should be escaped
		s = s.replaceAll("&#37;", "\\%");
		s = s.replaceAll("&#38;", "&");
		s = s.replaceAll("&#39;", "'");
		s = s.replaceAll("&#40;", "(");
		s = s.replaceAll("&#41;", ")");
		s = s.replaceAll("&#42;", "*");
		s = s.replaceAll("&#43;", "+");
		s = s.replaceAll("&#44;", ",");
		s = s.replaceAll("&#45;", "-");
		s = s.replaceAll("&#46;", ".");
		s = s.replaceAll("&#47;", "/");
		s = s.replaceAll("&#58;", ":");
		s = s.replaceAll("&#59;", ";");
		s = s.replaceAll("&#60;", "<");

		s = s.replaceAll("&#61;", "=");
		s = s.replaceAll("&#62;", ">");
		s = s.replaceAll("&#63;", "?");
		s = s.replaceAll("&#64;", "@");
		s = s.replaceAll("&#91;", "[");
		s = s.replaceAll("&#92;", "\\\\");// ////////
		s = s.replaceAll("&#93;", "]");
		s = s.replaceAll("&#94;", "^");
		s = s.replaceAll("&#95;", "_");
		s = s.replaceAll("&#96;", "`");
		s = s.replaceAll("&#123;", "{");
		s = s.replaceAll("&#124;", "|");
		s = s.replaceAll("&#125;", "}");
		s = s.replaceAll("&#126;", "~");
		// s = s.replaceAll("&#199;", "?");

		s = s.replaceAll("\r", "");
		s = s.replaceAll("\n", "\r\n");
		s = s.replaceAll("&#34;", "\"");

		// body = string.gsub(body, "<!%-%-%$%$imageserver%-%->",
		// internalState.strImgServer)

		s = s.replaceAll("\r\n\\.", "\r\n\\.\\.");
		// s = s.replaceAll("&#(\\d*);","");
		Pattern p = null;
		Matcher m = null;
		p = Pattern.compile("&#(\\d*);");
		m = p.matcher(s);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, numToString(m.group(1)));
		}
		m.appendTail(sb);
		String x = sb.toString().replaceAll("&#[^;]*;", "");
		return x;
	}

	public static String numToString(String num) {
		String result = "";
		int n = Integer.parseInt(num);
		if (n > 255 && n < 19968)
			result = new String("&#" + n + ";");
		else {
			result = new String("" + (char) n);
		}
		return result;
	}

	/**
	 * @return
	 */
	public static boolean isNullOrEmpty(String... strs) {
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] == null || strs[i].trim().length() == 0)
				return true;
		}
		return false;
	}

	public static String getThreadsPartitonID(String taskid) {
		return String.valueOf(Integer.valueOf(taskid) + 1);
	}

	public static String getPostsPartitonID(String taskid, String tid) {
		String partitionid = tid.replaceAll("[^\\d]*", "").replaceAll("(\\d)*",
				"$1");
		// ???????????????????????? 3*taskid+tid????????????????????????%3-1
		Integer partition = 3 * Integer.valueOf(taskid)
				+ Integer.valueOf(partitionid) % 3 - 1;
		return String.valueOf(partition);
	}


	public static String XmlToString(Node node) {
		try {
			// org.apache.xml.serializer.TreeWalker;
			Source source = new DOMSource(node);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(source, result);
			return stringWriter.getBuffer().toString().replaceAll("<\\?.*\\?>",
					"");
		} catch (TransformerConfigurationException e) {
		} catch (TransformerException e) {
		}
		return null;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public static String getTextContent(Node node) {
		if (node == null)
			return null;
		String textContent = node.getTextContent();
		if (textContent == null)
			return textContent;
		return textContent.trim();
	}

	/**
	 * 
	 * @param node
	 * @param attrName
	 * @return
	 */
	public static String getNodeValue(Node node, String attrName) {
		if (node == null || node.getAttributes() == null)
			return null;
		Node attrNode = node.getAttributes().getNamedItem(attrName);
		if (attrNode == null || attrNode.getNodeValue() == null)
			return null;
		return attrNode.getNodeValue().trim();
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public static String getTagContent(Node node) {
		return JavaUtil.XmlToString(node);
	}

	private static String fixhtml = "";

	private static String writeDoc(Node node) {
		short type = node.getNodeType();
		switch (type) {

		case Node.ELEMENT_NODE: {
			String name = "<" + node.getNodeName();
			NamedNodeMap attrs = node.getAttributes();
			if (attrs != null) {
				int length = attrs.getLength();
				for (int i = 0; i < length; i++) {
					Node attr = attrs.item(i);
					name += " " + attr.getNodeName();
					name += "=\"" + attr.getNodeValue() + "\"";
				}
			}
			name += ">";
			fixhtml = fixhtml + name;

			NodeList children = node.getChildNodes();
			if (children != null) {
				int length = children.getLength();
				for (int i = 0; i < length; i++)
					writeDoc(children.item(i));
			}
			if (node.getNodeName().equalsIgnoreCase("br"))
				break;
			fixhtml = fixhtml + "</" + node.getNodeName() + ">";
			break;
		}
		case Node.TEXT_NODE: {
			fixhtml = fixhtml + node.getNodeValue();
			break;
		}
		}
		return fixhtml;
	}
	
	/**
	* @param upFilePath
	* @param mediaPicPath
	 */
	public static void videohandler(String upFilePath,String mediaPicPath){ 
		String ffmpegPath = "D:/ffmpeg/bin/ffmpeg.exe";
		List cutpic = new ArrayList(); 
		cutpic.add(ffmpegPath); // ??????????????????????????? 
		cutpic.add("-i"); // ???????????????-i??????????????????????????????????????? 
		cutpic.add(upFilePath); // ?????????????????? 
		cutpic.add("-y"); 
		cutpic.add("-f"); 
		cutpic.add("image2"); 
		cutpic.add("-ss"); // ???????????????-ss?????????????????????????????????????????? 
		cutpic.add("5"); // ????????????????????????1??? 
		cutpic.add("-t"); // ???????????????-t????????????????????????????????? 
		cutpic.add("0.001"); // ?????????????????????1?????? 
		cutpic.add("-s"); // ???????????????-s?????????????????????????????????????????? 
		cutpic.add("800*600"); // ??????????????????????????????800*600 
		cutpic.add(mediaPicPath); // ????????????????????????????????????

        boolean mark = true;  
        ProcessBuilder builder = new ProcessBuilder();  
        try {  

            builder.command(cutpic);  
            builder.redirectErrorStream(true);  
            // ?????????????????? true????????????????????????????????? start() ?????????????????????????????????????????????????????????????????????????????????  
            //???????????????????????? Process.getInputStream() ???????????????????????????????????????????????????????????????????????????  
            builder.start();  
        } catch (Exception e) {  
            mark = false;  
            e.printStackTrace();  
        }  
     }  
}