/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React, {Dispatch, SetStateAction} from 'react';

interface SidebarBodyProps {
	setViewRelatedObjects: Dispatch<SetStateAction<boolean>>;
}

export default function SidebarFooter({
	setViewRelatedObjects,
}: SidebarBodyProps) {
	return (
		<div className="sidebar-footer">
			<ClayButton
				displayType="secondary"
				onClick={() => setViewRelatedObjects(true)}
			>
				{Liferay.Language.get('view-related-objects')}
			</ClayButton>
		</div>
	);
}
