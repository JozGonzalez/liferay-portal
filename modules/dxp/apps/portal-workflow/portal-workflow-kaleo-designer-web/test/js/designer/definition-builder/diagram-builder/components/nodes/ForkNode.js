/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {render} from '@testing-library/react';
import React from 'react';

import ForkNode from '../../../../../../../src/main/resources/META-INF/resources/designer/js/definition-builder/diagram-builder/components/nodes/ForkNode';
import MockDefinitionBuilderContext from '../../../../../../mock/MockDefinitionBuilderContext';
import MockDiagramBuilderContext from '../../../../../../mock/MockDiagramBuilderContext';

describe('The ForkNode component should', () => {
	let container;

	it('Be rendered with correct icon and default values for label and description, if they are not set', () => {
		const renderResult = render(
			<MockDefinitionBuilderContext>
				<MockDiagramBuilderContext>
					<ForkNode />
				</MockDiagramBuilderContext>
			</MockDefinitionBuilderContext>
		);

		container = renderResult.container;

		const icon = container.querySelector('svg.lexicon-icon-arrow-split');

		expect(icon).toBeTruthy();

		const label = container.querySelector('span.node-label');
		const description = container.querySelector('span.node-description');

		expect(label).toHaveTextContent('fork');
		expect(description).toHaveTextContent(
			'split-the-workflow-into-multiple-paths'
		);
	});

	it('Be rendered with custom label and description', () => {
		const renderResult = render(
			<MockDefinitionBuilderContext>
				<MockDiagramBuilderContext>
					<ForkNode
						data={{
							description: 'test description',
							label: {en_US: 'test label'},
						}}
					/>
				</MockDiagramBuilderContext>
			</MockDefinitionBuilderContext>
		);

		container = renderResult.container;

		const label = container.querySelector('span.node-label');
		const description = container.querySelector('span.node-description');

		expect(label).toHaveTextContent('test label');
		expect(description).toHaveTextContent('test description');
	});
});
