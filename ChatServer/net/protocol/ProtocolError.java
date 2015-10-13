package protocol;

import org.json.JSONObject;

public class ProtocolError implements ProtocolInterface{
	
	public int code;
	public String message;
	
	public ProtocolError(int code, String message){
		this.code = code;
		this.message = message;
	}
	public JSONObject toJson(){
		JSONObject ret = new JSONObject();
		ret.put("code", code);
		ret.put("message", message);
		return ret;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	@Override
	public String getType() {
		return "error";
	}
}
