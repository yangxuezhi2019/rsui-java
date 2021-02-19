package org.rs.core.beans;

public class RsSessionAttribute {

	private String session_primary_id;
	private String attribute_name;
	private byte[] attribute_bytes;
	
	public String getSession_primary_id() {
		return session_primary_id;
	}
	public void setSession_primary_id(String session_primary_id) {
		this.session_primary_id = session_primary_id;
	}
	public String getAttribute_name() {
		return attribute_name;
	}
	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
	}
	public byte[] getAttribute_bytes() {
		return attribute_bytes;
	}
	public void setAttribute_bytes(byte[] attribute_bytes) {
		this.attribute_bytes = attribute_bytes;
	}
}
