/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {PermissionsState} from '../reducers/permissionsReducer';

export default function selectCanUpdateItemConfiguration({
	permissions,
}: {
	permissions: PermissionsState;
}) {
	return (
		!permissions.LOCKED_SEGMENTS_EXPERIMENT &&
		(permissions.UPDATE ||
			permissions.UPDATE_LAYOUT_BASIC ||
			permissions.UPDATE_LAYOUT_LIMITED)
	);
}
