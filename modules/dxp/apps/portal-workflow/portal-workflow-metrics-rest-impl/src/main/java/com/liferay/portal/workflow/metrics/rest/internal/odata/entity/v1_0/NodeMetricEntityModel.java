/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.odata.entity.v1_0;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;

import java.util.Map;

/**
 * @author Inácio Nery
 */
public class NodeMetricEntityModel implements EntityModel {

	public NodeMetricEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new IntegerEntityField(
				"breachedInstancePercentage",
				locale -> "breachedInstancePercentage"),
			new IntegerEntityField("durationAvg", locale -> "durationAvg"),
			new IntegerEntityField("instanceCount", locale -> "instanceCount"),
			new IntegerEntityField(
				"onTimeInstanceCount", locale -> "onTimeInstanceCount"),
			new IntegerEntityField(
				"overdueInstanceCount", locale -> "overdueInstanceCount"));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	@Override
	public String getName() {
		return StringUtil.replace(
			NodeMetricEntityModel.class.getName(), CharPool.PERIOD,
			CharPool.UNDERLINE);
	}

	private final Map<String, EntityField> _entityFieldsMap;

}