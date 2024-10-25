/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.portal.workflow.kaleo.model.KaleoCondition;
import com.liferay.portal.workflow.kaleo.service.KaleoConditionLocalServiceUtil;

/**
 * The extended model base implementation for the KaleoCondition service. Represents a row in the &quot;KaleoCondition&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link KaleoConditionImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoConditionImpl
 * @see KaleoCondition
 * @generated
 */
public abstract class KaleoConditionBaseImpl
	extends KaleoConditionModelImpl implements KaleoCondition {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kaleo condition model instance should use the <code>KaleoCondition</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			KaleoConditionLocalServiceUtil.addKaleoCondition(this);
		}
		else {
			KaleoConditionLocalServiceUtil.updateKaleoCondition(this);
		}
	}

}