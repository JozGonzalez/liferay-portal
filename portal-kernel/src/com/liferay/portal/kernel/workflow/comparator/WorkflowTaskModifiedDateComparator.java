/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.workflow.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowTask;

import java.util.Date;

/**
 * @author Shuyang Zhou
 */
public class WorkflowTaskModifiedDateComparator
	extends OrderByComparator<WorkflowTask> {

	public WorkflowTaskModifiedDateComparator(
		boolean ascending, String orderByAsc, String orderByDesc,
		String[] orderByFields) {

		_ascending = ascending;
		_orderByAsc = orderByAsc;
		_orderByDesc = orderByDesc;
		_orderByFields = orderByFields;
	}

	@Override
	public int compare(WorkflowTask workflowTask1, WorkflowTask workflowTask2) {
		Date createDate1 = workflowTask1.getCreateDate();
		Date createDate2 = workflowTask2.getCreateDate();

		int value = createDate1.compareTo(createDate2);

		if (value == 0) {
			Long workflowTaskId1 = workflowTask1.getWorkflowTaskId();
			Long workflowTaskId2 = workflowTask2.getWorkflowTaskId();

			value = workflowTaskId1.compareTo(workflowTaskId2);
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (isAscending()) {
			return _orderByAsc;
		}

		return _orderByDesc;
	}

	@Override
	public String[] getOrderByFields() {
		return _orderByFields;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	@Override
	public boolean isAscending(String field) {
		if (field.equals("completed")) {
			return true;
		}

		return super.isAscending(field);
	}

	private final boolean _ascending;
	private final String _orderByAsc;
	private final String _orderByDesc;
	private final String[] _orderByFields;

}