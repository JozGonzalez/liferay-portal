/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal.exportimport;

import com.liferay.portal.security.ldap.SafeLdapContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Edward C. Han
 */
public class LDAPImportContext {

	public LDAPImportContext(
		long companyId, Properties contactExpandoMappings,
		Properties contactMappings, Properties groupMappings,
		SafeLdapContext safeLdapContext, long ldapServerId,
		Set<String> ldapUserIgnoreAttributes, Properties userExpandoMappings,
		Properties userMappings) {

		_companyId = companyId;
		_contactExpandoMappings = contactExpandoMappings;
		_contactMappings = contactMappings;
		_groupMappings = groupMappings;
		_safeLdapContext = safeLdapContext;
		_ldapServerId = ldapServerId;
		_ldapUserIgnoreAttributes = ldapUserIgnoreAttributes;
		_userExpandoMappings = userExpandoMappings;
		_userMappings = userMappings;
	}

	public void addImportedUserId(String fullUserDN, long userId) {
		_importedLdapUsers.put(fullUserDN, userId);
	}

	public boolean containsImportedUser(String fullUserDN) {
		return _importedLdapUsers.containsKey(fullUserDN);
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Properties getContactExpandoMappings() {
		return _contactExpandoMappings;
	}

	public Properties getContactMappings() {
		return _contactMappings;
	}

	public Properties getGroupMappings() {
		return _groupMappings;
	}

	public Long getImportedUserId(String fullUserDN) {
		return _importedLdapUsers.get(fullUserDN);
	}

	public long getLdapServerId() {
		return _ldapServerId;
	}

	public Set<String> getLdapUserIgnoreAttributes() {
		return _ldapUserIgnoreAttributes;
	}

	public SafeLdapContext getSafeLdapContext() {
		return _safeLdapContext;
	}

	public Properties getUserExpandoMappings() {
		return _userExpandoMappings;
	}

	public Properties getUserMappings() {
		return _userMappings;
	}

	private final long _companyId;
	private final Properties _contactExpandoMappings;
	private final Properties _contactMappings;
	private final Properties _groupMappings;
	private final Map<String, Long> _importedLdapUsers = new HashMap<>();
	private final long _ldapServerId;
	private final Set<String> _ldapUserIgnoreAttributes;
	private final SafeLdapContext _safeLdapContext;
	private final Properties _userExpandoMappings;
	private final Properties _userMappings;

}