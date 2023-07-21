/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.task.dalo;

import com.liferay.jethr0.entity.dalo.BaseEntityRelationshipDALO;
import com.liferay.jethr0.entity.factory.EntityFactory;
import com.liferay.jethr0.task.Task;
import com.liferay.jethr0.task.TaskFactory;
import com.liferay.jethr0.task.run.TaskRun;
import com.liferay.jethr0.task.run.TaskRunFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class TaskToTaskRunsDALO
	extends BaseEntityRelationshipDALO<Task, TaskRun> {

	@Override
	public EntityFactory<TaskRun> getChildEntityFactory() {
		return _taskRunFactory;
	}

	@Override
	public EntityFactory<Task> getParentEntityFactory() {
		return _taskFactory;
	}

	@Override
	protected String getObjectRelationshipName() {
		return "taskToTaskRuns";
	}

	@Autowired
	private TaskFactory _taskFactory;

	@Autowired
	private TaskRunFactory _taskRunFactory;

}