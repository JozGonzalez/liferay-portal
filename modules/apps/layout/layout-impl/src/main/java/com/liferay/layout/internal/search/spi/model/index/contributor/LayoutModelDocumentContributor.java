/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.search.spi.model.index.contributor;

import com.liferay.layout.content.LayoutContentProvider;
import com.liferay.layout.model.LayoutLocalization;
import com.liferay.layout.service.LayoutLocalizationLocalService;
import com.liferay.layout.util.LayoutServiceContextHelper;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vagner B.C
 */
@Component(
	property = "indexer.class.name=com.liferay.portal.kernel.model.Layout",
	service = ModelDocumentContributor.class
)
public class LayoutModelDocumentContributor
	implements ModelDocumentContributor<Layout> {

	public static final String CLASS_NAME = Layout.class.getName();

	@Override
	public void contribute(Document document, Layout layout) {
		if (layout.isSystem() ||
			(layout.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			return;
		}

		document.addText(
			Field.DEFAULT_LANGUAGE_ID, layout.getDefaultLanguageId());
		document.addLocalizedText(Field.NAME, layout.getNameMap());
		document.addKeyword(Field.STATUS, _getStatus(layout));

		_addLayoutContentFields(document, layout);

		for (String languageId : layout.getAvailableLanguageIds()) {
			Locale locale = LocaleUtil.fromLanguageId(languageId);

			document.addText(
				Field.getLocalizedName(locale, Field.TITLE),
				layout.getName(locale));
		}

		document.addText(Field.TYPE, layout.getType());
		document.addText(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));
		document.addLocalizedKeyword(
			"localized_title",
			_localization.populateLocalizationMap(
				layout.getNameMap(), layout.getDefaultLanguageId(),
				layout.getGroupId()),
			true, true);
	}

	private void _addLayoutContentFields(Document document, Layout layout) {
		if (!layout.isTypeContent() || !layout.isPublished()) {
			return;
		}

		Map<String, String> layoutContentMap = new HashMap<>();

		List<LayoutLocalization> layoutLocalizations =
			_layoutLocalizationLocalService.getLayoutLocalizations(
				layout.getPlid());

		for (LayoutLocalization layoutLocalization : layoutLocalizations) {
			layoutContentMap.put(
				layoutLocalization.getLanguageId(),
				layoutLocalization.getContent());
		}

		try (AutoCloseable autoCloseable =
				_layoutServiceContextHelper.getServiceContextAutoCloseable(
					layout)) {

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

			for (Locale locale :
					_language.getAvailableLocales(layout.getGroupId())) {

				String languageId = LocaleUtil.toLanguageId(locale);

				if (layoutContentMap.containsKey(languageId)) {
					continue;
				}

				layoutContentMap.put(
					languageId,
					_layoutContentProvider.getLayoutContent(
						themeDisplay.getRequest(), themeDisplay.getResponse(),
						layout, locale));
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get layout content for PLID " + layout.getPlid(),
					exception);
			}
		}

		for (Map.Entry<String, String> entry : layoutContentMap.entrySet()) {
			document.addText(
				Field.getLocalizedName(entry.getKey(), Field.CONTENT),
				entry.getValue());
		}
	}

	private int _getStatus(Layout layout) {
		if (layout.isPublished()) {
			return WorkflowConstants.STATUS_APPROVED;
		}

		return WorkflowConstants.STATUS_DRAFT;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutModelDocumentContributor.class);

	@Reference
	private Language _language;

	@Reference
	private LayoutContentProvider _layoutContentProvider;

	@Reference
	private LayoutLocalizationLocalService _layoutLocalizationLocalService;

	@Reference
	private LayoutServiceContextHelper _layoutServiceContextHelper;

	@Reference
	private Localization _localization;

}