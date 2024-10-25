/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PartnershipLevels} from '../components/dashboard/enums/partnershipLevels';
import LiferayPicklist from './liferayPicklist';

export default interface PartnerLevelTypePicklist extends LiferayPicklist {
	key: PartnershipLevels;
}
