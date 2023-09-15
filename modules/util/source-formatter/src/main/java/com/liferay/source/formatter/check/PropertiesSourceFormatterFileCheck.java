/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.source.formatter.check.comparator.PropertyValueComparator;
import com.liferay.source.formatter.check.util.SourceUtil;
import com.liferay.source.formatter.util.FileUtil;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author Hugo Huijser
 */
public class PropertiesSourceFormatterFileCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws Exception {

		if (!absolutePath.endsWith("/source-formatter.properties")) {
			return content;
		}

		content = _fixCheckProperties(content);

		_checkCheckstyleGroupAndOrder(fileName, content, "checkstyle.");
		_checkSourceCheckGroupAndOrder(fileName, content, "source.check.");

		_sortByRootSourceFormatter(fileName, absolutePath, content);

		return _formatSourceFormatterProperties(fileName, content);
	}

	private void _checkCheckstyleGroupAndOrder(
		String fileName, String content, String prefix) {

		String properties = _getProperites(content, prefix);

		if (properties == null) {
			return;
		}

		_checkPropertiesGroupAndOrder(fileName, prefix, properties);
	}

	private void _checkPropertiesGroupAndOrder(
		String fileName, String prefix, String content) {

		String previousPropertyKey = StringPool.BLANK;

		for (String line : content.split("\n")) {
			String trimmedLine = line.trim();

			if (Validator.isNull(trimmedLine) ||
				trimmedLine.startsWith(StringPool.POUND)) {

				continue;
			}

			int pos = trimmedLine.indexOf(CharPool.EQUAL);

			if (pos == -1) {
				continue;
			}

			String propertyKey = StringUtil.trim(trimmedLine.substring(0, pos));

			if (!StringUtil.startsWith(propertyKey, prefix)) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Property '", propertyKey,
						"' should not be in the group for '", prefix, "*'"));

				return;
			}

			if (Validator.isNotNull(previousPropertyKey) &&
				(previousPropertyKey.compareToIgnoreCase(propertyKey) > 0)) {

				addMessage(
					fileName,
					StringBundler.concat(
						"Incorrect order of properties: '", propertyKey,
						"' should come before '", previousPropertyKey, "'"));

				return;
			}

			previousPropertyKey = propertyKey;
		}
	}

	private void _checkSourceCheckGroupAndOrder(
		String fileName, String content, String prefix) {

		String properties = _getProperites(content, prefix);

		if (properties == null) {
			return;
		}

		_checkPropertiesGroupAndOrder(fileName, prefix, properties);
	}

	private String _fixCheckProperties(String content) throws Exception {
		Matcher matcher = _checkPropertyPattern.matcher(content);

		while (matcher.find()) {
			List<String> checkNames = null;

			if (Objects.equals(matcher.group(1), "checkstyle")) {
				checkNames = _getCheckstyleCheckNames();
			}
			else {
				checkNames = _getSourceCheckCheckNames();
			}

			String match = matcher.group(2);

			String formattedMatch = StringUtil.removeChar(
				match, CharPool.PERIOD);

			for (String checkName : checkNames) {
				if (StringUtil.equalsIgnoreCase(checkName, formattedMatch)) {
					return StringUtil.replaceFirst(
						content, match, checkName, matcher.start());
				}
			}
		}

		return content;
	}

	private String _formatSourceFormatterProperties(
			String fileName, String content)
		throws Exception {

		Properties properties = new Properties();

		properties.load(new StringReader(content));

		Enumeration<String> enumeration =
			(Enumeration<String>)properties.propertyNames();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

			String value = properties.getProperty(key);

			if (Validator.isNull(value)) {
				continue;
			}

			List<String> propertyValues = ListUtil.fromString(
				value, StringPool.COMMA);

			if (propertyValues.size() > 1) {
				content = _sortPropertyValues(content, key, propertyValues);
			}

			if (!key.endsWith("excludes") && !key.endsWith("FileNames")) {
				continue;
			}

			for (String propertyFileName : propertyValues) {
				if (propertyFileName.contains(StringPool.STAR) ||
					propertyFileName.endsWith("-ext.properties") ||
					(isPortalSource() && !_hasPrivateAppsDir() &&
					 isModulesApp(propertyFileName, true))) {

					continue;
				}

				int pos = propertyFileName.indexOf(CharPool.AT);

				if (pos != -1) {
					propertyFileName = propertyFileName.substring(0, pos);
				}

				pos = propertyFileName.indexOf("->");

				if (pos != -1) {
					propertyFileName = propertyFileName.substring(pos + 2);
				}

				File file = getFile(propertyFileName, getMaxDirLevel());

				if (file == null) {
					addMessage(
						fileName,
						"Property value '" + propertyFileName +
							"' points to file that does not exist");
				}
			}
		}

		return content;
	}

	private List<String> _getCheckstyleCheckNames() throws Exception {
		Element element = _getRootElement("checkstyle.xml");

		if (element == null) {
			return Collections.emptyList();
		}

		return _getCheckstyleCheckNames(element);
	}

	private List<String> _getCheckstyleCheckNames(Element moduleElement) {
		List<String> checkstyleCheckNames = new ArrayList<>();

		String checkName = moduleElement.attributeValue("name");

		int x = checkName.lastIndexOf(CharPool.PERIOD);

		if (x != -1) {
			checkstyleCheckNames.add(checkName.substring(x + 1));
		}
		else {
			checkstyleCheckNames.add(checkName);
		}

		for (Element childModuleElement :
				(List<Element>)moduleElement.elements("module")) {

			checkstyleCheckNames.addAll(
				_getCheckstyleCheckNames(childModuleElement));
		}

		return checkstyleCheckNames;
	}

	private String _getProperites(String content, String prefix) {
		int x = content.indexOf(StringPool.FOUR_SPACES + prefix);

		if (x == -1) {
			return null;
		}

		int y = content.lastIndexOf(StringPool.FOUR_SPACES + prefix);

		y = content.indexOf("=", y + 1);

		return content.substring(x, y + 1);
	}

	private Element _getRootElement(String fileName) throws Exception {
		ClassLoader classLoader =
			PropertiesSourceFormatterFileCheck.class.getClassLoader();

		if (classLoader == null) {
			return null;
		}

		InputStream inputStream = classLoader.getResourceAsStream(fileName);

		if (inputStream == null) {
			return null;
		}

		String content = StringUtil.read(inputStream);

		Document document = SourceUtil.readXML(content);

		if (document == null) {
			return null;
		}

		return document.getRootElement();
	}

	private List<String> _getSourceCheckCheckNames() throws Exception {
		List<String> sourceCheckCheckNames = new ArrayList<>();

		Element rootElement = _getRootElement("sourcechecks.xml");

		if (rootElement == null) {
			return sourceCheckCheckNames;
		}

		for (Element sourceProcessorElement :
				(List<Element>)rootElement.elements("source-processor")) {

			for (Element checkElement :
					(List<Element>)sourceProcessorElement.elements("check")) {

				sourceCheckCheckNames.add(checkElement.attributeValue("name"));
			}
		}

		return sourceCheckCheckNames;
	}

	private synchronized List<String> _getSourceFormatterProperties()
		throws Exception {

		if (_sourceFormatterProperties != null) {
			return _sourceFormatterProperties;
		}

		File file = new File(getPortalDir() + "/source-formatter.properties");

		String content = FileUtil.read(file);

		if (content == null) {
			return Collections.emptyList();
		}

		_sourceFormatterProperties = new ArrayList<>();

		for (String line : content.split("\n")) {
			String trimmedLine = line.trim();

			if (Validator.isNull(trimmedLine) ||
				trimmedLine.startsWith(StringPool.POUND)) {

				continue;
			}

			int pos = trimmedLine.indexOf(CharPool.EQUAL);

			if (pos == -1) {
				continue;
			}

			_sourceFormatterProperties.add(
				StringUtil.trim(trimmedLine.substring(0, pos)));
		}

		return _sourceFormatterProperties;
	}

	private synchronized boolean _hasPrivateAppsDir() {
		if (_hasPrivateAppsDir != null) {
			return _hasPrivateAppsDir;
		}

		_hasPrivateAppsDir = false;

		if (isPortalSource()) {
			return _hasPrivateAppsDir;
		}

		File dxpAppsDir = getFile("modules/dxp/apps", getMaxDirLevel());

		if (dxpAppsDir != null) {
			_hasPrivateAppsDir = true;

			return _hasPrivateAppsDir;
		}

		File privateAppsDir = getFile("modules/private/apps", getMaxDirLevel());

		if (privateAppsDir != null) {
			_hasPrivateAppsDir = true;
		}

		return _hasPrivateAppsDir;
	}

	private void _sortByRootSourceFormatter(
			String fileName, String absolutePath, String content)
		throws Exception {

		int pos = absolutePath.lastIndexOf(CharPool.SLASH);

		String fileLocation = fileName.substring(0, pos);

		if (fileLocation.equals(SourceUtil.getRootDirName(absolutePath))) {
			return;
		}

		List<String> sourceFormatterProperties =
			_getSourceFormatterProperties();

		if (sourceFormatterProperties.isEmpty()) {
			return;
		}

		Matcher matcher = _propertiesKeyPattern.matcher(content);

		int preIndex = -1;

		while (matcher.find()) {
			int index = sourceFormatterProperties.indexOf(
				StringUtil.trim(matcher.group(1)));

			if (index == -1) {
				continue;
			}

			if ((preIndex != -1) && (index < preIndex)) {
				addMessage(
					fileName,
					StringBundler.concat(
						"Property '", sourceFormatterProperties.get(preIndex),
						"' and '", sourceFormatterProperties.get(index),
						"' should follow root source-formatter.properties ",
						"sort"));

				return;
			}

			preIndex = index;
		}
	}

	private String _sortPropertyValues(
		String content, String propertyKey, List<String> propertyValues) {

		PropertyValueComparator comparator = new PropertyValueComparator();

		for (int i = 0; i < (propertyValues.size() - 1); i++) {
			String propertyValue = propertyValues.get(i);
			String nextPropertyValue = propertyValues.get(i + 1);

			if (comparator.compare(propertyValue, nextPropertyValue) > 0) {
				return _swapValues(
					content, propertyKey, propertyValue, nextPropertyValue);
			}
		}

		return content;
	}

	private String _swapValues(String content, int x, String s1, String s2) {
		while (true) {
			x = content.indexOf(s1, x + 1);

			if (x == -1) {
				return content;
			}

			char c = content.charAt(x - 1);

			if (!Character.isWhitespace(c) && (c != CharPool.EQUAL) &&
				(c != CharPool.COMMA)) {

				continue;
			}

			if ((x + s1.length()) < content.length()) {
				c = content.charAt(x + s1.length());

				if (!Character.isWhitespace(c) && (c != CharPool.COMMA)) {
					continue;
				}
			}

			return StringUtil.replaceFirst(content, s1, s2, x);
		}
	}

	private String _swapValues(
		String content, String propertyKey, String propertyValue,
		String nextPropertyValue) {

		int x = -1;

		while (true) {
			x = content.indexOf(propertyKey + "=");

			if (x == -1) {
				return content;
			}

			if ((x == 0) || Character.isWhitespace(content.charAt(x - 1))) {
				break;
			}
		}

		content = _swapValues(content, x, nextPropertyValue, propertyValue);
		content = _swapValues(content, x, propertyValue, nextPropertyValue);

		return content;
	}

	private static final Pattern _checkPropertyPattern = Pattern.compile(
		"\n\\s*#?(checkstyle|source\\.check)\\.(.*\\.check)\\.");
	private static final Pattern _propertiesKeyPattern = Pattern.compile(
		"(?<=\\A|\n) +([\\w.]+)=");
	private static List<String> _sourceFormatterProperties;

	private Boolean _hasPrivateAppsDir;

}