/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.membership.policy.usergroup;

import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserGroupTestUtil;
import com.liferay.portal.security.membership.policy.BaseMembershipPolicyTestCase;

import org.junit.After;
import org.junit.Before;

/**
 * @author Roberto Díaz
 */
public abstract class BaseUserGroupMembershipPolicyTestCase
	extends BaseMembershipPolicyTestCase {

	public static long[] getForbiddenUserGroupIds() {
		return _forbiddenUserGroupIds;
	}

	public static long[] getRequiredUserGroupIds() {
		return _requiredUserGroupIds;
	}

	public static long[] getStandardUserGroupIds() {
		return _standardUserGroupIds;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		userGroup = UserGroupTestUtil.addUserGroup();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_forbiddenUserGroupIds = new long[2];
		_requiredUserGroupIds = new long[2];
		_standardUserGroupIds = new long[2];
	}

	protected long[] addForbiddenUserGroups() throws Exception {
		UserGroup forbiddenUserGroup1 = UserGroupTestUtil.addUserGroup();

		_forbiddenUserGroupIds[0] = forbiddenUserGroup1.getUserGroupId();

		UserGroup forbiddenUserGroup2 = UserGroupTestUtil.addUserGroup();

		_forbiddenUserGroupIds[1] = forbiddenUserGroup2.getUserGroupId();

		return _forbiddenUserGroupIds;
	}

	protected long[] addRequiredUserGroups() throws Exception {
		UserGroup requiredUserGroup1 = UserGroupTestUtil.addUserGroup();

		_requiredUserGroupIds[0] = requiredUserGroup1.getUserGroupId();

		UserGroup requiredUserGroup2 = UserGroupTestUtil.addUserGroup();

		_requiredUserGroupIds[1] = requiredUserGroup2.getUserGroupId();

		return _requiredUserGroupIds;
	}

	protected long[] addStandardUserGroups() throws Exception {
		UserGroup standardUserGroup1 = UserGroupTestUtil.addUserGroup();

		_standardUserGroupIds[0] = standardUserGroup1.getUserGroupId();

		UserGroup standardUserGroup2 = UserGroupTestUtil.addUserGroup();

		_standardUserGroupIds[1] = standardUserGroup2.getUserGroupId();

		return _standardUserGroupIds;
	}

	@DeleteAfterTestRun
	protected UserGroup userGroup;

	private static long[] _forbiddenUserGroupIds = new long[2];
	private static long[] _requiredUserGroupIds = new long[2];
	private static long[] _standardUserGroupIds = new long[2];

}