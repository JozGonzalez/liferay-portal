/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.executor;

import com.liferay.petra.concurrent.ThreadPoolHandler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class PortalExecutorConfigTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new CodeCoverageAssertor() {

				@Override
				public void appendAssertClasses(List<Class<?>> assertClasses) {
					assertClasses.add(PortalExecutorManager.class);
				}

			},
			LiferayUnitTestRule.INSTANCE);

	@Test
	public void testPortalExecutorConfig() {
		try {
			new PortalExecutorConfig(null, 0, 0, 0, null, 0, null, null, null);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"To ensure FIFO, core pool size must be 1 or greater",
				illegalArgumentException.getMessage());
		}

		String name = "name";

		int corePoolSize = 1;

		int maxPoolSize = 2;

		long keepAliveTime = 3;

		TimeUnit timeUnit = TimeUnit.HOURS;

		int maxQueueSize = 4;

		ThreadFactory threadFactory = runnable -> null;

		RejectedExecutionHandler rejectedExecutionHandler =
			new ThreadPoolExecutor.AbortPolicy();

		ThreadPoolHandler threadPoolHandler = new ThreadPoolHandler() {

			@Override
			public void afterExecute(Runnable runnable, Throwable throwable) {
			}

			@Override
			public void beforeExecute(Thread thread, Runnable runnable) {
			}

			@Override
			public void terminated() {
			}

		};

		PortalExecutorConfig portalExecutorConfig = new PortalExecutorConfig(
			name, corePoolSize, maxPoolSize, keepAliveTime, timeUnit,
			maxQueueSize, threadFactory, rejectedExecutionHandler,
			threadPoolHandler);

		Assert.assertSame(name, portalExecutorConfig.getName());
		Assert.assertSame(corePoolSize, portalExecutorConfig.getCorePoolSize());
		Assert.assertSame(maxPoolSize, portalExecutorConfig.getMaxPoolSize());
		Assert.assertSame(
			keepAliveTime, portalExecutorConfig.getKeepAliveTime());
		Assert.assertSame(timeUnit, portalExecutorConfig.getTimeUnit());
		Assert.assertSame(maxQueueSize, portalExecutorConfig.getMaxQueueSize());
		Assert.assertSame(
			threadFactory, portalExecutorConfig.getThreadFactory());
		Assert.assertSame(
			rejectedExecutionHandler,
			portalExecutorConfig.getRejectedExecutionHandler());
		Assert.assertSame(
			threadPoolHandler, portalExecutorConfig.getThreadPoolHandler());
	}

}