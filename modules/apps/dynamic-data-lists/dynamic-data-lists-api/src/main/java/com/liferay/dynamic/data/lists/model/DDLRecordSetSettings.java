/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.model;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayout;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.annotations.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bruno Basto
 */
@DDMForm(
	rules = {
		@DDMFormRule(
			actions = {
				"setVisible('emailFromAddress', getValue('sendEmailNotification'))",
				"setVisible('emailFromName', getValue('sendEmailNotification'))",
				"setVisible('emailSubject', getValue('sendEmailNotification'))",
				"setVisible('emailToAddress', getValue('sendEmailNotification'))",
				"setVisible('published', FALSE)"
			},
			condition = "TRUE"
		)
	}
)
@DDMFormLayout(
	{
		@DDMFormLayoutPage(
			title = "%form-options",
			value = {
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"requireAuthentication", "requireCaptcha",
								"redirectURL", "storageType",
								"workflowDefinition"
							}
						)
					}
				)
			}
		),
		@DDMFormLayoutPage(
			title = "%email-notifications",
			value = {
				@DDMFormLayoutRow(
					{
						@DDMFormLayoutColumn(
							size = 12,
							value = {
								"sendEmailNotification", "emailFromName",
								"emailFromAddress", "emailToAddress",
								"emailSubject", "published"
							}
						)
					}
				)
			}
		)
	}
)
@ProviderType
public interface DDLRecordSetSettings {

	@DDMFormField(
		label = "%from-address",
		validationErrorMessage = "%please-enter-a-valid-email-address",
		validationExpression = "isEmailAddress(emailFromAddress)"
	)
	public String emailFromAddress();

	@DDMFormField(label = "%from-name")
	public String emailFromName();

	@DDMFormField(label = "%subject")
	public String emailSubject();

	@DDMFormField(
		label = "%to-address",
		validationErrorMessage = "%please-enter-valid-email-addresses-separated-by-commas",
		validationExpression = "isEmailAddress(emailToAddress)"
	)
	public String emailToAddress();

	@DDMFormField
	public boolean published();

	@DDMFormField(
		label = "%redirect-url-on-success",
		properties = "placeholder=%enter-a-valid-url",
		validationErrorMessage = "%please-enter-a-valid-url",
		validationExpression = "isURL(redirectURL)"
	)
	public String redirectURL();

	@DDMFormField(
		label = "%require-user-authentication", predefinedValue = "false",
		properties = "showAsSwitcher=true"
	)
	public default boolean requireAuthentication() {
		return false;
	}

	@DDMFormField(
		label = "%require-captcha", properties = "showAsSwitcher=true",
		type = "checkbox"
	)
	public boolean requireCaptcha();

	@DDMFormField(
		label = "%send-an-email-notification-for-each-entry",
		properties = "showAsSwitcher=true", type = "checkbox"
	)
	public boolean sendEmailNotification();

	@DDMFormField(
		label = "%select-a-storage-type", predefinedValue = "[\"default\"]",
		properties = {
			"dataSourceType=data-provider",
			"ddmDataProviderInstanceId=ddm-storage-types"
		},
		type = "select"
	)
	public String storageType();

	@DDMFormField(
		label = "%select-a-workflow", predefinedValue = "[\"no-workflow\"]",
		properties = {
			"dataSourceType=data-provider",
			"ddmDataProviderInstanceId=workflow-definitions"
		},
		type = "select"
	)
	public String workflowDefinition();

}