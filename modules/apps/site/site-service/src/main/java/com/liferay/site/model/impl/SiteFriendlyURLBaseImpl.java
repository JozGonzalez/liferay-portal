/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.model.impl;

import com.liferay.site.model.SiteFriendlyURL;
import com.liferay.site.service.SiteFriendlyURLLocalServiceUtil;

/**
 * The extended model base implementation for the SiteFriendlyURL service. Represents a row in the &quot;SiteFriendlyURL&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SiteFriendlyURLImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SiteFriendlyURLImpl
 * @see SiteFriendlyURL
 * @generated
 */
public abstract class SiteFriendlyURLBaseImpl
	extends SiteFriendlyURLModelImpl implements SiteFriendlyURL {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a site friendly url model instance should use the <code>SiteFriendlyURL</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			SiteFriendlyURLLocalServiceUtil.addSiteFriendlyURL(this);
		}
		else {
			SiteFriendlyURLLocalServiceUtil.updateSiteFriendlyURL(this);
		}
	}

}