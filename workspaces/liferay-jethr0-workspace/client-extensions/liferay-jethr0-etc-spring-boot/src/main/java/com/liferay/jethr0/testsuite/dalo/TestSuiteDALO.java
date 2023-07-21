/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.testsuite.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.testsuite.TestSuite;
import com.liferay.jethr0.testsuite.TestSuiteFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TestSuiteDALO extends BaseEntityDALO<TestSuite> {

	@Override
	protected EntityFactory<TestSuite> getEntityFactory() {
		return _testSuiteFactory;
	}

	@Autowired
	private TestSuiteFactory _testSuiteFactory;

}