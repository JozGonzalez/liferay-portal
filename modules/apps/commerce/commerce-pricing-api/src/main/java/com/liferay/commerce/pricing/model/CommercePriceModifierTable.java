/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.math.BigDecimal;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommercePriceModifier&quot; database table.
 *
 * @author Riccardo Alberti
 * @see CommercePriceModifier
 * @generated
 */
public class CommercePriceModifierTable
	extends BaseTable<CommercePriceModifierTable> {

	public static final CommercePriceModifierTable INSTANCE =
		new CommercePriceModifierTable();

	public final Column<CommercePriceModifierTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommercePriceModifierTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommercePriceModifierTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, String>
		externalReferenceCode = createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, Long>
		commercePriceModifierId = createColumn(
			"commercePriceModifierId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommercePriceModifierTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, Long> commercePriceListId =
		createColumn(
			"commercePriceListId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, String> title =
		createColumn("title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, String> target =
		createColumn(
			"target", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, BigDecimal> modifierAmount =
		createColumn(
			"modifierAmount", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, String> modifierType =
		createColumn(
			"modifierType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, Double> priority =
		createColumn(
			"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, Boolean> active =
		createColumn(
			"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, Date> displayDate =
		createColumn(
			"displayDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, Date> expirationDate =
		createColumn(
			"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, Integer> status =
		createColumn(
			"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceModifierTable, Date> statusDate =
		createColumn(
			"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CommercePriceModifierTable() {
		super("CommercePriceModifier", CommercePriceModifierTable::new);
	}

}