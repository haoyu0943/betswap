package com.yijiang.util.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

public class TmElseTag extends BodyTagSupport{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void release() {
        super.release();
    }
    
    @Override
	public int doStartTag() throws JspException {
        Tag parent = getParent();

        if(parent==null || !(parent instanceof TmIfTag)){
            throw new JspTagException("else tag must inside if tag");
        }
        
        TmIfTag ifTag = (TmIfTag)parent;
        if(ifTag.isSucceeded()){
            // 已经有执行成功的条件，保存之前的html
            ifTag.setBody();
        }else{
            // 之前没有的判断没有成功条件,则清除之前的输出
            ifTag.getBodyContent().clearBody();
            ifTag.succeeded();
        }
            
        return EVAL_BODY_BUFFERED;
    }
    
}