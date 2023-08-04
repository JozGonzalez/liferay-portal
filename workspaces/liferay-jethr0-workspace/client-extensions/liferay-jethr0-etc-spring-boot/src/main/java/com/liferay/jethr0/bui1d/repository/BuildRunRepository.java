/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d.repository;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.bui1d.dalo.BuildRunDALO;
import com.liferay.jethr0.bui1d.dalo.BuildToBuildRunsDALO;
import com.liferay.jethr0.bui1d.run.BuildRun;
import com.liferay.jethr0.entity.dalo.EntityDALO;
import com.liferay.jethr0.entity.repository.BaseEntityRepository;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class BuildRunRepository extends BaseEntityRepository<BuildRun> {

	public BuildRun add(Build build, BuildRun.State state) {
		JSONObject jsonObject = new JSONObject();

		if (state == null) {
			state = BuildRun.State.OPENED;
		}

		jsonObject.put(
			"r_buildToBuildRuns_c_buildId", build.getId()
		).put(
			"state", state.getJSONObject()
		);

		BuildRun buildRun = _buildRunDALO.create(jsonObject);

		buildRun.setBuild(build);

		build.addBuildRun(buildRun);

		return add(buildRun);
	}

	public Set<BuildRun> getAll(Build build) {
		Set<BuildRun> buildRuns = new HashSet<>(
			_buildToBuildRunsDALO.getChildEntities(build));

		for (BuildRun buildRun : buildRuns) {
			buildRun.setBuild(build);
		}

		return addAll(buildRuns);
	}

	@Override
	public EntityDALO<BuildRun> getEntityDALO() {
		return _buildRunDALO;
	}

	@Override
	public void initialize() {
	}

	@Override
	public synchronized void initializeRelationships() {
		_buildRepository.initializeRelationships();

		for (BuildRun buildRun : getAll()) {
			Build build = null;

			long buildId = buildRun.getBuildId();

			if (buildId != 0) {
				build = _buildRepository.getById(buildId);
			}

			buildRun.setBuild(build);
		}
	}

	public void setBuildRepository(BuildRepository buildRepository) {
		_buildRepository = buildRepository;
	}

	private BuildRepository _buildRepository;

	@Autowired
	private BuildRunDALO _buildRunDALO;

	@Autowired
	private BuildToBuildRunsDALO _buildToBuildRunsDALO;

}