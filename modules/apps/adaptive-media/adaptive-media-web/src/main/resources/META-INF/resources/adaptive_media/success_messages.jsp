<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/adaptive_media/init.jsp" %>

<liferay-ui:success key="configurationEntriesDeleted">

	<%
	List<AMImageConfigurationEntry> amImageConfigurationEntries = (List<AMImageConfigurationEntry>)SessionMessages.get(renderRequest, "configurationEntriesDeleted");
	%>

	<c:choose>
		<c:when test="<%= amImageConfigurationEntries.size() == 1 %>">
			<liferay-ui:message arguments="<%= HtmlUtil.escape(amImageConfigurationEntries.get(0).getName()) %>" key="x-was-deleted-successfully" translateArguments="<%= false %>" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message arguments='<%= HtmlUtil.escape(ListUtil.toString(amImageConfigurationEntries, "name")) %>' key="x-were-deleted-successfully" translateArguments="<%= false %>" />
		</c:otherwise>
	</c:choose>
</liferay-ui:success>

<liferay-ui:success key="configurationEntryEnabled">

	<%
	AMImageConfigurationEntry amImageConfigurationEntry = (AMImageConfigurationEntry)SessionMessages.get(renderRequest, "configurationEntryEnabled");
	%>

	<liferay-ui:message arguments="<%= HtmlUtil.escape(amImageConfigurationEntry.getName()) %>" key="x-was-enabled-successfully" translateArguments="<%= false %>" />
</liferay-ui:success>

<liferay-ui:success key="configurationEntryDisabled">

	<%
	AMImageConfigurationEntry amImageConfigurationEntry = (AMImageConfigurationEntry)SessionMessages.get(renderRequest, "configurationEntryDisabled");
	%>

	<liferay-ui:message arguments="<%= HtmlUtil.escape(amImageConfigurationEntry.getName()) %>" key="x-was-disabled-successfully" translateArguments="<%= false %>" />
</liferay-ui:success>

<liferay-ui:success key="configurationEntryUpdatedAndIDRenamed">

	<%
	AMImageConfigurationEntry amImageConfigurationEntry = (AMImageConfigurationEntry)SessionMessages.get(renderRequest, "configurationEntryUpdatedAndIDRenamed");
	%>

	<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(amImageConfigurationEntry.getName()), amImageConfigurationEntry.getUUID()} %>" key="x-was-saved-successfully.-the-id-was-duplicated-and-renamed-to-x" translateArguments="<%= false %>" />
</liferay-ui:success>

<liferay-ui:success key="optimizeImages" message="processing-images.-this-could-take-a-while-depending-on-the-number-of-images" />