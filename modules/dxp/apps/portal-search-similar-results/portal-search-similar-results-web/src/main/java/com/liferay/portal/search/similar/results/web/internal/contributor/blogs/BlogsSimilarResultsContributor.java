/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.contributor.blogs;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.similar.results.web.internal.helper.HttpHelper;
import com.liferay.portal.search.similar.results.web.internal.util.SearchStringUtil;
import com.liferay.portal.search.similar.results.web.spi.contributor.SimilarResultsContributor;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.CriteriaHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.DestinationHelper;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteBuilder;
import com.liferay.portal.search.similar.results.web.spi.contributor.helper.RouteHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@Component(service = SimilarResultsContributor.class)
public class BlogsSimilarResultsContributor
	implements SimilarResultsContributor {

	@Override
	public void detectRoute(
		RouteBuilder routeBuilder, RouteHelper routeHelper) {

		String[] parameters = _httpHelper.getFriendlyURLParameters(
			HttpComponentsUtil.decodePath(routeHelper.getURLString()));

		SearchStringUtil.requireEquals("blogs", parameters[0]);

		routeBuilder.addAttribute("urlTitle", parameters[1]);
	}

	@Override
	public void resolveCriteria(
		CriteriaBuilder criteriaBuilder, CriteriaHelper criteriaHelper) {

		long groupId = criteriaHelper.getGroupId();

		String urlTitle = (String)criteriaHelper.getRouteParameter("urlTitle");

		BlogsEntry blogsEntry = _blogsEntryLocalService.fetchEntry(
			groupId, urlTitle);

		if (blogsEntry == null) {
			return;
		}

		criteriaBuilder.uid(_uidFactory.getUID(blogsEntry));
	}

	@Override
	public void writeDestination(
		DestinationBuilder destinationBuilder,
		DestinationHelper destinationHelper) {

		AssetRenderer<?> assetRenderer = destinationHelper.getAssetRenderer();

		if (assetRenderer.getGroupId() != destinationHelper.getScopeGroupId()) {
			destinationBuilder.replaceURLString(
				destinationHelper.getAssetViewURL());

			return;
		}

		String urlTitle = (String)destinationHelper.getRouteParameter(
			"urlTitle");

		destinationBuilder.replace(
			_getBlogsURLParameterPattern(urlTitle),
			_getBlogsURLParameterPattern(assetRenderer.getUrlTitle()));
	}

	private String _getBlogsURLParameterPattern(String parameterValue) {
		return "-/blogs/" + parameterValue + "?";
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private HttpHelper _httpHelper;

	@Reference
	private UIDFactory _uidFactory;

}