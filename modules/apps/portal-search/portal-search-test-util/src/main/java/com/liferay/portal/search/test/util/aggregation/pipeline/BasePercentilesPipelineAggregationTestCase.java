/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.aggregation.pipeline;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.aggregation.pipeline.PercentilesBucketPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.PercentilesBucketPipelineAggregationResult;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
public abstract class BasePercentilesPipelineAggregationTestCase
	extends BaseIndexingTestCase {

	@Test
	public void testPercentiles() throws Exception {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		PercentilesBucketPipelineAggregation
			percentilesBucketPipelineAggregation = getAggregation();

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> {
						searchRequestBuilder.addAggregation(
							aggregationFixture.
								getDefaultHistogramAggregation());
						searchRequestBuilder.addPipelineAggregation(
							percentilesBucketPipelineAggregation);
					});

				indexingTestHelper.search();

				PercentilesBucketPipelineAggregationResult
					percentilesBucketPipelineAggregationResult =
						indexingTestHelper.getAggregationResult(
							percentilesBucketPipelineAggregation);

				Map<Double, Double> percentiles =
					percentilesBucketPipelineAggregationResult.getPercentiles();

				Assert.assertEquals(
					"Total number of buckets", 7, percentiles.size(), 0);
				Assert.assertEquals("1%", 10, percentiles.get(1.0), 0);
				Assert.assertEquals("5%", 10, percentiles.get(5.0), 0);
				Assert.assertEquals("25.%", 20, percentiles.get(25.0), 0);
				Assert.assertEquals("50%", 35, percentiles.get(50.0), 0);
				Assert.assertEquals("75%", 60, percentiles.get(75.0), 0);
				Assert.assertEquals("95%", 85, percentiles.get(95.0), 0);
				Assert.assertEquals("99%", 85, percentiles.get(99.0), 0);
			});
	}

	@Test
	public void testPercentilesWithSpecifiedPercents() throws Exception {
		for (int i = 1; i <= 20; i++) {
			addDocument(
				DocumentCreationHelpers.singleNumber(Field.PRIORITY, i));
		}

		PercentilesBucketPipelineAggregation
			percentilesBucketPipelineAggregation = getAggregation();

		percentilesBucketPipelineAggregation.setPercents(25.0, 50.0, 75.0);

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder -> {
						searchRequestBuilder.addAggregation(
							aggregationFixture.
								getDefaultHistogramAggregation());
						searchRequestBuilder.addPipelineAggregation(
							percentilesBucketPipelineAggregation);
					});

				indexingTestHelper.search();

				PercentilesBucketPipelineAggregationResult
					percentilesBucketPipelineAggregationResult =
						indexingTestHelper.getAggregationResult(
							percentilesBucketPipelineAggregation);

				Map<Double, Double> percentiles =
					percentilesBucketPipelineAggregationResult.getPercentiles();

				Assert.assertEquals(
					"Total number of buckets", 3, percentiles.size(), 0);
				Assert.assertEquals("25%", 20, percentiles.get(25.0), 0);
				Assert.assertEquals("50%", 35, percentiles.get(50.0), 0);
				Assert.assertEquals("75%", 60, percentiles.get(75.0), 0);
			});
	}

	protected PercentilesBucketPipelineAggregation getAggregation() {
		return aggregations.percentilesBucket("percentiles", "histogram>sum");
	}

}