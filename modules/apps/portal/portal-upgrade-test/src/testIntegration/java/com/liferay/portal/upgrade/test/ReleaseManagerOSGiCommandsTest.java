/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class ReleaseManagerOSGiCommandsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testListInitialUpgradeStep() {
		Bundle bundle = FrameworkUtil.getBundle(
			ReleaseManagerOSGiCommandsTest.class);

		String bundleSymbolicName = bundle.getSymbolicName();

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<UpgradeStep> serviceRegistration =
			bundleContext.registerService(
				UpgradeStep.class, new DummyUpgradeStep(),
				HashMapDictionaryBuilder.<String, Object>put(
					"upgrade.bundle.symbolic.name", bundleSymbolicName
				).put(
					"upgrade.from.schema.version", "0.0.0"
				).put(
					"upgrade.initial.database.creation", "true"
				).put(
					"upgrade.to.schema.version", "1.0.0"
				).build());

		try {
			Set<String> bundleSymbolicNames = ReflectionTestUtil.invoke(
				_releaseManagerImpl, "getBundleSymbolicNames", null);

			Assert.assertTrue(bundleSymbolicNames.contains(bundleSymbolicName));

			String listInfo = ReflectionTestUtil.invoke(
				_releaseManagerOSGiCommands, "list",
				new Class<?>[] {String.class}, bundleSymbolicName);

			Assert.assertFalse(Validator.isBlank(listInfo));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Inject(
		filter = "component.name=com.liferay.portal.upgrade.internal.release.ReleaseManagerImpl",
		type = Inject.NoType.class
	)
	private Object _releaseManagerImpl;

	@Inject(
		filter = "component.name=com.liferay.portal.upgrade.internal.release.osgi.commands.ReleaseManagerOSGiCommands",
		type = Inject.NoType.class
	)
	private Object _releaseManagerOSGiCommands;

}