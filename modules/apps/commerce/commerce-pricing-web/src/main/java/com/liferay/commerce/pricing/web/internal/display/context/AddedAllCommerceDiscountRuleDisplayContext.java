/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.display.context;

import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.commerce.product.item.selector.criterion.CPDefinitionItemSelectorCriterion;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class AddedAllCommerceDiscountRuleDisplayContext {

	public AddedAllCommerceDiscountRuleDisplayContext(
		CommerceDiscountRuleService commerceDiscountRuleService,
		CPDefinitionService cpDefinitionService,
		HttpServletRequest httpServletRequest, ItemSelector itemSelector) {

		_commerceDiscountRuleService = commerceDiscountRuleService;
		_cpDefinitionService = cpDefinitionService;
		_httpServletRequest = httpServletRequest;
		_itemSelector = itemSelector;
	}

	public CommerceDiscountRule getCommerceDiscountRule()
		throws PortalException {

		if (_commerceDiscountRule != null) {
			return _commerceDiscountRule;
		}

		long commerceDiscountRuleId = ParamUtil.getLong(
			_httpServletRequest, "commerceDiscountRuleId");

		if (commerceDiscountRuleId > 0) {
			_commerceDiscountRule =
				_commerceDiscountRuleService.getCommerceDiscountRule(
					commerceDiscountRuleId);
		}

		return _commerceDiscountRule;
	}

	public List<CPDefinition> getCPDefinitions() throws PortalException {
		List<CPDefinition> cpDefinitions = new ArrayList<>();

		String[] cpDefinitionIds = StringUtil.split(getTypeSettings());

		for (String cpDefinitionId : cpDefinitionIds) {
			CPDefinition cpDefinition = _cpDefinitionService.fetchCPDefinition(
				GetterUtil.getLong(cpDefinitionId));

			if (cpDefinition != null) {
				cpDefinitions.add(cpDefinition);
			}
		}

		return cpDefinitions;
	}

	public String getItemSelectorUrl() throws PortalException {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest);

		CPDefinitionItemSelectorCriterion cpDefinitionItemSelectorCriterion =
			new CPDefinitionItemSelectorCriterion();

		cpDefinitionItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new UUIDItemSelectorReturnType()));

		String checkedCPDefinitionIds = StringUtil.merge(
			getCheckedCPDefinitionIds());

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory, "productDefinitionsSelectItem",
				cpDefinitionItemSelectorCriterion)
		).setParameter(
			"checkedCPDefinitionIds", checkedCPDefinitionIds
		).setParameter(
			"disabledCPDefinitionIds", checkedCPDefinitionIds
		).buildString();
	}

	public String getTypeSettings() throws PortalException {
		CommerceDiscountRule commerceDiscountRule = getCommerceDiscountRule();

		if (commerceDiscountRule == null) {
			return StringPool.BLANK;
		}

		String type = BeanParamUtil.getString(
			commerceDiscountRule, _httpServletRequest, "type");

		return commerceDiscountRule.getSettingsProperty(type);
	}

	protected long[] getCheckedCPDefinitionIds() throws PortalException {
		return ListUtil.toLongArray(
			getCPDefinitions(), CPDefinition.CP_DEFINITION_ID_ACCESSOR);
	}

	private CommerceDiscountRule _commerceDiscountRule;
	private final CommerceDiscountRuleService _commerceDiscountRuleService;
	private final CPDefinitionService _cpDefinitionService;
	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;

}