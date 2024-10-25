/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v1_0_1;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Marcellus Tavares
 */
public class KaleoProcessUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateWorkflowDefinition();
	}

	private void _updateKaleoProcess(
			long kaleoProcessId, String workflowDefinitioName,
			int workflowDefinitionVersion)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update KaleoProcess set workflowDefinitionName = ?, " +
					"workflowDefinitionVersion = ? where kaleoProcessId = ?")) {

			preparedStatement.setString(1, workflowDefinitioName);
			preparedStatement.setInt(2, workflowDefinitionVersion);
			preparedStatement.setLong(3, kaleoProcessId);

			preparedStatement.executeUpdate();
		}
	}

	private void _updateWorkflowDefinition() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select classPK, workflowDefinitionName, " +
					"workflowDefinitionVersion from WorkflowDefinitionLink " +
						"where classNameId = ?")) {

			long kaleoProcessClassNameId = PortalUtil.getClassNameId(
				KaleoProcess.class);

			preparedStatement.setLong(1, kaleoProcessClassNameId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					long kaleoProcessId = resultSet.getLong("classPK");
					String workflowDefinitionName = resultSet.getString(
						"workflowDefinitionName");
					int workflowDefinitionVersion = resultSet.getInt(
						"workflowDefinitionVersion");

					_updateKaleoProcess(
						kaleoProcessId, workflowDefinitionName,
						workflowDefinitionVersion);
				}
			}
		}
	}

}