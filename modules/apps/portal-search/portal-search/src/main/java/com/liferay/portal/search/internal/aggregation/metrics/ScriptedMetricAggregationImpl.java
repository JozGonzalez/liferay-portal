/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.metrics;

import com.liferay.portal.search.aggregation.AggregationVisitor;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregation;
import com.liferay.portal.search.internal.aggregation.BaseAggregation;
import com.liferay.portal.search.script.Script;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class ScriptedMetricAggregationImpl
	extends BaseAggregation implements ScriptedMetricAggregation {

	public ScriptedMetricAggregationImpl(String name) {
		super(name);
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	public void clearParameter(String paramName) {
		_parameters.remove(paramName);
	}

	public void clearParameters() {
		_parameters.clear();
	}

	@Override
	public Script getCombineScript() {
		return _combineScript;
	}

	@Override
	public Script getInitScript() {
		return _initScript;
	}

	@Override
	public Script getMapScript() {
		return _mapScript;
	}

	@Override
	public Map<String, Object> getParameters() {
		return Collections.unmodifiableMap(_parameters);
	}

	@Override
	public Script getReduceScript() {
		return _reduceScript;
	}

	@Override
	public void putParameter(String paramName, Object paramValue) {
		_parameters.put(paramName, paramValue);
	}

	@Override
	public void setCombineScript(Script combineScript) {
		_combineScript = combineScript;
	}

	@Override
	public void setInitScript(Script initScript) {
		_initScript = initScript;
	}

	@Override
	public void setMapScript(Script mapScript) {
		_mapScript = mapScript;
	}

	@Override
	public void setParameters(Map<String, Object> parameters) {
		_parameters = parameters;
	}

	@Override
	public void setReduceScript(Script reduceScript) {
		_reduceScript = reduceScript;
	}

	private Script _combineScript;
	private Script _initScript;
	private Script _mapScript;
	private Map<String, Object> _parameters = new HashMap<>();
	private Script _reduceScript;

}