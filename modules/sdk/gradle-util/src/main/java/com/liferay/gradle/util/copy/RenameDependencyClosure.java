/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.util.copy;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedModuleVersion;

/**
 * @author Andrea Di Giorgi
 */
public class RenameDependencyClosure extends Closure<String> {

	public RenameDependencyClosure(
		Project project, String... configurationNames) {

		super(project);

		_project = project;
		_configurationNames = configurationNames;
	}

	public String doCall(String name) {
		Map<String, String> newDependencyNames = _getNewDependencyNames();

		String newDependencyName = newDependencyNames.get(name);

		if (Validator.isNotNull(newDependencyName)) {
			return newDependencyName;
		}

		return name;
	}

	private Map<String, String> _getNewDependencyNames() {
		if (_newDependencyNames != null) {
			return _newDependencyNames;
		}

		_newDependencyNames = new HashMap<>();

		for (String configurationName : _configurationNames) {
			Configuration configuration = GradleUtil.getConfiguration(
				_project, configurationName);

			ResolvedConfiguration resolvedConfiguration =
				configuration.getResolvedConfiguration();

			for (ResolvedArtifact resolvedArtifact :
					resolvedConfiguration.getResolvedArtifacts()) {

				ResolvedModuleVersion resolvedModuleVersion =
					resolvedArtifact.getModuleVersion();

				ModuleVersionIdentifier moduleVersionIdentifier =
					resolvedModuleVersion.getId();

				File file = resolvedArtifact.getFile();

				String oldDependencyName = file.getName();

				String newDependencyName = null;

				String suffix =
					"-" + moduleVersionIdentifier.getVersion() + ".jar";

				if (oldDependencyName.endsWith(suffix)) {
					newDependencyName = oldDependencyName.substring(
						0, oldDependencyName.length() - suffix.length());

					newDependencyName += ".jar";
				}
				else {
					newDependencyName =
						moduleVersionIdentifier.getName() + ".jar";
				}

				_newDependencyNames.put(oldDependencyName, newDependencyName);
			}
		}

		return _newDependencyNames;
	}

	private final String[] _configurationNames;
	private Map<String, String> _newDependencyNames;
	private final Project _project;

}