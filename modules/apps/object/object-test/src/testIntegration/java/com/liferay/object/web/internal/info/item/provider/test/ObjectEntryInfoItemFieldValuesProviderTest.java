/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.builder.AttachmentObjectFieldBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@FeatureFlags({"LPS-169992", "LPS-176083"})
@RunWith(Arquillian.class)
public class ObjectEntryInfoItemFieldValuesProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_childObjectDefinition = _addObjectDefinition(
			new AttachmentObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"attachmentObjectFieldName"
			).objectFieldSettings(
				Arrays.asList(
					_createObjectFieldSetting("acceptedFileExtensions", "txt"),
					_createObjectFieldSetting(
						"fileSource", "documentsAndMedia"),
					_createObjectFieldSetting("maximumFileSize", "100"))
			).build());

		_childObjectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_childObjectDefinition.getObjectDefinitionId());

		_parentObjectDefinition = _addObjectDefinition(
			new TextObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString())
			).name(
				"parentTextObjectFieldName"
			).build());

		_parentObjectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_parentObjectDefinition.getObjectDefinitionId());

		_objectRelationshipLocalService.addObjectRelationship(
			TestPropsValues.getUserId(),
			_parentObjectDefinition.getObjectDefinitionId(),
			_childObjectDefinition.getObjectDefinitionId(), 0,
			ObjectRelationshipConstants.DELETION_TYPE_CASCADE,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"oneToManyRelationshipName",
			ObjectRelationshipConstants.TYPE_ONE_TO_MANY);
	}

	@Test
	public void testObjectEntryInfoItemFieldValuesProvider() throws Exception {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "test.txt",
			ContentTypes.TEXT, RandomTestUtil.randomBytes(), null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		InfoItemFieldValuesProvider<ObjectEntry> infoItemFieldValuesProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class,
				_childObjectDefinition.getClassName());

		String parentTextObjectFieldNameValue = RandomTestUtil.randomString();

		ObjectEntry parentObjectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_parentObjectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"parentTextObjectFieldName", parentTextObjectFieldNameValue
			).build(),
			ServiceContextTestUtil.getServiceContext());

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_childObjectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"r_oneToManyRelationshipName_" +
					_parentObjectDefinition.getPKObjectFieldName(),
				parentObjectEntry.getObjectEntryId()
			).put(
				"attachmentObjectFieldName", fileEntry.getFileEntryId()
			).build(),
			ServiceContextTestUtil.getServiceContext());

		ObjectAction objectAction = _objectActionLocalService.addObjectAction(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			objectEntry.getObjectDefinitionId(), true, StringPool.BLANK,
			RandomTestUtil.randomString(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			RandomTestUtil.randomString(),
			ObjectActionExecutorConstants.KEY_GROOVY,
			ObjectActionTriggerConstants.KEY_STANDALONE,
			UnicodePropertiesBuilder.put(
				"script", StringPool.BLANK
			).build());

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(objectEntry);

		Assert.assertNotNull(infoItemFieldValues);

		InfoFieldValue<Object> infoLocalizedValue =
			infoItemFieldValues.getInfoFieldValue(objectAction.getName());

		Map<Locale, String> labelMap = objectAction.getLabelMap();

		for (Map.Entry<Locale, String> entry : labelMap.entrySet()) {
			Assert.assertEquals(
				entry.getValue(), infoLocalizedValue.getValue(entry.getKey()));
		}

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			_childObjectDefinition.getObjectDefinitionId(),
			"attachmentObjectFieldName");

		InfoFieldValue<Object> downloadURLInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue(
				objectField.getObjectFieldId() + "#downloadURL");

		Assert.assertEquals(
			HttpComponentsUtil.removeParameter(
				_dlURLHelper.getDownloadURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK),
				"t"),
			HttpComponentsUtil.removeParameter(
				String.valueOf(downloadURLInfoFieldValue.getValue()), "t"));

		_assertInfoFieldValue(
			"#fileName", infoItemFieldValues, objectField,
			fileEntry.getFileName());
		_assertInfoFieldValue(
			"#mimeType", infoItemFieldValues, objectField,
			fileEntry.getMimeType());
		_assertInfoFieldValue(
			"#size", infoItemFieldValues, objectField, fileEntry.getSize());

		InfoFieldValue<Object> parentTextObjectFieldNameInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("parentTextObjectFieldName");

		Assert.assertEquals(
			parentTextObjectFieldNameValue,
			parentTextObjectFieldNameInfoFieldValue.getValue());
	}

	private ObjectDefinition _addObjectDefinition(ObjectField objectField)
		throws Exception {

		return _objectDefinitionLocalService.addCustomObjectDefinition(
			TestPropsValues.getUserId(), 0, false, false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"A" + RandomTestUtil.randomString(), null, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			true, ObjectDefinitionConstants.SCOPE_SITE,
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
			Arrays.asList(objectField));
	}

	private void _assertInfoFieldValue(
		String infoFieldName, InfoItemFieldValues infoItemFieldValues,
		ObjectField objectField, Object expectedValue) {

		InfoFieldValue<Object> fileNameInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue(
				objectField.getObjectFieldId() + infoFieldName);

		Assert.assertEquals(expectedValue, fileNameInfoFieldValue.getValue());
	}

	private ObjectFieldSetting _createObjectFieldSetting(
		String name, String value) {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.createObjectFieldSetting(0L);

		objectFieldSetting.setName(name);
		objectFieldSetting.setValue(value);

		return objectFieldSetting;
	}

	@DeleteAfterTestRun
	private ObjectDefinition _childObjectDefinition;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLURLHelper _dlURLHelper;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Inject
	private ObjectActionLocalService _objectActionLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	@Inject
	private ObjectFieldLocalService _objectFieldLocalService;

	@Inject
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Inject
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _parentObjectDefinition;

}