/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {ERRORS} from './errors';
import {stringToURLParameterFormat} from './string';

interface HTTPMethod {
	href: string;
	method: string;
}

interface Actions {
	delete: HTTPMethod;
	get: HTTPMethod;
	permissions: HTTPMethod;
	update: HTTPMethod;
}

interface ErrorDetails extends Error {
	detail?: string;
}

type NotificationTemplateType = 'email' | 'userNotification';

type RecipientType = 'role' | 'term' | 'user';

type Recipient = {
	bcc: string;
	cc: string;
	from: string;
	fromName: LocalizedValue<string>;
	to: LocalizedValue<string>;
};

export interface NotificationTemplate {
	attachmentObjectFieldIds: string[] | number[];
	bcc: string;
	body: LocalizedValue<string>;
	cc: string;
	description: string;
	editorType: 'freemarker' | 'richText';
	externalReferenceCode: string;
	from: string;
	fromName: LocalizedValue<string>;
	id: number;
	name: string;
	objectDefinitionExternalReferenceCode: string;
	objectDefinitionId: number | null;
	recipientType: RecipientType;
	recipients: Recipient[];
	subject: LocalizedValue<string>;
	to: LocalizedValue<string>;
	type: NotificationTemplateType;
}

interface ObjectFolderItem {
	linkedObjectDefinition: boolean;
	objectDefinitionExternalReferenceCode: string;
	positionX: number;
	positionY: number;
}

interface ObjectFolder {
	actions: Actions;
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	label: LocalizedValue<string>;
	name: string;
	objectFolderItems: ObjectFolderItem[];
}

type ObjectRelationshipType = 'manyToMany' | 'oneToMany' | 'oneToOne';

interface ObjectRelationship {
	deletionType: string;
	edge: boolean;
	id: number;
	label: LocalizedValue<string>;
	name: string;
	objectDefinitionExternalReferenceCode1: string;
	objectDefinitionExternalReferenceCode2: string;
	objectDefinitionId1: number;
	objectDefinitionId2: number;
	readonly objectDefinitionName2: string;
	objectRelationshipId: number;
	parameterObjectFieldId?: number;
	reverse: boolean;
	type: ObjectRelationshipType;
}

interface ListTypeEntry {
	externalReferenceCode: string;
	listTypeDefinitionId: number;
	key: string;
	name: string;
	name_i18n: LocalizedValue<string>;
}

interface ListTypeDefinition {
	actions: Actions;
	externalReferenceCode: string;
	id: number;
	key: string;
	listTypeEntries: ListTypeEntry[];
	name: string;
	name_i18n: LocalizedValue<string>;
	system: boolean;
}

interface saveProps {
	item: unknown;
	method?: 'PATCH' | 'POST' | 'PUT';
	returnValue?: boolean;
	url: string;
}

const headers = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
});

async function deleteItem(url: string) {
	const response = await fetch(url, {headers, method: 'DELETE'});

	if (response.status === 401) {
		window.location.reload();
	}
	else if (!response.ok) {
		const {
			title,
		}: {
			title?: string;
		} = await response.json();

		const errorMessage = title || Liferay.Language.get('an-error-occurred');

		throw new Error(errorMessage);
	}
}

export async function postListTypeEntry({
	key,
	listTypeDefinitionId,
	name_i18n,
}: Partial<ListTypeEntry>) {
	return await save({
		item: {key, name_i18n},
		method: 'POST',
		url: `/o/headless-admin-list-type/v1.0/list-type-definitions/${listTypeDefinitionId}/list-type-entries`,
	});
}

export function deleteObjectDefinition(objectDefinitionId: number) {
	return deleteItem(`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}`);
}

export function deleteObjectField(objectFieldId: number) {
	return deleteItem(`/o/object-admin/v1.0/object-fields/${objectFieldId}`);
}

export function deleteObjectFolder(objectFolderId: number) {
	return deleteItem(`/o/object-admin/v1.0/object-folders/${objectFolderId}`);
}

export function deleteObjectRelationship(objectRelationshipId: number) {
	return deleteItem(`/o/object-admin/v1.0/object-relationships/${objectRelationshipId}`);
}

export async function fetchJSON<T>(input: RequestInfo, init?: RequestInit) {
	const result = await fetch(input, {headers, method: 'GET', ...init});

	return (await result.json()) as T;
}

export async function getAllObjectDefinitions() {
	return await getList<ObjectDefinition>(
		'/o/object-admin/v1.0/object-definitions?page=-1'
	);
}

export async function getAllObjectFolders() {
	return await getList<ObjectFolder>(
		'/o/object-admin/v1.0/object-folders?pageSize=-1'
	);
}

export async function getList<T>(url: string) {
	const {items} = await fetchJSON<{items: T[]}>(url);

	return items;
}

export async function getNotificationTemplateByExternalReferenceCode(
	externalReferenceCode: string
) {
	return await fetchJSON<NotificationTemplate>(
		`/o/notification/v1.0/notification-templates/by-external-reference-code/${externalReferenceCode}`
	);
}

export async function getNotificationTemplateById(
	notificationTemplateId: number
) {
	return await fetchJSON<NotificationTemplate>(
		`/o/notification/v1.0/notification-templates/${notificationTemplateId}`
	);
}

export async function getNotificationTemplates() {
	return await getList<NotificationTemplate>(
		'/o/notification/v1.0/notification-templates?pageSize=-1'
	);
}

export async function getObjectDefinitionByExternalReferenceCode(
	externalReferenceCode: string
) {
	return await fetchJSON<ObjectDefinition>(
		`/o/object-admin/v1.0/object-definitions/by-external-reference-code/${externalReferenceCode}`
	);
}

export async function getObjectDefinitionById(objectDefinitionId: number) {
	return await fetchJSON<ObjectDefinition>(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}`
	);
}

export async function getObjectDefinitions(parameters?: string) {
	if (!parameters) {
		return await getList<ObjectDefinition>(
			'/o/object-admin/v1.0/object-definitions?pageSize=-1'
		);
	}

	return await getList<ObjectDefinition>(
		`/o/object-admin/v1.0/object-definitions?pageSize=-1&${stringToURLParameterFormat(
			parameters
		)}`
	);
}

export async function getObjectField(objectFieldId: number) {
	return await fetchJSON<ObjectField>(
		`/o/object-admin/v1.0/object-fields/${objectFieldId}`
	);
}

export async function getObjectFieldsByExternalReferenceCode(
	externalReferenceCode: string
) {
	return await getList<ObjectField>(
		`/o/object-admin/v1.0/object-definitions/by-external-reference-code/${externalReferenceCode}/object-fields?pageSize=-1`
	);
}

export async function getObjectDefinitionObjectFields(objectDefinitionId: number) {
	return await getList<ObjectField>(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/object-fields?pageSize=-1`
	);
}

export async function getObjectFolderByExternalReferenceCode(
	objectFolderExternalReferenceCode: string
) {
	const objectFolderResponse = await fetch(
		`/o/object-admin/v1.0/object-folders/by-external-reference-code/${objectFolderExternalReferenceCode}`,
		{method: 'GET'}
	);

	const objectFolder = (await objectFolderResponse.json()) as ObjectFolder;

	return objectFolder;
}

export async function getObjectRelationshipsByExternalReferenceCode(
	externalReferenceCode: string
) {
	return await getList<ObjectRelationship>(
		`/o/object-admin/v1.0/object-definitions/by-external-reference-code/${externalReferenceCode}/object-relationships`
	);
}

export async function getObjectRelationshipsById(objectDefinitionId: number) {
	return await getList<ObjectRelationship>(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/object-relationships`
	);
}

export async function getObjectValidationRuleById<T>(
	objectValidationRuleId: number
) {
	return await fetchJSON<T>(
		`/o/object-admin/v1.0/object-validation-rules/${objectValidationRuleId}`
	);
}

export async function getPickList(pickListId: number): Promise<ListTypeDefinition> {
	return await fetchJSON<ListTypeDefinition>(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${pickListId}`
	);
}

export async function getPickListItems(pickListId: number) {
	return await getList<ListTypeEntry>(
		`/o/headless-admin-list-type/v1.0/list-type-definitions/${pickListId}/list-type-entries?pageSize=-1`
	);
}

export async function getPickLists() {
	return await getList<ListTypeDefinition>(
		'/o/headless-admin-list-type/v1.0/list-type-definitions?pageSize=-1'
	);
}

export async function getRelationship<T>(objectRelationshipId: number) {
	return fetchJSON<T>(
		`/o/object-admin/v1.0/object-relationships/${objectRelationshipId}`
	);
}

export async function save({
	item,
	method = 'PUT',
	returnValue = false,
	url,
}: saveProps) {
	const isFormData = item instanceof FormData;

	const response = await fetch(url, {
		body: isFormData ? item : JSON.stringify(item),
		...(!isFormData && {headers}),
		method,
	});

	if (response.status === 401) {
		window.location.reload();
	}
	else if (!response.ok) {
		const {
			detail,
			title,
			type,
		}: {
			detail?: string;
			title?: string;
			type?: string;
		} = await response.json();

		const errorMessage =
			(type && ERRORS[type]) ??
			title ??
			Liferay.Language.get('an-error-occurred');

		const ErrorDetails = () => {
			return {
				detail,
				message: errorMessage,
				name: '',
			} as ErrorDetails;
		};
		throw ErrorDetails();
	}

	if (returnValue) {
		return response.json();
	}
}

export async function postObjectDefinition(
	objectDefinition: Partial<ObjectDefinition>
) {
	return await save({
		item: objectDefinition,
		method: 'POST',
		returnValue: true,
		url: `/o/object-admin/v1.0/object-definitions`,
	});
}

export async function publishObjectDefinitionById(objectDefinitionId: number) {
	return await fetch(
		`/o/object-admin/v1.0/object-definitions/${objectDefinitionId}/publish`,
		{
			method: 'POST',
		}
	);
}

export async function putObjectDefinitionByExternalReferenceCode(
	values: Partial<ObjectDefinition>
) {
	return await fetch(
		`/o/object-admin/v1.0/object-definitions/by-external-reference-code/${values.externalReferenceCode}`,
		{
			body: JSON.stringify(values),
			headers,
			method: 'PUT',
		}
	);
}

export async function putObjectFolderByExternalReferenceCode(
	objectFolder: Partial<ObjectFolder>
) {
	return await fetch(
		`/o/object-admin/v1.0/object-folders/by-external-reference-code/${objectFolder.externalReferenceCode}`,
		{
			body: JSON.stringify(objectFolder),
			headers,
			method: 'PUT',
		}
	);
}

export async function updatePickList({
	externalReferenceCode,
	id,
	listTypeEntries,
	name_i18n,
}: Partial<ListTypeDefinition>) {
	return await save({
		item: {externalReferenceCode, listTypeEntries, name_i18n},
		method: 'PUT',
		url: `/o/headless-admin-list-type/v1.0/list-type-definitions/${id}`,
	});
}

export async function updatePickListItem({
	externalReferenceCode,
	listTypeDefinitionId,
	name_i18n,
}: Partial<ListTypeEntry>) {
	return await save({
		item: {externalReferenceCode, name_i18n},
		method: 'PUT',
		url: `/o/headless-admin-list-type/v1.0/list-type-entries/${listTypeDefinitionId}`,
	});
}

export async function updateRelationship({
	objectRelationshipId,
	...others
}: ObjectRelationship) {
	return await save({
		item: others,
		url: `/o/object-admin/v1.0/object-relationships/${objectRelationshipId}`,
	});
}
