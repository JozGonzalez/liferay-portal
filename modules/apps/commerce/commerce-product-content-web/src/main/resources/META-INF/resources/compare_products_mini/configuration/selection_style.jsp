<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPCompareContentMiniDisplayContext cpCompareContentMiniDisplayContext = (CPCompareContentMiniDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<aui:fieldset markupView="lexicon">
	<aui:input id="productsLimit" label="products-limit" name="preferences--productsLimit--" type="number" value="<%= cpCompareContentMiniDisplayContext.getProductsLimit() %>" />

	<aui:input checked="<%= cpCompareContentMiniDisplayContext.isSelectionStyleADT() %>" id="selectionStyleADT" label="use-adt" name="preferences--selectionStyle--" onChange='<%= liferayPortletResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="adt" />

	<aui:input checked="<%= cpCompareContentMiniDisplayContext.isSelectionStyleCustomRenderer() %>" id="selectionStyleCustomRenderer" label="use-custom-renderer" name="preferences--selectionStyle--" onChange='<%= liferayPortletResponse.getNamespace() + "chooseSelectionStyle();" %>' type="radio" value="custom" />
</aui:fieldset>

<aui:script>
	function <portlet:namespace />chooseSelectionStyle() {
		var form = window.document.querySelector(document.<portlet:namespace />fm);

		submitForm(form);
	}
</aui:script>