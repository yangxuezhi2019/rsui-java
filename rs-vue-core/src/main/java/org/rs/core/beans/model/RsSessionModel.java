package org.rs.core.beans.model;

import java.util.List;
import org.rs.core.beans.RsSession;
import org.rs.core.beans.RsSessionAttribute;

public class RsSessionModel extends RsSession{

	private List<RsSessionAttribute> sessionAttrs;

	public List<RsSessionAttribute> getSessionAttrs() {
		return sessionAttrs;
	}

	public void setSessionAttrs(List<RsSessionAttribute> sessionAttrs) {
		this.sessionAttrs = sessionAttrs;
	}
}
