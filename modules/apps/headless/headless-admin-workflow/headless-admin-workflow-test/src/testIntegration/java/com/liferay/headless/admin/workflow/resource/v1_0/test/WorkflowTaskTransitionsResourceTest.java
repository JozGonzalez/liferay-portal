/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.workflow.client.dto.v1_0.Transition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowInstance;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTask;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskIds;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskTransition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowTaskTransitions;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.ObjectReviewedTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowDefinitionTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowInstanceTestUtil;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowTaskTestUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowTaskTransitionsResourceTest
	extends BaseWorkflowTaskTransitionsResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_workflowInstance = WorkflowInstanceTestUtil.addWorkflowInstance(
			testGroup.getGroupId(), ObjectReviewedTestUtil.addObjectReviewed(),
			WorkflowDefinitionTestUtil.addWorkflowDefinition());
	}

	@Override
	@Test
	public void testPostWorkflowTaskTransition() throws Exception {
		WorkflowTask workflowTask = WorkflowTaskTestUtil.getWorkflowTask(
			_workflowInstance.getId());

		WorkflowTaskTransitions workflowTaskTransitions =
			workflowTaskTransitionsResource.postWorkflowTaskTransition(
				new WorkflowTaskIds() {
					{
						workflowTaskIds = new Long[] {workflowTask.getId()};
					}
				});

		WorkflowTaskTransition workflowTaskTransition =
			(WorkflowTaskTransition)ArrayUtil.getValue(
				workflowTaskTransitions.getWorkflowTaskTransitions(), 0);

		Transition[] transitions = workflowTaskTransition.getTransitions();

		Assert.assertEquals(
			Arrays.toString(transitions), 2, transitions.length);

		String[] expectedTransitionNames = {"approve", "reject"};

		for (Transition transition : transitions) {
			Assert.assertTrue(
				ArrayUtil.contains(
					expectedTransitionNames, transition.getName()));
		}
	}

	private WorkflowInstance _workflowInstance;

}