/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.gitbranch.GitBranch;
import com.liferay.jethr0.gitbranch.GitBranchFactory;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.ProjectFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class ProjectsToGitBranchesDALO
	extends BaseEntityRelationshipDALO<Project, GitBranch> {

	@Override
	public EntityFactory<GitBranch> getChildEntityFactory() {
		return _gitBranchFactory;
	}

	@Override
	public EntityFactory<Project> getParentEntityFactory() {
		return _projectFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "projectsToGitBranches";
	}

	@Autowired
	private GitBranchFactory _gitBranchFactory;

	@Autowired
	private ProjectFactory _projectFactory;

}