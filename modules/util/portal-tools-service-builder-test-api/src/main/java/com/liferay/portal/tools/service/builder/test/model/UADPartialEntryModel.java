/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the UADPartialEntry service. Represents a row in the &quot;UADPartialEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.portal.tools.service.builder.test.model.impl.UADPartialEntryModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.portal.tools.service.builder.test.model.impl.UADPartialEntryImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UADPartialEntry
 * @generated
 */
@ProviderType
public interface UADPartialEntryModel extends BaseModel<UADPartialEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a uad partial entry model instance should use the {@link UADPartialEntry} interface instead.
	 */

	/**
	 * Returns the primary key of this uad partial entry.
	 *
	 * @return the primary key of this uad partial entry
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this uad partial entry.
	 *
	 * @param primaryKey the primary key of this uad partial entry
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uad partial entry ID of this uad partial entry.
	 *
	 * @return the uad partial entry ID of this uad partial entry
	 */
	public long getUadPartialEntryId();

	/**
	 * Sets the uad partial entry ID of this uad partial entry.
	 *
	 * @param uadPartialEntryId the uad partial entry ID of this uad partial entry
	 */
	public void setUadPartialEntryId(long uadPartialEntryId);

	/**
	 * Returns the user ID of this uad partial entry.
	 *
	 * @return the user ID of this uad partial entry
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this uad partial entry.
	 *
	 * @param userId the user ID of this uad partial entry
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this uad partial entry.
	 *
	 * @return the user uuid of this uad partial entry
	 */
	public String getUserUuid();

	/**
	 * Sets the user uuid of this uad partial entry.
	 *
	 * @param userUuid the user uuid of this uad partial entry
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this uad partial entry.
	 *
	 * @return the user name of this uad partial entry
	 */
	@AutoEscape
	public String getUserName();

	/**
	 * Sets the user name of this uad partial entry.
	 *
	 * @param userName the user name of this uad partial entry
	 */
	public void setUserName(String userName);

	/**
	 * Returns the message of this uad partial entry.
	 *
	 * @return the message of this uad partial entry
	 */
	@AutoEscape
	public String getMessage();

	/**
	 * Sets the message of this uad partial entry.
	 *
	 * @param message the message of this uad partial entry
	 */
	public void setMessage(String message);

	@Override
	public UADPartialEntry cloneWithOriginalValues();

	public default String toXmlString() {
		return null;
	}

}