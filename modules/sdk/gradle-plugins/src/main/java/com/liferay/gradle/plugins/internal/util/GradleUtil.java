/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.internal.util;

import java.io.File;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.BasePluginConvention;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class GradleUtil extends com.liferay.gradle.util.GradleUtil {

	public static String getArchivesBaseName(Project project) {
		BasePluginConvention basePluginConvention = getConvention(
			project, BasePluginConvention.class);

		return basePluginConvention.getArchivesBaseName();
	}

	public static Integer getProperty(
		ExtensionAware extensionAware, String name, int defaultValue) {

		Object value = getProperty(extensionAware, name);

		if (value == null) {
			return defaultValue;
		}

		return toInteger(value);
	}

	public static File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	/**
	 * Copied from <code>com.liferay.portal.kernel.util.ThreadUtil</code>.
	 */
	public static Thread[] getThreads() {
		Thread currentThread = Thread.currentThread();

		ThreadGroup threadGroup = currentThread.getThreadGroup();

		while (threadGroup.getParent() != null) {
			threadGroup = threadGroup.getParent();
		}

		int threadCountGuess = threadGroup.activeCount();

		Thread[] threads = new Thread[threadCountGuess];

		int threadCountActual = threadGroup.enumerate(threads);

		while (threadCountActual == threadCountGuess) {
			threadCountGuess *= 2;

			threads = new Thread[threadCountGuess];

			threadCountActual = threadGroup.enumerate(threads);
		}

		return threads;
	}

	public static boolean hasPlugin(
		Project project, Class<? extends Plugin<?>> pluginClass) {

		PluginContainer pluginContainer = project.getPlugins();

		return pluginContainer.hasPlugin(pluginClass);
	}

	public static boolean hasPlugin(Project project, String pluginId) {
		PluginContainer pluginContainer = project.getPlugins();

		return pluginContainer.hasPlugin(pluginId);
	}

	public static boolean hasTask(Project project, String name) {
		TaskContainer taskContainer = project.getTasks();

		if (taskContainer.findByName(name) != null) {
			return true;
		}

		return false;
	}

	public static boolean isRunningInsideDaemon() {
		for (Thread thread : getThreads()) {
			if (thread == null) {
				continue;
			}

			String name = thread.getName();

			if (name.startsWith("Daemon worker")) {
				return true;
			}
		}

		return false;
	}

	public static Map<String, String> toStringMap(Map<String, ?> map) {
		Map<String, String> stringMap = new HashMap<>();

		for (Map.Entry<String, ?> entry : map.entrySet()) {
			String value = toString(entry.getValue());

			stringMap.put(entry.getKey(), value);
		}

		return stringMap;
	}

	public static <P extends Plugin<? extends Project>> void withPlugin(
		Project project, Class<P> pluginClass, Action<P> action) {

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(pluginClass, action);
	}

}