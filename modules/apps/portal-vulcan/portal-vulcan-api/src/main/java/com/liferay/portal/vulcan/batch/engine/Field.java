/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.batch.engine;

/**
 * @author Javier de Arcos
 */
public class Field {

	public static Field of(
		String description, String name, boolean readOnly, boolean required,
		String type, boolean writeOnly) {

		return new Field(
			_toAccessType(readOnly, writeOnly), description, name, required,
			type);
	}

	public AccessType getAccessType() {
		return _accessType;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	public boolean isRequired() {
		return _required;
	}

	public enum AccessType {

		READ, READWRITE, WRITE

	}

	private static AccessType _toAccessType(
		boolean readOnly, boolean writeOnly) {

		if (readOnly) {
			return AccessType.READ;
		}
		else if (writeOnly) {
			return AccessType.WRITE;
		}

		return AccessType.READWRITE;
	}

	private Field(
		AccessType accessType, String description, String name,
		boolean required, String type) {

		_accessType = accessType;
		_description = description;
		_name = name;
		_required = required;
		_type = type;
	}

	private final AccessType _accessType;
	private final String _description;
	private final String _name;
	private final boolean _required;
	private final String _type;

}