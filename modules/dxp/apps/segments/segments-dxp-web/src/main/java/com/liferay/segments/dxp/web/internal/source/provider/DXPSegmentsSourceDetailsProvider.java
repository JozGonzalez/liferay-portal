/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.dxp.web.internal.source.provider;

import com.liferay.portal.kernel.language.Language;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.source.provider.SegmentsSourceDetailsProvider;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"segments.source=" + SegmentsEntryConstants.SOURCE_DEFAULT,
		"service.ranking:Integer=100"
	},
	service = SegmentsSourceDetailsProvider.class
)
public class DXPSegmentsSourceDetailsProvider
	implements SegmentsSourceDetailsProvider {

	@Override
	public String getIconSrc() {
		return _servletContext.getContextPath() + "/images/dxp_icon.svg";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "source.dxp");
	}

	@Reference
	private Language _language;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.segments.dxp.web)")
	private ServletContext _servletContext;

}