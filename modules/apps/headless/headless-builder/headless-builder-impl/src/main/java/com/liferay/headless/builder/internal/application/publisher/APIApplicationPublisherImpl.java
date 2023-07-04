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

package com.liferay.headless.builder.internal.application.publisher;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.headless.builder.application.publisher.APIApplicationPublisher;
import com.liferay.headless.builder.internal.application.resource.HeadlessBuilderResourceImpl;
import com.liferay.headless.builder.internal.application.resource.OpenAPIResourceImpl;
import com.liferay.headless.builder.internal.jaxrs.context.provider.APIApplicationContextProvider;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.ws.rs.core.Application;

import org.apache.cxf.jaxrs.ext.ContextProvider;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.PrototypeServiceFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = APIApplicationPublisher.class)
public class APIApplicationPublisherImpl implements APIApplicationPublisher {

	@Override
	public void publish(APIApplication apiApplication) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-186757")) {
			throw new UnsupportedOperationException(
				"APIApplicationPublisher not available");
		}

		_headlessBuilderApplicationServiceRegistrationsMap.computeIfAbsent(
			apiApplication.getOSGiJaxRsName(),
			key -> new ArrayList<ServiceRegistration<?>>() {
				{
					add(_registerApplication(apiApplication));
					add(_registerContextProvider(apiApplication));
					add(
						_registerResource(
							apiApplication, HeadlessBuilderResourceImpl.class,
							() -> new HeadlessBuilderResourceImpl()));
					add(
						_registerResource(
							apiApplication, OpenAPIResourceImpl.class,
							() -> new OpenAPIResourceImpl(_openAPIResource)));
				}
			});
	}

	@Override
	public void unpublish(APIApplication apiApplication) {
		if (!FeatureFlagManagerUtil.isEnabled("LPS-186757")) {
			throw new UnsupportedOperationException(
				"APIApplicationPublisher not available");
		}

		List<ServiceRegistration<?>> serviceRegistrations =
			_headlessBuilderApplicationServiceRegistrationsMap.remove(
				apiApplication.getOSGiJaxRsName());

		if (serviceRegistrations != null) {
			_unregisterServiceRegistrations(serviceRegistrations);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		for (List<ServiceRegistration<?>> serviceRegistrations :
				_headlessBuilderApplicationServiceRegistrationsMap.values()) {

			_unregisterServiceRegistrations(serviceRegistrations);
		}

		_headlessBuilderApplicationServiceRegistrationsMap.clear();
	}

	private ServiceRegistration<Application> _registerApplication(
		APIApplication apiApplication) {

		return _bundleContext.registerService(
			Application.class, new Application(),
			HashMapDictionaryBuilder.<String, Object>put(
				"companyId", apiApplication.getCompanyId()
			).put(
				"liferay.filter.disabled", true
			).put(
				"liferay.headless.builder.application", true
			).put(
				"liferay.jackson", false
			).put(
				"osgi.jaxrs.application.base", apiApplication.getBaseURL()
			).put(
				"osgi.jaxrs.extension.select",
				"(osgi.jaxrs.name=Liferay.Vulcan)"
			).put(
				"osgi.jaxrs.name", apiApplication.getOSGiJaxRsName()
			).build());
	}

	private ServiceRegistration<?> _registerContextProvider(
		APIApplication apiApplication) {

		return _bundleContext.registerService(
			ContextProvider.class,
			new APIApplicationContextProvider(_apiApplicationProvider, _portal),
			HashMapDictionaryBuilder.<String, Object>put(
				"osgi.jaxrs.application.select",
				"(osgi.jaxrs.name=" + apiApplication.getOSGiJaxRsName() + ")"
			).put(
				"osgi.jaxrs.extension", "true"
			).put(
				"osgi.jaxrs.name",
				apiApplication.getOSGiJaxRsName() +
					"APIApplicationContextProvider"
			).build());
	}

	private <T> ServiceRegistration<T> _registerResource(
		APIApplication apiApplication, Class<T> resourceClass,
		Supplier<T> resourceSupplier) {

		return _bundleContext.registerService(
			resourceClass,
			new PrototypeServiceFactory<T>() {

				@Override
				public T getService(
					Bundle bundle, ServiceRegistration<T> serviceRegistration) {

					return resourceSupplier.get();
				}

				@Override
				public void ungetService(
					Bundle bundle, ServiceRegistration<T> serviceRegistration,
					T t) {
				}

			},
			HashMapDictionaryBuilder.<String, Object>put(
				"api.version", "v1.0"
			).put(
				"osgi.jaxrs.application.select",
				"(osgi.jaxrs.name=" + apiApplication.getOSGiJaxRsName() + ")"
			).put(
				"osgi.jaxrs.resource", "true"
			).build());
	}

	private void _unregisterServiceRegistrations(
		List<ServiceRegistration<?>> serviceRegistrations) {

		for (ServiceRegistration<?> serviceRegistration :
				serviceRegistrations) {

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}
	}

	private static BundleContext _bundleContext;

	@Reference
	private APIApplicationProvider _apiApplicationProvider;

	private final Map<String, List<ServiceRegistration<?>>>
		_headlessBuilderApplicationServiceRegistrationsMap = new HashMap<>();

	@Reference
	private OpenAPIResource _openAPIResource;

	@Reference
	private Portal _portal;

}