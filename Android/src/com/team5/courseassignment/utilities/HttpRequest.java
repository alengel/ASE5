package com.team5.courseassignment.utilities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * This class created to handle GET/POST requests by calling
 * makeGetRequest|makePostRequest we can actually establish connection with the
 * database.
 */
public class HttpRequest {
	
	public static JSONObject makePostRequest(String baseUrl, String url, List<NameValuePair> data)
	{
		JSONObject obj = HttpRequest.makePostRequest(baseUrl + url, data);
		if(obj == null)
		{
			String backupBaseUrl = SharedPreferencesEditor.getBackUpBaseUrl(PinMeApplication.getContext());
			obj = HttpRequest.makePostRequest(backupBaseUrl + url, data);
		}
		return obj;
	}
	
	/**
	 * This method creates POST request
	 * 
	 * @param url
	 *            - URL required to establish connection.
	 * @param data
	 *            - List of data to make POST request
	 * @return JSON as result.
	 */
	public static JSONObject makePostRequest(String url,
			List<NameValuePair> data) {

		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		
		HttpClient client = new DefaultHttpClient(httpParams);	
		HttpPost post = new HttpPost(url);
		HttpResponse response = null;

		try {
			HttpEntity entity = new UrlEncodedFormEntity(data, "UTF-8");
			post.setEntity(entity);

			Log.d("log http post request", "post: " + post);

			response = client.execute(post);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject resultJson = null;

		if (response != null) {
			try {

				Log.d("log http post request", "response: " + response);

				String resultString = EntityUtils
						.toString(response.getEntity());

				Log.d("log http post request", "resultString: " + resultString);

				resultJson = new JSONObject(resultString);

				Log.d("log http post request",
						"resultJson: " + resultJson.toString());

			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return resultJson;
	}
	
	public static JSONObject makeGetRequest(String baseUrl, String url, String data)
	{
		JSONObject obj = HttpRequest.makeGetRequest(baseUrl + url, data);
		if(obj == null)
		{
			String backupBaseUrl = SharedPreferencesEditor.getBackUpBaseUrl(PinMeApplication.getContext());
			obj = HttpRequest.makeGetRequest(backupBaseUrl + url, data);
		}
		return obj;
	}

	/**
	 * This method creates GET request.
	 * 
	 * @param url
	 *            - URL required to establish connection
	 * @param data
	 *            - List of data to make GET request.
	 * @return JSON as result
	 */
	public static JSONObject makeGetRequest(String url, String data) {

		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		
		HttpClient client = new DefaultHttpClient(httpParams);		
		String createUrl = url + data;
		HttpGet get = new HttpGet(createUrl);
		HttpResponse response = null;

		try {

			Log.d("log http get request", "get: " + get);

			response = client.execute(get);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject resultJson = null;

		if (response != null) {
			try {

				Log.d("log http get request", "response: " + response);

				String resultString = EntityUtils
						.toString(response.getEntity());

				Log.d("log http get request", "resultString: " + resultString);

				resultJson = new JSONObject(resultString);

				Log.d("log http get request",
						"resultJson: " + resultJson.toString());

			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return resultJson;
	}
}
