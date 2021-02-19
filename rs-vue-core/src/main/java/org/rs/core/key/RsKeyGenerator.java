package org.rs.core.key;

public interface RsKeyGenerator {

	String getUUIDKey();
	String getZipKey( String keyName, int keyLength );
	String getDateKey( String keyName, int keyLength );
	String getDateKeyStepOne( String keyName, int keyMinLength );
	String getKey( String keyName, int keyLength );
	long getIndexKey( String keyName );
}
