/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.model.impl;

import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarResourceLocalServiceUtil;

/**
 * The extended model base implementation for the CalendarResource service. Represents a row in the &quot;CalendarResource&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CalendarResourceImpl}.
 * </p>
 *
 * @author Eduardo Lundgren
 * @see CalendarResourceImpl
 * @see CalendarResource
 * @generated
 */
public abstract class CalendarResourceBaseImpl
	extends CalendarResourceModelImpl implements CalendarResource {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a calendar resource model instance should use the <code>CalendarResource</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			CalendarResourceLocalServiceUtil.addCalendarResource(this);
		}
		else {
			CalendarResourceLocalServiceUtil.updateCalendarResource(this);
		}
	}

}