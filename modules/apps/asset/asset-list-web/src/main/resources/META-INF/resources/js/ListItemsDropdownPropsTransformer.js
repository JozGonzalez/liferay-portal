/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getTop} from 'frontend-js-web';

const ACTIONS = {
	editContent(itemData) {
		this.navigate(itemData.editContentURL);
	},

	editDisplayPageTemplate(itemData) {
		this.navigate(itemData.editDisplayPageTemplateURL);
	},

	navigate(url) {
		const openerWindow = getTop();

		openerWindow.Liferay.Util.navigate(url);
	},

	viewDisplayPage(itemData) {
		this.navigate(itemData.viewDisplayPageURL);
	},
};

export default function propsTransformer({items, ...props}) {
	return {
		...props,
		items: items.map((item) => {
			return {
				...item,
				onClick(event) {
					const action = item.data?.action;

					if (action) {
						event.preventDefault();

						ACTIONS[action](item.data);
					}
				},
			};
		}),
	};
}
