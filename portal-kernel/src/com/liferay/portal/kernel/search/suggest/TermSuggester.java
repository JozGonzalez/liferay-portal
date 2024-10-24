/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.suggest;

/**
 * @author Michael C. Han
 */
public class TermSuggester extends BaseSuggester {

	public TermSuggester(String name, String field) {
		super(name, field);
	}

	public TermSuggester(String name, String field, String value) {
		super(name, field, value);
	}

	@Override
	public <T> T accept(SuggesterVisitor<T> suggesterVisitor) {
		return suggesterVisitor.visit(this);
	}

	public Float getAccuracy() {
		return _accuracy;
	}

	public String getAnalyzer() {
		return _analyzer;
	}

	public Integer getMaxEdits() {
		return _maxEdits;
	}

	public Integer getMaxInspections() {
		return _maxInspections;
	}

	public Integer getMaxTermFreq() {
		return _maxTermFreq;
	}

	public Integer getMinDocFreq() {
		return _minDocFreq;
	}

	public Integer getMinWordLength() {
		return _minWordLength;
	}

	public Integer getPrefixLength() {
		return _prefixLength;
	}

	public Integer getShardSize() {
		return _shardSize;
	}

	public Integer getSize() {
		return _size;
	}

	public Sort getSort() {
		return _sort;
	}

	public StringDistance getStringDistance() {
		return _stringDistance;
	}

	public SuggestMode getSuggestMode() {
		return _suggestMode;
	}

	public void setAccuracy(Float accuracy) {
		_accuracy = accuracy;
	}

	public void setAnalyzer(String analyzer) {
		_analyzer = analyzer;
	}

	public void setMaxEdits(Integer maxEdits) {
		_maxEdits = maxEdits;
	}

	public void setMaxInspections(Integer maxInspections) {
		_maxInspections = maxInspections;
	}

	public void setMaxTermFreq(Integer maxTermFreq) {
		_maxTermFreq = maxTermFreq;
	}

	public void setMinDocFreq(Integer minDocFreq) {
		_minDocFreq = minDocFreq;
	}

	public void setMinWordLength(Integer minWordLength) {
		_minWordLength = minWordLength;
	}

	public void setPrefixLength(Integer prefixLength) {
		_prefixLength = prefixLength;
	}

	public void setShardSize(Integer shardSize) {
		_shardSize = shardSize;
	}

	public void setSize(Integer size) {
		_size = size;
	}

	public void setSort(Sort sort) {
		_sort = sort;
	}

	public void setStringDistance(StringDistance stringDistance) {
		_stringDistance = stringDistance;
	}

	public void setSuggestMode(SuggestMode suggestMode) {
		_suggestMode = suggestMode;
	}

	private Float _accuracy;
	private String _analyzer;
	private Integer _maxEdits;
	private Integer _maxInspections;
	private Integer _maxTermFreq;
	private Integer _minDocFreq;
	private Integer _minWordLength;
	private Integer _prefixLength;
	private Integer _shardSize;
	private Integer _size;
	private Sort _sort;
	private StringDistance _stringDistance;
	private SuggestMode _suggestMode;

}