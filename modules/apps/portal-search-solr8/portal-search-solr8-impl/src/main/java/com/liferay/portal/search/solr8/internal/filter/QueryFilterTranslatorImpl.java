/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.filter;

import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.search.solr8.internal.query.LuceneQueryConverter;

import org.apache.lucene.search.Query;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = QueryFilterTranslator.class)
public class QueryFilterTranslatorImpl implements QueryFilterTranslator {

	@Override
	public Query translate(QueryFilter queryFilter) {
		return _luceneQueryConverter.convert(queryFilter.getQuery());
	}

	@Reference
	private LuceneQueryConverter _luceneQueryConverter;

}