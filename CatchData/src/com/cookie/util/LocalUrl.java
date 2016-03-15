package com.cookie.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class LocalUrl {

	public static final ExecutorService taskExecutor = Executors
			.newFixedThreadPool(5);

	public static String netUrlToLoaclUrl(String url, String fileName) {
		taskExecutor.execute(new DownloadTask(url, fileName));
		return url;
	}

	public static void waitDownloadTaskFinish() {
		taskExecutor.shutdown();

		try {
			taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void downloadImg(String url) {

	}
	
}

class DownloadTask implements Runnable {
	String url;
	String fileName;

	public DownloadTask(String url, String fileName) {
		this.url = url;
		this.fileName = fileName;
	}

	@Override
	public void run() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response1 = null;
		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the
		// network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST call CloseableHttpResponse#close() from a finally
		// clause.
		// Please note that if response content is not fully consumed the
		// underlying
		// connection cannot be safely re-used and will be shut down and
		// discarded
		// by the connection manager.
		try {
			response1 = httpclient.execute(httpGet);
			HttpEntity entity1 = response1.getEntity();
			InputStream is = entity1.getContent();
			String filePath = String.format("e:/image/%s", fileName);
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			int inByte;
			while ((inByte = is.read()) != -1)
				fos.write(inByte);
			is.close();
			fos.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
}
