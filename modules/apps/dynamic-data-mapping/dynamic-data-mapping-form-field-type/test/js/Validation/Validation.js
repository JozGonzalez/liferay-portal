/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {FormProvider} from 'data-engine-js-components-web';
import React from 'react';

import Validation from '../../../src/main/resources/META-INF/resources/Validation/Validation';

const globalLanguageDirection = Liferay.Language.direction;

const generateValue = (parameter = null) => ({
	errorMessage: {
		en_US: null,
	},
	expression: {
		en_US: null,
	},
	parameter: {
		en_US: parameter,
	},
});

const ValidationWithProvider = ({formBuilder, validations, ...props}) => (
	<FormProvider initialState={{formBuilder, validations}}>
		<Validation {...props} />
	</FormProvider>
);

describe('Validation', () => {
	beforeAll(() => {
		Liferay.Language.direction = {
			en_US: 'rtl',
		};
	});

	afterAll(() => {
		Liferay.Language.direction = globalLanguageDirection;
	});

	afterEach(cleanup);

	it('renders checkbox to enable Validation', () => {
		const {container} = render(
			<ValidationWithProvider
				dataType="string"
				label="Validator"
				name="validation"
				onChange={() => {}}
				validations={{
					string: [
						{
							label: '',
							name: '',
							parameterMessage: '',
							template: '',
						},
					],
				}}
				value={generateValue()}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('enables validation after click on toogle', () => {
		const onChange = jest.fn();

		const {container} = render(
			<ValidationWithProvider
				dataType="string"
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				expression={{}}
				label="Validator"
				name="validation"
				onChange={onChange}
				validation={{
					dataType: 'string',
					fieldName: 'textfield',
				}}
				validations={{
					string: [
						{
							label: '',
							name: 'contains',
							parameterMessage: '',
							template: 'contains({name}, "{parameter}")',
						},
					],
				}}
				value={generateValue()}
			/>
		);

		const inputCheckbox = container.querySelector('input[type="checkbox"]');

		userEvent.click(inputCheckbox);

		expect(onChange).toHaveBeenLastCalledWith({
			target: {
				value: {
					enableValidation: true,
					errorMessage: {
						en_US: null,
					},
					expression: {
						name: 'contains',
						value: 'contains(textfield, "{parameter}")',
					},
					parameter: {
						en_US: null,
					},
				},
			},
		});
	});

	it('renders parameter field with Numeric element', () => {
		const onChange = jest.fn();

		const {container} = render(
			<ValidationWithProvider
				dataType="numeric"
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				expression={{}}
				label="Validator"
				name="validation"
				onChange={onChange}
				validation={{
					dataType: 'integer',
					fieldName: 'numericfield',
				}}
				validations={{
					numeric: [
						{
							label: '',
							name: 'eq',
							parameterMessage: '',
							template: '{name}=={parameter}',
						},
					],
				}}
				value={generateValue()}
			/>
		);

		const inputCheckbox = container.querySelector('input[type="checkbox"]');

		userEvent.click(inputCheckbox);

		expect(onChange).toHaveBeenLastCalledWith({
			target: {
				value: {
					enableValidation: true,
					errorMessage: {
						en_US: null,
					},
					expression: {
						name: 'eq',
						value: 'numericfield=={parameter}',
					},
					parameter: {
						en_US: null,
					},
				},
			},
		});
	});

	it('renders parameter field with Date element', () => {
		const onChange = jest.fn();

		const {container} = render(
			<ValidationWithProvider
				dataType="date"
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				expression={{}}
				formBuilder={{pages: []}}
				label="Validator"
				name="validation"
				onChange={onChange}
				validation={{
					dataType: 'date',
					fieldName: 'dateField',
				}}
				validations={{
					date: [
						{
							label: '',
							name: 'futureDates',
							parameterMessage: '',
							template: 'futureDates({name}, "{parameter}")',
						},
					],
				}}
				value={generateValue({startsFrom: {}})}
			/>
		);

		const inputCheckbox = container.querySelector('input[type="checkbox"]');

		userEvent.click(inputCheckbox);

		expect(onChange).toHaveBeenLastCalledWith({
			target: {
				value: {
					enableValidation: true,
					errorMessage: {
						en_US: null,
					},
					expression: {
						name: 'futureDates',
						value: 'futureDates(dateField, "{parameter}")',
					},
					parameter: {
						en_US: {
							startsFrom: {},
						},
					},
				},
			},
		});
	});
});
