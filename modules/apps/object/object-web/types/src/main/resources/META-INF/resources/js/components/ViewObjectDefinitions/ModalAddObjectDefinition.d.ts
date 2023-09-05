/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './ModalAddObjectDefinition.scss';
interface ModalAddObjectDefinitionProps {
	apiURL: string;
	handleOnClose: () => void;
	objectFolderExternalReferenceCode?: string;
	onAfterSubmit?: (value: ObjectDefinition) => void;
	reload?: boolean;
	setNewNode?: (value: ObjectDefinition) => void;
	storages: LabelValueObject[];
}
export declare function ModalAddObjectDefinition({
	apiURL,
	handleOnClose,
	objectFolderExternalReferenceCode,
	onAfterSubmit,
	reload,
	storages,
}: ModalAddObjectDefinitionProps): JSX.Element;
export {};
