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

package com.liferay.object.field.util;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionException;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.dynamic.data.mapping.expression.ObjectEntryDDMExpressionFieldAccessor;
import com.liferay.object.entry.util.ObjectEntryThreadLocal;
import com.liferay.object.exception.ObjectFieldReadOnlyException;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.service.ObjectFieldSettingLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Guilherme Camacho
 */
public class ObjectFieldUtil {

	public static ObjectField addCustomObjectField(ObjectField objectField)
		throws Exception {

		return ObjectFieldLocalServiceUtil.addCustomObjectField(
			objectField.getExternalReferenceCode(), objectField.getUserId(),
			objectField.getListTypeDefinitionId(),
			objectField.getObjectDefinitionId(), objectField.getBusinessType(),
			objectField.getDBType(), objectField.isIndexed(),
			objectField.isIndexedAsKeyword(),
			objectField.getIndexedLanguageId(), objectField.getLabelMap(),
			objectField.isLocalized(), objectField.getName(),
			objectField.getReadOnly(),
			objectField.getReadOnlyConditionExpression(),
			objectField.isRequired(), objectField.isState(),
			objectField.getObjectFieldSettings());
	}

	public static ObjectField createObjectField(
		long listTypeDefinitionId, String businessType, String dbColumnName,
		String dbType, boolean indexed, boolean indexedAsKeyword,
		String indexedLanguageId, String label, String name, boolean required,
		boolean system) {

		return createObjectField(
			businessType, dbColumnName, dbType, indexed, indexedAsKeyword,
			indexedLanguageId, label, listTypeDefinitionId, name,
			Collections.emptyList(), ObjectFieldConstants.READ_ONLY_FALSE, null,
			required, system);
	}

	public static ObjectField createObjectField(
		String businessType, String dbType, boolean indexed,
		boolean indexedAsKeyword, String indexedLanguageId, String label,
		String name, boolean required) {

		return createObjectField(
			0, businessType, null, dbType, indexed, indexedAsKeyword,
			indexedLanguageId, label, name, required, false);
	}

	public static ObjectField createObjectField(
		String businessType, String dbType, boolean indexed,
		boolean indexedAsKeyword, String indexedLanguageId, String label,
		String name, List<ObjectFieldSetting> objectFieldSettings,
		boolean required) {

		return createObjectField(
			businessType, null, dbType, indexed, indexedAsKeyword,
			indexedLanguageId, label, 0, name, objectFieldSettings,
			ObjectFieldConstants.READ_ONLY_FALSE, null, required, false);
	}

	public static ObjectField createObjectField(
		String businessType, String dbType, String name) {

		return createObjectField(businessType, dbType, name, name, false);
	}

	public static ObjectField createObjectField(
		String businessType, String dbColumnName, String dbType,
		boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
		String label, long listTypeDefinitionId, String name,
		List<ObjectFieldSetting> objectFieldSettings, String readOnly,
		String readOnlyConditionExpression, boolean required, boolean system) {

		ObjectField objectField = ObjectFieldLocalServiceUtil.createObjectField(
			0);

		objectField.setListTypeDefinitionId(listTypeDefinitionId);
		objectField.setBusinessType(businessType);
		objectField.setDBColumnName(dbColumnName);
		objectField.setDBType(dbType);
		objectField.setIndexed(indexed);
		objectField.setIndexedAsKeyword(indexedAsKeyword);
		objectField.setIndexedLanguageId(indexedLanguageId);
		objectField.setLabelMap(LocalizedMapUtil.getLocalizedMap(label));
		objectField.setName(name);
		objectField.setObjectFieldSettings(objectFieldSettings);
		objectField.setReadOnly(readOnly);
		objectField.setReadOnlyConditionExpression(readOnlyConditionExpression);
		objectField.setRequired(required);
		objectField.setSystem(system);

		return objectField;
	}

	public static ObjectField createObjectField(
		String businessType, String dbType, String name,
		List<ObjectFieldSetting> objectFieldSettings) {

		return createObjectField(
			businessType, null, dbType, false, false, null, name, 0, name,
			objectFieldSettings, ObjectFieldConstants.READ_ONLY_FALSE, null,
			false, false);
	}

	public static ObjectField createObjectField(
		String businessType, String dbType, String label, String name) {

		return createObjectField(businessType, dbType, label, name, false);
	}

	public static ObjectField createObjectField(
		String businessType, String dbType, String label, String name,
		boolean required) {

		return createObjectField(
			0, businessType, null, dbType, false, false, null, label, name,
			required, false);
	}

	public static ObjectField createObjectField(
		String businessType, String dbType, String label, String name,
		List<ObjectFieldSetting> objectFieldSettings) {

		return createObjectField(
			businessType, null, dbType, false, false, null, label, 0, name,
			objectFieldSettings, ObjectFieldConstants.READ_ONLY_FALSE, null,
			false, false);
	}

	public static Map<String, ObjectField> toObjectFieldsMap(
		List<ObjectField> objectFields) {

		Map<String, ObjectField> objectFieldsMap = new LinkedHashMap<>();

		for (ObjectField objectField : objectFields) {
			objectFieldsMap.put(objectField.getName(), objectField);
		}

		return objectFieldsMap;
	}

	public static void validateReadOnlyObjectFields(
			DDMExpressionFactory ddmExpressionFactory,
			Map<String, Object> existingValues, List<ObjectField> objectFields,
			Map<String, Object> values)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-170122") ||
			ObjectEntryThreadLocal.isSkipReadOnlyObjectFieldsValidation()) {

			return;
		}

		existingValues.put("currentUserId", PrincipalThreadLocal.getUserId());

		Map<String, ObjectField> objectFieldsMap = toObjectFieldsMap(
			objectFields);

		for (ObjectField objectField : objectFields) {
			if (existingValues.get(objectField.getName()) == null) {
				existingValues.put(
					objectField.getName(),
					ObjectFieldSettingUtil.getDefaultValueAsString(
						null, objectField.getObjectFieldId(),
						ObjectFieldSettingLocalServiceUtil.getService(), null));
			}

			if (objectField.isLocalized()) {
				objectFieldsMap.put(
					objectField.getI18nObjectFieldName(), objectField);
			}
			else if (Objects.equals(
						objectField.getRelationshipType(),
						ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

				String objectRelationshipERCObjectFieldName =
					ObjectFieldSettingUtil.getValue(
						ObjectFieldSettingConstants.
							NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME,
						objectField);

				objectFieldsMap.put(
					objectRelationshipERCObjectFieldName, objectField);
			}
		}

		for (Map.Entry<String, Object> entry : values.entrySet()) {
			if (Objects.equals(entry.getKey(), "status")) {
				continue;
			}

			ObjectField objectField = objectFieldsMap.get(entry.getKey());

			if ((objectField == null) ||
				Objects.equals(
					objectField.getReadOnly(),
					ObjectFieldConstants.READ_ONLY_FALSE)) {

				continue;
			}

			if (Objects.equals(
					objectField.getReadOnly(),
					ObjectFieldConstants.READ_ONLY_TRUE)) {

				_validateNewValue(
					objectField.getDBType(), existingValues.get(entry.getKey()),
					objectField.getName(), entry.getValue());

				continue;
			}

			try {
				DDMExpression<Boolean> ddmExpression =
					ddmExpressionFactory.createExpression(
						CreateExpressionRequest.Builder.newBuilder(
							objectField.getReadOnlyConditionExpression()
						).withDDMExpressionFieldAccessor(
							new ObjectEntryDDMExpressionFieldAccessor(
								existingValues)
						).build());

				ddmExpression.setVariables(existingValues);

				if (ddmExpression.evaluate()) {
					_validateNewValue(
						objectField.getDBType(),
						existingValues.get(entry.getKey()),
						objectField.getName(), entry.getValue());
				}
			}
			catch (DDMExpressionException ddmExpressionException) {
				_log.error(ddmExpressionException);
			}
		}
	}

	private static void _validateNewValue(
			String dbType, Object existingValue, String objectFieldName,
			Object value)
		throws PortalException {

		if (Validator.isNull(existingValue) && Validator.isNull(value)) {
			return;
		}

		if (Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_BLOB) ||
			Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_CLOB) ||
			Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_STRING)) {

			if (Objects.equals(
					GetterUtil.getString(value),
					GetterUtil.getString(existingValue))) {

				return;
			}
		}
		else if (Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_BOOLEAN)) {
			if (Objects.equals(
					GetterUtil.getBoolean(value),
					GetterUtil.getBoolean(existingValue))) {

				return;
			}
		}
		else if (Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_DATE) ||
				 Objects.equals(
					 dbType, ObjectFieldConstants.DB_TYPE_DATE_TIME)) {

			String existingDateValue = String.valueOf(existingValue);

			if (Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_DATE)) {
				existingDateValue = existingDateValue.substring(0, 10);
			}
			else if (Objects.equals(
						dbType, ObjectFieldConstants.DB_TYPE_DATE_TIME)) {

				existingDateValue = existingDateValue.substring(0, 16);
				existingDateValue = existingDateValue.replaceAll(
					"[A-Z]", StringPool.SPACE);
			}

			if (Objects.equals(existingDateValue, value)) {
				return;
			}
		}
		else if (Objects.equals(
					dbType, ObjectFieldConstants.DB_TYPE_BIG_DECIMAL) ||
				 Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_DOUBLE) ||
				 Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_INTEGER) ||
				 Objects.equals(dbType, ObjectFieldConstants.DB_TYPE_LONG)) {

			BigDecimal bigDecimal1 = new BigDecimal(existingValue.toString());
			BigDecimal bigDecimal2 = new BigDecimal(value.toString());

			if (bigDecimal1.compareTo(bigDecimal2) == 0) {
				return;
			}
		}
		else {
			if (Objects.equals(existingValue, value)) {
				return;
			}
		}

		throw new ObjectFieldReadOnlyException(
			"Object field " + objectFieldName + " is read only");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectFieldUtil.class);

}