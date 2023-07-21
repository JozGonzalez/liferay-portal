/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

export const headers = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
});

export async function fetchJSON<T>({
	init,
	input,
}: {
	init?: RequestInit;
	input: RequestInfo;
}) {
	const result = await fetch(input, {headers, method: 'GET', ...init});

	return (await result.json()) as T;
}

export async function getItems<T>({url}: {url: string}) {
	const {items} = await fetchJSON<{items: T[]}>({input: url});

	return items;
}

export async function updateData({
	dataToUpdate,
	onError,
	onSuccess,
	url,
}: {
	dataToUpdate: Partial<APIApplicationItem>;
	onError: (error: string) => void;
	onSuccess: voidReturn;
	url: string;
}) {
	fetch(url, {
		body: JSON.stringify(dataToUpdate),
		headers,
		method: 'PATCH',
	})
		.then((response) => {
			if (response.ok) {
				onSuccess();
			}
			else {
				return response.json();
			}
		})
		.then((errorResponse) => {
			if (errorResponse) {
				throw new Error(errorResponse.title);
			}
		})
		.catch((error) => {
			onError(error);
		});
}
