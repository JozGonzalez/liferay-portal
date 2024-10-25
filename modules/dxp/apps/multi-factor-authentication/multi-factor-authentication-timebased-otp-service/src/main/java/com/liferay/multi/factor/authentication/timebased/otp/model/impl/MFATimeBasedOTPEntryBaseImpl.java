/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.timebased.otp.model.impl;

import com.liferay.multi.factor.authentication.timebased.otp.model.MFATimeBasedOTPEntry;
import com.liferay.multi.factor.authentication.timebased.otp.service.MFATimeBasedOTPEntryLocalServiceUtil;

/**
 * The extended model base implementation for the MFATimeBasedOTPEntry service. Represents a row in the &quot;MFATimeBasedOTPEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MFATimeBasedOTPEntryImpl}.
 * </p>
 *
 * @author Arthur Chan
 * @see MFATimeBasedOTPEntryImpl
 * @see MFATimeBasedOTPEntry
 * @generated
 */
public abstract class MFATimeBasedOTPEntryBaseImpl
	extends MFATimeBasedOTPEntryModelImpl implements MFATimeBasedOTPEntry {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a mfa time based otp entry model instance should use the <code>MFATimeBasedOTPEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			MFATimeBasedOTPEntryLocalServiceUtil.addMFATimeBasedOTPEntry(this);
		}
		else {
			MFATimeBasedOTPEntryLocalServiceUtil.updateMFATimeBasedOTPEntry(
				this);
		}
	}

}