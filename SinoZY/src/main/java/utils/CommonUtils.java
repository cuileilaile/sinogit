package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;


@Component
public class CommonUtils {

	public static String apikey="74086698-caac-4a86-b0dd-0d9467d49791";
//	
//	@Value("${apikey}")
//	private String apikeyTmp;
//
//
//	@PostConstruct
//	public void init() {
//		apikey = apikeyTmp;
//	}
//	@Value("${zhiyun.apikey}")
//	public void setApikey(String key) {
//		CommonUtils.apikey = apikey;
//	}

	public static String get(String url, Map<String, Object> headers) {
		InputStream inputStream = null;
		HttpURLConnection conn = null;
		try {
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			if (headers != null && !headers.isEmpty()) {
				for (Map.Entry<String, Object> header : headers.entrySet()) {
					conn.setRequestProperty(header.getKey(), header.getValue().toString());
				}
			}
			conn.connect();
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = conn.getInputStream();
				StringBuilder sb = new StringBuilder();
				byte[] buffer = new byte[1024 * 4];
				int len;
				while ((len = inputStream.read(buffer)) > 0) {
					sb.append(new String(buffer, 0, len));
				}
				return sb.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					//
				}
			}
		}
		return null;
	}

	public static String post(String url, String buildParams, Map<String, Object> headers) {
		PrintWriter out = null;
		InputStream inputStream = null;
		HttpURLConnection conn = null;
		try {
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			if (headers != null && !headers.isEmpty()) {
				for (Map.Entry<String, Object> header : headers.entrySet()) {
					conn.setRequestProperty(header.getKey(), header.getValue().toString());
				}
			}
			conn.setDoInput(true);
			conn.setDoOutput(true);
			out = new PrintWriter(conn.getOutputStream());
			out.print(buildParams);
			out.flush();

			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				inputStream = conn.getInputStream();
				StringBuilder sb = new StringBuilder();
				byte[] buffer = new byte[1024 * 4];
				int len;
				while ((len = inputStream.read(buffer)) > 0) {
					sb.append(new String(buffer, 0, len));
				}
				return sb.toString();
			}
		} catch (IOException e) {
			return null;
		} finally {
			if (out != null)
				out.close();
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

	public static String getToken() {

		Map<String, Object> authHeaders = new HashMap<>();
		authHeaders.put("apikey",apikey);
		Map<String, Object> headers = new HashMap<>();
		// 添加验证头
		headers.putAll(authHeaders);
		// 指定参数类型
		headers.put("Content-Type", "application/json");
		String jsonParams = "{\"account\":\"admin\",\"pwd\":\"admin\"}";

		String resp = post("http://gw.test.coc.tencent.com/unified/login/byAccount", jsonParams, headers);
		System.err.println("resp-----------"+resp+"+++++++++++");
		
		JSONObject jo=JSONObject.fromObject(resp);
		JSONObject obj=JSONObject.fromObject(jo.get("data").toString());
		String token=obj.get("X-Token").toString();
		return token;
	}

	public static void main(String[] args) {
		CommonUtils.getToken();
	}

}
