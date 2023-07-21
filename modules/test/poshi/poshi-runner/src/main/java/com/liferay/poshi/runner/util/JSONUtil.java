/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.util;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import com.liferay.poshi.core.util.ListUtil;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Leslie Wong
 */
public class JSONUtil {

	public static void assertEquals(
			JSONObject jsonObject1, JSONObject jsonObject2)
		throws Exception {

		if (!equals(jsonObject1, jsonObject2)) {
			throw new RuntimeException(
				"JSON object \n" + jsonObject1.toString() +
					"\n is not equal to \n" + jsonObject2);
		}
	}

	public static void assertSimilar(
			JSONObject jsonObject1, JSONObject jsonObject2)
		throws Exception {

		if (!similar(jsonObject1, jsonObject2)) {
			throw new RuntimeException(
				"JSON object \n" + jsonObject1.toString() +
					"\n is not similar to \n" + jsonObject2);
		}
	}

	public static boolean equals(
		JSONObject jsonObject1, JSONObject jsonObject2) {

		return jsonObject1.equals(jsonObject2);
	}

	public static String formatJSONString(String json) {
		JSONObject jsonObject = toJSONObject(json);

		return jsonObject.toString();
	}

	public static Object get(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.opt(name);
	}

	public static boolean getBoolean(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.optBoolean(name);
	}

	public static double getDouble(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.optDouble(name);
	}

	public static int getInt(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.optInt(name);
	}

	public static JSONArray getJSONArray(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.optJSONArray(name);
	}

	public static JSONObject getJSONObject(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.optJSONObject(name);
	}

	public static long getLong(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.optLong(name);
	}

	public static String getString(JSONObject jsonObject, String name)
		throws Exception {

		return jsonObject.optString(name);
	}

	public static String getWithJSONPath(String jsonString, String jsonPath) {
		return getWithJSONPath(jsonString, jsonPath, "true");
	}

	public static String getWithJSONPath(
		String jsonString, String jsonPath, String format) {

		DocumentContext documentContext = JsonPath.parse(jsonString);

		Object object = documentContext.read(jsonPath);

		if (object == null) {
			throw new RuntimeException(
				"Invalid JSON path " + jsonPath + " in " + jsonString);
		}

		if (Boolean.parseBoolean(format) && (object instanceof List)) {
			List<Object> list = (List)object;

			return ListUtil.toString(list);
		}

		return object.toString();
	}

	public static boolean similar(
		JSONObject jsonObject1, JSONObject jsonObject2) {

		return jsonObject1.similar(jsonObject2);
	}

	public static JSONArray toJSONArray(String json) {
		try {
			return new JSONArray(json);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException("Invalid JSON: '" + json + "'");
		}
	}

	public static JSONObject toJSONObject(String json) {
		try {
			return new JSONObject(json);
		}
		catch (JSONException jsonException) {
			throw new RuntimeException("Invalid JSON: '" + json + "'");
		}
	}

}