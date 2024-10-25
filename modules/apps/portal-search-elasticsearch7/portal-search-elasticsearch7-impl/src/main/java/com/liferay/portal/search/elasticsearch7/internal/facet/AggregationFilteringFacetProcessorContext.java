/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.facet;

import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.RangeFacet;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.facet.nested.NestedFacet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.join.ScoreMode;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public class AggregationFilteringFacetProcessorContext
	implements FacetProcessorContext {

	public static FacetProcessorContext newInstance(Collection<Facet> facets) {
		return new AggregationFilteringFacetProcessorContext(
			_getSelectionFiltersMap(facets));
	}

	@Override
	public AggregationBuilder postProcessAggregationBuilder(
		AggregationBuilder aggregationBuilder) {

		String aggregationName = aggregationBuilder.getName();

		AggregationBuilder superAggregationBuilder =
			_getSuperAggregationBuilder(aggregationName);

		if (superAggregationBuilder != null) {
			return superAggregationBuilder.subAggregation(aggregationBuilder);
		}

		return aggregationBuilder;
	}

	private static List<QueryBuilder> _getSelectionFilters(
		com.liferay.portal.search.facet.Facet facet) {

		List<QueryBuilder> queryBuilders = new ArrayList<>();

		String fieldName = facet.getFieldName();

		if (facet instanceof NestedFacet) {
			NestedFacet nestedFacet = (NestedFacet)facet;

			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

			if (Validator.isNotNull(nestedFacet.getFilterField())) {
				boolQueryBuilder.must(
					QueryBuilders.termsQuery(
						nestedFacet.getFilterField(),
						nestedFacet.getFilterValue()));
			}

			boolQueryBuilder.must(
				QueryBuilders.termsQuery(
					facet.getFieldName(), facet.getSelections()));

			queryBuilders.add(
				QueryBuilders.nestedQuery(
					nestedFacet.getPath(), boolQueryBuilder, ScoreMode.Total));
		}
		else if (facet instanceof RangeFacet) {
			for (String value : facet.getSelections()) {
				queryBuilders.add(
					_rangeQuery(fieldName, RangeParserUtil.parserRange(value)));
			}
		}
		else {
			queryBuilders.add(
				QueryBuilders.termsQuery(fieldName, facet.getSelections()));
		}

		return queryBuilders;
	}

	private static Map<String, List<QueryBuilder>> _getSelectionFiltersMap(
		Collection<Facet> facets) {

		Map<String, List<QueryBuilder>> map = new HashMap<>();

		for (Facet facet : facets) {
			if ((facet instanceof com.liferay.portal.search.facet.Facet) &&
				!facet.isStatic()) {

				com.liferay.portal.search.facet.Facet osgiFacet =
					(com.liferay.portal.search.facet.Facet)facet;

				if (!ArrayUtil.isEmpty(osgiFacet.getSelections())) {
					map.put(
						osgiFacet.getAggregationName(),
						_getSelectionFilters(osgiFacet));
				}
			}
		}

		return map;
	}

	private static QueryBuilder _rangeQuery(String fieldName, String[] ranges) {
		RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(
			fieldName);

		rangeQueryBuilder.gte(ranges[0]);
		rangeQueryBuilder.lte(ranges[1]);

		return rangeQueryBuilder;
	}

	private AggregationFilteringFacetProcessorContext(
		Map<String, List<QueryBuilder>> selectionFiltersMap) {

		_selectionFiltersMap = selectionFiltersMap;
	}

	private BoolQueryBuilder _getSelectionFiltersOfOthersAsBoolQueryBuilder(
		String aggregationName) {

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

		for (Map.Entry<String, List<QueryBuilder>> entry :
				_selectionFiltersMap.entrySet()) {

			String filterAggregationName = entry.getKey();

			if (!filterAggregationName.equals(aggregationName)) {
				List<QueryBuilder> queryBuilders = entry.getValue();

				if (queryBuilders.size() == 1) {
					boolQueryBuilder.must(queryBuilders.get(0));
				}
				else if (queryBuilders.size() > 1) {
					BoolQueryBuilder queryBuildersBoolQueryBuilder =
						QueryBuilders.boolQuery();

					for (QueryBuilder queryBuilder : queryBuilders) {
						queryBuildersBoolQueryBuilder.should(queryBuilder);
					}

					boolQueryBuilder.must(queryBuildersBoolQueryBuilder);
				}
			}
		}

		return boolQueryBuilder;
	}

	private AggregationBuilder _getSuperAggregationBuilder(
		String aggregationName) {

		if (_selectionFiltersMap.isEmpty()) {
			return null;
		}

		BoolQueryBuilder boolQueryBuilder =
			_getSelectionFiltersOfOthersAsBoolQueryBuilder(aggregationName);

		if (!boolQueryBuilder.hasClauses()) {
			return null;
		}

		return new FilterAggregationBuilder(aggregationName, boolQueryBuilder);
	}

	private final Map<String, List<QueryBuilder>> _selectionFiltersMap;

}