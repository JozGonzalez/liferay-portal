/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.repository.capabilities;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.repository.capabilities.util.DLFileEntryServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFolderServiceAdapter;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class LiferayBulkOperationCapability implements BulkOperationCapability {

	public LiferayBulkOperationCapability(
		DocumentRepository documentRepository,
		DLFileEntryServiceAdapter dlFileEntryServiceAdapter,
		DLFolderServiceAdapter dlFolderServiceAdapter) {

		_documentRepository = documentRepository;
		_dlFileEntryServiceAdapter = dlFileEntryServiceAdapter;
		_dlFolderServiceAdapter = dlFolderServiceAdapter;
	}

	@Override
	public void execute(
			Filter<?> filter, RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		_executeOnAllFileEntries(filter, repositoryModelOperation);
		_executeOnAllFolders(filter, repositoryModelOperation);
	}

	@Override
	public void execute(RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		execute(null, repositoryModelOperation);
	}

	private void _executeOnAllFileEntries(
			Filter<?> filter, RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_dlFileEntryServiceAdapter.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new RepositoryModelAddCriteriaMethod(filter));
		actionableDynamicQuery.setPerformActionMethod(
			new FileEntryPerformActionMethod(repositoryModelOperation));

		actionableDynamicQuery.performActions();
	}

	private void _executeOnAllFolders(
			Filter<?> filter, RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_dlFolderServiceAdapter.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new RepositoryModelAddCriteriaMethod(filter, true));
		actionableDynamicQuery.setPerformActionMethod(
			new FolderPerformActionMethod(repositoryModelOperation));

		actionableDynamicQuery.performActions();
	}

	private static final Map<Class<? extends Field<?>>, String> _fieldNames =
		HashMapBuilder.<Class<? extends Field<?>>, String>put(
			Field.CreateDate.class, "createDate"
		).put(
			Field.FolderId.class, "folderId"
		).build();

	private final DLFileEntryServiceAdapter _dlFileEntryServiceAdapter;
	private final DLFolderServiceAdapter _dlFolderServiceAdapter;
	private final DocumentRepository _documentRepository;

	private static class FileEntryPerformActionMethod
		implements ActionableDynamicQuery.PerformActionMethod<DLFileEntry> {

		public FileEntryPerformActionMethod(
			RepositoryModelOperation repositoryModelOperation) {

			_repositoryModelOperation = repositoryModelOperation;
		}

		@Override
		public void performAction(DLFileEntry dlFileEntry)
			throws PortalException {

			if (dlFileEntry.isInTrash()) {
				return;
			}

			FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

			fileEntry.execute(_repositoryModelOperation);
		}

		private RepositoryModelOperation _repositoryModelOperation;

	}

	private static class FolderPerformActionMethod
		implements ActionableDynamicQuery.PerformActionMethod<DLFolder> {

		public FolderPerformActionMethod(
			RepositoryModelOperation repositoryModelOperation) {

			_repositoryModelOperation = repositoryModelOperation;
		}

		@Override
		public void performAction(DLFolder dlFolder) throws PortalException {
			if (dlFolder.isMountPoint() || dlFolder.isInTrash()) {
				return;
			}

			Folder folder = new LiferayFolder(dlFolder);

			folder.execute(_repositoryModelOperation);
		}

		private final RepositoryModelOperation _repositoryModelOperation;

	}

	private class RepositoryModelAddCriteriaMethod
		implements ActionableDynamicQuery.AddCriteriaMethod {

		public RepositoryModelAddCriteriaMethod(Filter<?> filter) {
			this(filter, false);
		}

		public RepositoryModelAddCriteriaMethod(
			Filter<?> filter, boolean folder) {

			_filter = filter;
			_folder = folder;
		}

		@Override
		public void addCriteria(DynamicQuery dynamicQuery) {
			dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"repositoryId", _documentRepository.getRepositoryId()));

			if (_filter != null) {
				addFilterCriteria(dynamicQuery);
			}
		}

		protected void addFilterCriteria(DynamicQuery dynamicQuery) {
			Class<? extends Field<?>> field = _filter.getField();

			String fieldName = _fieldNames.get(field);

			if (fieldName == null) {
				throw new UnsupportedOperationException(
					"Unsupported field " + field.getName());
			}

			if (_folder && fieldName.equals("folderId")) {
				fieldName = "parentFolderId";
			}

			Operator operator = _filter.getOperator();

			Object value = _filter.getValue();

			if (operator == Operator.EQ) {
				dynamicQuery.add(RestrictionsFactoryUtil.eq(fieldName, value));
			}
			else if (operator == Operator.LE) {
				dynamicQuery.add(RestrictionsFactoryUtil.le(fieldName, value));
			}
			else if (operator == Operator.LT) {
				dynamicQuery.add(RestrictionsFactoryUtil.lt(fieldName, value));
			}
			else if (operator == Operator.GE) {
				dynamicQuery.add(RestrictionsFactoryUtil.ge(fieldName, value));
			}
			else if (operator == Operator.GT) {
				dynamicQuery.add(RestrictionsFactoryUtil.gt(fieldName, value));
			}
		}

		private final Filter<?> _filter;
		private boolean _folder;

	}

}