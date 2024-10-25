<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String referer = request.getHeader(HttpHeaders.REFERER);

String redirect = GetterUtil.getString(request.getAttribute(WebKeys.REDIRECT), referer);
%>

<liferay-ui:header
	backURL='<%= Validator.isNotNull(redirect) ? redirect : "javascript:history.go(-1);" %>'
	title="error"
/>

<liferay-ui:error exception="<%= NoSuchGroupException.class %>" message="the-site-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchLayoutException.class %>" message="the-page-could-not-be-found" />
<liferay-ui:error exception="<%= NoSuchRoleException.class %>" message="the-role-could-not-be-found" />

<liferay-ui:error-principal />