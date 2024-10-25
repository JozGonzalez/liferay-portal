/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.chemistry.opencmis.client.api.Document;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCMISRepositoryDefiner extends BaseRepositoryDefiner {

	@Override
	public String getRepositoryTypeLabel(Locale locale) {
		ResourceBundleLoader resourceBundleLoader = getResourceBundleLoader();

		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		return ResourceBundleUtil.getString(
			resourceBundle, _MODEL_RESOURCE_NAME_PREFIX + getClassName());
	}

	@Override
	public void registerCapabilities(
		CapabilityRegistry<DocumentRepository> capabilityRegistry) {

		DocumentRepository documentRepository = capabilityRegistry.getTarget();

		PortalCapabilityLocator portalCapabilityLocator =
			getPortalCapabilityLocator();

		capabilityRegistry.addSupportedCapability(
			ProcessorCapability.class,
			new RefreshingProcessorCapability(
				portalCapabilityLocator.getProcessorCapability(
					documentRepository,
					ProcessorCapability.ResourceGenerationStrategy.REUSE)));
	}

	protected abstract PortalCapabilityLocator getPortalCapabilityLocator();

	protected ResourceBundleLoader getResourceBundleLoader() {
		return ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
	}

	private static final String _MODEL_RESOURCE_NAME_PREFIX = "model.resource.";

	private static class RefreshingProcessorCapability
		implements ProcessorCapability {

		public RefreshingProcessorCapability(
			ProcessorCapability processorCapability) {

			_processorCapability = processorCapability;
		}

		@Override
		public void cleanUp(FileEntry fileEntry) throws PortalException {
			_refresh(fileEntry);

			_processorCapability.cleanUp(fileEntry);
		}

		@Override
		public void cleanUp(FileVersion fileVersion) throws PortalException {
			_refresh(fileVersion);

			_processorCapability.cleanUp(fileVersion);
		}

		@Override
		public void copy(FileEntry fileEntry, FileVersion fileVersion)
			throws PortalException {

			_refresh(fileEntry);
			_refresh(fileVersion);

			_processorCapability.copy(fileEntry, fileVersion);
		}

		@Override
		public void generateNew(FileEntry fileEntry) throws PortalException {
			_refresh(fileEntry);

			_processorCapability.generateNew(fileEntry);
		}

		private void _refresh(FileEntry fileEntry) {
			Document document = (Document)fileEntry.getModel();

			document.refresh();
		}

		private void _refresh(FileVersion fileVersion) throws PortalException {
			Document document = (Document)fileVersion.getModel();

			document.refresh();

			_refresh(fileVersion.getFileEntry());
		}

		private final ProcessorCapability _processorCapability;

	}

}