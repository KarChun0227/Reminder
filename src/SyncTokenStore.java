import java.util.HashMap;
import java.util.Map;

public class SyncTokenStore {
	private static Map<String, String> syncTokenStore = new HashMap<String, String>();
	
	public static void addToTokenStore(String userSessionID, String token) {
		syncTokenStore.put(userSessionID, token);
	}
	
	public static String getRelevantToken(String userSessionID) {
		return syncTokenStore.get(userSessionID);
	}
	
	public static void deleteToken(String userSessionID) {
		syncTokenStore.remove(userSessionID);
	}
}
