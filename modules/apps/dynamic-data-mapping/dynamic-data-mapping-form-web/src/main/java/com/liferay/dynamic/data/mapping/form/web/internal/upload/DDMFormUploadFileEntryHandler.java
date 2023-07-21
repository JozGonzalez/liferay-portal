/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.upload;

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.constants.DDMFormConstants;
import com.liferay.dynamic.data.mapping.form.web.internal.security.permission.resource.DDMFormInstancePermission;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = DDMFormUploadFileEntryHandler.class)
public class DDMFormUploadFileEntryHandler implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		File file = null;

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"file")) {

			long formInstanceId = ParamUtil.getLong(
				uploadPortletRequest, "formInstanceId");
			long groupId = ParamUtil.getLong(uploadPortletRequest, "groupId");
			long folderId = ParamUtil.getLong(uploadPortletRequest, "folderId");

			file = FileUtil.createTempFile(inputStream);

			String fileName = uploadPortletRequest.getFileName("file");

			DDMFormUploadValidator.validateFileSize(file, fileName);

			long objectFieldId = ParamUtil.getLong(
				uploadPortletRequest, "objectFieldId");

			if (objectFieldId > 0) {
				_validateAttachmentObjectField(fileName, objectFieldId);
			}

			DDMFormUploadValidator.validateFileExtension(fileName);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)uploadPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return addFileEntry(
				formInstanceId, groupId, folderId, file, fileName,
				MimeTypesUtil.getContentType(file, fileName), themeDisplay);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	protected FileEntry addFileEntry(
			long formInstanceId, long groupId, long folderId, File file,
			String fileName, String mimeType, ThemeDisplay themeDisplay)
		throws PortalException {

		if (!DDMFormInstancePermission.contains(
				themeDisplay.getPermissionChecker(), formInstanceId,
				DDMActionKeys.ADD_FORM_INSTANCE_RECORD)) {

			throw new PrincipalException.MustHavePermission(
				themeDisplay.getPermissionChecker(),
				DDMFormInstance.class.getName(), formInstanceId,
				DDMActionKeys.ADD_FORM_INSTANCE_RECORD);
		}

		long userId = _getDDMFormDefaultUserId(themeDisplay.getCompanyId());

		String uniqueFileName = PortletFileRepositoryUtil.getUniqueFileName(
			groupId, folderId, fileName);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			null, groupId, userId, DDMFormInstance.class.getName(), 0,
			DDMFormConstants.SERVICE_NAME, folderId, file, uniqueFileName,
			mimeType, true);
	}

	private long _getDDMFormDefaultUserId(long companyId)
		throws PortalException {

		return _userLocalService.getUserIdByScreenName(
			companyId, DDMFormConstants.DDM_FORM_DEFAULT_USER_SCREEN_NAME);
	}

	private void _validateAttachmentObjectField(
			String fileName, long objectFieldId)
		throws PortalException {

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectFieldId, "acceptedFileExtensions");

		String value = objectFieldSetting.getValue();

		if (!ArrayUtil.contains(
				value.split("\\s*,\\s*"), FileUtil.getExtension(fileName),
				true)) {

			throw new ObjectEntryValuesException.InvalidFileExtension(
				FileUtil.getExtension(fileName), fileName);
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private UserLocalService _userLocalService;

}