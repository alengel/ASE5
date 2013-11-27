package com.team5.courseassignment;

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
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpRequest {
	public static JSONObject makePostRequest(String url,
			List<NameValuePair> data) {

		HttpClient client = new DefaultHttpClient();
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

	public static JSONObject makeGetRequest(String url, String data) {

		HttpClient client = new DefaultHttpClient();
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
