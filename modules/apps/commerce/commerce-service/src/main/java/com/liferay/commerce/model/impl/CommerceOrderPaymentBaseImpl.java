/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model.impl;

import com.liferay.commerce.model.CommerceOrderPayment;
import com.liferay.commerce.service.CommerceOrderPaymentLocalServiceUtil;

/**
 * The extended model base implementation for the CommerceOrderPayment service. Represents a row in the &quot;CommerceOrderPayment&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceOrderPaymentImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderPaymentImpl
 * @see CommerceOrderPayment
 * @generated
 */
public abstract class CommerceOrderPaymentBaseImpl
	extends CommerceOrderPaymentModelImpl implements CommerceOrderPayment {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce order payment model instance should use the <code>CommerceOrderPayment</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			CommerceOrderPaymentLocalServiceUtil.addCommerceOrderPayment(this);
		}
		else {
			CommerceOrderPaymentLocalServiceUtil.updateCommerceOrderPayment(
				this);
		}
	}

}