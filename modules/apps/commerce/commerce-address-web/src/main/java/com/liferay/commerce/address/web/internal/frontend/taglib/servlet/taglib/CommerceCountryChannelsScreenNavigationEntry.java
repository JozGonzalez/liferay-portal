/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.address.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.User;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Victor Silvestre
 */
@Component(
	property = "screen.navigation.entry.order:Integer=30",
	service = ScreenNavigationEntry.class
)
public class CommerceCountryChannelsScreenNavigationEntry
	extends CommerceCountryChannelsScreenNavigationCategory
	implements ScreenNavigationEntry<Country> {

	@Override
	public String getEntryKey() {
		return getCategoryKey();
	}

	@Override
	public boolean isVisible(User user, Country country) {
		if (country == null) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/commerce_country/channels.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.address.web)"
	)
	private ServletContext _servletContext;

}