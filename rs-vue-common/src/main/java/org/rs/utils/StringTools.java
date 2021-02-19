package org.rs.utils;

public class StringTools {

	public static String formatDbLikeValue(String value) {
		String[] valueArray = formatLikeValue(value);
		if (valueArray == null) {
			return null;
		}
		return "'%" + valueArray[0] + "%'" + valueArray[1];
	}

	public static String formatDbLeftLikeValue(String value) {
		String[] valueArray = formatLikeValue(value);
		if (valueArray == null) {
			return null;
		}
		return "'" + valueArray[0] + "%'" + valueArray[1];
	}

	public static String formatDbRightLikeValue(String value) {
		String[] valueArray = formatLikeValue(value);
		if (valueArray == null) {
			return null;
		}
		return "'%" + valueArray[0] + "'" + valueArray[1];
	}

	private static String[] formatLikeValue(String value) {
		if ((value == null) || ("".equals(value.trim()))) {
			return null;
		}
		String tempValue = value;
		if (tempValue.indexOf("'") >= 0) {
			tempValue = tempValue.replaceAll("'", "''");
		}
		String[] rtnValue = new String[2];
		rtnValue[0] = tempValue;
		rtnValue[1] = "";
		if (tempValue.indexOf("!") >= 0) {
			tempValue = tempValue.replaceAll("!", "!!");
			rtnValue[0] = tempValue;
			rtnValue[1] = " ESCAPE '!'";
		}
		if (tempValue.indexOf("%") >= 0) {
			tempValue = tempValue.replaceAll("%", "!%");
			rtnValue[0] = tempValue;
			rtnValue[1] = " ESCAPE '!'";
		}
		if (tempValue.indexOf("_") >= 0) {
			tempValue = tempValue.replaceAll("_", "!_");
			rtnValue[0] = tempValue;
			rtnValue[1] = " ESCAPE '!'";
		}
		return rtnValue;
	}
	
	public static boolean hasUpperLetter( String str ) {
		
		for(int i=0; i<str.length(); i++){
			char c = str.charAt(i);
			if(c >= 65 && c <= 90) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasLowerLetter( String str ) {
		
		for(int i=0; i<str.length(); i++){
			char c = str.charAt(i);
			if(c >= 97 && c <= 122) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasDigitLetter( String str ) {
		
		for(int i=0; i<str.length(); i++){
			char c = str.charAt(i);
			if(c >= 48 && c <= 57) {
				return true;
			}
		}
		return false;
	}
	
	public static String getAllExceptionMessage(Throwable throwable) {
		
		StringBuffer sb = new StringBuffer();
		for( Throwable ex = throwable; ex != null; ex = ex.getCause()) {
			sb.append(ex.getMessage() + "; ");			
		}
		return sb.toString();
	}
}
