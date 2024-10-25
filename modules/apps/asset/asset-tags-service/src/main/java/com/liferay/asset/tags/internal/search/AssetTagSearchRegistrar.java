/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.internal.search;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 * @author Lucas Marques
 */
@Component(service = {})
public class AssetTagSearchRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = modelSearchRegistrarHelper.register(
			AssetTag.class, bundleContext,
			modelSearchDefinition -> {
				modelSearchDefinition.setDefaultSelectedFieldNames(
					Field.COMPANY_ID, Field.ENTRY_CLASS_PK,
					Field.ENTRY_CLASS_NAME, Field.GROUP_ID, Field.UID);
				modelSearchDefinition.setModelIndexWriteContributor(
					modelIndexWriterContributor);
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Reference(
		target = "(indexer.class.name=com.liferay.asset.kernel.model.AssetTag)"
	)
	protected ModelIndexerWriterContributor<AssetTag>
		modelIndexWriterContributor;

	@Reference
	protected ModelSearchRegistrarHelper modelSearchRegistrarHelper;

	private ServiceRegistration<?> _serviceRegistration;

}