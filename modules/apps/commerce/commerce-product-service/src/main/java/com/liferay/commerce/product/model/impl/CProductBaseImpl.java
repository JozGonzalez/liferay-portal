/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CProductLocalServiceUtil;

/**
 * The extended model base implementation for the CProduct service. Represents a row in the &quot;CProduct&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CProductImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see CProductImpl
 * @see CProduct
 * @generated
 */
public abstract class CProductBaseImpl
	extends CProductModelImpl implements CProduct {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a c product model instance should use the <code>CProduct</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			CProductLocalServiceUtil.addCProduct(this);
		}
		else {
			CProductLocalServiceUtil.updateCProduct(this);
		}
	}

}