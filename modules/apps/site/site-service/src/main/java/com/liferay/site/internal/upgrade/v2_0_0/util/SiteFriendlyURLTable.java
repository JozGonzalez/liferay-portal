/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class SiteFriendlyURLTable {

	public static final String TABLE_NAME = "SiteFriendlyURL";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"siteFriendlyURLId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"groupId", Types.BIGINT},
		{"friendlyURL", Types.VARCHAR},
		{"languageId", Types.VARCHAR},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("siteFriendlyURLId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("friendlyURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("languageId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table SiteFriendlyURL (uuid_ VARCHAR(75) null,siteFriendlyURLId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,groupId LONG,friendlyURL VARCHAR(75) null,languageId VARCHAR(75) null,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table SiteFriendlyURL";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_FF899B2F on SiteFriendlyURL (companyId, friendlyURL[$COLUMN_LENGTH:75$])",
		"create unique index IX_7A3B7A2C on SiteFriendlyURL (companyId, groupId, languageId[$COLUMN_LENGTH:75$])",
		"create index IX_E6D46A97 on SiteFriendlyURL (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_82D4AAD9 on SiteFriendlyURL (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}