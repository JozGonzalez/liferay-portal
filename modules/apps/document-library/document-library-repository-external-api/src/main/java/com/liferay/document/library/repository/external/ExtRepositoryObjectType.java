/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.external;

/**
 * Provides external repository constants describing the types of {@link
 * ExtRepositoryObject}'s available. Each constant is connected to an {@link
 * ExtRepositoryObject} derived interface and can be used as parameters for
 * methods that need to differentiate between, for example, files or folders.
 *
 * <p>
 * The constants are designed so that Java Generics can be used to make the
 * return of methods type-safe. See method {@link
 * ExtRepository#getExtRepositoryObject(ExtRepositoryObjectType, String,
 * String)} for an example of a method signature using these constants.
 * </p>
 *
 * @author Iván Zaera
 * @author Sergio González
 */
public final class ExtRepositoryObjectType<T extends ExtRepositoryModel> {

	/**
	 * An {@link ExtRepositoryObjectType} constant referring to interface {@link
	 * ExtRepositoryFileEntry}.
	 */
	public static final ExtRepositoryObjectType<ExtRepositoryFileEntry> FILE =
		new ExtRepositoryObjectType<>("FILE");

	/**
	 * An {@link ExtRepositoryObjectType} constant referring to interface {@link
	 * ExtRepositoryFolder}.
	 */
	public static final ExtRepositoryObjectType<ExtRepositoryFolder> FOLDER =
		new ExtRepositoryObjectType<>("FOLDER");

	/**
	 * An {@link ExtRepositoryObjectType} constant referring to interface {@link
	 * ExtRepositoryObject} (includes both files and folders).
	 */
	public static final ExtRepositoryObjectType<ExtRepositoryObject> OBJECT =
		new ExtRepositoryObjectType<>("OBJECT");

	@Override
	public String toString() {
		return _name;
	}

	private ExtRepositoryObjectType(String name) {
		_name = name;
	}

	private final String _name;

}