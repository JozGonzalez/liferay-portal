/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.document.library.web.internal.optimizer.test;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.counter.AMImageCounter;
import com.liferay.adaptive.media.image.optimizer.AMImageOptimizer;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLTrashLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class DLAMImageOptimizerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company1 = CompanyTestUtil.addCompany();

		_user1 = UserTestUtil.getAdminUser(_company1.getCompanyId());

		_group1 = GroupTestUtil.addGroup(
			_company1.getCompanyId(), _user1.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);
	}

	@After
	public void tearDown() throws Exception {
		_deleteAllAMImageConfigurationEntries(_company1.getCompanyId());
	}

	@Test
	public void testDLAMImageOptimizerOptimizesEveryAMImageConfigurationEntryInSpecificCompany()
		throws Exception {

		int count = _amImageCounter.countExpectedAMImageEntries(
			_company1.getCompanyId());

		_dlAppLocalService.addFileEntry(
			null, _user1.getUserId(), _group1.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null,
			ServiceContextTestUtil.getServiceContext(
				_group1.getGroupId(), _user1.getUserId()));

		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_addAMImageConfigurationEntry(_company1.getCompanyId());
		AMImageConfigurationEntry amImageConfigurationEntry2 =
			_addAMImageConfigurationEntry(_company1.getCompanyId());

		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));

		_amImageOptimizer.optimize(_company1.getCompanyId());

		Assert.assertEquals(
			count + 1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			count + 1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));
	}

	@Test
	public void testDLAMImageOptimizerOptimizesEveryAMImageConfigurationEntryOnlyInSpecificCompany()
		throws Exception {

		Company company2 = CompanyTestUtil.addCompany();

		User user2 = UserTestUtil.getAdminUser(company2.getCompanyId());

		Group group2 = GroupTestUtil.addGroup(
			company2.getCompanyId(), user2.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		try {
			int company1Count = _amImageCounter.countExpectedAMImageEntries(
				_company1.getCompanyId());
			int company2Count = _amImageCounter.countExpectedAMImageEntries(
				company2.getCompanyId());

			_dlAppLocalService.addFileEntry(
				null, _user1.getUserId(), _group1.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
				_getImageBytes(), null, null,
				ServiceContextTestUtil.getServiceContext(
					_group1.getGroupId(), _user1.getUserId()));
			_dlAppLocalService.addFileEntry(
				null, user2.getUserId(), group2.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
				_getImageBytes(), null, null,
				ServiceContextTestUtil.getServiceContext(
					group2.getGroupId(), user2.getUserId()));

			AMImageConfigurationEntry amImageConfigurationEntry1 =
				_addAMImageConfigurationEntry(_company1.getCompanyId());
			AMImageConfigurationEntry amImageConfigurationEntry2 =
				_addAMImageConfigurationEntry(company2.getCompanyId());

			Assert.assertEquals(
				0,
				_amImageEntryLocalService.getAMImageEntriesCount(
					_company1.getCompanyId(),
					amImageConfigurationEntry1.getUUID()));
			Assert.assertEquals(
				0,
				_amImageEntryLocalService.getAMImageEntriesCount(
					company2.getCompanyId(),
					amImageConfigurationEntry2.getUUID()));

			_amImageOptimizer.optimize(_company1.getCompanyId());

			Assert.assertEquals(
				company1Count + 1,
				_amImageEntryLocalService.getAMImageEntriesCount(
					_company1.getCompanyId(),
					amImageConfigurationEntry1.getUUID()));
			Assert.assertEquals(
				0,
				_amImageEntryLocalService.getAMImageEntriesCount(
					company2.getCompanyId(),
					amImageConfigurationEntry2.getUUID()));

			_amImageOptimizer.optimize(company2.getCompanyId());

			Assert.assertEquals(
				company1Count + 1,
				_amImageEntryLocalService.getAMImageEntriesCount(
					_company1.getCompanyId(),
					amImageConfigurationEntry1.getUUID()));
			Assert.assertEquals(
				company2Count + 1,
				_amImageEntryLocalService.getAMImageEntriesCount(
					company2.getCompanyId(),
					amImageConfigurationEntry2.getUUID()));
		}
		finally {
			_companyLocalService.deleteCompany(company2);
		}
	}

	@Test
	public void testDLAMImageOptimizerOptimizesForImagesNotInTrash()
		throws Exception {

		int count = _amImageCounter.countExpectedAMImageEntries(
			_company1.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group1.getGroupId(), _user1.getUserId());

		_dlAppLocalService.addFileEntry(
			null, _user1.getUserId(), _group1.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, serviceContext);

		FileEntry fileEntry2 = _dlAppLocalService.addFileEntry(
			null, _user1.getUserId(), _group1.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, serviceContext);

		_dlTrashLocalService.moveFileEntryToTrash(
			_user1.getUserId(), _group1.getGroupId(),
			fileEntry2.getFileEntryId());

		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_addAMImageConfigurationEntry(_company1.getCompanyId());

		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));

		_amImageOptimizer.optimize(
			_company1.getCompanyId(), amImageConfigurationEntry1.getUUID());

		Assert.assertEquals(
			count + 1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));

		_dlTrashLocalService.moveFileEntryFromTrash(
			_user1.getUserId(), _group1.getGroupId(),
			fileEntry2.getFileEntryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

		_amImageOptimizer.optimize(
			_company1.getCompanyId(), amImageConfigurationEntry1.getUUID());

		Assert.assertEquals(
			count + 2,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
	}

	@Test
	public void testDLAMImageOptimizerOptimizesForSpecificAMImageConfigurationEntry()
		throws Exception {

		int count = _amImageCounter.countExpectedAMImageEntries(
			_company1.getCompanyId());

		_dlAppLocalService.addFileEntry(
			null, _user1.getUserId(), _group1.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null,
			ServiceContextTestUtil.getServiceContext(
				_group1.getGroupId(), _user1.getUserId()));

		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_addAMImageConfigurationEntry(_company1.getCompanyId());
		AMImageConfigurationEntry amImageConfigurationEntry2 =
			_addAMImageConfigurationEntry(_company1.getCompanyId());

		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));

		_amImageOptimizer.optimize(
			_company1.getCompanyId(), amImageConfigurationEntry1.getUUID());

		Assert.assertEquals(
			count + 1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));

		_amImageOptimizer.optimize(
			_company1.getCompanyId(), amImageConfigurationEntry2.getUUID());

		Assert.assertEquals(
			count + 1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			count + 1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));
	}

	@Test
	public void testDLAMImageOptimizerOptimizesImagesWithSupportedMimeTypes()
		throws Exception {

		int count = _amImageCounter.countExpectedAMImageEntries(
			_company1.getCompanyId());

		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_addAMImageConfigurationEntry(_company1.getCompanyId());

		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));

		_amImageOptimizer.optimize(
			_company1.getCompanyId(), amImageConfigurationEntry1.getUUID());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group1.getGroupId(), _user1.getUserId());

		_dlAppLocalService.addFileEntry(
			null, _user1.getUserId(), _group1.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, serviceContext);

		_dlAppLocalService.addFileEntry(
			null, _user1.getUserId(), _group1.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			TestDataConstants.TEST_BYTE_ARRAY, null, null, serviceContext);

		Assert.assertEquals(
			count + 1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
	}

	private AMImageConfigurationEntry _addAMImageConfigurationEntry(
			long companyId)
		throws Exception {

		String amImageConfigurationEntry1Name = RandomTestUtil.randomString();

		return _amImageConfigurationHelper.addAMImageConfigurationEntry(
			companyId, amImageConfigurationEntry1Name, StringPool.BLANK,
			amImageConfigurationEntry1Name,
			HashMapBuilder.put(
				"max-height", String.valueOf(RandomTestUtil.randomLong())
			).put(
				"max-width", String.valueOf(RandomTestUtil.randomLong())
			).build());
	}

	private void _deleteAllAMImageConfigurationEntries(long companyId)
		throws Exception {

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				companyId, amImageConfigurationEntry -> true);

		for (AMImageConfigurationEntry amImageConfigurationEntry :
				amImageConfigurationEntries) {

			_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
				companyId, amImageConfigurationEntry.getUUID());
		}
	}

	private byte[] _getImageBytes() throws Exception {
		return FileUtil.getBytes(
			DLAMImageOptimizerTest.class, "dependencies/image.jpg");
	}

	@Inject
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Inject(
		filter = "adaptive.media.key=document-library",
		type = AMImageCounter.class
	)
	private AMImageCounter _amImageCounter;

	@Inject
	private AMImageEntryLocalService _amImageEntryLocalService;

	@Inject(
		filter = "adaptive.media.key=document-library",
		type = AMImageOptimizer.class
	)
	private AMImageOptimizer _amImageOptimizer;

	@DeleteAfterTestRun
	private Company _company1;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLTrashLocalService _dlTrashLocalService;

	private Group _group1;
	private User _user1;

}