/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import MDFClaimActivityDTO from '../../../interfaces/dto/mdfClaimActivityDTO';
import MDFClaimActivity from '../../../interfaces/mdfClaimActivity';

export default function getDTOFromMDFClaimActivity(
	mdfClaimActivity: MDFClaimActivity,
	mdfClaimId: number,
	companyId: number
): MDFClaimActivityDTO {
	return {
		currency: mdfClaimActivity.currency,
		eventProgram: mdfClaimActivity.eventProgram?.documentId,
		listOfQualifiedLeads: mdfClaimActivity.listOfQualifiedLeads?.documentId,
		metrics: mdfClaimActivity.metrics,
		name: mdfClaimActivity.name,
		r_accToMDFClmActs_accountEntryId: companyId,
		r_actToMDFClmActs_c_activityId:
			mdfClaimActivity.r_actToMDFClmActs_c_activityId,
		r_mdfClmToMDFClmActs_c_mdfClaimId: mdfClaimId,
		selected: mdfClaimActivity.selected,
		telemarketingMetrics: mdfClaimActivity.telemarketingMetrics,
		telemarketingScript: mdfClaimActivity.telemarketingScript?.documentId,
		totalCost: mdfClaimActivity.totalCost,
		typeActivity: mdfClaimActivity.typeActivity,
		videoLink: mdfClaimActivity.videoLink,
	};
}
