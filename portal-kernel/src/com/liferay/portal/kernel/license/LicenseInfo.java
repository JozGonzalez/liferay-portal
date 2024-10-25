/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.license;

import java.util.Date;

/**
 * @author Amos Fong
 */
public class LicenseInfo {

	public LicenseInfo(
		String owner, String description, String productEntryName,
		String productId, String productVersion, String licenseEntryType,
		String licenseVersion, Date startDate, Date expirationDate,
		long maxUsers, String[] hostNames, String[] ipAddresses,
		String[] macAddresses) {

		_owner = owner;
		_description = description;
		_productEntryName = productEntryName;
		_productId = productId;
		_productVersion = productVersion;
		_licenseEntryType = licenseEntryType;
		_licenseVersion = licenseVersion;
		_startDate = startDate;
		_expirationDate = expirationDate;
		_maxUsers = maxUsers;
		_hostNames = hostNames;
		_ipAddresses = ipAddresses;
		_macAddresses = macAddresses;
	}

	public String getDescription() {
		return _description;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public String[] getHostNames() {
		return _hostNames;
	}

	public String[] getIpAddresses() {
		return _ipAddresses;
	}

	public String getLicenseEntryType() {
		return _licenseEntryType;
	}

	public String getLicenseVersion() {
		return _licenseVersion;
	}

	public String[] getMacAddresses() {
		return _macAddresses;
	}

	public long getMaxUsers() {
		return _maxUsers;
	}

	public String getOwner() {
		return _owner;
	}

	public String getProductEntryName() {
		return _productEntryName;
	}

	public String getProductId() {
		return _productId;
	}

	public String getProductVersion() {
		return _productVersion;
	}

	public Date getStartDate() {
		return _startDate;
	}

	private final String _description;
	private final Date _expirationDate;
	private final String[] _hostNames;
	private final String[] _ipAddresses;
	private final String _licenseEntryType;
	private final String _licenseVersion;
	private final String[] _macAddresses;
	private final long _maxUsers;
	private final String _owner;
	private final String _productEntryName;
	private final String _productId;
	private final String _productVersion;
	private final Date _startDate;

}