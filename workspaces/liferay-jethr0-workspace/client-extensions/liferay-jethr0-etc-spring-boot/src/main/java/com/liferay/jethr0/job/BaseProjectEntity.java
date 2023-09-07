/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.gitbranch.GitBranchEntity;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.task.TaskEntity;
import com.liferay.jethr0.testsuite.TestSuiteEntity;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseProjectEntity
	extends BaseEntity implements ProjectEntity {

	@Override
	public void addBuildEntities(Set<BuildEntity> buildEntities) {
		addRelatedEntities(buildEntities);
	}

	@Override
	public void addBuildEntity(BuildEntity buildEntity) {
		addRelatedEntity(buildEntity);
	}

	@Override
	public void addGitBranchEntities(Set<GitBranchEntity> gitBranchEntities) {
		addRelatedEntities(gitBranchEntities);
	}

	@Override
	public void addGitBranchEntity(GitBranchEntity gitBranchEntity) {
		addRelatedEntity(gitBranchEntity);
	}

	@Override
	public void addJenkinsCohortEntities(
		Set<JenkinsCohortEntity> jenkinsCohortEntities) {

		addRelatedEntities(jenkinsCohortEntities);
	}

	@Override
	public void addJenkinsCohortEntity(
		JenkinsCohortEntity jenkinsCohortEntity) {

		addRelatedEntity(jenkinsCohortEntity);
	}

	@Override
	public void addTaskEntities(Set<TaskEntity> taskEntities) {
		addRelatedEntities(taskEntities);
	}

	@Override
	public void addTaskEntity(TaskEntity taskEntity) {
		addRelatedEntity(taskEntity);
	}

	@Override
	public void addTestSuiteEntities(Set<TestSuiteEntity> testSuiteEntities) {
		addRelatedEntities(testSuiteEntities);
	}

	@Override
	public void addTestSuiteEntity(TestSuiteEntity testSuiteEntity) {
		addRelatedEntity(testSuiteEntity);
	}

	@Override
	public Set<BuildEntity> getBuildEntities() {
		return getRelatedEntities(BuildEntity.class);
	}

	@Override
	public Set<GitBranchEntity> getGitBranchEntities() {
		return getRelatedEntities(GitBranchEntity.class);
	}

	@Override
	public Set<JenkinsCohortEntity> getJenkinsCohortEntities() {
		return getRelatedEntities(JenkinsCohortEntity.class);
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		ProjectEntity.State state = getState();
		ProjectEntity.Type type = getType();

		jsonObject.put(
			"name", getName()
		).put(
			"position", getPosition()
		).put(
			"priority", getPriority()
		).put(
			"startDate", StringUtil.toString(getStartDate())
		).put(
			"state", state.getJSONObject()
		).put(
			"type", type.getJSONObject()
		);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int getPosition() {
		return _position;
	}

	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public Date getStartDate() {
		return _startDate;
	}

	@Override
	public State getState() {
		return _state;
	}

	@Override
	public Set<TaskEntity> getTaskEntities() {
		return getRelatedEntities(TaskEntity.class);
	}

	@Override
	public Set<TestSuiteEntity> getTestSuiteEntities() {
		return getRelatedEntities(TestSuiteEntity.class);
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public void removeBuildEntities(Set<BuildEntity> buildEntities) {
		removeRelatedEntities(buildEntities);
	}

	@Override
	public void removeBuildEntity(BuildEntity buildEntity) {
		removeRelatedEntity(buildEntity);
	}

	@Override
	public void removeGitBranchEntities(
		Set<GitBranchEntity> gitBranchEntities) {

		removeRelatedEntities(gitBranchEntities);
	}

	@Override
	public void removeGitBranchEntity(GitBranchEntity gitBranchEntity) {
		removeRelatedEntity(gitBranchEntity);
	}

	@Override
	public void removeJenkinsCohortEntities(
		Set<JenkinsCohortEntity> jenkinsCohortEntities) {

		removeRelatedEntities(jenkinsCohortEntities);
	}

	@Override
	public void removeJenkinsCohortEntity(
		JenkinsCohortEntity jenkinsCohortEntity) {

		removeRelatedEntity(jenkinsCohortEntity);
	}

	public void removeTaskEntities(Set<TaskEntity> taskEntities) {
		removeRelatedEntities(taskEntities);
	}

	@Override
	public void removeTaskEntity(TaskEntity taskEntity) {
		removeRelatedEntity(taskEntity);
	}

	@Override
	public void removeTestSuiteEntities(
		Set<TestSuiteEntity> testSuiteEntities) {

		removeRelatedEntities(testSuiteEntities);
	}

	@Override
	public void removeTestSuiteEntity(TestSuiteEntity testSuiteEntity) {
		removeRelatedEntity(testSuiteEntity);
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setPosition(int position) {
		if (position <= 0) {
			position = Integer.MAX_VALUE;
		}

		_position = position;
	}

	@Override
	public void setPriority(int priority) {
		_priority = priority;
	}

	@Override
	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	@Override
	public void setState(State state) {
		_state = state;
	}

	protected BaseProjectEntity(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.getString("name");

		int position = jsonObject.optInt("position", Integer.MAX_VALUE);

		if (position <= 0) {
			position = Integer.MAX_VALUE;
		}

		_position = position;

		_priority = jsonObject.optInt("priority");
		_startDate = StringUtil.toDate(jsonObject.optString("startDate"));
		_state = State.get(jsonObject.getJSONObject("state"));
		_type = Type.get(jsonObject.getJSONObject("type"));
	}

	private String _name;
	private int _position;
	private int _priority;
	private Date _startDate;
	private State _state;
	private final Type _type;

}