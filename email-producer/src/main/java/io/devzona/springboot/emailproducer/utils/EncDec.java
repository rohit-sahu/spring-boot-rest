package io.devzona.springboot.emailproducer.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class EncDec {

    private static final String KEYGEN_STR = "23435356677";
    private static final String PADDING = "DES/ECB/PKCS5Padding";
    private static final String UTF8 = StandardCharsets.UTF_8.name();
    static Logger logger = LoggerFactory.getLogger(EncDec.class);
    private static Cipher ecipher;
    private static Cipher dcipher;

    private static Key getKey() {
        try {
            byte[] bytes = getbytes(KEYGEN_STR);
            DESKeySpec pass = new DESKeySpec(bytes);
            SecretKeyFactory sKeyFactory = SecretKeyFactory.getInstance("DES");
            return sKeyFactory.generateSecret(pass);
        } catch (Exception ex) {
            logger.error("Exception in getKey {} ", ex);
        }
        return null;
    }

    private static byte[] getbytes(String str) {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        StringTokenizer sTokenizer = new StringTokenizer(str, "-", false);
        while (sTokenizer.hasMoreTokens()) {
            try {
                byteOutputStream.write(sTokenizer.nextToken().getBytes());
            } catch (IOException ex) {
                logger.error("IOException in getbytes {} ", ex);
            }
        }
        byteOutputStream.toByteArray();
        return byteOutputStream.toByteArray();
    }

    public static String encrypt(String sourceStr) {
        try {
            // Get secret key
            Key key = getKey();
            byte[] enc;
            ecipher = Cipher.getInstance(PADDING);
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            enc = ecipher.doFinal(sourceStr.getBytes(UTF8));
            // Encode bytes to base64 to get a string
            Pattern whileSpace = Pattern.compile("\\s+", Pattern.MULTILINE);
            String encryptedString = new sun.misc.BASE64Encoder().encode(enc);
            return whileSpace.matcher(encryptedString).replaceAll("");
        } catch (Exception ex) {
            logger.error("Exception in encrypt {} ", ex);
        }
        return null;
    }

    public static String decrypt(String sourceStr) {
        try {
            // Get secret key
            sourceStr = sourceStr.replace(' ', '+');
            sourceStr = sourceStr.replaceAll("\n", "");
            sourceStr = sourceStr.replace("%20", "+");

            Key key = getKey();
            byte[] dec;
            dcipher = Cipher.getInstance(PADDING);
            dcipher.init(Cipher.DECRYPT_MODE, key);
            // Decode base64 to get bytes
            dec = new sun.misc.BASE64Decoder().decodeBuffer(sourceStr);
            //Decrypt data in a single step
            byte[] utf8 = dcipher.doFinal(dec);
            // Decode using utf-8
            return new String(utf8, UTF8);
        } catch (Exception ex) {
            logger.info("Can not decrypt the string ::{} : Exception thrown {} :", sourceStr, ex);
        }
        return null;
    }

    public static String encodePartialMobile(String mobile) {
        if (StringUtils.isNotBlank(mobile) && mobile.length() > 4) {
            try {
                StringBuilder encodeMobileStr = new StringBuilder(mobile.substring(0, 2));
                for (int i = 0; i < mobile.length() - 4; i++) {
                    encodeMobileStr.append("*");
                }
                encodeMobileStr.append(mobile.substring(mobile.length() - 2));
                return encodeMobileStr.toString();
            } catch (Exception ex) {
                logger.info("Can not decrypt the string ::{} : Exception thrown {} :", mobile, ex);
            }
        }
        return null;
    }

    /* private static Key getKey(String encKey){
         try{
             byte[] bytes = getbytes(encKey);
             DESKeySpec pass = new DESKeySpec(bytes);
             SecretKeyFactory sKeyFactory = SecretKeyFactory.getInstance("DES");
             return sKeyFactory.generateSecret(pass);
         }
         catch(Exception ex){
             logger.error("Exception in getKey {}", ex);
         }
         return null;
     }*/

    /*	    public String encryptNonStatic(String sourceStr){        
	        try{
	            // Get secret key
	            Key key = getKey();
	            byte[] enc ;
		            Cipher ecipher1 = Cipher.getInstance(PADDING);
		            ecipher1.init(Cipher.ENCRYPT_MODE, key);  
		            enc = ecipher1.doFinal(sourceStr.getBytes(UTF8));
	             // Encode bytes to base64 to get a string
	            return new sun.misc.BASE64Encoder().encode(enc);  	        }
	        catch(Exception ex){
	        	logger.error("[Exception [EncDec : while encrypting the string]]");
	        }
	        return null;
	    }

	    public static String encrypt(String sourceStr){        
	        try{
	            // Get secret key
	            Key key = getKey();
	            byte[] enc;
		            ecipher = Cipher.getInstance(PADDING);
		            ecipher.init(Cipher.ENCRYPT_MODE, key);  
		            enc = ecipher.doFinal(sourceStr.getBytes(UTF8));
	             // Encode bytes to base64 to get a string
	            Pattern whileSpace = Pattern.compile("\\s+",Pattern.MULTILINE);
	            String encryptedString = new sun.misc.BASE64Encoder().encode(enc);
	            return whileSpace.matcher(encryptedString).replaceAll("");   	        
	          }catch(Exception ex){
	        	 logger.error("Exception in encrypt {} ",ex);
	        }
	        return null;
	    }
	    public static String encrypt(String sourceStr,String encKey){        
	        try{
	            // Get secret key
	            Key key = getKey(encKey);
	            byte[] enc;
		            ecipher = Cipher.getInstance(PADDING);
		            ecipher.init(Cipher.ENCRYPT_MODE, key);  
		            enc = ecipher.doFinal(sourceStr.getBytes(UTF8));
	             // Encode bytes to base64 to get a string
	            Pattern whileSpace = Pattern.compile("\\s+",Pattern.MULTILINE);
	            String encryptedString = new sun.misc.BASE64Encoder().encode(enc);
	            return whileSpace.matcher(encryptedString).replaceAll("");   	        
	          }catch(Exception ex){
	        	 logger.error("Exception in encrypt {} ",ex);
	        }
	        return null;
	    }
	    
	    public String decryptNonStatic(String sourceStr){
	    	if(sourceStr == null || "".equals(sourceStr)){
	    		return null;
	    	}
	        try{
	            // Get secret key
	        	sourceStr = sourceStr.replace(' ', '+');
	        	sourceStr = sourceStr.replace("%20", "+");
	        	sourceStr = sourceStr.replaceAll("\n", "");
	        	
	            Key key = getKey();
	            byte[] dec ;
		            dcipher = Cipher.getInstance(PADDING);                      
		            dcipher.init(Cipher.DECRYPT_MODE, key);  
		            // Decode base64 to get bytes
		            dec = new sun.misc.BASE64Decoder().decodeBuffer(sourceStr);
		            //Decrypt data in a single step
	            byte[] utf8 = dcipher.doFinal(dec);
	            // Decode using utf-8
	            return new String(utf8, UTF8);
	        }
	        catch(Exception ex){
	        	logger.error("javax.crypto.IllegalBlockSizeException: Input length must be multiple of 8 when decrypting with padded cipher");
	        }
	        return null;
	    }
	    public static String decryptDynamic(String sourceStr, String keyStr){
	        try{
	            // Get secret key
	        	sourceStr = sourceStr.replace(' ', '+');
	        	sourceStr = sourceStr.replaceAll("\n", "");
	        	sourceStr = sourceStr.replace("%20", "+");
	    		
	            Key key = getKey(keyStr);
	            byte[] dec;
		            dcipher = Cipher.getInstance(PADDING);                      
		            dcipher.init(Cipher.DECRYPT_MODE, key);  
		            // Decode base64 to get bytes
		            dec = new sun.misc.BASE64Decoder().decodeBuffer(sourceStr);
	            //Decrypt data in a single step
	            byte[] utf8 = dcipher.doFinal(dec);
	            // Decode using utf-8
	            return new String(utf8, UTF8);
	        }
	        catch(Exception ex){
	        	logger.info("Can not decrypt the string {} Exception thrown {}",sourceStr,ex);
	        }
	        return null;
	    }  
	   */
	    
	   /* public static String decrypt(String sourceStr,String encKey){
	        try{
	            // Get secret key
	        	sourceStr = sourceStr.replace(' ', '+');
	        	sourceStr = sourceStr.replaceAll("\n", "");
	        	sourceStr = sourceStr.replace("%20", "+");
	    		
	            Key key = getKey(encKey);
	            byte[] dec;
		            dcipher = Cipher.getInstance(PADDING);                      
		            dcipher.init(Cipher.DECRYPT_MODE, key);  
		            // Decode base64 to get bytes
		            dec = new sun.misc.BASE64Decoder().decodeBuffer(sourceStr);
	            //Decrypt data in a single step
	            byte[] utf8 = dcipher.doFinal(dec);
	            // Decode using utf-8
	            return new String(utf8,UTF8);
	        }
	        catch(Exception ex){
	        	logger.info("Can not decrypt the string ::{} : Exception thrown {} :",sourceStr,ex);
	        }
	        return null;
	    }  */
}
