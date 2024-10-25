/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.upgrade.v3_2_0;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Adolfo Pérez
 */
public class StorageQuotaUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select companyId, sum(size_) from DLFileVersion group by " +
					"companyId");
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					"insert into DLStorageQuota (mvccVersion, " +
						"dlStorageQuotaId, companyId, storageSize) values (" +
							"?, ?, ?, ?)");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong(1);
				long storageSize = resultSet.getLong(2);

				preparedStatement2.setLong(1, 0);
				preparedStatement2.setLong(2, increment());
				preparedStatement2.setLong(3, companyId);
				preparedStatement2.setLong(4, storageSize);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}