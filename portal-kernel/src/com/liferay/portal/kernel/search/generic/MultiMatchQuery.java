/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.generic;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.BaseQueryImpl;
import com.liferay.portal.kernel.search.query.QueryVisitor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class MultiMatchQuery extends BaseQueryImpl {

	public MultiMatchQuery(String value) {
		_value = value;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visitQuery(this);
	}

	public void addField(String field) {
		_fields.add(field);
	}

	public void addFields(Collection<String> fields) {
		_fields.addAll(fields);
	}

	public void addFields(String... fields) {
		Collections.addAll(_fields, fields);
	}

	public String getAnalyzer() {
		return _analyzer;
	}

	public Float getCutOffFrequency() {
		return _cutOffFrequency;
	}

	public Set<String> getFields() {
		return _fields;
	}

	public Map<String, Float> getFieldsBoosts() {
		return _fieldsBoosts;
	}

	public String getFuzziness() {
		return _fuzziness;
	}

	public MatchQuery.RewriteMethod getFuzzyRewriteMethod() {
		return _fuzzyRewriteMethod;
	}

	public Integer getMaxExpansions() {
		return _maxExpansions;
	}

	public String getMinShouldMatch() {
		return _minShouldMatch;
	}

	public MatchQuery.Operator getOperator() {
		return _operator;
	}

	public Integer getPrefixLength() {
		return _prefixLength;
	}

	public Integer getSlop() {
		return _slop;
	}

	public Float getTieBreaker() {
		return _tieBreaker;
	}

	public Type getType() {
		return _type;
	}

	public String getValue() {
		return _value;
	}

	public MatchQuery.ZeroTermsQuery getZeroTermsQuery() {
		return _zeroTermsQuery;
	}

	public boolean isFieldBoostsEmpty() {
		return _fieldsBoosts.isEmpty();
	}

	public boolean isFieldsEmpty() {
		return _fields.isEmpty();
	}

	public Boolean isLenient() {
		return _lenient;
	}

	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	public void setCutOffFrequency(Float cutOffFrequency) {
		_cutOffFrequency = cutOffFrequency;
	}

	public void setFuzziness(String fuzziness) {
		_fuzziness = fuzziness;
	}

	public void setFuzzyRewriteMethod(
		MatchQuery.RewriteMethod fuzzyRewriteMethod) {

		_fuzzyRewriteMethod = fuzzyRewriteMethod;
	}

	public void setLenient(Boolean lenient) {
		_lenient = lenient;
	}

	public void setMaxExpansions(Integer maxExpansions) {
		_maxExpansions = maxExpansions;
	}

	public void setMinShouldMatch(String minShouldMatch) {
		_minShouldMatch = minShouldMatch;
	}

	public void setOperator(MatchQuery.Operator operator) {
		_operator = operator;
	}

	public void setPrefixLength(Integer prefixLength) {
		_prefixLength = prefixLength;
	}

	public void setSlop(Integer slop) {
		_slop = slop;
	}

	public void setTieBreaker(Float tieBreaker) {
		_tieBreaker = tieBreaker;
	}

	public void setType(Type type) {
		_type = type;
	}

	public void setZeroTermsQuery(MatchQuery.ZeroTermsQuery zeroTermsQuery) {
		_zeroTermsQuery = zeroTermsQuery;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{analyzer=");
		sb.append(_analyzer);
		sb.append(", className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append(", cutOffFrequency=");
		sb.append(_cutOffFrequency);
		sb.append(", fields=");
		sb.append(_fields);
		sb.append(", fuzziness=");
		sb.append(_fuzziness);
		sb.append(", lenient=");
		sb.append(_lenient);
		sb.append(", maxExpansions=");
		sb.append(_maxExpansions);
		sb.append(", minShouldMatch=");
		sb.append(_minShouldMatch);
		sb.append(", operator=");
		sb.append(_operator);
		sb.append(", prefixLength=");
		sb.append(_prefixLength);
		sb.append(", slop=");
		sb.append(_slop);
		sb.append(", tieBreaker=");
		sb.append(_tieBreaker);
		sb.append(", type=");
		sb.append(_type);
		sb.append(", value=");
		sb.append(_value);
		sb.append("}");

		return sb.toString();
	}

	public enum Type {

		BEST_FIELDS, CROSS_FIELDS, MOST_FIELDS, PHRASE, PHRASE_PREFIX

	}

	private String _analyzer;
	private Float _cutOffFrequency;
	private final Set<String> _fields = new HashSet<>();
	private final Map<String, Float> _fieldsBoosts = new HashMap<>();
	private String _fuzziness;
	private MatchQuery.RewriteMethod _fuzzyRewriteMethod;
	private Boolean _lenient;
	private Integer _maxExpansions;
	private String _minShouldMatch;
	private MatchQuery.Operator _operator;
	private Integer _prefixLength;
	private Integer _slop;
	private Float _tieBreaker;
	private Type _type;
	private final String _value;
	private MatchQuery.ZeroTermsQuery _zeroTermsQuery;

}