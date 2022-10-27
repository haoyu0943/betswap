package com.yijiang.util.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;


public class TmIfTag extends BodyTagSupport {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String body = null; // 用于存放成功条件后的内容
	private boolean subtagSucceeded;
	private boolean test;
	
	public TmIfTag() {
		super();
		init();
	}

	@Override
	public void release() {
		super.release();
		init();
	}

	@Override
	public int doStartTag() throws JspException {
		if (test) {
			this.succeeded();
		}
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			if (subtagSucceeded)
				pageContext.getOut().write(getBody());
		} catch (IOException e) {
			throw new JspException("IOError while writing the body: "
					+ e.getMessage(), e);
		}

		init();
		return super.doEndTag();
	}
	

	public void setBody() {
		if (body == null) {
			body = bodyContent.getString().trim();
		}
	}

	private String getBody() {
		if (body == null)
			return bodyContent.getString().trim();
		else
			return body;
	}

	/**
	 * 子条件判断成功
	 */
	public void succeeded() {
		subtagSucceeded = true;
	}

	/**
	 * 是否已经执行完毕
	 * 
	 * @return
	 */
	public boolean isSucceeded() {
		return subtagSucceeded;
	}

	private void init() {
		test = false;
		subtagSucceeded = false;
		body = null;
	}
	

	public void setTest(boolean test) {
		this.test = test;
	}
}
