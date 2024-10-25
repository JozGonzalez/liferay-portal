/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ERCCompanyEntry service. Represents a row in the &quot;ERCCompanyEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ERCCompanyEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntryImpl"
)
@ProviderType
public interface ERCCompanyEntry extends ERCCompanyEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.model.impl.ERCCompanyEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ERCCompanyEntry, Long>
		ERC_COMPANY_ENTRY_ID_ACCESSOR = new Accessor<ERCCompanyEntry, Long>() {

			@Override
			public Long get(ERCCompanyEntry ercCompanyEntry) {
				return ercCompanyEntry.getErcCompanyEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ERCCompanyEntry> getTypeClass() {
				return ERCCompanyEntry.class;
			}

		};

}