/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';

import ContentView from '../../shared/components/content-view/ContentView.es';
import ReloadButton from '../../shared/components/list/ReloadButton.es';
import PaginationBar from '../../shared/components/pagination-bar/PaginationBar.es';
import Table from './PerformanceByAssigneePageTable.es';

function Body({filtered, items, page, pageSize, totalCount}) {
	const statesProps = {
		emptyProps: {
			emptyType: filtered ? 'not-found' : 'empty',
			message: filtered
				? Liferay.Language.get('no-results-were-found')
				: Liferay.Language.get('there-is-no-data-at-the-moment'),
		},
		errorProps: {
			actionButton: <ReloadButton />,
			hideAnimation: true,
			message: Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			),
		},
		loadingProps: {className: 'pb-6 pt-6 sheet'},
	};

	return (
		<ClayLayout.ContainerFluid className="mt-4">
			<ContentView {...statesProps}>
				{totalCount > 0 && (
					<>
						<Body.Table items={items} />

						<PaginationBar
							page={page}
							pageSize={pageSize}
							totalCount={totalCount}
						/>
					</>
				)}
			</ContentView>
		</ClayLayout.ContainerFluid>
	);
}

Body.Table = Table;

export default Body;
