/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.forms.model.impl;

import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessLocalServiceUtil;

/**
 * The extended model base implementation for the KaleoProcess service. Represents a row in the &quot;KaleoProcess&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link KaleoProcessImpl}.
 * </p>
 *
 * @author Marcellus Tavares
 * @see KaleoProcessImpl
 * @see KaleoProcess
 * @generated
 */
public abstract class KaleoProcessBaseImpl
	extends KaleoProcessModelImpl implements KaleoProcess {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a kaleo process model instance should use the <code>KaleoProcess</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			KaleoProcessLocalServiceUtil.addKaleoProcess(this);
		}
		else {
			KaleoProcessLocalServiceUtil.updateKaleoProcess(this);
		}
	}

}