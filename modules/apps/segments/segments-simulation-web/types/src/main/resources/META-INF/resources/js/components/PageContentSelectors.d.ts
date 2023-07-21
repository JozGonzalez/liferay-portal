/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import SegmentEntry from '../../types/SegmentEntry';
import SegmentExperience from '../../types/SegmentExperience';
interface Props {
	deactivateSimulationURL: string;
	namespace: string;
	portletNamespace: string;
	segmentationEnabled: boolean;
	segmentsCompanyConfigurationURL: string;
	segmentsEntries: SegmentEntry[];
	segmentsExperiences: SegmentExperience[];
	selectSegmentsEntryURL: string;
	selectSegmentsExperienceURL: string;
	simulateSegmentsEntriesURL: string;
}
declare function PageContentSelectors({
	deactivateSimulationURL,
	namespace,
	portletNamespace,
	segmentationEnabled,
	segmentsCompanyConfigurationURL,
	segmentsEntries,
	segmentsExperiences,
	selectSegmentsEntryURL,
	selectSegmentsExperienceURL,
	simulateSegmentsEntriesURL,
}: Props): JSX.Element;
export default PageContentSelectors;
