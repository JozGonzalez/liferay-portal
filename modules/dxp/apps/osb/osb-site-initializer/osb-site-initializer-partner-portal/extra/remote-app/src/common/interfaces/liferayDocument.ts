/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default interface LiferayDocument {
	contentUrl?: string;
	dateModified?: string;
	encodingFormat?: string;
	id?: number;
	link?: string;
	name?: string;
	size?: number;
	sizeInBytes?: number;
	title?: string;
	type?: string;
}
