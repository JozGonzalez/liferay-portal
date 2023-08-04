/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

interface Option {
	label: string;
	value: string;
}

interface SelectProps {
	cleanUp?: voidReturn;
	onClick: (value: string) => void;
	options: Option[];
	placeholder?: string;
}

export function Select({cleanUp, onClick, options, placeholder}: SelectProps) {
	const [dropdownActive, setDropdownActive] = useState(false);
	const [inputValue, setInputValue] = useState('');

	const filteredOptions = options.filter((option) =>
		option.label.toUpperCase().includes(inputValue.toUpperCase())
	);

	const handleBlur = () => {
		if (cleanUp && !options.some((option) => option.label === inputValue)) {
			cleanUp();
		}
	};

	const handleInputChange = (value: string) => {
		setInputValue(value);

		if (!dropdownActive) {
			setDropdownActive(true);
		}
	};

	const handleSelect = (option: Option) => {
		setInputValue(option.label);
		onClick(option.value);
		setDropdownActive(false);
	};

	return (
		<ClayAutocomplete>
			<ClayIcon
				className="select-field-icon"
				onClick={() => setDropdownActive((active) => !active)}
				symbol="caret-double"
			/>

			<ClayAutocomplete.Input
				onBlur={handleBlur}
				onChange={({target: {value}}) => handleInputChange(value)}
				onFocus={() => setDropdownActive((active) => !active)}
				placeholder={
					placeholder ??
					Liferay.Language.get('please-select-an-option')
				}
				value={inputValue}
			/>

			<ClayAutocomplete.DropDown
				active={dropdownActive}
				closeOnClickOutside
				onSetActive={setDropdownActive}
			>
				{filteredOptions.map((option) => (
					<ClayAutocomplete.Item
						key={option.value}
						match={inputValue}
						onClick={() => handleSelect(option)}
					>
						{option.label}
					</ClayAutocomplete.Item>
				))}
			</ClayAutocomplete.DropDown>
		</ClayAutocomplete>
	);
}
