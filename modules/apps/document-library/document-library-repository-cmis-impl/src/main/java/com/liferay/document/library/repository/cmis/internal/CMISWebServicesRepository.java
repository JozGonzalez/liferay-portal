/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.document.library.repository.cmis.CMISRepositoryHandler;
import com.liferay.document.library.repository.cmis.Session;
import com.liferay.document.library.repository.cmis.internal.constants.CMISRepositoryConstants;
import com.liferay.portal.kernel.exception.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Map;

import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;

/**
 * @author Alexander Chow
 */
public class CMISWebServicesRepository extends CMISRepositoryHandler {

	@Override
	public Session getSession() throws PortalException {
		Locale locale = LocaleUtil.getSiteDefault();

		Map<String, String> parameters = HashMapBuilder.put(
			SessionParameter.BINDING_TYPE, BindingType.WEBSERVICES.value()
		).put(
			SessionParameter.COMPRESSION, Boolean.TRUE.toString()
		).put(
			SessionParameter.LOCALE_ISO639_LANGUAGE, locale.getLanguage()
		).put(
			SessionParameter.LOCALE_ISO3166_COUNTRY, locale.getCountry()
		).put(
			SessionParameter.PASSWORD, PrincipalThreadLocal.getPassword()
		).put(
			SessionParameter.USER, getLogin()
		).put(
			SessionParameter.WEBSERVICES_ACL_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.CMIS_WEBSERVICES_ACL_SERVICE_PARAMETER)
		).put(
			SessionParameter.WEBSERVICES_DISCOVERY_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_DISCOVERY_SERVICE_PARAMETER)
		).put(
			SessionParameter.WEBSERVICES_MULTIFILING_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_MULTIFILING_SERVICE_PARAMETER)
		).put(
			SessionParameter.WEBSERVICES_NAVIGATION_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_NAVIGATION_SERVICE_PARAMETER)
		).put(
			SessionParameter.WEBSERVICES_OBJECT_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_OBJECT_SERVICE_PARAMETER)
		).put(
			SessionParameter.WEBSERVICES_POLICY_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_POLICY_SERVICE_PARAMETER)
		).put(
			SessionParameter.WEBSERVICES_RELATIONSHIP_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_RELATIONSHIP_SERVICE_PARAMETER)
		).put(
			SessionParameter.WEBSERVICES_REPOSITORY_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_REPOSITORY_SERVICE_PARAMETER)
		).put(
			SessionParameter.WEBSERVICES_VERSIONING_SERVICE,
			getTypeSettingsValue(
				CMISRepositoryConstants.
					CMIS_WEBSERVICES_VERSIONING_SERVICE_PARAMETER)
		).build();

		CMISRepositoryUtil.checkRepository(
			getRepositoryId(), parameters, getTypeSettingsProperties(),
			CMISRepositoryConstants.CMIS_WEBSERVICES_REPOSITORY_ID_PARAMETER);

		return CMISRepositoryUtil.createSession(parameters);
	}

	protected String getTypeSettingsValue(String typeSettingsKey)
		throws InvalidRepositoryException {

		return CMISRepositoryUtil.getTypeSettingsValue(
			getTypeSettingsProperties(), typeSettingsKey);
	}

}