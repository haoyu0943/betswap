//// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
//// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
//// Decompiler options: packimports(3) fieldsfirst ansi
//// Source File Name:   HTMLPage.java
//
//package yijiang.jboot.thread.getWebinfo;
//
//import java.io.IOException;
//import java.io.StringReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.Vector;
//
//import javax.swing.text.BadLocationException;
//import javax.swing.text.MutableAttributeSet;
//import javax.swing.text.html.HTML;
//import javax.swing.text.html.HTMLEditorKit;
//
//import com.heaton.bot.Attribute;
//import com.heaton.bot.AttributeList;
//import com.heaton.bot.HTMLForm;
//import com.heaton.bot.HTMLParse;
//import com.heaton.bot.HTTP;
//import com.heaton.bot.Link;
//import com.heaton.bot.URLUtility;
//
//
//public class HTMLPage
//{
//    /* member class not found */
//
//
//
//    protected Vector _images;
//    protected Vector _links;
//    protected Vector _forms;
//    protected HTTP _http;
//    protected String _base;
//
//    public HTMLPage(HTTP http)
//    {
//        _images = new Vector();
//        _links = new Vector();
//        _forms = new Vector();
//        _http = http;
//    }
//
//    public void open(String s, HTMLEditorKit.ParserCallback parsercallback)
//        throws IOException, BadLocationException
//    {
//    	try {
//    		_http.send(s,null);
//            _base = s;
//            processPage(parsercallback);
//   	  } catch (Exception e) {
//   	     e.printStackTrace();
//   	  }
//    }
//
//    protected void processPage(HTMLEditorKit.ParserCallback parsercallback)
//        throws IOException
//    {
//        StringReader stringreader = new StringReader(_http.getBody());
//        HTMLEditorKit.Parser parser = (new HTMLParse()).getParser();
//        HTMLEditorKit.Parser parse = new HTMLParse().getParser();
//        if(parsercallback == null)
//        {
//        	Parser p = new Parser();
//            parser.parse(stringreader, p, true);
//        } else
//        {
//            parser.parse(stringreader, parsercallback, false);
//        }
//    }
//
//    public HTTP getHTTP()
//    {
//        return _http;
//    }
//
//    public Vector getLinks()
//    {
//        return _links;
//    }
//
//    public Vector getImages()
//    {
//        return _images;
//    }
//
//    public Vector getForms()
//    {
//        return _forms;
//    }
//
//    public void post(HTMLForm htmlform)
//        throws IOException
//    {
//        _http.getClientHeaders().set("Content-Type", "application/x-www-form-urlencoded");
//        _http.send(htmlform.getAction(), htmlform.toString());
//        processPage(null);
//    }
//
//    public String getURL()
//    {
//        return _http.getURL();
//    }
//
//    protected void addImage(String s)
//    {
//        s = URLUtility.resolveBase(_base, s);
//        for(int i = 0; i < _images.size(); i++)
//        {
//            String s1 = (String)_images.elementAt(i);
//            if(s1.equalsIgnoreCase(s))
//                return;
//        }
//
//        _images.addElement(s);
//    }
//    protected class Parser
//    extends HTMLEditorKit.ParserCallback {
//
//    /**
//     * Used to build up data for an HTML form.
//     */
//    protected HTMLForm tempForm;
//
//    /**
//     * Used to build up options for an HTML form.
//     */
//    protected AttributeList tempOptions;
//
//    /**
//     * Used to build up options for an HTML form.
//     */
//    protected Attribute tempElement = new Attribute();
//
//    /**
//     * Holds the prompt text(just before or after a control.
//     */
//    protected String tempPrompt = "";
//
//    /**
//     * Holds the link till the end link is found
//     */
//    protected Link tempLink;
//
//
//
//
//
//    /**
//     * Called to handle comments.
//     *
//     * @param data The comment.
//     * @param pos The position.
//     */
//    public void handleComment(char[] data,int pos)
//    {
//    }
//
//    /**
//     * Called to handle an ending tag.
//     *
//     * @param t The ending tag.
//     * @param pos The position.
//     */
//    public void handleEndTag(HTML.Tag t,int pos)
//    {
//      if ( t==HTML.Tag.OPTION ) {
//        if ( tempElement!=null ) {
//          tempElement.setName(tempPrompt);
//          tempOptions.add(tempElement);
//          tempPrompt = "";
//        }
//        tempElement = null;
//      } else if ( t==HTML.Tag.FORM ) {
//        if ( tempForm!=null )
//          _forms.addElement(tempForm);
//        tempPrompt = "";
//      } else if ( t==HTML.Tag.A ) {
//        if ( tempLink!=null ){
//          tempLink.setPrompt(tempPrompt);
//        }
//        tempPrompt = "";
//      }
//
//    }
//
//    /**
//     * Called to handle an error. Not used.
//     *
//     * @param errorMsg The error.
//     * @param pos The position.
//     */
//    public void handleError(String errorMsg,int pos)
//    {
//    }
//
//    /**
//     * Called to handle a simple tag.
//     *
//     * @param t The simple tag.
//     * @param a The attribute list.
//     * @param pos The position.
//     */
//    public void handleSimpleTag(HTML.Tag t,
//                                MutableAttributeSet a,int pos)
//    {
//      handleStartTag(t,a,pos);
//    }
//
//    /**
//     * Called to handle a starting tag.
//     *
//     * @param t The starting tag.
//     * @param a The attribute list.
//     * @param pos The position.
//     */
//    public void handleStartTag(HTML.Tag t,
//                               MutableAttributeSet a,int pos)
//    {
//      String type = "";
//
//      // is it some sort of a link
//      String href = (String)a.getAttribute(HTML.Attribute.HREF);
//      if ( (href!=null) && (t!=HTML.Tag.BASE) ) {
//        String alt = (String)a.getAttribute(HTML.Attribute.ALT);
//        Link link = new Link(
//                            alt,
//                            URLUtility.resolveBase(_base,href),
//                            null);
//        _links.addElement(tempLink=link);
//      } else if ( t==HTML.Tag.OPTION ) {
//        tempElement = new Attribute();
//        tempElement.setName("");
//        tempElement.setValue((String)a.getAttribute(HTML.Attribute.VALUE));
//      } else if ( t==HTML.Tag.SELECT ) {
//        if ( tempForm==null )
//          return;
//
//        tempOptions = new AttributeList();
//        tempForm.addInput(
//                          (String)a.getAttribute(HTML.Attribute.NAME),
//                          null,
//                          "select",
//                          tempPrompt,
//                          tempOptions);
//        tempPrompt = "";
//      } else if ( t==HTML.Tag.TEXTAREA ) {
//        if ( tempForm==null )
//          return;
//
//        tempForm.addInput(
//                          (String)a.getAttribute(HTML.Attribute.NAME),
//                          null,
//                          "textarea",
//                          tempPrompt,
//                          null);
//        tempPrompt = "";
//      }
//
//      else if ( t==HTML.Tag.FORM ) {
//        if ( tempForm!=null )
//          _forms.addElement(tempForm);
//
//        String action =
//        (String)a.getAttribute(HTML.Attribute.ACTION);
//        if ( action!=null ) {
//          try {
//            URL aurl = new URL(new URL(_http.getURL()),action);
//            action = aurl.toString();
//          } catch ( MalformedURLException e ) {
//            action = null;
//          }
//        }
//
//        tempForm = new HTMLForm(
//                                (String)a.getAttribute(HTML.Attribute.METHOD),
//                                action );
//        tempPrompt = "";
//      } else if ( t==HTML.Tag.INPUT ) {
//        if ( tempForm==null )
//          return;
//        if ( t!=HTML.Tag.INPUT ) {
//          type = (String)a.getAttribute(HTML.Attribute.TYPE);
//          if ( type==null )
//            return;
//        } else
//          type = "select";
//
//        if ( type.equalsIgnoreCase("text") ||
//             type.equalsIgnoreCase("edit") ||
//             type.equalsIgnoreCase("password") ||
//             type.equalsIgnoreCase("select") ||
//             type.equalsIgnoreCase("hidden") ) {
//          tempForm.addInput(
//                            (String)a.getAttribute(HTML.Attribute.NAME),
//                            (String)a.getAttribute(HTML.Attribute.VALUE),
//                            type,
//                            tempPrompt,
//                            null);
//          tempOptions = new AttributeList();
//        }
//      } else if ( t==HTML.Tag.BASE ) {
//        href = (String)a.getAttribute(HTML.Attribute.HREF);
//        if ( href!=null )
//          _base = href;
//      } else if ( t==HTML.Tag.IMG ) {
//        String src = (String)a.getAttribute(HTML.Attribute.SRC);
//        if ( src!=null )
//          addImage(src);
//      }
//
//    }
//
//    /**
//     * Called to handle text.
//     *
//     * @param data The text.
//     * @param pos The position.
//     */
//    public void handleText(char[] data,int pos)
//    {
//      tempPrompt = new String(data) + " ";
//    }
//
//  }
//}
