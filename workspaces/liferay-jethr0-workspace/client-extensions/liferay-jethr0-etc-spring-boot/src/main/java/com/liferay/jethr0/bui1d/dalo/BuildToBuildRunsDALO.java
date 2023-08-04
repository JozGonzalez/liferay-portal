/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.dalo;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.BuildFactory;
import com.liferay.jethr0.bui1d.run.BuildRun;
import com.liferay.jethr0.bui1d.run.BuildRunFactory;
import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildToBuildRunsDALO
	extends BaseEntityRelationshipDALO<Build, BuildRun> {

	@Override
	public EntityFactory<BuildRun> getChildEntityFactory() {
		return _buildRunFactory;
	}

	@Override
	public EntityFactory<Build> getParentEntityFactory() {
		return _buildFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "buildToBuildRuns";
	}

	@Autowired
	private BuildFactory _buildFactory;

	@Autowired
	private BuildRunFactory _buildRunFactory;

}