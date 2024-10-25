/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the CacheFieldEntry service. Represents a row in the &quot;CacheFieldEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheFieldEntryModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheFieldEntryImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CacheFieldEntry
 * @generated
 */
@ProviderType
public interface CacheFieldEntryModel extends BaseModel<CacheFieldEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a cache field entry model instance should use the {@link CacheFieldEntry} interface instead.
	 */

	/**
	 * Returns the primary key of this cache field entry.
	 *
	 * @return the primary key of this cache field entry
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this cache field entry.
	 *
	 * @param primaryKey the primary key of this cache field entry
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the cache field entry ID of this cache field entry.
	 *
	 * @return the cache field entry ID of this cache field entry
	 */
	public long getCacheFieldEntryId();

	/**
	 * Sets the cache field entry ID of this cache field entry.
	 *
	 * @param cacheFieldEntryId the cache field entry ID of this cache field entry
	 */
	public void setCacheFieldEntryId(long cacheFieldEntryId);

	/**
	 * Returns the group ID of this cache field entry.
	 *
	 * @return the group ID of this cache field entry
	 */
	public long getGroupId();

	/**
	 * Sets the group ID of this cache field entry.
	 *
	 * @param groupId the group ID of this cache field entry
	 */
	public void setGroupId(long groupId);

	/**
	 * Returns the name of this cache field entry.
	 *
	 * @return the name of this cache field entry
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this cache field entry.
	 *
	 * @param name the name of this cache field entry
	 */
	public void setName(String name);

	@Override
	public CacheFieldEntry cloneWithOriginalValues();

	public default String toXmlString() {
		return null;
	}

}