/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {fireEvent, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {openSelectionModal} from 'frontend-js-web';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import PageContentSelectors from '../../../src/main/resources/META-INF/resources/js/components/PageContentSelectors';

const mockProps = {
	deactivateSimulationURL: 'http://test.com',
	namespace: 'test_namespace',
	portletNamespace: 'portlet_test_namespace',
	segmentationEnabled: true,
	segmentsCompanyConfigurationURL: 'http://test.com',
	segmentsEntries: [
		{id: 0, name: 'Anyone'},
		{id: 50959, name: 'Liferayers'},
	],
	segmentsExperiences: [
		{
			segmentsEntryName: 'Liferayers',
			segmentsExperienceActive: true,
			segmentsExperienceId: '50963',
			segmentsExperienceName: 'Experience 1',
			segmentsExperienceStatusLabel: 'Active',
		},
		{
			segmentsEntryName: 'Anyone',
			segmentsExperienceActive: true,
			segmentsExperienceId: '43499',
			segmentsExperienceName: 'Default',
			segmentsExperienceStatusLabel: 'Active',
		},
	],
	selectSegmentsEntryURL: 'http://test.com',
	selectSegmentsExperienceURL: 'http://test.com',
	simulateSegmentsEntriesURL: 'http://test.com',
};

jest.mock('frontend-js-web', () => ({
	openSelectionModal: jest.fn(),
	sub: jest.fn(),
}));

describe('PageContentSelectors', () => {
	beforeEach(() => {
		global.Liferay.on = jest.fn().mockReturnValue({
			detach: jest.fn(),
		});
	});

	afterEach(() => {
		jest.restoreAllMocks();
		jest.clearAllMocks();
	});

	it('renders a selector to choose between “Experiences” or “Segments”', () => {
		const {getByRole, getByText} = render(
			<PageContentSelectors {...mockProps} />
		);

		expect(getByText('preview-by')).toBeInTheDocument();
		expect(getByRole('combobox')).toHaveValue('segments');
		expect(getByRole('option', {name: 'segments'}).selected).toBe(true);
		expect(getByRole('option', {name: 'experiences'}).selected).toBe(false);
	});

	it('If no segments available renders empty segments message', () => {
		const {getByText} = render(
			<PageContentSelectors {...{...mockProps, segmentsEntries: []}} />
		);

		expect(
			getByText('no-segments-have-been-added-yet')
		).toBeInTheDocument();
	});

	it('If no experiences available renders empty experiences message', () => {
		const {getByRole, getByText} = render(
			<PageContentSelectors
				{...{...mockProps, segmentsExperiences: []}}
			/>
		);

		fireEvent.change(getByRole('combobox'), {
			target: {value: 'experiences'},
		});

		expect(
			getByText('no-experiences-have-been-added-yet')
		).toBeInTheDocument();
	});

	it('renders segments selector with no "More segments" button', () => {
		const {getAllByText, getByRole, getByText, queryByText} = render(
			<PageContentSelectors {...mockProps} />
		);

		expect(getByText('segments')).toBeInTheDocument();

		const button = getByRole('button');
		expect(button).toHaveTextContent('Anyone');

		userEvent.click(button);
		expect(getByText('Liferayers')).toBeInTheDocument();

		const selectedOption = getAllByText('Anyone');
		expect(selectedOption.length).toBe(2);
		expect(queryByText('more-segments')).not.toBeInTheDocument();
	});

	it('renders "More segments" button to open item selector if more than 8 segments available', () => {
		const {getByRole, getByText} = render(
			<PageContentSelectors
				{...{
					...mockProps,
					segmentsEntries: [
						{id: 0, name: 'Anyone'},
						{id: 1, name: 'Test1'},
						{id: 2, name: 'Test2'},
						{id: 3, name: 'Test3'},
						{id: 4, name: 'Test4'},
						{id: 5, name: 'Test5'},
						{id: 6, name: 'Test6'},
						{id: 7, name: 'Test7'},
						{id: 8, name: 'Test8'},
					],
				}}
			/>
		);

		const button = getByRole('button');

		userEvent.click(button);
		expect(getByText('more-segments')).toBeInTheDocument();
		userEvent.click(getByText('more-segments'));

		expect(openSelectionModal).toHaveBeenCalled();
	});

	it('renders experiences selector with no "More experiences" button', () => {
		const {getAllByText, getByRole, getByText, queryByText} = render(
			<PageContentSelectors {...mockProps} />
		);

		fireEvent.change(getByRole('combobox'), {
			target: {value: 'experiences'},
		});

		const button = getByRole('button');
		expect(button).toHaveTextContent('Experience 1');

		userEvent.click(button);
		expect(getByText('Default')).toBeInTheDocument();

		const selectedOption = getAllByText('Experience 1');
		expect(selectedOption.length).toBe(2);
		expect(queryByText('more-experiences')).not.toBeInTheDocument();
	});

	it('renders "More segments" button to open item selector if more than 8 segments available', () => {
		const {getByRole, getByText} = render(
			<PageContentSelectors
				{...{
					...mockProps,
					segmentsExperiences: [
						{
							segmentsEntryName: 'Liferayers',
							segmentsExperienceActive: true,
							segmentsExperienceId: '1',
							segmentsExperienceName: 'Experience 1',
							segmentsExperienceStatusLabel: 'Active',
						},
						{
							segmentsEntryName: 'Anyone',
							segmentsExperienceActive: true,
							segmentsExperienceId: '2',
							segmentsExperienceName: 'Default',
							segmentsExperienceStatusLabel: 'Active',
						},
						{
							segmentsEntryName: 'Liferayers',
							segmentsExperienceActive: true,
							segmentsExperienceId: '3',
							segmentsExperienceName: 'Experience 3',
							segmentsExperienceStatusLabel: 'Active',
						},
						{
							segmentsEntryName: 'Liferayers',
							segmentsExperienceActive: true,
							segmentsExperienceId: '4',
							segmentsExperienceName: 'Experience 4',
							segmentsExperienceStatusLabel: 'Active',
						},
						{
							segmentsEntryName: 'Liferayers',
							segmentsExperienceActive: true,
							segmentsExperienceId: '5',
							segmentsExperienceName: 'Experience 5',
							segmentsExperienceStatusLabel: 'Active',
						},
						{
							segmentsEntryName: 'Liferayers',
							segmentsExperienceActive: true,
							segmentsExperienceId: '6',
							segmentsExperienceName: 'Experience 6',
							segmentsExperienceStatusLabel: 'Active',
						},
						{
							segmentsEntryName: 'Liferayers',
							segmentsExperienceActive: true,
							segmentsExperienceId: '7',
							segmentsExperienceName: 'Experience 7',
							segmentsExperienceStatusLabel: 'Active',
						},
						{
							segmentsEntryName: 'Liferayers',
							segmentsExperienceActive: true,
							segmentsExperienceId: '8',
							segmentsExperienceName: 'Experience 8',
							segmentsExperienceStatusLabel: 'Active',
						},
						{
							segmentsEntryName: 'Liferayers',
							segmentsExperienceActive: true,
							segmentsExperienceId: '9',
							segmentsExperienceName: 'Experience 9',
							segmentsExperienceStatusLabel: 'Active',
						},
					],
				}}
			/>
		);

		fireEvent.change(getByRole('combobox'), {
			target: {value: 'experiences'},
		});

		const button = getByRole('button');

		userEvent.click(button);
		expect(getByText('more-experiences')).toBeInTheDocument();
		userEvent.click(getByText('more-experiences'));

		expect(openSelectionModal).toHaveBeenCalled();
	});
});
