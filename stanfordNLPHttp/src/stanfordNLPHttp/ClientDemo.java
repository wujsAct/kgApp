package stanfordNLPHttp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class ClientDemo {
	public static void main(String[] args) throws UnsupportedEncodingException{
		JSONObject obj = new JSONObject();
		try {
			obj.put("tokenArrays", "wujs");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "http://192.168.3.196:8000/query?" + URLEncoder.encode(obj.toString(), "UTF-8");
		System.out.println(url);
		HttpClient client = new DefaultHttpClient();  
	    HttpGet request = new HttpGet(url);
	    HttpResponse responseNER;
		try {
			responseNER = client.execute(request);
			System.out.println("Response Code: " +  
			        responseNER.getStatusLine().getStatusCode());  
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
	}
}
