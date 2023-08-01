/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.project.dalo.ProjectPrioritizerDALO;
import com.liferay.jethr0.project.dalo.ProjectPrioritizerToProjectComparatorsDALO;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizer;
import com.liferay.jethr0.project.prioritizer.ProjectPrioritizerFactory;

import java.util.Objects;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectPrioritizerRepository
	extends BaseEntityRepository<ProjectPrioritizer> {

	public ProjectPrioritizer add(String name) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("name", name);

		return add(jsonObject);
	}

	public ProjectPrioritizer getByName(String name) {
		for (ProjectPrioritizer projectPrioritizer : getAll()) {
			if (!Objects.equals(projectPrioritizer.getName(), name)) {
				continue;
			}

			return projectPrioritizer;
		}

		return null;
	}

	@Override
	public ProjectPrioritizerDALO getEntityDALO() {
		return _projectPrioritizerDALO;
	}

	@Override
	public void initializeRelationships() {
		for (ProjectPrioritizer projectPrioritizer : getAll()) {
			for (long projectComparatorId :
					_projectPrioritizerToProjectComparatorsDALO.
						getChildEntityIds(projectPrioritizer)) {

				if (projectComparatorId == 0) {
					continue;
				}

				projectPrioritizer.addProjectComparator(
					_projectComparatorRepository.getById(projectComparatorId));
			}
		}
	}

	public void setProjectComparatorRepository(
		ProjectComparatorRepository projectComparatorRepository) {

		_projectComparatorRepository = projectComparatorRepository;
	}

	private ProjectComparatorRepository _projectComparatorRepository;

	@Autowired
	private ProjectPrioritizerDALO _projectPrioritizerDALO;

	@Autowired
	private ProjectPrioritizerFactory _projectPrioritizerFactory;

	@Autowired
	private ProjectPrioritizerToProjectComparatorsDALO
		_projectPrioritizerToProjectComparatorsDALO;

}