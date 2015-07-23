package com.p2p.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ObjectToMap {

	@SuppressWarnings("unchecked")
	public static Map<String, String> objectToMap(Object obj) {
		return JSON.parseObject(JSONObject.toJSONString(obj), HashMap.class);
	}

	@SuppressWarnings("unchecked")
	public static Map<byte[], byte[]> mapTObyte(Object obj) {
		Map<byte[], byte[]> bMap = new HashMap<byte[], byte[]>();
		Map<String, Object> map = JSON.parseObject(JSONObject.toJSONString(obj), HashMap.class);
		for (String key : map.keySet()) {
			try {
				bMap.put(key.getBytes("utf-8"), String.valueOf(map.get(key)).getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bMap;
	}

	public static Map<String, String> byteToMap(Map<byte[], byte[]> map) {
		Map<String, String> m = new HashMap<String, String>();
		for (byte[] b : map.keySet()) {
			try {
				m.put(new String(b, "utf-8"), new String(map.get(b), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return m;
	}

	public static List<String> byteToList(List<byte[]> b) {
		List<String> list = new ArrayList<String>();
		for (byte[] bt : b) {
			try {
				list.add(new String(bt, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
}
