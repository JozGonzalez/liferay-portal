/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.model.impl;

import com.liferay.client.extension.model.ClientExtensionEntryRel;
import com.liferay.client.extension.service.ClientExtensionEntryRelLocalServiceUtil;

/**
 * The extended model base implementation for the ClientExtensionEntryRel service. Represents a row in the &quot;ClientExtensionEntryRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ClientExtensionEntryRelImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ClientExtensionEntryRelImpl
 * @see ClientExtensionEntryRel
 * @generated
 */
public abstract class ClientExtensionEntryRelBaseImpl
	extends ClientExtensionEntryRelModelImpl
	implements ClientExtensionEntryRel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a client extension entry rel model instance should use the <code>ClientExtensionEntryRel</code> interface instead.
	 */
	@Override
	public void persist() {
		if (this.isNew()) {
			ClientExtensionEntryRelLocalServiceUtil.addClientExtensionEntryRel(
				this);
		}
		else {
			ClientExtensionEntryRelLocalServiceUtil.
				updateClientExtensionEntryRel(this);
		}
	}

}