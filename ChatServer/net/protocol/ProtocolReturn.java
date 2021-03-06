package protocol;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProtocolReturn implements ProtocolInterface{
	public ProtocolReturn(String methodName, Object[] result, int id, ProtocolError error) {
		super();
		this.methodName = methodName;
		this.result = result;
		Id = id;
		this.error = error;
	}
	public String methodName;
	public Object[] result;
	public int Id;
	public ProtocolError error;
	
	public JSONObject toJson(){
		JSONObject ret = new JSONObject();
		ret.put("return", methodName);
		JSONArray res = new JSONArray();
		if(result != null){
			for (int i = 0; i < result.length; i++) {
				res.put(result[i]);
			}
		}
		ret.put("result", res);
		JSONObject error = this.error.toJson();
		ret.put("error", error);
		ret.put("id", Id);
		return ret;
	}
	
	@Override
	public String toString() {
		return toJson().toString();
	}

	@Override
	public String getType() {
		return "return";
	}
}
