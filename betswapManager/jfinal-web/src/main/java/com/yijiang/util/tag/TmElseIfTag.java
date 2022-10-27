package com.yijiang.util.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

public class TmElseIfTag extends BodyTagSupport{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private boolean test;  
    public TmElseIfTag() {
        super();
        init();
    }

    @Override
    public int doStartTag() throws JspException {
        Tag parent = getParent();

        if(parent==null || !(parent instanceof com.yijiang.util.tag.TmIfTag)){
            throw new JspTagException("else tag must inside if tag");
        }
        
        com.yijiang.util.tag.TmIfTag ifTag = (com.yijiang.util.tag.TmIfTag)parent;
        if(ifTag.isSucceeded()){
            // 已经有执行成功的条件，保存之前的html
            ifTag.setBody();
        }else if(test){        // 当前条件为true,之前无条件为true
            ifTag.succeeded();
            // 则清除之前的输出
            ifTag.getBodyContent().clearBody();
        }
            
        return EVAL_BODY_BUFFERED;
    }
     
    @Override
    public void release() {
        super.release();
        init();
    }
    
    private void init() {
        test = false;
    }
    
    public void setTest(boolean test) {
        this.test = test;
    }
}