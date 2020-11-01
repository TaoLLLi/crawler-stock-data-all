package com.ths.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

	private static OkHttpClient client = new OkHttpClient();

	/**
	 * get 请求
	 * 
	 * @param url 请求URL
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url) throws Exception {
		return doGet(url, Maps.newHashMap());
	}

	/**
	 * get 请求
	 * 
	 * @param url   请求URL
	 * @param query 携带参数参数
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url, Map<String, Object> query) throws Exception {
		return doGet(url, Maps.newHashMap(), query);
	}

	/**
	 * get 请求
	 * 
	 * @param url    url
	 * @param header 请求头参数
	 * @param query  参数
	 * @return
	 */
	public static String doGet(String url, Map<String, Object> header, Map<String, Object> query) throws Exception {

		Request.Builder builder = new Request.Builder();
		Request request = builder.url(url).build();

		HttpUrl.Builder urlBuilder = request.url().newBuilder();
		Headers.Builder headerBuilder = request.headers().newBuilder();

		Iterator<Map.Entry<String, Object>> headerIterator = header.entrySet().iterator();
		headerIterator.forEachRemaining(e -> {
			headerBuilder.add(e.getKey(), (String) e.getValue());
		});
		Iterator<Map.Entry<String, Object>> queryIterator = query.entrySet().iterator();
		queryIterator.forEachRemaining(e -> {
			urlBuilder.addQueryParameter(e.getKey(), (String) e.getValue());
		});

		builder.url(urlBuilder.build()).headers(headerBuilder.build());
		try (Response execute = client.newCall(builder.build()).execute()) {
			return execute.body().string();
		}
	}

	/**
	 * post 请求， 请求参数 并且 携带文件上传
	 * 
	 * @param url
	 * @param header
	 * @param parameter
	 * @param file         文件
	 * @param fileFormName 远程接口接收 file 的参数
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, Map<String, Object> header, Map<String, Object> parameter, File file,
			String fileFormName) throws Exception {
		Request.Builder builder = new Request.Builder();
		Request request = builder.url(url).build();
		Headers.Builder headerBuilder = request.headers().newBuilder();
		Iterator<Map.Entry<String, Object>> headerIterator = header.entrySet().iterator();
		headerIterator.forEachRemaining(e -> {
			headerBuilder.add(e.getKey(), (String) e.getValue());
		});
		MultipartBody.Builder requestBuilder = new MultipartBody.Builder();

		Iterator<Map.Entry<String, Object>> queryIterator = parameter.entrySet().iterator();
		queryIterator.forEachRemaining(e -> {
			requestBuilder.addFormDataPart(e.getKey(), (String) e.getValue());
		});

		if (null != file) {
			requestBuilder.addFormDataPart(!StringUtils.isEmpty(fileFormName) ? fileFormName : "file", file.getName(),
					RequestBody.create(MediaType.parse("application/octet-stream"), file));
		}

		builder.headers(headerBuilder.build()).post(requestBuilder.build());
		try (Response execute = client.newCall(builder.build()).execute()) {
			return execute.body().string();
		}
	}

	/**
	 * post 请求， 请求参数 并且 携带文件上传二进制流
	 * 
	 * @param url
	 * @param header
	 * @param parameter
	 * @param fileName     文件名
	 * @param fileByte     文件的二进制流
	 * @param fileFormName 远程接口接收 file 的参数
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, Map<String, Object> header, Map<String, Object> parameter, String fileName,
			byte[] fileByte, String fileFormName) throws Exception {
		Request.Builder builder = new Request.Builder();
		Request request = builder.url(url).build();

		Headers.Builder headerBuilder = request.headers().newBuilder();

		Iterator<Map.Entry<String, Object>> headerIterator = header.entrySet().iterator();
		headerIterator.forEachRemaining(e -> {
			headerBuilder.add(e.getKey(), (String) e.getValue());
		});

		MultipartBody.Builder requestBuilder = new MultipartBody.Builder();

		Iterator<Map.Entry<String, Object>> queryIterator = parameter.entrySet().iterator();
		queryIterator.forEachRemaining(e -> {
			requestBuilder.addFormDataPart(e.getKey(), (String) e.getValue());
		});

		if (fileByte.length > 0) {
			requestBuilder.addFormDataPart(!StringUtils.isEmpty(fileFormName) ? fileFormName : "file", fileName,
					RequestBody.create(MediaType.parse("application/octet-stream"), fileByte));
		}

		builder.headers(headerBuilder.build()).post(requestBuilder.build());
		try (Response execute = client.newCall(builder.build()).execute()) {
			return execute.body().string();
		}
	}

	/**
	 * post 请求 携带文件上传
	 * 
	 * @param url
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, File file, String fileFormName) throws Exception {
		return doPost(url, Maps.newHashMap(), Maps.newHashMap(), file, fileFormName);
	}

	/**
	 * post 请求
	 * 
	 * @param url
	 * @param header    请求头
	 * @param parameter 参数
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, Map<String, Object> header, Map<String, Object> parameter)
			throws Exception {
		return doPost(url, header, parameter, null, null);
	}

	/**
	 * post 请求
	 * 
	 * @param url
	 * @param parameter 参数
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, Map<String, Object> parameter) throws Exception {
		return doPost(url, Maps.newHashMap(), parameter, null, null);
	}

	/**
	 * JSON数据格式请求
	 * 
	 * @param url
	 * @param header
	 * @param json
	 * @return
	 */
	private static String json(String url, Map<String, Object> header, String json) throws Exception {
		Request.Builder builder = new Request.Builder();
		Request request = builder.url(url).build();

		Headers.Builder headerBuilder = request.headers().newBuilder();

		Iterator<Map.Entry<String, Object>> headerIterator = header.entrySet().iterator();
		headerIterator.forEachRemaining(e -> {
			headerBuilder.add(e.getKey(), (String) e.getValue());
		});
		RequestBody requestBody = FormBody.create(MediaType.parse("application/json"), json);
		builder.headers(headerBuilder.build()).post(requestBody);
		try (Response execute = client.newCall(builder.build()).execute()) {
			return execute.body().string();
		}
	}

	/**
	 * post请求 参数JSON格式
	 * 
	 * @param url
	 * @param header 请求头
	 * @param json   JSON数据
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, Object> header, String json) throws Exception {
		return json(url, header, json);
	}

	/**
	 * post请求 参数JSON格式
	 * 
	 * @param url
	 * @param json JSON数据
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String url, String json) throws Exception {
		return json(url, Maps.newHashMap(), json);
	}

	public static String doDelete(String url, Map<String, Object> header) throws Exception {
		return doDelete(url, header, Maps.newHashMap());
	}
	public static String doDelete(String url, Map<String, Object> header, Map<String, Object> query) throws Exception {
		Request.Builder builder = new Request.Builder();
		Request request = builder.url(url).delete().build();

		HttpUrl.Builder urlBuilder = request.url().newBuilder();
		Headers.Builder headerBuilder = request.headers().newBuilder();

		Iterator<Map.Entry<String, Object>> headerIterator = header.entrySet().iterator();
		headerIterator.forEachRemaining(e -> {
			headerBuilder.add(e.getKey(), (String) e.getValue());
		});
		Iterator<Map.Entry<String, Object>> queryIterator = query.entrySet().iterator();
		queryIterator.forEachRemaining(e -> {
			urlBuilder.addQueryParameter(e.getKey(), (String) e.getValue());
		});

		builder.url(urlBuilder.build()).headers(headerBuilder.build());
		try (Response execute = client.newCall(builder.build()).execute()) {
			return execute.body().string();
		}
	}
}
