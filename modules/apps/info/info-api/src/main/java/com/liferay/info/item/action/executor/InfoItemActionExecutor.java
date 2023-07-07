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

package com.liferay.info.item.action.executor;

import com.liferay.info.exception.InfoItemActionExecutionException;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;
import java.util.Map;

/**
 * @author Rubén Pulido
 */
public interface InfoItemActionExecutor<T> {

	public void executeInfoItemAction(
			InfoItemIdentifier infoItemIdentifier, String fieldId)
		throws InfoItemActionExecutionException;

	public Map<Locale, String> getInfoItemActionErrorMessageMap(String fieldId)
		throws PortalException;

}