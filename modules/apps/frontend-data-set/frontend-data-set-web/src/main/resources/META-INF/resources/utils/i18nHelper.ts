/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface ILocalizedItemDetails {
	rootPropertyName: string;
	value: string;
	valuePath: Array<string>;
}

const languageId = Liferay.ThemeDisplay.getLanguageId();
const BCP47LanguageId = Liferay.ThemeDisplay.getBCP47LanguageId();
const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

function getLanguageKey(data: any): string {
	let languageKey = defaultLanguageId as string;

	if (data[languageId]) {
		languageKey = languageId as string;
	}
	else if (data[BCP47LanguageId]) {
		languageKey = BCP47LanguageId;
	}

	return languageKey;
}

export function getLocalizedValueFromItem(
	item: any,
	fieldName: string | Array<string>
): ILocalizedItemDetails | null {
	if (!fieldName) {
		return null;
	}

	const rootPropertyName =
		typeof fieldName === 'string' ? fieldName : fieldName[0];
	let navigatedValue = item;
	const valuePath = [];

	if (
		typeof fieldName === 'string' &&
		item[fieldName] &&
		item[fieldName][getLanguageKey(item)]
	) {
		valuePath.push(fieldName);
		navigatedValue = navigatedValue[fieldName][getLanguageKey(item)];
	}
	else if (Array.isArray(fieldName)) {
		fieldName.forEach((property) => {
			let formattedProperty = property;

			if (property === 'LANG') {
				formattedProperty = getLanguageKey(navigatedValue);
			}

			valuePath.push(formattedProperty);

			if (navigatedValue) {
				navigatedValue = navigatedValue[formattedProperty];
			}
		});
	}
	else {
		valuePath.push(fieldName);
		navigatedValue = navigatedValue[fieldName];
	}

	return {
		rootPropertyName,
		value: navigatedValue,
		valuePath,
	};
}
