/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.bean.AutoEscape;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the ClassName service. Represents a row in the &quot;ClassName_&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.portal.model.impl.ClassNameModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.portal.model.impl.ClassNameImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClassName
 * @generated
 */
@ProviderType
public interface ClassNameModel
	extends BaseModel<ClassName>, MVCCModel, TypedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a class name model instance should use the {@link ClassName} interface instead.
	 */

	/**
	 * Returns the primary key of this class name.
	 *
	 * @return the primary key of this class name
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this class name.
	 *
	 * @param primaryKey the primary key of this class name
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this class name.
	 *
	 * @return the mvcc version of this class name
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this class name.
	 *
	 * @param mvccVersion the mvcc version of this class name
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the fully qualified class name of this class name.
	 *
	 * @return the fully qualified class name of this class name
	 */
	@Override
	public String getClassName();

	public void setClassName(String className);

	/**
	 * Returns the class name ID of this class name.
	 *
	 * @return the class name ID of this class name
	 */
	@Override
	public long getClassNameId();

	/**
	 * Sets the class name ID of this class name.
	 *
	 * @param classNameId the class name ID of this class name
	 */
	@Override
	public void setClassNameId(long classNameId);

	/**
	 * Returns the value of this class name.
	 *
	 * @return the value of this class name
	 */
	@AutoEscape
	public String getValue();

	/**
	 * Sets the value of this class name.
	 *
	 * @param value the value of this class name
	 */
	public void setValue(String value);

	@Override
	public ClassName cloneWithOriginalValues();

	public default String toXmlString() {
		return null;
	}

}