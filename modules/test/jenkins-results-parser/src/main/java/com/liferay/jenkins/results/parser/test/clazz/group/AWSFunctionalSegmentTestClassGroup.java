/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class AWSFunctionalSegmentTestClassGroup
	extends FunctionalSegmentTestClassGroup {

	@Override
	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.getTestCasePropertiesContent());
		sb.append("\n");

		List<Map.Entry<String, String>> entries = new ArrayList<>();

		entries.add(_getAppServerTypeEntry());
		entries.add(_getDatabaseTypeEntry());
		entries.add(_getDatabaseVersionEntry());
		entries.add(_getOperatingSystemTypeEntry());
		entries.add(_getOperatingSystemVersionEntry());

		entries.removeAll(Collections.singleton(null));

		for (Map.Entry<String, String> entry : entries) {
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			sb.append("\n");
		}

		return sb.toString();
	}

	protected AWSFunctionalSegmentTestClassGroup(
		BatchTestClassGroup parentBatchTestClassGroup) {

		super(parentBatchTestClassGroup);
	}

	protected AWSFunctionalSegmentTestClassGroup(
		BatchTestClassGroup parentBatchTestClassGroup, JSONObject jsonObject) {

		super(parentBatchTestClassGroup, jsonObject);
	}

	private Map.Entry<String, String> _getAppServerTypeEntry() {
		return getEnvironmentVariableEntry(
			"APP_SERVER_TYPE", "environment.app.server.type");
	}

	private Map.Entry<String, String> _getDatabaseTypeEntry() {
		return getEnvironmentVariableEntry(
			"DATABASE_TYPE", "environment.database.type");
	}

	private Map.Entry<String, String> _getDatabaseVersionEntry() {
		return getEnvironmentVariableEntry(
			"DATABASE_VERSION", "environment.database.version");
	}

	private Map.Entry<String, String> _getOperatingSystemTypeEntry() {
		return getEnvironmentVariableEntry(
			"OPERATING_SYSTEM_TYPE", "environment.operating.system.type");
	}

	private Map.Entry<String, String> _getOperatingSystemVersionEntry() {
		return getEnvironmentVariableEntry(
			"OPERATING_SYSTEM_VERSION", "environment.operating.system.version");
	}

}