/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.admin.web.internal.upgrade.v_1_0_2;

import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Alec Shay
 */
public class LayoutSetTypeSettingsUpgradeProcess extends UpgradeProcess {

	public LayoutSetTypeSettingsUpgradeProcess(
		GroupLocalService groupLocalService,
		LayoutSetLocalService layoutSetLocalService) {

		_groupLocalService = groupLocalService;
		_layoutSetLocalService = layoutSetLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateRobots();
	}

	private void _updateLayoutSetTypeSettings(
			String key, String property, long groupId, boolean privateLayout)
		throws Exception {

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			groupId, privateLayout);

		UnicodeProperties typeSettingsUnicodeProperties =
			layoutSet.getSettingsProperties();

		typeSettingsUnicodeProperties.setProperty(key, property);

		_layoutSetLocalService.updateSettings(
			groupId, privateLayout, typeSettingsUnicodeProperties.toString());
	}

	private void _updateRobots() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select groupId, typeSettings from Group_ where typeSettings " +
					"like '%robots.txt%'")) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long groupId = resultSet.getLong("groupId");

					String typeSettings = resultSet.getString("typeSettings");

					UnicodeProperties typeSettingsUnicodeProperties =
						UnicodePropertiesBuilder.load(
							typeSettings
						).build();

					String privateRobots =
						typeSettingsUnicodeProperties.getProperty(
							"true-robots.txt");

					if (privateRobots != null) {
						_updateLayoutSetTypeSettings(
							"true-robots.txt", privateRobots, groupId, true);

						typeSettingsUnicodeProperties.remove("true-robots.txt");
					}

					String publicRobots =
						typeSettingsUnicodeProperties.getProperty(
							"false-robots.txt");

					if (publicRobots != null) {
						_updateLayoutSetTypeSettings(
							"false-robots.txt", publicRobots, groupId, false);

						typeSettingsUnicodeProperties.remove(
							"false-robots.txt");
					}

					_groupLocalService.updateGroup(
						groupId, typeSettingsUnicodeProperties.toString());
				}
			}
		}
	}

	private final GroupLocalService _groupLocalService;
	private final LayoutSetLocalService _layoutSetLocalService;

}