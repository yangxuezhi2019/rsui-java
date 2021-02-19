package org.rs.utils;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RsaUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(RsaUtils.class);
	public static String publicKey = "";
	public static String privateKey = "";
	static {
		Security.addProvider(new BouncyCastleProvider());
		KeyPair  keyPair = generateKeyPair(1024);
		publicKey = Base64.encodeBase64String(keyPair.getPublic().getEncoded());
		privateKey = Base64.encodeBase64String(keyPair.getPrivate().getEncoded());
	}
	
	public static KeyPair generateKeyPair(int keySize) {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
			keyPairGen.initialize(keySize, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			return keyPair;
		} catch (NoSuchAlgorithmException var3) {
			var3.printStackTrace();
		} catch (NoSuchProviderException var4) {
			var4.printStackTrace();
		}
		return null;
	}
	
	public static RSAPublicKey generateRSAPublicKey(String key ) throws Exception {
		
		byte[] decoded = Base64.decodeBase64(key);
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decoded);
		KeyFactory keyFac = KeyFactory.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
		return (RSAPublicKey) keyFac.generatePublic(x509EncodedKeySpec);
	}
	
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
		KeyFactory keyFac = KeyFactory.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
		return (RSAPublicKey) keyFac.generatePublic(publicKeySpec);
	}
	
	public static RSAPrivateKey generateRSAPrivateKey( String key ) {
		byte[] decoded = Base64.decodeBase64(key);
		try {
			KeyFactory keyFac = KeyFactory.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decoded);
			return (RSAPrivateKey) keyFac.generatePrivate(privateKeySpec);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception {
		KeyFactory keyFac = KeyFactory.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
		RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),
				new BigInteger(privateExponent));
		return (RSAPrivateKey) keyFac.generatePrivate(privateKeySpec);
	}
	
	public static byte[] encrypt(PublicKey pk, byte[] data ) throws Exception {
		
		return encrypt(pk,data,data.length);
	}

	public static byte[] encrypt(PublicKey pk, byte[] data, int inputLen ) throws Exception {
		
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", BouncyCastleProvider.PROVIDER_NAME);
		cipher.init(Cipher.ENCRYPT_MODE, pk);
		int blockSize = cipher.getBlockSize();
		int outputSize = cipher.getOutputSize(inputLen);
		int leaveSize = inputLen % blockSize;
		int blocksSize = leaveSize != 0 ? inputLen / blockSize + 1 : inputLen / blockSize;
		byte[] raw = new byte[outputSize * blocksSize];

		for (int i = 0; inputLen - i * blockSize > 0; ++i) {
			if (inputLen - i * blockSize > blockSize) {
				cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
			} else {
				cipher.doFinal(data, i * blockSize, inputLen - i * blockSize, raw, i * outputSize);
			}
		}
		return raw;
	}
	
	public static byte[] decrypt(PrivateKey key, byte[] raw ) {
		
		return decrypt(key,raw,raw.length);
	}

	public static byte[] decrypt(PrivateKey key, byte[] data, int inputLen ) {
		
		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", BouncyCastleProvider.PROVIDER_NAME);
			cipher.init(Cipher.DECRYPT_MODE, key);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(blockSize);		
			int offset = 0;
			while (inputLen - offset > 0) {
	            if ( inputLen - offset > blockSize ) {
	            	bout.write(cipher.doFinal(data, offset, blockSize));
	            	offset += blockSize;
	            } else {
	            	bout.write(cipher.doFinal(data, offset, inputLen - offset));
	            	offset += (inputLen - offset);
	            }
	        }
			return bout.toByteArray();
		}catch(Exception ex) {
			
			logger.error("decrypt error : {}", StringTools.getAllExceptionMessage(ex));
		}
		return null;
	}
}
