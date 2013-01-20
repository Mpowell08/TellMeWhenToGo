package GoogleMatrixAPIHelpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class QueryExecutor {
	private static String URL;
	private JSONObject jObj;
	private InputStream is;

    static String json = "";
	public QueryExecutor(GoogleDMQuery query){
		URL = query.MakeURL();
		this.jObj = getJSON();
	}
	
	
	private JSONObject getJSON(){
		 
        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL);
            httpPost.setHeader("Content-type", "application/json");
            Log.d("URL", URL);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();           
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
 
        return jObj;
 
	}
	
	public GoogleDMResult createResult(){

		String status = "No", origin = "", destination = "";
		int time = 0;
		JSONObject json = jObj;

		JSONArray destination_addresses = null;
		JSONArray origin_addresses = null;
		JSONArray rows = null;

		try {
			destination_addresses = json.getJSONArray("destination_addresses");
			destination = destination_addresses.getString(0);
			Log.d("dest", destination);
			
			origin_addresses = json.getJSONArray("origin_addresses");
			origin = origin_addresses.getString(0);
			Log.d("origin", origin);
			
			
			rows = json.getJSONArray("rows");
			for(int i = 0; i < rows.length(); i++){
				JSONObject row = rows.getJSONObject(i);
				JSONArray elements = row.getJSONArray("elements");
				
				for(int j = 0; j < elements.length(); j++) {
					JSONObject element = elements.getJSONObject(j);
					status = element.get("status").toString();
					JSONObject duration = element.getJSONObject("duration");
					//JSONObject distance = element.getJSONObject("distance");
					
					time = duration.getInt("value");
					Log.d("status", status);
				}
				
			}
			
			
			
			
			
		} catch (JSONException e){
			e.printStackTrace();
		}
		
		return new GoogleDMResult(status, origin, destination, time);
	}
	
}
