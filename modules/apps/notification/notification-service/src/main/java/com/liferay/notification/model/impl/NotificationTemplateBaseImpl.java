/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.model.impl;

import com.liferay.notification.model.NotificationTemplate;
import com.liferay.notification.service.NotificationTemplateLocalServiceUtil;

/**
 * The extended model base implementation for the NotificationTemplate service. Represents a row in the &quot;NotificationTemplate&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link NotificationTemplateImpl}.
 * </p>
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateImpl
 * @see NotificationTemplate
 * @generated
 */
public abstract class NotificationTemplateBaseImpl
	extends NotificationTemplateModelImpl implements NotificationTemplate {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a notification template model instance should use the <code>NotificationTemplate</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			NotificationTemplateLocalServiceUtil.addNotificationTemplate(this);
		}
		else {
			NotificationTemplateLocalServiceUtil.updateNotificationTemplate(
				this);
		}
	}

}