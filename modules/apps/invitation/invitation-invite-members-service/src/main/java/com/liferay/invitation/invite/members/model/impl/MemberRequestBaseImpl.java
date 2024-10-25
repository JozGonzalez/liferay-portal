/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.invitation.invite.members.model.impl;

import com.liferay.invitation.invite.members.model.MemberRequest;
import com.liferay.invitation.invite.members.service.MemberRequestLocalServiceUtil;

/**
 * The extended model base implementation for the MemberRequest service. Represents a row in the &quot;IM_MemberRequest&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MemberRequestImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MemberRequestImpl
 * @see MemberRequest
 * @generated
 */
public abstract class MemberRequestBaseImpl
	extends MemberRequestModelImpl implements MemberRequest {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a member request model instance should use the <code>MemberRequest</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			MemberRequestLocalServiceUtil.addMemberRequest(this);
		}
		else {
			MemberRequestLocalServiceUtil.updateMemberRequest(this);
		}
	}

}