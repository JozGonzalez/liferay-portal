/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.fragment.helper;

import com.liferay.portal.kernel.json.JSONObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Víctor Galán
 */
@ProviderType
public interface DefaultInputFragmentEntryConfigurationProvider {

	public static final String FORM_INPUT_SUBMIT_BUTTON =
		"FORM_INPUT_SUBMIT_BUTTON";

	public JSONObject getDefaultInputFragmentEntryKeysJSONObject(long groupId);

	public void updateDefaultInputFragmentEntryKeysJSONObject(
			JSONObject defaultInputFragmentEntryKeysJSONObject, long groupId)
		throws Exception;

}