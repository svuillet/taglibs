package com.silverpeas.tags.almanach;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.silverpeas.tags.authentication.AuthenticationManager;

/**
 * TAG almanach
 * 
 */

public class AlmanachTag extends TagSupport{
	
	    public static final String PAGE_ID			= "page";
		public static final String REQUEST_ID		= "request";
		public static final String SESSION_ID		= "session";
		public static final String APPLICATION_ID	= "application";

		private String		name;
		private String		scope	= REQUEST_ID;
		private String		componentId;
		

		/**
	   * Constructor
	   */
		public AlmanachTag()
		{
			super();
		}
		
		/**
	   * Method used by the JSP container to set the parameter Name.
	   */
		public void setName(String name)
		{
			this.name = name;
		}

	     public String getName()
		{
			if (name == null || name.equals("null"))
					name = componentId;
			return name;
		}
		
		public void setScope(String scope)
		{
			this.scope = scope;
		}
		
		public String getScope()
		{
			return scope;
		}

		public void setComponentId(String componentId)
			{
				this.componentId = componentId;
			}
		public String getComponentId()
		{
			return componentId;
		}
	
	public int doStartTag() throws JspTagException
	{
		int iScope = translateScope(scope);
		
		AlmanachTagUtil atu = (AlmanachTagUtil) pageContext.getAttribute(getName(), iScope);
		if (atu == null)
		{
			String userId = AuthenticationManager.getUserId((HttpServletRequest)pageContext.getRequest());
			
			//create a new object which have a reference on Publication EJB
			atu = new AlmanachTagUtil(getComponentId(), userId);
			pageContext.setAttribute(getName(), atu, iScope);
		}
		return EVAL_PAGE;
	}

	protected int translateScope(String scope)
	{
		if(scope.equalsIgnoreCase(PAGE_ID)) {
			return PageContext.PAGE_SCOPE;
		} else if(scope.equalsIgnoreCase(REQUEST_ID)) {
			return PageContext.REQUEST_SCOPE;
		} else if(scope.equalsIgnoreCase(SESSION_ID)) {
			return PageContext.SESSION_SCOPE;
		} else if(scope.equalsIgnoreCase(APPLICATION_ID)) {
			return PageContext.APPLICATION_SCOPE;
		} else
			return PageContext.REQUEST_SCOPE;
	}
}