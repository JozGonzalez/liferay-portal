<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAddressDisplayContext commerceAddressDisplayContext = (CommerceAddressDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:choose>
	<c:when test="<%= !commerceAddressDisplayContext.hasCommerceChannel() %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:otherwise>

		<%
		Map<String, Object> contextObjects = HashMapBuilder.<String, Object>put(
			"commerceAddressDisplayContext", commerceAddressDisplayContext
		).build();

		SearchContainer<CommerceAddress> commerceAddressSearchContainer = commerceAddressDisplayContext.getSearchContainer();

		PortletURL portletURL = PortletURLBuilder.create(
			commerceAddressDisplayContext.getPortletURL()
		).setParameter(
			"searchContainerId", "commerceAddresses"
		).buildPortletURL();

		request.setAttribute("view.jsp-portletURL", portletURL);
		%>

		<liferay-ddm:template-renderer
			className="<%= CommerceAddressContentPortlet.class.getName() %>"
			contextObjects="<%= contextObjects %>"
			displayStyle="<%= commerceAddressDisplayContext.getDisplayStyle() %>"
			displayStyleGroupId="<%= commerceAddressDisplayContext.getDisplayStyleGroupId() %>"
			entries="<%= commerceAddressSearchContainer.getResults() %>"
		>
			<div class="container-fluid container-fluid-max-xl" id="<portlet:namespace />addressesContainer">
				<aui:form action="<%= portletURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm">
					<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
					<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />

					<div class="addresses-container" id="<portlet:namespace />entriesContainer">
						<liferay-ui:search-container
							id="commerceAddresses"
							iteratorURL="<%= portletURL %>"
							searchContainer="<%= commerceAddressSearchContainer %>"
						>
							<liferay-ui:search-container-row
								className="com.liferay.commerce.model.CommerceAddress"
								keyProperty="commerceAddressId"
								modelVar="commerceAddress"
							>
								<liferay-ui:search-container-column-text
									href="<%= commerceAddressDisplayContext.getEditCommerceAddressURL(commerceAddress.getCommerceAddressId()) %>"
									property="name"
								/>

								<liferay-ui:search-container-column-text
									cssClass="table-cell-expand"
									property="street1"
								/>

								<liferay-ui:search-container-column-text
									property="city"
								/>

								<liferay-ui:search-container-column-text
									property="zip"
								/>

								<liferay-ui:search-container-column-text
									name="country"
									property="country.name"
								/>

								<%
								Region region = commerceAddress.getRegion();
								%>

								<liferay-ui:search-container-column-text
									name="region"
									value="<%= (region != null) ? HtmlUtil.escape(region.getName()) : StringPool.BLANK %>"
								/>

								<liferay-ui:search-container-column-jsp
									cssClass="entry-action-column"
									path="/address_action.jsp"
								/>
							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator
								displayStyle="list"
								markupView="lexicon"
							/>
						</liferay-ui:search-container>
					</div>
				</aui:form>

				<aui:button-row>
					<aui:button cssClass="btn-lg" href="<%= commerceAddressDisplayContext.getAddCommerceAddressURL() %>" name="addAddressButton" value='<%= LanguageUtil.get(request, "add-address") %>' />
				</aui:button-row>
			</div>
		</liferay-ddm:template-renderer>
	</c:otherwise>
</c:choose>