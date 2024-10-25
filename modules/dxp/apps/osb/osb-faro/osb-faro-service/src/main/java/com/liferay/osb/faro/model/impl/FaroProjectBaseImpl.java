/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.model.impl;

import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroProjectLocalServiceUtil;

/**
 * The extended model base implementation for the FaroProject service. Represents a row in the &quot;OSBFaro_FaroProject&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link FaroProjectImpl}.
 * </p>
 *
 * @author Matthew Kong
 * @see FaroProjectImpl
 * @see FaroProject
 * @generated
 */
public abstract class FaroProjectBaseImpl
	extends FaroProjectModelImpl implements FaroProject {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a faro project model instance should use the <code>FaroProject</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			FaroProjectLocalServiceUtil.addFaroProject(this);
		}
		else {
			FaroProjectLocalServiceUtil.updateFaroProject(this);
		}
	}

}