<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Calendar calendar = (Calendar)row.getObject();
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="<%= StringPool.BLANK %>"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= CalendarPermission.contains(permissionChecker, calendar, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_calendar.jsp" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="calendarId" value="<%= String.valueOf(calendar.getCalendarId()) %>" />
			<portlet:param name="calendarResourceId" value="<%= String.valueOf(calendar.getCalendarResourceId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= CalendarPermission.contains(permissionChecker, calendar, ActionKeys.VIEW) %>">
		<liferay-portlet:resourceURL id="exportCalendar" var="exportURL">
			<portlet:param name="calendarId" value="<%= String.valueOf(calendar.getCalendarId()) %>" />
		</liferay-portlet:resourceURL>

		<liferay-ui:icon
			message="export"
			url="<%= exportURL %>"
		/>
	</c:if>

	<c:if test="<%= CalendarPermission.contains(permissionChecker, calendar, ActionKeys.UPDATE) %>">
		<liferay-portlet:actionURL name="importCalendar" var="importURL">
			<portlet:param name="calendarId" value="<%= String.valueOf(calendar.getCalendarId()) %>" />
		</liferay-portlet:actionURL>

		<%
		StringBundler sb = new StringBundler(5);

		sb.append("javascript:");
		sb.append(liferayPortletResponse.getNamespace());
		sb.append("importCalendar('");
		sb.append(importURL);
		sb.append("');");
		%>

		<liferay-ui:icon
			message="import"
			url="<%= sb.toString() %>"
		/>
	</c:if>

	<c:if test="<%= CalendarPermission.contains(permissionChecker, calendar, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= Calendar.class.getName() %>"
			modelResourceDescription="<%= calendar.getName(locale) %>"
			resourceGroupId="<%= calendar.getGroupId() %>"
			resourcePrimKey="<%= String.valueOf(calendar.getCalendarId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= enableRSS %>">
		<liferay-portlet:resourceURL id="calendarBookingsRSS" varImpl="calendarRSSURL">
			<portlet:param name="calendarId" value="<%= String.valueOf(calendar.getCalendarId()) %>" />
		</liferay-portlet:resourceURL>

		<liferay-rss:rss
			resourceURL="<%= calendarRSSURL %>"
		/>
	</c:if>

	<c:if test="<%= CalendarPermission.contains(permissionChecker, calendar, ActionKeys.DELETE) && !calendar.isDefaultCalendar() %>">
		<portlet:actionURL name="deleteCalendar" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="calendarId" value="<%= String.valueOf(calendar.getCalendarId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>