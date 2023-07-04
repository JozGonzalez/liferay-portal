/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.login.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.PasswordPolicyLocalService;
import com.liferay.portal.kernel.service.PasswordPolicyRelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.passwordpoliciesadmin.util.test.PasswordPolicyTestUtil;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alvaro Saugar
 * @author Olivér Kecskeméty
 */
@RunWith(Arquillian.class)
public class ForgotPasswordMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testSendPasswordReminderToLockedOutUser() throws Exception {
		_createUser();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.captcha.configuration.CaptchaConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"sendPasswordCaptchaEnabled", false
					).build())) {

			List<Ticket> ticketsBefore = _ticketLocalService.getTickets(
				_user.getCompanyId(), User.class.getName(), _user.getUserId());

			_mvcActionCommand.processAction(
				_getMockLiferayPortletActionRequest(),
				new MockLiferayPortletActionResponse());

			List<Ticket> ticketsAfter = _ticketLocalService.getTickets(
				_user.getCompanyId(), User.class.getName(), _user.getUserId());

			Assert.assertTrue(
				(ticketsBefore.size() + 1) == ticketsAfter.size());
		}
	}

	private void _createUser() throws Exception {
		_user = UserTestUtil.addUser();

		_user.setLockout(true);
		_user.setLockoutDate(new Date());

		_userLocalService.updateUser(_user);

		_passwordPolicyRelLocalService.deletePasswordPolicyRel(
			User.class.getName(), _user.getUserId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(_user.getUserId());

		_testPasswordPolicy = PasswordPolicyTestUtil.addPasswordPolicy(
			serviceContext);

		_testPasswordPolicy.setChangeable(true);
		_testPasswordPolicy.setLockout(true);
		_testPasswordPolicy.setLockoutDuration(0);
		_testPasswordPolicy.setResetTicketMaxAge(10);

		_testPasswordPolicy = _passwordPolicyLocalService.updatePasswordPolicy(
			_testPasswordPolicy);

		_userLocalService.addPasswordPolicyUsers(
			_testPasswordPolicy.getPasswordPolicyId(),
			new long[] {_user.getUserId()});
	}

	private MockLiferayPortletActionRequest
			_getMockLiferayPortletActionRequest()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			CompanyLocalServiceUtil.fetchCompany(
				TestPropsValues.getCompanyId()));
		themeDisplay.setUser(_user);

		mockLiferayPortletActionRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG, null);
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayPortletActionRequest.addParameter(
			"login", _user.getEmailAddress());

		return mockLiferayPortletActionRequest;
	}

	@DeleteAfterTestRun
	private static User _user;

	@Inject(
		filter = "mvc.command.name=/login/forgot_password",
		type = MVCActionCommand.class
	)
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private PasswordPolicyLocalService _passwordPolicyLocalService;

	@Inject
	private PasswordPolicyRelLocalService _passwordPolicyRelLocalService;

	@DeleteAfterTestRun
	private PasswordPolicy _testPasswordPolicy;

	@Inject
	private TicketLocalService _ticketLocalService;

	@Inject
	private UserLocalService _userLocalService;

}