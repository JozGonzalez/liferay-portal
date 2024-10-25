/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalServiceUtil;

/**
 * The extended model base implementation for the CPInstanceUnitOfMeasure service. Represents a row in the &quot;CPInstanceUOM&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CPInstanceUnitOfMeasureImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see CPInstanceUnitOfMeasureImpl
 * @see CPInstanceUnitOfMeasure
 * @generated
 */
public abstract class CPInstanceUnitOfMeasureBaseImpl
	extends CPInstanceUnitOfMeasureModelImpl
	implements CPInstanceUnitOfMeasure {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cp instance unit of measure model instance should use the <code>CPInstanceUnitOfMeasure</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			CPInstanceUnitOfMeasureLocalServiceUtil.addCPInstanceUnitOfMeasure(
				this);
		}
		else {
			CPInstanceUnitOfMeasureLocalServiceUtil.
				updateCPInstanceUnitOfMeasure(this);
		}
	}

}