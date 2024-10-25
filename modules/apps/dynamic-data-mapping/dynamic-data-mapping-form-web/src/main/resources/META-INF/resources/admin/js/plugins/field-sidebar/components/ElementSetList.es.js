/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	elementSetAdded,
	useConfig,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import {EmptyState, FieldType, SearchUtils} from 'data-engine-taglib';
import React from 'react';

const EmptyPanel = ({searchTerm}) => (
	<div className="mt-2">
		<EmptyState
			emptyState={{
				description: Liferay.Language.get(
					'there-are-no-element-sets-yet'
				),
				title: Liferay.Language.get('there-are-no-element-sets'),
			}}
			keywords={searchTerm}
			small
		/>
	</div>
);

const ElementSetList = ({searchTerm = ''}) => {
	const {fieldSetDefinitionURL, portletNamespace} = useConfig();
	const {activePage, editingLanguageId, elementSets, pages} = useFormState();
	const dispatch = useForm();
	const regex = SearchUtils.getSearchRegex(searchTerm);

	const elementSetList = elementSets.filter(({name}) => regex.test(name));

	return elementSetList.length ? (
		<div className="mt-3">
			{elementSetList.map((elementSet, key) => {
				const payload = {
					definitionURL: fieldSetDefinitionURL,
					editingLanguageId,
					elementSetId: elementSet.id,
					elementSets,
					namespace: portletNamespace,
				};

				return (
					<FieldType
						dragType="elementSet:add"
						icon="forms"
						key={key}
						label={elementSet.name}
						onDoubleClick={() => {
							const indexes = {
								pageIndex: activePage,
								rowIndex: pages[activePage].rows.length,
							};

							dispatch(elementSetAdded({indexes, ...payload}));
						}}
						payload={payload}
					/>
				);
			})}
		</div>
	) : (
		<EmptyPanel searchTerm={searchTerm} />
	);
};

export default ElementSetList;
