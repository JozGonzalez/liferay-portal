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

package com.liferay.fragment.service.impl;

import com.liferay.fragment.constants.FragmentActionKeys;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCompositionTable;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryTable;
import com.liferay.fragment.service.FragmentCompositionLocalService;
import com.liferay.fragment.service.base.FragmentEntryServiceBaseImpl;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = {
		"json.web.service.context.name=fragment",
		"json.web.service.context.path=FragmentEntry"
	},
	service = AopService.class
)
public class FragmentEntryServiceImpl extends FragmentEntryServiceBaseImpl {

	@Override
	public FragmentEntry addFragmentEntry(
			long groupId, long fragmentCollectionId, String fragmentEntryKey,
			String name, String css, String html, String js, boolean cacheable,
			String configuration, String icon, long previewFileEntryId,
			int type, String typeOptions, int status,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.addFragmentEntry(
			getUserId(), groupId, fragmentCollectionId, fragmentEntryKey, name,
			css, html, js, cacheable, configuration, icon, previewFileEntryId,
			type, typeOptions, status, serviceContext);
	}

	@Override
	public FragmentEntry copyFragmentEntry(
			long groupId, long sourceFragmentEntryId, long fragmentCollectionId,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.copyFragmentEntry(
			getUserId(), groupId, sourceFragmentEntryId, fragmentCollectionId,
			serviceContext);
	}

	@Override
	public void deleteFragmentEntries(long[] fragmentEntriesIds)
		throws PortalException {

		for (long fragmentEntryId : fragmentEntriesIds) {
			FragmentEntry fragmentEntry =
				fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

			_portletResourcePermission.check(
				getPermissionChecker(), fragmentEntry.getGroupId(),
				FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

			fragmentEntryLocalService.deleteFragmentEntry(fragmentEntry);
		}
	}

	@Override
	public FragmentEntry deleteFragmentEntry(long fragmentEntryId)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.deleteFragmentEntry(fragmentEntryId);
	}

	@Override
	public FragmentEntry fetchDraft(long primaryKey) {
		return fragmentEntryLocalService.fetchDraft(primaryKey);
	}

	@Override
	public FragmentEntry fetchFragmentEntry(long fragmentEntryId)
		throws PortalException {

		return fragmentEntryLocalService.fetchFragmentEntry(fragmentEntryId);
	}

	@Override
	public FragmentEntry getDraft(long primaryKey) throws PortalException {
		return fragmentEntryLocalService.getDraft(primaryKey);
	}

	@Override
	public List<Object> getFragmentCompositionsAndFragmentEntries(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		OrderByComparator<?> orderByComparator) {

		Table<?> tempFragmentEntryTable = _getFragmentCompositionGroupByStep(
			groupId, fragmentCollectionId, StringPool.BLANK, status
		).unionAll(
			_getFragmentEntryGroupByStep(
				groupId, fragmentCollectionId, StringPool.BLANK, status)
		).as(
			"tempFragmentCompositionsAndFragmentEntriesTable"
		);

		DSLQuery dslQuery = DSLQueryFactoryUtil.select(
			tempFragmentEntryTable
		).from(
			tempFragmentEntryTable
		).orderBy(
			tempFragmentEntryTable, orderByComparator
		).limit(
			start, end
		);

		List<Object> models = new ArrayList<>();

		for (Object[] array :
				(List<Object[]>)fragmentEntryPersistence.dslQuery(dslQuery)) {

			long fragmentCompositionId = (Long)array[0];
			Object object = null;

			if (fragmentCompositionId > 0) {
				object =
					_fragmentCompositionLocalService.fetchFragmentComposition(
						fragmentCompositionId);
			}
			else {
				object = fragmentEntryLocalService.fetchFragmentEntry(
					(Long)array[1]);
			}

			models.add(object);
		}

		return models;
	}

	@Override
	public List<Object> getFragmentCompositionsAndFragmentEntries(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end, OrderByComparator<?> orderByComparator) {

		try {
			Table<?> tempFragmentEntryTable =
				_getFragmentCompositionGroupByStep(
					groupId, fragmentCollectionId, name, status
				).unionAll(
					_getFragmentEntryGroupByStep(
						groupId, fragmentCollectionId, name, status)
				).as(
					"tempFragmentCompositionsAndFragmentEntriesTable"
				);

			DSLQuery dslQuery = DSLQueryFactoryUtil.select(
				tempFragmentEntryTable
			).from(
				tempFragmentEntryTable
			).orderBy(
				tempFragmentEntryTable, orderByComparator
			).limit(
				start, end
			);

			List<Object> models = new ArrayList<>();

			for (Object[] array :
					(List<Object[]>)fragmentEntryPersistence.dslQuery(
						dslQuery)) {

				long fragmentCompositionId = (Long)array[0];

				Object object = null;

				if (fragmentCompositionId > 0) {
					object =
						_fragmentCompositionLocalService.
							fetchFragmentComposition(fragmentCompositionId);
				}
				else {
					long fragmentEntryId = (Long)array[1];

					object = fragmentEntryLocalService.fetchFragmentEntry(
						fragmentEntryId);
				}

				models.add(object);
			}

			return models;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Override
	public int getFragmentCompositionsAndFragmentEntriesCount(
		long groupId, long fragmentCollectionId, int status) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(status);

		return fragmentEntryFinder.countFC_FE_ByG_FCI(
			groupId, fragmentCollectionId, queryDefinition);
	}

	@Override
	public int getFragmentCompositionsAndFragmentEntriesCount(
		long groupId, long fragmentCollectionId, String name, int status) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(status);

		return fragmentEntryFinder.countFC_FE_ByG_FCI_N(
			groupId, fragmentCollectionId, name, queryDefinition);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(long fragmentCollectionId) {
		return fragmentEntryLocalService.getFragmentEntries(
			fragmentCollectionId);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end) {

		return fragmentEntryPersistence.findByG_FCI(
			groupId, fragmentCollectionId, start, end);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return fragmentEntryPersistence.findByG_FCI(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntriesByName(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator) {

		return fragmentEntryPersistence.findByG_FCI_LikeN(
			groupId, fragmentCollectionId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0], start,
			end, orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntriesByNameAndStatus(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return fragmentEntryPersistence.findByG_FCI_LikeN(
				groupId, fragmentCollectionId,
				_customSQL.keywords(name, false, WildcardMode.SURROUND)[0],
				start, end, orderByComparator);
		}

		return fragmentEntryPersistence.findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0], status,
			start, end, orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntriesByStatus(
		long groupId, long fragmentCollectionId, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return fragmentEntryPersistence.findByG_FCI(
				groupId, fragmentCollectionId);
		}

		return fragmentEntryLocalService.getFragmentEntries(
			groupId, fragmentCollectionId, status);
	}

	@Override
	public List<FragmentEntry> getFragmentEntriesByStatus(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return fragmentEntryPersistence.findByG_FCI(
				groupId, fragmentCollectionId, start, end, orderByComparator);
		}

		return fragmentEntryPersistence.findByG_FCI_S(
			groupId, fragmentCollectionId, status, start, end,
			orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntriesByType(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return fragmentEntryPersistence.findByG_FCI_T(
			groupId, fragmentCollectionId, type, start, end, orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntriesByTypeAndStatus(
		long groupId, long fragmentCollectionId, int type, int status) {

		return fragmentEntryPersistence.findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status);
	}

	@Override
	public List<FragmentEntry> getFragmentEntriesByTypeAndStatus(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return fragmentEntryPersistence.findByG_FCI_T(
				groupId, fragmentCollectionId, type, start, end,
				orderByComparator);
		}

		return fragmentEntryPersistence.findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, start, end,
			orderByComparator);
	}

	@Override
	public int getFragmentEntriesCount(
		long groupId, long fragmentCollectionId) {

		return fragmentEntryPersistence.countByG_FCI(
			groupId, fragmentCollectionId);
	}

	@Override
	public int getFragmentEntriesCountByName(
		long groupId, long fragmentCollectionId, String name) {

		return fragmentEntryPersistence.countByG_FCI_LikeN(
			groupId, fragmentCollectionId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0]);
	}

	@Override
	public int getFragmentEntriesCountByNameAndStatus(
		long groupId, long fragmentCollectionId, String name, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return fragmentEntryPersistence.countByG_FCI_LikeN(
				groupId, fragmentCollectionId,
				_customSQL.keywords(name, false, WildcardMode.SURROUND)[0]);
		}

		return fragmentEntryPersistence.countByG_FCI_LikeN_S(
			groupId, fragmentCollectionId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0], status);
	}

	@Override
	public int getFragmentEntriesCountByStatus(
		long groupId, long fragmentCollectionId, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return fragmentEntryPersistence.countByG_FCI(
				groupId, fragmentCollectionId);
		}

		return fragmentEntryPersistence.countByG_FCI_S(
			groupId, fragmentCollectionId, status);
	}

	@Override
	public int getFragmentEntriesCountByType(
		long groupId, long fragmentCollectionId, int type) {

		return fragmentEntryPersistence.countByG_FCI_T(
			groupId, fragmentCollectionId, type);
	}

	@Override
	public int getFragmentEntriesCountByTypeAndStatus(
		long groupId, long fragmentCollectionId, int type, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return fragmentEntryPersistence.countByG_FCI_T(
				groupId, fragmentCollectionId, type);
		}

		return fragmentEntryPersistence.countByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status);
	}

	@Override
	public String[] getTempFileNames(long groupId, String folderName)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.getTempFileNames(
			getUserId(), groupId, folderName);
	}

	@Override
	public FragmentEntry moveFragmentEntry(
			long fragmentEntryId, long fragmentCollectionId)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.moveFragmentEntry(
			fragmentEntryId, fragmentCollectionId);
	}

	@Override
	public FragmentEntry publishDraft(FragmentEntry draftFragmentEntry)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), draftFragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.publishDraft(draftFragmentEntry);
	}

	@Override
	public FragmentEntry updateDraft(FragmentEntry draftFragmentEntry)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), draftFragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.updateDraft(draftFragmentEntry);
	}

	@Override
	public FragmentEntry updateFragmentEntry(FragmentEntry fragmentEntry)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.updateFragmentEntry(fragmentEntry);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, boolean cacheable)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntryId, cacheable);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, long previewFileEntryId)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntryId, previewFileEntryId);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, long fragmentCollectionId, String name,
			String css, String html, String js, boolean cacheable,
			String configuration, String icon, long previewFileEntryId,
			int status)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.updateFragmentEntry(
			getUserId(), fragmentEntryId, fragmentCollectionId, name, css, html,
			js, cacheable, configuration, icon, previewFileEntryId,
			fragmentEntry.getTypeOptions(), status);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, long fragmentCollectionId, String name,
			String css, String html, String js, boolean cacheable,
			String configuration, String icon, long previewFileEntryId,
			String typeOptions, int status)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.updateFragmentEntry(
			getUserId(), fragmentEntryId, fragmentCollectionId, name, css, html,
			js, cacheable, configuration, icon, previewFileEntryId, typeOptions,
			status);
	}

	@Override
	public FragmentEntry updateFragmentEntry(long fragmentEntryId, String name)
		throws PortalException {

		FragmentEntry fragmentEntry =
			fragmentEntryLocalService.getFragmentEntry(fragmentEntryId);

		_portletResourcePermission.check(
			getPermissionChecker(), fragmentEntry.getGroupId(),
			FragmentActionKeys.MANAGE_FRAGMENT_ENTRIES);

		return fragmentEntryLocalService.updateFragmentEntry(
			fragmentEntryId, name);
	}

	private GroupByStep _getFragmentCompositionGroupByStep(
		long groupId, long fragmentCollectionId, String name, int status) {

		return DSLQueryFactoryUtil.selectDistinct(
			FragmentCompositionTable.INSTANCE.fragmentCompositionId.as(
				"fragmentCompositionId"),
			new Scalar<>(
				0L
			).as(
				"fragmentEntryId"
			),
			FragmentCompositionTable.INSTANCE.createDate.as("createDate"),
			FragmentCompositionTable.INSTANCE.modifiedDate.as("modifiedDate"),
			FragmentCompositionTable.INSTANCE.name.as("name")
		).from(
			FragmentCompositionTable.INSTANCE
		).where(
			FragmentCompositionTable.INSTANCE.groupId.eq(
				groupId
			).and(
				FragmentCompositionTable.INSTANCE.fragmentCollectionId.eq(
					fragmentCollectionId)
			).and(
				() -> {
					if (Validator.isNotNull(name)) {
						return DSLFunctionFactoryUtil.lower(
							FragmentCompositionTable.INSTANCE.name
						).like(
							_customSQL.keywords(
								name, false, WildcardMode.SURROUND)[0]
						);
					}

					return null;
				}
			).and(
				FragmentCompositionTable.INSTANCE.status.eq(status)
			)
		);
	}

	private GroupByStep _getFragmentEntryGroupByStep(
		long groupId, long fragmentCollectionId, String name, int status) {

		return DSLQueryFactoryUtil.selectDistinct(
			new Scalar<>(
				0L
			).as(
				"fragmentCompositionId"
			),
			FragmentEntryTable.INSTANCE.fragmentEntryId.as("fragmentEntryId"),
			FragmentEntryTable.INSTANCE.createDate.as("createDate"),
			FragmentEntryTable.INSTANCE.modifiedDate.as("modifiedDate"),
			FragmentEntryTable.INSTANCE.name.as("name")
		).from(
			FragmentEntryTable.INSTANCE
		).where(
			FragmentEntryTable.INSTANCE.groupId.eq(
				groupId
			).and(
				FragmentEntryTable.INSTANCE.fragmentCollectionId.eq(
					fragmentCollectionId)
			).and(
				FragmentEntryTable.INSTANCE.head.eq(
					true
				).or(
					FragmentEntryTable.INSTANCE.headId.eq(
						FragmentEntryTable.INSTANCE.fragmentEntryId)
				).withParentheses()
			).and(
				() -> {
					if (Validator.isNotNull(name)) {
						return DSLFunctionFactoryUtil.lower(
							FragmentEntryTable.INSTANCE.name
						).like(
							_customSQL.keywords(
								name, false, WildcardMode.SURROUND)[0]
						);
					}

					return null;
				}
			).and(
				FragmentEntryTable.INSTANCE.status.eq(status)
			)
		);
	}

	@Reference
	private CustomSQL _customSQL;

	@Reference
	private FragmentCompositionLocalService _fragmentCompositionLocalService;

	@Reference(
		target = "(resource.name=" + FragmentConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}