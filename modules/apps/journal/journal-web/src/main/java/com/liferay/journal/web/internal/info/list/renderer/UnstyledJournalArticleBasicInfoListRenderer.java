/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.info.list.renderer;

import com.liferay.info.list.renderer.InfoListRenderer;
import com.liferay.info.taglib.list.renderer.UnstyledBasicInfoListRenderer;
import com.liferay.journal.model.JournalArticle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(service = InfoListRenderer.class)
public class UnstyledJournalArticleBasicInfoListRenderer
	extends BaseJournalArticleBasicInfoListRenderer
	implements UnstyledBasicInfoListRenderer<JournalArticle> {
}