/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.jdbc;

import javax.sql.DataSource;

/**
 * @author Brian Wing Shun Chan
 */
public class MappingSqlQueryFactoryUtil {

	public static <T> MappingSqlQuery<T> getMappingSqlQuery(
		DataSource dataSource, String sql, RowMapper<T> rowMapper,
		ParamSetter... paramSetters) {

		return new MappingSqlQueryImpl<>(
			dataSource, sql, rowMapper, paramSetters);
	}

}