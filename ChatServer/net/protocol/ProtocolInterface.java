package protocol;

import org.json.JSONObject;

public interface ProtocolInterface {
	
	public String toString();
	public JSONObject toJson();
	public String getType();
	
}