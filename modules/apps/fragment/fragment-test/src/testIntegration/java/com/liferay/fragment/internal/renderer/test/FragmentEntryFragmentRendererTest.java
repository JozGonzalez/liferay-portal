/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.renderer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.util.FragmentTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.struts.Definition;
import com.liferay.portal.struts.TilesUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class FragmentEntryFragmentRendererTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0);

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
	}

	@Test
	public void testCacheableFragmentEntryLink() throws Exception {
		FragmentEntry fragmentEntry = _getFragmentEntry(true);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				_defaultSegmentsExperienceId, _layout.getPlid(),
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), fragmentEntry.getConfiguration(), null,
				StringPool.BLANK, 0, null, fragmentEntry.getType(),
				_serviceContext);

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(fragmentEntryLink);

		defaultFragmentRendererContext.setLocale(LocaleUtil.US);

		_fragmentRenderer.render(
			defaultFragmentRendererContext, _getMockHttpServletRequest(),
			new MockHttpServletResponse());

		PortalCache<String, String> portalCache =
			(PortalCache<String, String>)_multiVMPool.getPortalCache(
				FragmentEntryLink.class.getName());

		String content = portalCache.get(_getPortalCacheKey(fragmentEntryLink));

		Assert.assertTrue(content.contains(fragmentEntry.getHtml()));
	}

	@Test
	public void testNoncacheableFragmentEntryLink() throws Exception {
		FragmentEntry fragmentEntry = _getFragmentEntry(false);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				_defaultSegmentsExperienceId, _layout.getPlid(),
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), fragmentEntry.getConfiguration(), null,
				StringPool.BLANK, 0, null, fragmentEntry.getType(),
				_serviceContext);

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(fragmentEntryLink);

		defaultFragmentRendererContext.setLocale(LocaleUtil.US);

		_fragmentRenderer.render(
			defaultFragmentRendererContext, _getMockHttpServletRequest(),
			new MockHttpServletResponse());

		PortalCache<String, String> portalCache =
			(PortalCache<String, String>)_multiVMPool.getPortalCache(
				FragmentEntryLink.class.getName());

		Assert.assertNull(
			portalCache.get(_getPortalCacheKey(fragmentEntryLink)));
	}

	@Test
	public void testUpdateCacheableFragmentEntryLink() throws Exception {
		FragmentEntry fragmentEntry = _getFragmentEntry(true);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(),
				_defaultSegmentsExperienceId, _layout.getPlid(),
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), fragmentEntry.getConfiguration(), null,
				StringPool.BLANK, 0, null, fragmentEntry.getType(),
				_serviceContext);

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(fragmentEntryLink);

		defaultFragmentRendererContext.setLocale(LocaleUtil.US);

		_fragmentRenderer.render(
			defaultFragmentRendererContext, _getMockHttpServletRequest(),
			new MockHttpServletResponse());

		fragmentEntry = _fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntry.getUserId(), fragmentEntry.getFragmentEntryId(),
			fragmentEntry.getFragmentCollectionId(), fragmentEntry.getName(),
			fragmentEntry.getCss(), "Updated Fragment Entry HTML",
			fragmentEntry.getJs(), fragmentEntry.isCacheable(),
			fragmentEntry.getConfiguration(), fragmentEntry.getIcon(),
			fragmentEntry.getStatus(), WorkflowConstants.STATUS_APPROVED);

		_fragmentEntryLinkLocalService.updateLatestChanges(
			fragmentEntryLink.getFragmentEntryLinkId());

		defaultFragmentRendererContext = new DefaultFragmentRendererContext(
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				fragmentEntryLink.getFragmentEntryLinkId()));

		defaultFragmentRendererContext.setLocale(LocaleUtil.US);

		_fragmentRenderer.render(
			defaultFragmentRendererContext, _getMockHttpServletRequest(),
			new MockHttpServletResponse());

		PortalCache<String, String> portalCache =
			(PortalCache<String, String>)_multiVMPool.getPortalCache(
				FragmentEntryLink.class.getName());

		String content = portalCache.get(_getPortalCacheKey(fragmentEntryLink));

		Assert.assertTrue(content.contains(fragmentEntry.getHtml()));
	}

	private FragmentEntry _getFragmentEntry(boolean cacheable)
		throws Exception {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(_group.getGroupId());

		return _fragmentEntryLocalService.addFragmentEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			fragmentCollection.getFragmentCollectionId(), null,
			RandomTestUtil.randomString(), StringPool.BLANK,
			"Fragment Entry HTML", StringPool.BLANK, cacheable, null, null, 0,
			FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);
	}

	private HttpServletRequest _getMockHttpServletRequest() throws Exception {
		HttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			TilesUtil.DEFINITION,
			new Definition(StringPool.BLANK, new HashMap<>()));
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(mockHttpServletRequest));

		return mockHttpServletRequest;
	}

	private String _getPortalCacheKey(FragmentEntryLink fragmentEntryLink) {
		StringBundler sb = new StringBundler(5);

		sb.append(fragmentEntryLink.getFragmentEntryLinkId());
		sb.append(StringPool.DASH);
		sb.append(LocaleUtil.US);
		sb.append(StringPool.DASH);
		sb.append(fragmentEntryLink.getSegmentsExperienceId());

		return sb.toString();
	}

	private ThemeDisplay _getThemeDisplay(HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());

		themeDisplay.setLocale(LocaleUtil.US);
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setUser(TestPropsValues.getUser());

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return themeDisplay;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private long _defaultSegmentsExperienceId;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Inject(
		filter = "component.name=com.liferay.fragment.internal.renderer.FragmentEntryFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;

}