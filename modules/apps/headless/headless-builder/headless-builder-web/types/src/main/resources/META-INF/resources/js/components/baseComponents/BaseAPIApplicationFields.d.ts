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

import {Dispatch, SetStateAction} from 'react';
declare type DataError = {
	baseURL: boolean;
	title: boolean;
};
interface BaseAPIApplicationFieldsProps {
	data: Partial<APIApplicationItem>;
	displayError: DataError;
	setData: Dispatch<SetStateAction<Partial<APIApplicationItem>>>;
	urlAutoFill?: boolean;
}
export default function BaseAPIApplicationFields({
	data,
	displayError,
	setData,
	urlAutoFill,
}: BaseAPIApplicationFieldsProps): JSX.Element;
export {};
