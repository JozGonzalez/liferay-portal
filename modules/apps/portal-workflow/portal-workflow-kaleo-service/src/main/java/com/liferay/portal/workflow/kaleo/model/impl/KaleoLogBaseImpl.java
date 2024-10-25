/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalServiceUtil;

/**
 * The extended model base implementation for the KaleoLog service. Represents a row in the &quot;KaleoLog&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link KaleoLogImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoLogImpl
 * @see KaleoLog
 * @generated
 */
public abstract class KaleoLogBaseImpl
	extends KaleoLogModelImpl implements KaleoLog {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kaleo log model instance should use the <code>KaleoLog</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			KaleoLogLocalServiceUtil.addKaleoLog(this);
		}
		else {
			KaleoLogLocalServiceUtil.updateKaleoLog(this);
		}
	}

}