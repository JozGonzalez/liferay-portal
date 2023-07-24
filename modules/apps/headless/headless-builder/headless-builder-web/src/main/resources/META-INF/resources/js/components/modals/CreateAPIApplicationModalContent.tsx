/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import {fetch, openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import BaseAPIApplicationField from '../baseComponents/BaseAPIApplicationFields';

type DataError = {
	baseURL: boolean;
	title: boolean;
};

interface HandleCreateInModal {
	apiApplicationsURLPath: string;
	baseURL: string;
	closeModal: voidReturn;
	loadData: voidReturn;
}

const headers = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
});

export function CreateAPIApplicationModalContent({
	apiApplicationsURLPath,
	baseURL,
	closeModal,
	loadData,
}: HandleCreateInModal) {
	const [data, setData] = useState<Partial<APIApplicationItem>>({});
	const [displayError, setDisplayError] = useState<DataError>({
		baseURL: false,
		title: false,
	});

	useEffect(() => {
		for (const key in data) {
			if (data[key as keyof APIApplicationItem] !== '') {
				setDisplayError((previousErrors) => ({
					...previousErrors,
					[key]: false,
				}));
			}
		}
	}, [data]);

	async function postData() {
		fetch(apiApplicationsURLPath, {
			body: JSON.stringify({
				...data,
				applicationStatus: {key: 'unpublished'},
				version: '1.0',
			}),
			headers,
			method: 'POST',
		})
			.then((response) => {
				if (response.ok) {
					closeModal();
					loadData();
					openToast({
						message: Liferay.Language.get(
							'new-api-application-was-created'
						),
						type: 'success',
					});
				}
				else {
					return response.json();
				}
			})
			.then((responseJson) => {
				if (responseJson) {
					throw new Error(responseJson.title);
				}
			})
			.catch((error) => {
				openToast({
					message: error.message,
					type: 'danger',
				});
			});
	}

	function validateData() {
		let isDataValid = true;
		const mandatoryFields = ['baseURL', 'title'];

		if (!Object.keys(data).length) {
			const errors = mandatoryFields.reduce(
				(errors, field) => ({...errors, [field]: true}),
				{}
			);
			setDisplayError(errors as DataError);

			isDataValid = false;
		}
		else {
			mandatoryFields.forEach((field) => {
				if (data[field as keyof APIApplicationItem]) {
					setDisplayError((previousErrors) => ({
						...previousErrors,
						[field]: false,
					}));
				}
				else {
					setDisplayError((previousErrors) => ({
						...previousErrors,
						[field]: true,
					}));
					isDataValid = false;
				}
			});
		}

		return isDataValid;
	}

	const handleCreate = () => {
		const isDataValid = validateData();

		if (isDataValid) {
			postData();
		}
		else {
			return;
		}
	};

	return (
		<>
			<ClayModal.Header>
				{Liferay.Language.get('new-api-application')}
			</ClayModal.Header>

			<div className="modal-body">
				<BaseAPIApplicationField
					baseURL={baseURL}
					data={data}
					displayError={displayError}
					setData={setData}
				/>
			</div>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							id="modalCancelButton"
							onClick={closeModal}
							type="button"
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							displayType="primary"
							id="modalCreateButton"
							onClick={handleCreate}
							type="button"
						>
							{Liferay.Language.get('create')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</>
	);
}
