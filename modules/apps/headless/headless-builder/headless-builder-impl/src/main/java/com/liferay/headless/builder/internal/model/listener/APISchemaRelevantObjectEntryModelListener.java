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

package com.liferay.headless.builder.internal.model.listener;

import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.listener.RelevantObjectEntryModelListener;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio Jiménez del Coso
 */
@Component(service = RelevantObjectEntryModelListener.class)
public class APISchemaRelevantObjectEntryModelListener
	extends BaseModelListener<ObjectEntry>
	implements RelevantObjectEntryModelListener {

	@Override
	public String getObjectDefinitionExternalReferenceCode() {
		return "L_API_SCHEMA";
	}

	@Override
	public void onBeforeCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		_validate(objectEntry);
	}

	@Override
	public void onBeforeUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		_validate(objectEntry);
	}

	private void _validate(ObjectEntry objectEntry) {
		Map<String, Serializable> values = objectEntry.getValues();

		try {
			if ((long)values.get(
					"r_apiApplicationToAPISchemas_c_apiApplicationId") == 0) {

				throw new ObjectEntryValuesException.InvalidObjectField(
					"An API schema must be related to an API application",
					"an-api-schema-must-be-related-to-an-api-application",
					null);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

}