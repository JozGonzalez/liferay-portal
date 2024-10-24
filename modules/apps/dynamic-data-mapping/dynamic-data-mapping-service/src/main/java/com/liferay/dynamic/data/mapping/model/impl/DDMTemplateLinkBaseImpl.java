/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMTemplateLink;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalServiceUtil;

/**
 * The extended model base implementation for the DDMTemplateLink service. Represents a row in the &quot;DDMTemplateLink&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DDMTemplateLinkImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateLinkImpl
 * @see DDMTemplateLink
 * @generated
 */
public abstract class DDMTemplateLinkBaseImpl
	extends DDMTemplateLinkModelImpl implements DDMTemplateLink {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a ddm template link model instance should use the <code>DDMTemplateLink</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			DDMTemplateLinkLocalServiceUtil.addDDMTemplateLink(this);
		}
		else {
			DDMTemplateLinkLocalServiceUtil.updateDDMTemplateLink(this);
		}
	}

}