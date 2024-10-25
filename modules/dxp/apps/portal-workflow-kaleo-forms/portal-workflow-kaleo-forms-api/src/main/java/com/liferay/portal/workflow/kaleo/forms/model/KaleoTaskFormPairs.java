/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.forms.model;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class KaleoTaskFormPairs implements Iterable<KaleoTaskFormPair> {

	public static KaleoTaskFormPairs parse(String data) {
		KaleoTaskFormPairs kaleoTaskFormPairs = new KaleoTaskFormPairs();

		for (String taskForm : StringUtil.split(data)) {
			String[] keyValue = StringUtil.split(taskForm, StringPool.COLON);

			KaleoTaskFormPair kaleoTaskFormPair = new KaleoTaskFormPair(
				keyValue[0], Long.valueOf(keyValue[1]));

			kaleoTaskFormPairs.add(kaleoTaskFormPair);
		}

		return kaleoTaskFormPairs;
	}

	public void add(int index, KaleoTaskFormPair kaleoTaskFormPair) {
		_kaleoTaskFormPairs.add(index, kaleoTaskFormPair);
	}

	public void add(KaleoTaskFormPair kaleoTaskFormPair) {
		_kaleoTaskFormPairs.add(kaleoTaskFormPair);
	}

	@Override
	public Iterator<KaleoTaskFormPair> iterator() {
		return _kaleoTaskFormPairs.iterator();
	}

	public List<KaleoTaskFormPair> list() {
		return _kaleoTaskFormPairs;
	}

	public int size() {
		return _kaleoTaskFormPairs.size();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(_kaleoTaskFormPairs.size() * 4);

		for (KaleoTaskFormPair taskFormPair : _kaleoTaskFormPairs) {
			sb.append(taskFormPair.getWorkflowTaskName());
			sb.append(StringPool.COLON);
			sb.append(taskFormPair.getDDMTemplateId());
			sb.append(StringPool.COMMA);
		}

		if (sb.index() > 0) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
	}

	private final List<KaleoTaskFormPair> _kaleoTaskFormPairs =
		new ArrayList<>();

}