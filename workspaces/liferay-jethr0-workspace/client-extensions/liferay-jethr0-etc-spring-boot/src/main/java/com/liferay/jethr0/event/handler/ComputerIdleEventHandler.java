/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.build.queue.BuildQueue;
import com.liferay.jethr0.build.repository.BuildRepository;
import com.liferay.jethr0.build.repository.BuildRunRepository;
import com.liferay.jethr0.build.run.BuildRun;
import com.liferay.jethr0.jenkins.node.JenkinsNode;
import com.liferay.jethr0.jms.JMSEventHandler;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ComputerIdleEventHandler extends ComputerUpdateEventHandler {

	public ComputerIdleEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	@Override
	public String process() throws Exception {
		super.process();

		JenkinsNode jenkinsNode = getJenkinsNode();

		if (jenkinsNode == null) {
			return null;
		}

		BuildQueue buildQueue = getBuildQueue();

		Build build = buildQueue.nextBuild(jenkinsNode);

		if (build == null) {
			return null;
		}

		build.setState(Build.State.QUEUED);

		BuildRunRepository buildRunRepository = getBuildRunRepository();

		BuildRun buildRun = buildRunRepository.add(
			build, BuildRun.State.QUEUED);

		JMSEventHandler jmsEventHandler = getJMSEventHandler();

		jmsEventHandler.send(
			jenkinsNode.getJenkinsServer(),
			String.valueOf(buildRun.getInvokeJSONObject()));

		BuildRepository buildRepository = getBuildRepository();

		buildRepository.update(build);

		buildRunRepository.update(buildRun);

		return jenkinsNode.toString();
	}

}