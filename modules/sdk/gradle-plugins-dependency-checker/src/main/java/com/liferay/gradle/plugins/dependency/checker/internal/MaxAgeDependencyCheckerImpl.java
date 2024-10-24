/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.dependency.checker.internal;

import com.liferay.gradle.plugins.dependency.checker.DependencyCheckerException;
import com.liferay.gradle.util.Validator;

import groovy.json.JsonSlurper;

import groovy.time.Duration;

import java.net.URL;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.jodah.expiringmap.ExpiringMap;

import org.gradle.api.logging.Logger;

/**
 * @author Andrea Di Giorgi
 */
public class MaxAgeDependencyCheckerImpl extends BaseDependencyCheckerImpl {

	public MaxAgeDependencyCheckerImpl(Logger logger) {
		_logger = logger;
	}

	@Override
	public void check(String group, String name, String version)
		throws Exception {

		VersionInfo latestVersionInfo = _getVersionInfo(group, name, null);

		if (latestVersionInfo.equals(VersionInfo.MISSING)) {
			if (_logger.isWarnEnabled()) {
				_logger.warn(
					"Unable to get information about the latest version of " +
						"'{}:{}'",
					group, name);
			}

			return;
		}

		Date latestDate = new Date(latestVersionInfo.timestamp);

		if (latestVersionInfo.version.equals(version)) {
			if (_logger.isInfoEnabled()) {
				_logger.info(
					"Dependency '{}:{}:{}', published {}, is the latest one " +
						"available",
					group, name, version, latestDate);
			}

			return;
		}

		VersionInfo currentVersionInfo = _getVersionInfo(group, name, version);

		if (currentVersionInfo.equals(VersionInfo.MISSING)) {
			if (_logger.isInfoEnabled()) {
				_logger.info(
					"Unable to get information about '{}:{}:{}'", group, name,
					version);
			}

			return;
		}

		Date currentDate = new Date(currentVersionInfo.timestamp);

		if (latestVersionInfo.timestamp <=
				(currentVersionInfo.timestamp + _maxAge.toMilliseconds())) {

			if (_logger.isWarnEnabled()) {
				_logger.warn(
					"Dependency '{}:{}:{}', published {}, is older than the " +
						"latest one available ('{}:{}:{}', published {}), " +
							"but not yet expired",
					group, name, version, currentDate, group, name,
					latestVersionInfo.version, latestDate);
			}

			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("Dependency '");
		sb.append(group);
		sb.append(':');
		sb.append(name);
		sb.append(':');
		sb.append(version);
		sb.append("', published ");
		sb.append(currentDate);
		sb.append(", is more than ");
		sb.append(_maxAge);
		sb.append(" older than the latest one available ('");
		sb.append(group);
		sb.append(':');
		sb.append(name);
		sb.append(':');
		sb.append(latestVersionInfo.version);
		sb.append("', published ");
		sb.append(latestDate);
		sb.append("), and it is expired");

		if (isThrowError()) {
			throw new DependencyCheckerException(sb.toString());
		}

		if (_logger.isWarnEnabled()) {
			_logger.warn(sb.toString());
		}
	}

	public Duration getMaxAge() {
		return _maxAge;
	}

	public void setMaxAge(Duration maxAge) {
		_maxAge = maxAge;
	}

	private VersionInfo _getVersionInfo(
			String group, String name, String version)
		throws Exception {

		URL url = _getVersionInfoURL(group, name, version);

		VersionInfo versionInfo = _versionInfos.get(url);

		if (versionInfo != null) {
			if (_logger.isDebugEnabled()) {
				_logger.debug("Cache hit for {}: {}", url, versionInfo);
			}

			return versionInfo;
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, Object> map = (Map<String, Object>)jsonSlurper.parse(url);

		Map<String, Object> responseMap = (Map<String, Object>)map.get(
			"response");

		List<Map<String, Object>> docsList =
			(List<Map<String, Object>>)responseMap.get("docs");

		if (docsList.isEmpty()) {
			versionInfo = VersionInfo.MISSING;
		}
		else {
			Map<String, Object> docMap = docsList.get(0);

			long timestamp = (Long)docMap.get("timestamp");

			if (Validator.isNull(version)) {
				version = (String)docMap.get("latestVersion");
			}

			versionInfo = new VersionInfo(timestamp, version);
		}

		_versionInfos.put(url, versionInfo);

		if (_logger.isDebugEnabled()) {
			_logger.debug("Cache miss for {}: {}", url, versionInfo);
		}

		return versionInfo;
	}

	private URL _getVersionInfoURL(String group, String name, String version)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append("https://search.maven.org/solrsearch/select?q=g:");
		sb.append(_ENCODED_DOUBLE_QUOTE);
		sb.append(group);
		sb.append(_ENCODED_DOUBLE_QUOTE);
		sb.append("+AND+a:");
		sb.append(_ENCODED_DOUBLE_QUOTE);
		sb.append(name);
		sb.append(_ENCODED_DOUBLE_QUOTE);

		if (Validator.isNotNull(version)) {
			sb.append("+AND+v:");
			sb.append(_ENCODED_DOUBLE_QUOTE);
			sb.append(version);
			sb.append(_ENCODED_DOUBLE_QUOTE);
		}

		sb.append("&wt=json");

		return new URL(sb.toString());
	}

	private static final String _ENCODED_DOUBLE_QUOTE = "%22";

	private static final ExpiringMap<URL, VersionInfo> _versionInfos =
		ExpiringMap.create();

	private final Logger _logger;
	private Duration _maxAge;

	private static class VersionInfo {

		public static final VersionInfo MISSING = new VersionInfo(0, "");

		public VersionInfo(long timestamp, String version) {
			this.timestamp = timestamp;
			this.version = version;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof VersionInfo)) {
				return false;
			}

			VersionInfo versionInfo = (VersionInfo)object;

			if ((timestamp == versionInfo.timestamp) &&
				version.equals(versionInfo.version)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			String s = toString();

			return s.hashCode();
		}

		@Override
		public String toString() {
			if (equals(MISSING)) {
				return "MISSING";
			}

			StringBuilder sb = new StringBuilder();

			sb.append("timestamp=");
			sb.append(timestamp);
			sb.append(", version=");
			sb.append(version);

			return sb.toString();
		}

		public final long timestamp;
		public final String version;

	}

}