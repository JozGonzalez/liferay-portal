/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

export function fetchImage(url) {
	return new Promise((resolve) => {
		const image = window.document.createElement('img');

		image.onload = () => {
			resolve(url);
		};

		image.src = url;
	});
}

export function updateGallery(formFields, namespace, viewCPAttachmentURL) {
	const ddmFormValues = JSON.stringify(formFields);
	const formData = new FormData();

	formData.append(`${namespace}ddmFormValues`, ddmFormValues);
	formData.append('groupId', themeDisplay.getScopeGroupId());

	return fetch(viewCPAttachmentURL, {
		body: formData,
		headers: new Headers({'x-csrf-token': Liferay.authToken}),
		method: 'post',
	}).then((response) => response.json());
}
