/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.model.LayoutTypeAccessPolicy;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutTypeImpl implements LayoutType {

	public static String getURL(String url, Map<String, String> variables) {
		if (Validator.isNull(url)) {
			url = _URL;
		}

		return replaceVariables(url, variables);
	}

	public LayoutTypeImpl(
		Layout layout, LayoutTypeController layoutTypeController,
		LayoutTypeAccessPolicy layoutTypeAccessPolicy) {

		_layout = layout;
		_layoutTypeController = layoutTypeController;
		_layoutTypeAccessPolicy = layoutTypeAccessPolicy;
	}

	@Override
	public String[] getConfigurationActionDelete() {
		return _layoutTypeController.getConfigurationActionDelete();
	}

	@Override
	public String[] getConfigurationActionUpdate() {
		return _layoutTypeController.getConfigurationActionUpdate();
	}

	@Override
	public Layout getLayout() {
		return _layout;
	}

	@Override
	public LayoutTypeAccessPolicy getLayoutTypeAccessPolicy() {
		return _layoutTypeAccessPolicy;
	}

	@Override
	public LayoutTypeController getLayoutTypeController() {
		return _layoutTypeController;
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		return _layout.getTypeSettingsProperties();
	}

	@Override
	public String getTypeSettingsProperty(String key) {
		return getTypeSettingsProperty(key, null);
	}

	@Override
	public String getTypeSettingsProperty(String key, String defaultValue) {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		return typeSettingsUnicodeProperties.getProperty(key, defaultValue);
	}

	@Override
	public String getURL(Map<String, String> variables) {
		return getURL(_layoutTypeController.getURL(), variables);
	}

	@Override
	public boolean isBrowsable() {
		return _layoutTypeController.isBrowsable();
	}

	@Override
	public boolean isFirstPageable() {
		return _layoutTypeController.isFirstPageable();
	}

	@Override
	public boolean isParentable() {
		return _layoutTypeController.isParentable();
	}

	@Override
	public boolean isSitemapable() {
		return _layoutTypeController.isSitemapable();
	}

	@Override
	public boolean isURLFriendliable() {
		return _layoutTypeController.isURLFriendliable();
	}

	@Override
	public void setTypeSettingsProperty(String key, String value) {
		UnicodeProperties typeSettingsUnicodeProperties =
			getTypeSettingsProperties();

		typeSettingsUnicodeProperties.setProperty(key, value);
	}

	protected static String getDefaultURL() {
		return _URL;
	}

	protected static String replaceVariables(
		String url, Map<String, String> variables) {

		return StringUtil.replace(
			url, StringPool.DOLLAR_AND_OPEN_CURLY_BRACE,
			StringPool.CLOSE_CURLY_BRACE, variables);
	}

	private static final String _URL =
		"${liferay:mainPath}/portal/layout?p_l_id=${liferay:plid}&" +
			"p_v_l_s_g_id=${liferay:pvlsgid}";

	private final Layout _layout;
	private final LayoutTypeAccessPolicy _layoutTypeAccessPolicy;
	private final LayoutTypeController _layoutTypeController;

}