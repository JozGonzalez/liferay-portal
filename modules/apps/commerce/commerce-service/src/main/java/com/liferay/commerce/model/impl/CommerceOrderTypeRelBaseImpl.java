/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model.impl;

import com.liferay.commerce.model.CommerceOrderTypeRel;
import com.liferay.commerce.service.CommerceOrderTypeRelLocalServiceUtil;

/**
 * The extended model base implementation for the CommerceOrderTypeRel service. Represents a row in the &quot;CommerceOrderTypeRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CommerceOrderTypeRelImpl}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderTypeRelImpl
 * @see CommerceOrderTypeRel
 * @generated
 */
public abstract class CommerceOrderTypeRelBaseImpl
	extends CommerceOrderTypeRelModelImpl implements CommerceOrderTypeRel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a commerce order type rel model instance should use the <code>CommerceOrderTypeRel</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			CommerceOrderTypeRelLocalServiceUtil.addCommerceOrderTypeRel(this);
		}
		else {
			CommerceOrderTypeRelLocalServiceUtil.updateCommerceOrderTypeRel(
				this);
		}
	}

}