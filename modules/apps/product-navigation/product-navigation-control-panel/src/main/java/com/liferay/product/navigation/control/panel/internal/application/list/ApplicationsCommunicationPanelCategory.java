/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.navigation.control.panel.internal.application.list;

import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"panel.category.key=" + PanelCategoryKeys.APPLICATIONS_MENU_APPLICATIONS,
		"panel.category.order:Integer=600"
	},
	service = PanelCategory.class
)
public class ApplicationsCommunicationPanelCategory extends BasePanelCategory {

	@Override
	public String getKey() {
		return PanelCategoryKeys.APPLICATIONS_MENU_APPLICATIONS_COMMUNICATION;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			locale, "category.applications_menu.applications.communication");
	}

	@Reference
	private Language _language;

}