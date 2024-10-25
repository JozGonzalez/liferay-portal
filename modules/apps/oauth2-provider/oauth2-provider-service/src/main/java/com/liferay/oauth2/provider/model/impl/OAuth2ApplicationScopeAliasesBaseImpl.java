/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.model.impl;

import com.liferay.oauth2.provider.model.OAuth2ApplicationScopeAliases;
import com.liferay.oauth2.provider.service.OAuth2ApplicationScopeAliasesLocalServiceUtil;

/**
 * The extended model base implementation for the OAuth2ApplicationScopeAliases service. Represents a row in the &quot;OAuth2ApplicationScopeAliases&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link OAuth2ApplicationScopeAliasesImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationScopeAliasesImpl
 * @see OAuth2ApplicationScopeAliases
 * @generated
 */
public abstract class OAuth2ApplicationScopeAliasesBaseImpl
	extends OAuth2ApplicationScopeAliasesModelImpl
	implements OAuth2ApplicationScopeAliases {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a o auth2 application scope aliases model instance should use the <code>OAuth2ApplicationScopeAliases</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			OAuth2ApplicationScopeAliasesLocalServiceUtil.
				addOAuth2ApplicationScopeAliases(this);
		}
		else {
			OAuth2ApplicationScopeAliasesLocalServiceUtil.
				updateOAuth2ApplicationScopeAliases(this);
		}
	}

}