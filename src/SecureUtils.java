import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SecureUtils {
	
	// Password handling
	
	public static String HashProcess(String password, String salt) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
	
	 public static String getSalt() throws NoSuchAlgorithmException {
		 	String salted = null;
		 	byte[] salt = new byte[16];
	        SecureRandom random = new SecureRandom();
	        random.nextBytes(salt);
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < salt.length; i++) {
                sb.append(Integer.toString((salt[i] & 0xff) + 0x100, 16).substring(1));
            }
	        salted = sb.toString();
	        return salted;
	 	}
	 
	 public static Boolean UserValidation(String password, String salt, String hash) {
		 String hashcheck = HashProcess(password, salt);
		 if(hashcheck.equals(hash)) return true;
		 return false;
	 }
	 
	 public static boolean isEmail(String email) 
	    { 
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
	                            "[a-zA-Z0-9_+&*-]+)*@" + 
	                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
	                            "A-Z]{2,7}$"; 
	                              
	        Pattern pat = Pattern.compile(emailRegex); 
	        if (email == null) 
	            return false; 
	        return pat.matcher(email).matches(); 
	    }
	 
	 public static Boolean isStrongPass(String password) 
	    { 
	        if (password.length() > 8) {
	        	return true; 
	        }else {
	        	return false;
	        }
	            
	    }
	 
	 
	//Section and cookie handling
	 
	 public static String generateSyncToken(String userSesID) {
		    return userSesID + "." + getRandomString();
		  }

	 private static String getRandomString() {
		    UUID uuid = UUID.randomUUID();
		    return uuid.toString();
		  }
	 
	 public static final Function<HttpSession, Cookie> SESSION_ID_COOKIE = new Function<HttpSession, Cookie>() {
		 	public Cookie apply(HttpSession userSession) {
		 		Cookie csrf = new Cookie("csrf", userSession.getId());
		 		csrf.setMaxAge(1800);
				return csrf;
			}
	 };
	
	 public static boolean sessionValidationBySessionCookie(HttpServletRequest request) {
			String userSessionID = request.getSession(false).getId();

			if (userSessionID == null) return false;
			
		    String cookieSessionID = null;
		    Cookie[] cookies = request.getCookies();
		    for(Cookie cookie : cookies) {
		    	if(cookie.getName().equals("csrf")) {
		    		cookieSessionID = cookie.getValue();

		    	return userSessionID.equals(cookieSessionID);
		    	}
		    }
		    return false;
		}
		
	 public static String getSessionIdFromSessionCookie(HttpServletRequest request) {
		    String cookieSessionID = null;
		    Cookie[] cookies = request.getCookies();
		    for(Cookie cookie : cookies) {
		    	if(cookie.getName().equals("csrf")) {
		    		cookieSessionID = cookie.getValue();
		    		break;
		    	}
		    }
		    return cookieSessionID;
		}
}
