/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_1_0;

import com.liferay.portal.kernel.upgrade.BaseCompanyIdUpgradeProcess;

/**
 * @author Pavel Savinov
 */
public class UpgradeCompanyId extends BaseCompanyIdUpgradeProcess {

	@Override
	protected TableUpdater[] getTableUpdaters() {
		return new TableUpdater[] {
			new TableUpdater("LayoutClassedModelUsage", "Layout", "plid")
		};
	}

}