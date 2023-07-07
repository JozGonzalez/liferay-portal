/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.batch.engine.internal.bundle;

import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.batch.engine.unit.BatchEngineUnitReader;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * @author Raymond Augé
 */
@Component(service = {})
public class BatchEngineBundleTracker {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_bundleTracker = new BundleTracker<>(
			bundleContext, Bundle.ACTIVE,
			new BatchEngineBundleTrackerCustomizer());

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
		_serviceRegistrationsMap.forEach(
			(bundle, serviceRegistration) -> serviceRegistration.unregister());
	}

	private boolean _isAlreadyProcessed(Bundle bundle) {
		String lastModifiedString = String.valueOf(bundle.getLastModified());

		File batchMarkerFile = bundle.getDataFile(
			".liferay-client-extension-batch");

		try {
			if ((batchMarkerFile != null) && batchMarkerFile.exists() &&
				Objects.equals(
					FileUtil.read(batchMarkerFile), lastModifiedString)) {

				return true;
			}

			if (!batchMarkerFile.exists()) {
				batchMarkerFile.createNewFile();
			}

			FileUtil.write(batchMarkerFile, lastModifiedString, true);
		}
		catch (IOException ioException) {
			ReflectionUtil.throwException(ioException);
		}

		return false;
	}

	@Reference
	private BatchEngineUnitProcessor _batchEngineUnitProcessor;

	@Reference
	private BatchEngineUnitReader _batchEngineUnitReader;

	private BundleContext _bundleContext;
	private BundleTracker<Void> _bundleTracker;
	private final Map
		<Bundle, ServiceRegistration<PortalInstanceLifecycleListener>>
			_serviceRegistrationsMap = new HashMap<>();

	private class BatchEngineBundleTrackerCustomizer
		implements BundleTrackerCustomizer<Void> {

		@Override
		public Void addingBundle(Bundle bundle, BundleEvent bundleEvent) {
			Dictionary<String, String> headers = bundle.getHeaders(
				StringPool.BLANK);

			if ((headers.get("Liferay-Client-Extension-Batch") != null) &&
				!_isAlreadyProcessed(bundle)) {

				Iterable<BatchEngineUnit> batchEngineUnits =
					_batchEngineUnitReader.getBatchEngineUnits(bundle);

				List<BatchEngineUnit> multiCompanyBatchEngineUnits =
					new ArrayList<>();
				List<BatchEngineUnit> singleCompanyBatchEngineUnits =
					new ArrayList<>();

				for (BatchEngineUnit batchEngineUnit : batchEngineUnits) {
					try {
						BatchEngineUnitConfiguration
							batchEngineUnitConfiguration =
								batchEngineUnit.
									getBatchEngineUnitConfiguration();

						if (batchEngineUnitConfiguration.isMultiCompany()) {
							multiCompanyBatchEngineUnits.add(batchEngineUnit);
						}
						else {
							singleCompanyBatchEngineUnits.add(batchEngineUnit);
						}
					}
					catch (Exception exception) {
						throw new RuntimeException(exception);
					}
				}

				if (!multiCompanyBatchEngineUnits.isEmpty()) {
					_serviceRegistrationsMap.put(
						bundle,
						_bundleContext.registerService(
							PortalInstanceLifecycleListener.class,
							new BasePortalInstanceLifecycleListener() {

								@Override
								public void portalInstanceRegistered(
									Company company) {

									_batchEngineUnitProcessor.
										processBatchEngineUnits(
											multiCompanyBatchEngineUnits);
								}

							},
							null));
				}

				_batchEngineUnitProcessor.processBatchEngineUnits(
					singleCompanyBatchEngineUnits);
			}

			return null;
		}

		@Override
		public void modifiedBundle(
			Bundle bundle, BundleEvent bundleEvent, Void unused) {
		}

		@Override
		public void removedBundle(
			Bundle bundle, BundleEvent bundleEvent, Void unused) {

			ServiceRegistration<PortalInstanceLifecycleListener>
				serviceRegistration = _serviceRegistrationsMap.remove(bundle);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}

	}

}