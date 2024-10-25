/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DDLRecordVersion&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersion
 * @generated
 */
public class DDLRecordVersionTable extends BaseTable<DDLRecordVersionTable> {

	public static final DDLRecordVersionTable INSTANCE =
		new DDLRecordVersionTable();

	public final Column<DDLRecordVersionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDLRecordVersionTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDLRecordVersionTable, Long> recordVersionId =
		createColumn(
			"recordVersionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDLRecordVersionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordVersionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordVersionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordVersionTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordVersionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDLRecordVersionTable, Long> DDMStorageId =
		createColumn(
			"DDMStorageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordVersionTable, Long> recordSetId = createColumn(
		"recordSetId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordVersionTable, String> recordSetVersion =
		createColumn(
			"recordSetVersion", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<DDLRecordVersionTable, Long> recordId = createColumn(
		"recordId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordVersionTable, String> version = createColumn(
		"version", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordVersionTable, Integer> displayIndex =
		createColumn(
			"displayIndex", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<DDLRecordVersionTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<DDLRecordVersionTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordVersionTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<DDLRecordVersionTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private DDLRecordVersionTable() {
		super("DDLRecordVersion", DDLRecordVersionTable::new);
	}

}