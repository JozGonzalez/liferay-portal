/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.theme.builder.maven;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.portal.tools.theme.builder.ThemeBuilderTest;
import com.liferay.portal.tools.theme.builder.internal.util.FileUtil;

import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.ClassRule;

/**
 * @author Andrea Di Giorgi
 */
public class BuildThemeMojoTest extends ThemeBuilderTest {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@Override
	protected void buildTheme(
			File diffsDir, String name, File outputDir, File parentDir,
			String parentName, String templateExtension, File unstyledDir)
		throws Exception {

		File projectDir = temporaryFolder.newFolder("maven");

		_preparePomXml(
			projectDir, diffsDir, name, outputDir, parentDir, parentName,
			templateExtension, unstyledDir);

		MavenExecutor.Result result = mavenExecutor.execute(
			projectDir, "theme-builder:build");

		Assert.assertEquals(result.output, 0, result.exitCode);
	}

	private static String _replace(String s, String key, File file) {
		String value = null;

		if (file != null) {
			value = file.getAbsolutePath();
		}

		return _replace(s, key, value);
	}

	private static String _replace(String s, String key, String value) {
		if (value == null) {
			value = "";
		}

		return s.replace(key, value);
	}

	private void _preparePomXml(
			File projectDir, File diffsDir, String name, File outputDir,
			File parentDir, String parentName, String templateExtension,
			File unstyledDir)
		throws Exception {

		String content = FileUtil.read(
			BuildThemeMojoTest.class, "dependencies/pom_xml.tmpl");

		content = _replace(
			content, "[$THEME_BUILDER_VERSION$]", _THEME_BUILDER_VERSION);

		content = _replace(content, "[$THEME_BUILDER_DIFFS_DIR$]", diffsDir);
		content = _replace(content, "[$THEME_BUILDER_NAME$]", name);
		content = _replace(content, "[$THEME_BUILDER_OUTPUT_DIR$]", outputDir);
		content = _replace(content, "[$THEME_BUILDER_PARENT_DIR$]", parentDir);
		content = _replace(
			content, "[$THEME_BUILDER_PARENT_NAME$]", parentName);
		content = _replace(
			content, "[$THEME_BUILDER_TEMPLATE_EXTENSION$]", templateExtension);
		content = _replace(
			content, "[$THEME_BUILDER_UNSTYLED_DIR$]", unstyledDir);

		File pomXmlFile = new File(projectDir, "pom.xml");

		Files.write(
			pomXmlFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
	}

	private static final String _THEME_BUILDER_VERSION = System.getProperty(
		"theme.builder.version");

}