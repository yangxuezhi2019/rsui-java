package org.rs.core.resp;

public class RespError {

	/**
	 * 认证错误，没有认证
	 * */
	static public final int ERR_NO_AUTH	= 10401;
	/**
	 * 没有授权，访问被拒绝
	 * */
	static public final int ERR_NO_ACCESS	= 10403;
	/**
	 * No handler found
	 * */
	static public final int ERR_NO_HANDLE = 10404;
	/**
	 * Method Not Allowed
	 * */
	static public final int ERR_NO_METHOD = 10405;
	/**
	 * Internal Server Error
	 * */
	static public final int ERR_UNKNOWN = 10500;
	
	/**
	 * InternalAuthenticationServiceException
	 * */
	static public final int ERR_AUTH_EXCEPTION = 20100;
	/**
	 * Bad Credentials Exception
	 * */
	static public final int ERR_BAD_CREDENTIALS = 20101;
	/**
	 * Username Not Found Exception
	 * */
	static public final int ERR_NO_USER = 20102;
	/**
	 * Session Authentication Exception
	 * */
	static public final int ERR_SESSION = 20103;
	/**
	 * Session Exceed Max Count
	 * */
	static public final int ERR_SESSION_EXCEED = 20104;
	/**
	 * Unable To Decrypt Password
	 * */
	static public final int ERR_DECRYPT_PASSWORD = 20105;
	/**
	 * DB error
	 * */
	static public final int ERR_DB = 20700;
	/**
	 * DuplicateKeyException
	 * */
	static public final int ERR_DuplicateKey = 20701;
	
}
