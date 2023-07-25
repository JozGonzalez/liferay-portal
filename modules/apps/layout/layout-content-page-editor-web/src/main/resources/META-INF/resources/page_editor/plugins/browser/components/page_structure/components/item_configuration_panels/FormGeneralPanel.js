/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {ClayInput, ClayToggle} from '@clayui/form';
import {useControlledState} from '@liferay/layout-js-components-web';
import React, {useCallback, useEffect, useState} from 'react';

import {addMappingFields} from '../../../../../../app/actions/index';
import updateItemLocalConfig from '../../../../../../app/actions/updateItemLocalConfig';
import {SelectField} from '../../../../../../app/components/fragment_configuration_fields/SelectField';
import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import selectLanguageId from '../../../../../../app/selectors/selectLanguageId';
import InfoItemService from '../../../../../../app/services/InfoItemService';
import updateFormItemConfig from '../../../../../../app/thunks/updateFormItemConfig';
import {formIsMapped} from '../../../../../../app/utils/formIsMapped';
import {formIsRestricted} from '../../../../../../app/utils/formIsRestricted';
import {formIsUnavailable} from '../../../../../../app/utils/formIsUnavailable';
import {getEditableLocalizedValue} from '../../../../../../app/utils/getEditableLocalizedValue';
import getMappingFieldsKey from '../../../../../../app/utils/getMappingFieldsKey';
import Collapse from '../../../../../../common/components/Collapse';
import CurrentLanguageFlag from '../../../../../../common/components/CurrentLanguageFlag';
import {LayoutSelector} from '../../../../../../common/components/LayoutSelector';
import MappingFieldSelector from '../../../../../../common/components/MappingFieldSelector';
import {useId} from '../../../../../../common/hooks/useId';
import {CommonStyles} from './CommonStyles';
import ContainerDisplayOptions from './ContainerDisplayOptions';
import FormMappingOptions from './FormMappingOptions';

export function FormGeneralPanel({item}) {
	const dispatch = useDispatch();

	const onValueSelect = useCallback(
		(nextConfig) =>
			dispatch(
				updateFormItemConfig({
					itemConfig: nextConfig,
					itemId: item.itemId,
				})
			),
		[dispatch, item.itemId]
	);

	if (Liferay.FeatureFlags['LPS-169923']) {
		if (formIsUnavailable(item)) {
			return (
				<ClayAlert
					displayType="warning"
					title={`${Liferay.Language.get('warning')}:`}
				>
					{Liferay.Language.get(
						'this-content-is-currently-unavailable-or-has-been-deleted.-users-cannot-see-this-fragment'
					)}
				</ClayAlert>
			);
		}
		else if (formIsRestricted(item)) {
			return (
				<ClayAlert displayType="secondary">
					{Liferay.Language.get(
						'this-content-cannot-be-displayed-due-to-permission-restrictions'
					)}
				</ClayAlert>
			);
		}
	}

	return (
		<>
			<FormOptions item={item} onValueSelect={onValueSelect} />

			<CommonStyles
				commonStylesValues={item.config.styles || {}}
				item={item}
				role={COMMON_STYLES_ROLES.general}
			/>
		</>
	);
}

function FormOptions({item, onValueSelect}) {
	return (
		<div className="mb-3">
			<Collapse
				label={Liferay.Language.get('form-container-options')}
				open
			>
				<FormMappingOptions item={item} onValueSelect={onValueSelect} />

				{formIsMapped(item) && (
					<>
						<SuccessInteractionOptions
							item={item}
							onValueSelect={onValueSelect}
						/>

						<ContainerDisplayOptions item={item} />
					</>
				)}
			</Collapse>
		</div>
	);
}

const EMBEDDED_OPTION = 'embedded';
const LAYOUT_OPTION = 'fromLayout';
const URL_OPTION = 'url';
const DISPLAY_PAGE_OPTION = 'displayPage';
const STAY_OPTION = 'none';

const SUCCESS_MESSAGE_OPTIONS = [
	...(Liferay.FeatureFlags['LPS-183498']
		? [
				{
					label: Liferay.Language.get('stay-in-page'),
					value: STAY_OPTION,
				},
		  ]
		: []),
	{
		label: Liferay.Language.get('show-embedded-message'),
		value: EMBEDDED_OPTION,
	},
	{
		label: Liferay.Language.get('go-to-page'),
		value: LAYOUT_OPTION,
	},
	{
		label: Liferay.Language.get('go-to-external-url'),
		value: URL_OPTION,
	},
	...(Liferay.FeatureFlags['LPS-183498']
		? [
				{
					label: Liferay.Language.get('go-to-entry-display-page'),
					value: DISPLAY_PAGE_OPTION,
				},
		  ]
		: []),
];

function SuccessInteractionOptions({item, onValueSelect}) {
	const {successMessage: interactionConfig = {}} = item.config;

	const languageId = useSelector(selectLanguageId);
	const dispatch = useDispatch();

	const helpTextId = useId();

	const [selectedSource, setSelectedSource] = useState(
		getSelectedOption(interactionConfig)
	);
	const [successMessage, setSuccessMessage] = useControlledState(
		getEditableLocalizedValue(
			interactionConfig.message,
			languageId,
			Liferay.Language.get(
				'thank-you.-your-information-was-successfully-received'
			)
		)
	);

	useEffect(() => {
		if (Object.keys(interactionConfig).length) {
			const nextSelectedSource = getSelectedOption(interactionConfig);

			setSelectedSource(nextSelectedSource);
		}
	}, [interactionConfig]);

	const [url, setUrl] = useControlledState(
		getEditableLocalizedValue(interactionConfig.url, languageId)
	);
	const [showMessagePreview, setShowMessagePreview] = useControlledState(
		Boolean(item.config.showMessagePreview)
	);

	const urlId = useId();
	const successTextId = useId();

	useEffect(() => {
		return () => {
			dispatch(
				updateItemLocalConfig({
					disableUndo: true,
					itemConfig: {
						showMessagePreview: false,
					},
					itemId: item.itemId,
				})
			);
		};
	}, [item.itemId, dispatch]);

	return (
		<>
			<SelectField
				field={{
					label: Liferay.Language.get('success-interaction'),
					name: 'source',
					typeOptions: {
						validValues: SUCCESS_MESSAGE_OPTIONS,
					},
				}}
				onValueSelect={(_name, type) => {
					setSelectedSource(type);

					onValueSelect({
						successMessage: {},
					});
				}}
				value={selectedSource}
			/>

			{selectedSource === LAYOUT_OPTION && (
				<LayoutSelector
					mappedLayout={interactionConfig?.layout}
					onLayoutSelect={(layout) =>
						onValueSelect({
							successMessage: {layout},
						})
					}
				/>
			)}

			{selectedSource === EMBEDDED_OPTION && (
				<>
					<ClayForm.Group small>
						<label htmlFor={successTextId}>
							{Liferay.Language.get('embedded-message')}
						</label>

						<ClayInput.Group small>
							<ClayInput.GroupItem>
								<ClayInput
									id={successTextId}
									onBlur={() =>
										onValueSelect({
											successMessage: {
												message: {
													...(interactionConfig?.message ||
														{}),
													[languageId]: successMessage,
												},
											},
										})
									}
									onChange={(event) =>
										setSuccessMessage(event.target.value)
									}
									onKeyDown={(event) => {
										if (event.key === 'Enter') {
											onValueSelect({
												successMessage: {
													message: {
														...(interactionConfig?.message ||
															{}),
														[languageId]: successMessage,
													},
												},
											});
										}
									}}
									type="text"
									value={successMessage || ''}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem shrink>
								<CurrentLanguageFlag />
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayForm.Group>

					<ClayToggle
						label={Liferay.Language.get('preview-embedded-message')}
						onToggle={(checked) => {
							setShowMessagePreview(checked);

							dispatch(
								updateItemLocalConfig({
									disableUndo: true,
									itemConfig: {
										showMessagePreview: checked,
									},
									itemId: item.itemId,
								})
							);
						}}
						toggled={showMessagePreview}
					/>
				</>
			)}

			{selectedSource === URL_OPTION && (
				<ClayForm.Group small>
					<label htmlFor={urlId}>
						{Liferay.Language.get('external-url')}
					</label>

					<ClayInput.Group small>
						<ClayInput.GroupItem>
							<ClayInput
								id={urlId}
								onBlur={() =>
									onValueSelect({
										successMessage: {
											url: {
												...(interactionConfig?.url ||
													{}),
												[languageId]: url,
											},
										},
									})
								}
								onChange={(event) => setUrl(event.target.value)}
								placeholder="https://url.com"
								type="text"
								value={url || ''}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem shrink>
							<CurrentLanguageFlag />
						</ClayInput.GroupItem>
					</ClayInput.Group>

					<p
						className="m-0 mt-1 small text-secondary"
						id={helpTextId}
					>
						{Liferay.Language.get(
							'urls-must-have-a-valid-protocol'
						)}
					</p>
				</ClayForm.Group>
			)}

			{selectedSource === DISPLAY_PAGE_OPTION && (
				<DisplayPageSelector
					item={item}
					onValueSelect={onValueSelect}
					selectedSource={selectedSource}
					selectedValue={interactionConfig?.displayPage}
				/>
			)}
		</>
	);
}

function DisplayPageSelector({
	item,
	onValueSelect,
	selectedSource,
	selectedValue,
}) {
	const dispatch = useDispatch();

	const mappingFields = useSelector((state) => state.mappingFields);

	const [displayPageFields, setDisplayPageFields] = useState(null);

	useEffect(() => {
		if (selectedSource !== DISPLAY_PAGE_OPTION) {
			return;
		}

		const {classNameId, classTypeId} = item.config;

		const key = getMappingFieldsKey({classNameId, classTypeId});

		const fields = mappingFields[key];

		if (fields) {
			setDisplayPageFields(filterFields(fields));
		}
		else {
			InfoItemService.getAvailableStructureMappingFields({
				classNameId,
				classTypeId,
				onNetworkStatus: dispatch,
			}).then((newFields) => {
				dispatch(addMappingFields({fields: newFields, key}));
			});
		}
	}, [dispatch, item, mappingFields, selectedSource]);

	return (
		<MappingFieldSelector
			className="mb-3"
			defaultLabel={`-- ${Liferay.Language.get('none')} --`}
			fields={displayPageFields}
			label={Liferay.Language.get('display-page')}
			onValueSelect={(event) =>
				onValueSelect({
					successMessage: {
						displayPage:
							event.target.value === 'unmapped'
								? null
								: event.target.value,
					},
				})
			}
			value={selectedValue}
		/>
	);
}

function filterFields(fields) {
	return fields.reduce((acc, fieldSet) => {
		const newFields = fieldSet.fields.filter(
			(field) => field.type === 'display-page'
		);

		if (newFields.length) {
			return [
				...acc,
				{
					...fieldSet,
					fields: newFields,
				},
			];
		}

		return acc;
	}, []);
}

function getSelectedOption(interactionConfig) {
	if (interactionConfig.stay) {
		return STAY_OPTION;
	}

	if (interactionConfig.url) {
		return URL_OPTION;
	}

	if (interactionConfig.message) {
		return EMBEDDED_OPTION;
	}

	if (interactionConfig.layout?.layoutUuid) {
		return LAYOUT_OPTION;
	}

	if (interactionConfig.displayPage) {
		return DISPLAY_PAGE_OPTION;
	}

	return EMBEDDED_OPTION;
}
