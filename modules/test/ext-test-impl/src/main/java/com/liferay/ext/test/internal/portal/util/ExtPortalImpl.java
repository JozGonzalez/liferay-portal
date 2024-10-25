/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ext.test.internal.portal.util;

import com.liferay.portal.util.PortalImpl;

/**
 * @author Victor Ware
 */
public class ExtPortalImpl extends PortalImpl {

	@Override
	public String getComputerName() {
		return "EXT_PORTAL_IMPL_INSTALLED_" + super.getComputerName();
	}

}