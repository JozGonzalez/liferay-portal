/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.project.repository.ProjectRepository;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseObjectEventHandler extends BaseEventHandler {

	protected BaseObjectEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	protected Project getProject(JSONObject projectJSONObject)
		throws Exception {

		if (projectJSONObject == null) {
			throw new Exception("Missing project");
		}

		long projectId = projectJSONObject.optLong("id");

		if (projectId <= 0) {
			throw new Exception("Missing ID from project");
		}

		ProjectRepository projectRepository = getProjectRepository();

		Project project = projectRepository.getById(projectId);

		BuildRepository buildRepository = getBuildRepository();

		buildRepository.getAll(project);

		return project;
	}

	protected JSONObject validateBuildJSONObject(JSONObject buildJSONObject)
		throws Exception {

		if (buildJSONObject == null) {
			throw new Exception("Missing build");
		}

		String buildName = buildJSONObject.optString("buildName");

		if (buildName.isEmpty()) {
			throw new Exception("Missing build name from build");
		}

		String jobName = buildJSONObject.optString("jobName");

		if (jobName.isEmpty()) {
			throw new Exception("Missing job name from build");
		}

		Build.State state = Build.State.getByKey(
			buildJSONObject.optString("state"));

		if (state == null) {
			state = Build.State.OPENED;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"buildName", buildName
		).put(
			"jobName", jobName
		).put(
			"parameters", buildJSONObject.optJSONObject("parameters")
		).put(
			"state", state.getJSONObject()
		);

		return jsonObject;
	}

	protected JSONArray validateBuildsJSONArray(JSONArray buildsJSONArray)
		throws Exception {

		JSONArray jsonArray = new JSONArray();

		if ((buildsJSONArray != null) && !buildsJSONArray.isEmpty()) {
			for (int i = 0; i < buildsJSONArray.length(); i++) {
				jsonArray.put(
					validateBuildJSONObject(buildsJSONArray.optJSONObject(i)));
			}
		}

		return jsonArray;
	}

	protected JSONObject validateProjectJSONObject(JSONObject projectJSONObject)
		throws Exception {

		if (projectJSONObject == null) {
			throw new Exception("Missing project");
		}

		if (projectJSONObject.has("id")) {
			return projectJSONObject;
		}

		String name = projectJSONObject.optString("name");

		if (name.isEmpty()) {
			throw new Exception("Missing name from project");
		}

		int priority = projectJSONObject.optInt("priority");

		if (priority <= 0) {
			throw new Exception("Missing priority from project");
		}

		Project.State state = Project.State.getByKey(
			projectJSONObject.optString("state"));

		if (state == null) {
			state = Project.State.OPENED;
		}

		Project.Type type = Project.Type.getByKey(
			projectJSONObject.optString("type"));

		if (type == null) {
			throw new Exception(
				"Project type is not one of the following: " +
					Project.Type.getKeys());
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"builds",
			validateBuildsJSONArray(projectJSONObject.optJSONArray("builds"))
		).put(
			"name", name
		).put(
			"priority", priority
		).put(
			"state", state.getJSONObject()
		).put(
			"type", type.getJSONObject()
		);

		return jsonObject;
	}

}