/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import {
	FormError,
	SingleSelect,
	Toggle,
} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {defaultLanguageId} from '../../utils/constants';

interface AccountRestrictionContainerProps {
	errors: FormError<ObjectDefinition>;
	isApproved: boolean;
	objectFields: ObjectField[];
	setValues: (values: Partial<ObjectDefinition>) => void;
	values: Partial<ObjectDefinition>;
}

export function AccountRestrictionContainer({
	errors,
	isApproved,
	objectFields,
	setValues,
	values,
}: AccountRestrictionContainerProps) {
	const [accountRelationshipFields, setAccountRelationshipFields] = useState<
		LabelValueObject[]
	>([]);

	const [selectedAccount, setSelectedAccount] = useState<string>();
	const [disableAccountToggle, setDisableAccountToggle] = useState<boolean>(
		false
	);
	const [disableAccountSelect, setDisableAccountSelect] = useState<boolean>(
		false
	);

	useEffect(() => {
		const accountRelationshipFieldsResponse = objectFields.filter(
			(field) => {
				if (values.storageType && values.storageType !== 'default') {
					return (
						field.businessType === 'Integer' ||
						field.businessType === 'LongInteger' ||
						field.businessType === 'Text'
					);
				}

				return (
					field.businessType === 'Relationship' &&
					field.objectFieldSettings?.find(
						(fieldSetting) => fieldSetting.value === 'AccountEntry'
					)
				);
			}
		);

		if (accountRelationshipFieldsResponse.length) {
			setAccountRelationshipFields(
				accountRelationshipFieldsResponse.map(
					(accountRelationshipField) => {
						return {
							label: accountRelationshipField.label[
								defaultLanguageId
							] as string,
							value: accountRelationshipField.name,
						};
					}
				)
			);

			const currentAccountRelationship = accountRelationshipFieldsResponse.find(
				(relationshipField) =>
					relationshipField.name ===
					values.accountEntryRestrictedObjectFieldName
			);

			setSelectedAccount(
				currentAccountRelationship?.label[defaultLanguageId] ?? ''
			);

			if (isApproved && values.accountEntryRestricted) {
				setDisableAccountToggle(true);
			}

			if (isApproved && values.accountEntryRestrictedObjectFieldName) {
				setDisableAccountSelect(true);
			}
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectFields]);

	useEffect(() => {
		const selectedAccountLabel = accountRelationshipFields.find(
			(relationshipField) =>
				relationshipField.value ===
				values.accountEntryRestrictedObjectFieldName
		)?.label;

		setSelectedAccount(selectedAccountLabel ?? '');
	}, [
		values.accountEntryRestrictedObjectFieldName,
		accountRelationshipFields,
	]);

	return (
		<ClayPanel
			collapsable
			defaultExpanded
			displayTitle={Liferay.Language.get('account-restriction')}
			displayType="unstyled"
		>
			<ClayPanel.Body>
				<Toggle
					disabled={
						!accountRelationshipFields.length ||
						disableAccountToggle
					}
					label={Liferay.Language.get('active')}
					name="accountEntryRestricted"
					onToggle={() =>
						setValues({
							accountEntryRestricted: !values.accountEntryRestricted,
							accountEntryRestrictedObjectFieldName:
								!values.accountEntryRestricted === false
									? ''
									: values.accountEntryRestrictedObjectFieldName,
						})
					}
					toggled={values.accountEntryRestricted}
				/>

				<SingleSelect<LabelValueObject>
					disabled={
						!accountRelationshipFields.length ||
						!values.accountEntryRestricted ||
						disableAccountSelect
					}
					error={errors.accountEntryRestrictedObjectFieldName}
					label={Liferay.Language.get(
						'account-entry-restricted-object-field-id'
					)}
					onChange={({value}) => {
						setValues({
							accountEntryRestrictedObjectFieldName: value,
						});
					}}
					options={accountRelationshipFields}
					required={
						!!accountRelationshipFields.length &&
						values.accountEntryRestricted &&
						!disableAccountSelect
					}
					value={selectedAccount}
				/>
			</ClayPanel.Body>
		</ClayPanel>
	);
}
