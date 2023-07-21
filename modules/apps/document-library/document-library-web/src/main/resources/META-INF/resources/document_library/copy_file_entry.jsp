<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
DLCopyEntryDisplayContext dlCopyEntryDisplayContext = new DLCopyEntryDisplayContext(request, liferayPortletResponse, themeDisplay);

dlCopyEntryDisplayContext.setViewAttributes();
%>

<div class="c-mt-3 sheet sheet-lg">
	<react:component
		module="document_library/js/DDMFolderSelector"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"copyActionURL", dlCopyEntryDisplayContext.getActionURL()
			).put(
				"fileShortcutId", dlCopyEntryDisplayContext.getFileShortcutId()
			).put(
				"itemType", "fileEntry"
			).put(
				"redirect", dlCopyEntryDisplayContext.getRedirect()
			).put(
				"selectionModalURL", dlCopyEntryDisplayContext.getSelectFolderURL()
			).put(
				"sourceFileEntryId", dlCopyEntryDisplayContext.getFileEntryId()
			).put(
				"sourceFileName", dlCopyEntryDisplayContext.getFileName()
			).build()
		%>'
	/>
</div>