/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ObjectValidationRuleSetting service. Represents a row in the &quot;ObjectValidationRuleSetting&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see ObjectValidationRuleSettingModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.object.model.impl.ObjectValidationRuleSettingImpl"
)
@ProviderType
public interface ObjectValidationRuleSetting
	extends ObjectValidationRuleSettingModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.object.model.impl.ObjectValidationRuleSettingImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ObjectValidationRuleSetting, Long>
		OBJECT_VALIDATION_RULE_SETTING_ID_ACCESSOR =
			new Accessor<ObjectValidationRuleSetting, Long>() {

				@Override
				public Long get(
					ObjectValidationRuleSetting objectValidationRuleSetting) {

					return objectValidationRuleSetting.
						getObjectValidationRuleSettingId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ObjectValidationRuleSetting> getTypeClass() {
					return ObjectValidationRuleSetting.class;
				}

			};

	public boolean compareName(String name);

}