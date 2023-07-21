<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<%
WikiPageInfoPanelDisplayContext wikiPageInfoPanelDisplayContext = new WikiPageInfoPanelDisplayContext(request);

WikiPage wikiPage = wikiPageInfoPanelDisplayContext.getFirstPage();

List<WikiPage> incomingLinkPages = WikiPageLocalServiceUtil.getIncomingLinks(wikiPage.getNodeId(), wikiPage.getTitle());
List<WikiPage> outgoingLinkPages = WikiPageLocalServiceUtil.getOutgoingLinks(wikiPage.getNodeId(), wikiPage.getTitle());

boolean hasIncomingLinkPages = ListUtil.isNotEmpty(incomingLinkPages);
boolean hasOutgoingLinkPages = ListUtil.isNotEmpty(outgoingLinkPages);
%>

<div>
	<c:choose>
		<c:when test="<%= hasIncomingLinkPages || hasOutgoingLinkPages %>">
			<liferay-ui:panel-container
				extended="<%= true %>"
				id="wikiPageLinks"
				markupView="lexicon"
				persistState="<%= true %>"
			>
				<liferay-ui:panel
					collapsible="<%= true %>"
					extended="<%= hasIncomingLinkPages %>"
					markupView="lexicon"
					title="incoming-links"
				>
					<c:choose>
						<c:when test="<%= hasIncomingLinkPages %>">
							<dl>

								<%
								for (WikiPage incomingLinkPage : incomingLinkPages) {
									WikiNode wikiNode = incomingLinkPage.getNode();
								%>

									<dt class="h5">
										<h4>
											<a
												class="text-default" href="<%=
PortletURLBuilder.createRenderURL(
										liferayPortletResponse
									).setMVCRenderCommandName(
										"/wiki/view"
									).setRedirect(
										currentURL
									).setParameter(
										"nodeName", wikiNode.getName()
									).setParameter(
										"title", incomingLinkPage.getTitle()
									).buildString() %>"><%= incomingLinkPage.getTitle() %></a
											>
										</h4>
									</dt>
									<dd>
										<small>
											<aui:workflow-status markupView="lexicon" showLabel="<%= false %>" status="<%= incomingLinkPage.getStatus() %>" />
										</small>
									</dd>

								<%
								}
								%>

							</dl>
						</c:when>
						<c:otherwise>
							<div class="alert alert-info">
								<liferay-ui:message key="there-are-no-pages-that-link-to-this-page" />
							</div>
						</c:otherwise>
					</c:choose>
				</liferay-ui:panel>

				<liferay-ui:panel
					collapsible="<%= true %>"
					extended="<%= hasOutgoingLinkPages %>"
					markupView="lexicon"
					title="outgoing-links"
				>
					<c:choose>
						<c:when test="<%= hasOutgoingLinkPages %>">
							<dl>

								<%
								for (WikiPage outgoingLinkPage : outgoingLinkPages) {
								%>

									<c:choose>
										<c:when test="<%= outgoingLinkPage.isNew() %>">
											<dt class="h5">
												<h4 class="text-truncate">
													<a class="text-default" href="<%= outgoingLinkPage.getTitle() %>"><%= outgoingLinkPage.getTitle() %></a>
												</h4>
											</dt>
										</c:when>
										<c:otherwise>

											<%
											WikiNode wikiNode = outgoingLinkPage.getNode();
											%>

											<dt class="h5">
												<h4 class="text-truncate">
													<a
														class="text-default" href="<%=
PortletURLBuilder.createRenderURL(
												liferayPortletResponse
											).setMVCRenderCommandName(
												"/wiki/view"
											).setRedirect(
												currentURL
											).setParameter(
												"nodeName", wikiNode.getName()
											).setParameter(
												"title", outgoingLinkPage.getTitle()
											).buildString() %>"><%= outgoingLinkPage.getTitle() %></a
													>
												</h4>
											</dt>
											<dd>
												<small>
													<aui:workflow-status markupView="lexicon" showLabel="<%= false %>" status="<%= outgoingLinkPage.getStatus() %>" />
												</small>
											</dd>
										</c:otherwise>
									</c:choose>

								<%
								}
								%>

							</dl>
						</c:when>
						<c:otherwise>
							<div class="alert alert-info">
								<liferay-ui:message key="this-page-has-no-links" />
							</div>
						</c:otherwise>
					</c:choose>
				</liferay-ui:panel>
			</liferay-ui:panel-container>
		</c:when>
		<c:otherwise>
			<div class="alert alert-info">
				<liferay-ui:message key="this-page-has-no-links" />
			</div>
		</c:otherwise>
	</c:choose>
</div>