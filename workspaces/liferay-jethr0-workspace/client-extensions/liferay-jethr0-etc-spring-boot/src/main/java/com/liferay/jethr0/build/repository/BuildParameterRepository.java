/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.build.repository;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.dalo.BuildParameterDALO;
import com.liferay.jethr0.build.dalo.BuildToBuildParametersDALO;
import com.liferay.jethr0.build.parameter.BuildParameter;
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
public class BuildParameterRepository
	extends BaseEntityRepository<BuildParameter> {

	public BuildParameter add(Build build, String name, String value) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"name", name
		).put(
			"r_buildToBuildParameters_c_buildId", build.getId()
		).put(
			"value", value
		);

		BuildParameter buildParameter = add(jsonObject);

		build.addBuildParameter(buildParameter);

		buildParameter.setBuild(build);

		return buildParameter;
	}

	public Set<BuildParameter> getAll(Build build) {
		Set<BuildParameter> buildParameters = new HashSet<>(
			_buildToBuildParametersDALO.getChildEntities(build));

		for (BuildParameter buildParameter : buildParameters) {
			buildParameter.setBuild(build);
		}

		return buildParameters;
	}

	@Override
	public EntityDALO<BuildParameter> getEntityDALO() {
		return _buildParameterDALO;
	}

	@Override
	public void initialize() {
	}

	@Override
	public synchronized void initializeRelationships() {
		_buildRepository.initializeRelationships();

		for (BuildParameter buildParameter : getAll()) {
			Build build = null;

			long buildId = buildParameter.getBuildId();

			if (buildId != 0) {
				build = _buildRepository.getById(buildId);
			}

			buildParameter.setBuild(build);
		}
	}

	public void setBuildRepository(BuildRepository buildRepository) {
		_buildRepository = buildRepository;
	}

	@Autowired
	private BuildParameterDALO _buildParameterDALO;

	private BuildRepository _buildRepository;

	@Autowired
	private BuildToBuildParametersDALO _buildToBuildParametersDALO;

}