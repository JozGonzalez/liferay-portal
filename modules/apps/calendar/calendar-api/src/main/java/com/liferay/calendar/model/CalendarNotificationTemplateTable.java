/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CalendarNotificationTemplate&quot; database table.
 *
 * @author Eduardo Lundgren
 * @see CalendarNotificationTemplate
 * @generated
 */
public class CalendarNotificationTemplateTable
	extends BaseTable<CalendarNotificationTemplateTable> {

	public static final CalendarNotificationTemplateTable INSTANCE =
		new CalendarNotificationTemplateTable();

	public final Column<CalendarNotificationTemplateTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CalendarNotificationTemplateTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CalendarNotificationTemplateTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CalendarNotificationTemplateTable, Long>
		calendarNotificationTemplateId = createColumn(
			"calendarNotificationTemplateId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CalendarNotificationTemplateTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CalendarNotificationTemplateTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CalendarNotificationTemplateTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CalendarNotificationTemplateTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CalendarNotificationTemplateTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CalendarNotificationTemplateTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CalendarNotificationTemplateTable, Long> calendarId =
		createColumn(
			"calendarId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CalendarNotificationTemplateTable, String>
		notificationType = createColumn(
			"notificationType", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CalendarNotificationTemplateTable, String>
		notificationTypeSettings = createColumn(
			"notificationTypeSettings", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CalendarNotificationTemplateTable, String>
		notificationTemplateType = createColumn(
			"notificationTemplateType", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CalendarNotificationTemplateTable, String> subject =
		createColumn(
			"subject", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CalendarNotificationTemplateTable, Clob> body =
		createColumn("body", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<CalendarNotificationTemplateTable, Date>
		lastPublishDate = createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private CalendarNotificationTemplateTable() {
		super(
			"CalendarNotificationTemplate",
			CalendarNotificationTemplateTable::new);
	}

}