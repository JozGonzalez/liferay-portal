/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.testsuite.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.testsuite.TestSuite;
import com.liferay.jethr0.testsuite.dalo.ProjectsToTestSuitesDALO;
import com.liferay.jethr0.testsuite.dalo.TestSuiteDALO;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TestSuiteRepository extends BaseEntityRepository<TestSuite> {

	public Set<TestSuite> getAll(Project project) {
		Set<TestSuite> projectTestSuites = new HashSet<>();

		Set<Long> testSuiteIds = _projectsToTestSuitesDALO.getChildEntityIds(
			project);

		for (TestSuite testSuite : getAll()) {
			if (!testSuiteIds.contains(testSuite.getId())) {
				continue;
			}

			project.addTestSuite(testSuite);

			testSuite.addProject(project);

			projectTestSuites.add(testSuite);
		}

		return projectTestSuites;
	}

	@Override
	public TestSuiteDALO getEntityDALO() {
		return _testSuiteDALO;
	}

	@Autowired
	private ProjectsToTestSuitesDALO _projectsToTestSuitesDALO;

	@Autowired
	private TestSuiteDALO _testSuiteDALO;

}