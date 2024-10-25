/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.exporter;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Locale;

/**
 * Provides a service for exporting Record Set records. The format this service
 * uses to export records is the format that was specified to the {@link
 * DDLExporterFactory} that created this service.
 *
 * @author Marcellus Tavares
 * @author Manuel de la Peña
 * @see    DDLExporterFactory
 */
public interface DDLExporter {

	/**
	 * Exports the record set's records as a byte array.
	 *
	 * @param  recordSetId the record set ID
	 * @return the byte values of the exported records
	 * @throws Exception if an unexpected exception occurred
	 */
	public byte[] export(long recordSetId) throws Exception;

	/**
	 * Exports the record set's records of the workflow status as a byte array.
	 *
	 * @param  recordSetId the record set ID
	 * @param  status the workflow status of the records to export
	 * @return the byte values of the exported records
	 * @throws Exception if an unexpected exception occurred
	 */
	public byte[] export(long recordSetId, int status) throws Exception;

	/**
	 * Exports a range of the record set's records as a byte array.
	 *
	 * @param  recordSetId the record set ID
	 * @param  status the workflow status of the records
	 * @param  start the lower bound of the range of records to export
	 * @param  end the upper bound of the range of records to export (not
	 *         inclusive)
	 * @return the byte values of the exported records
	 * @throws Exception if an unexpected exception occurred
	 */
	public byte[] export(long recordSetId, int status, int start, int end)
		throws Exception;

	/**
	 * Exports an ordered range of the record set's records as a byte array.
	 *
	 * @param  recordSetId the record set ID
	 * @param  status the workflow status of the records
	 * @param  start the lower bound of the range of records to export
	 * @param  end the upper bound of the range of records to export (not
	 *         inclusive)
	 * @param  orderByComparator a comparator to order the records (optionally
	 *         <code>null</code>)
	 * @return the byte values of the exported records
	 * @throws Exception if an unexpected exception occurred
	 */
	public byte[] export(
			long recordSetId, int status, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator)
		throws Exception;

	/**
	 * Returns the export format of the current DDL Exporter service instance.
	 *
	 * @return the format value of the current service instance
	 */
	public String getFormat();

	/**
	 * Returns the locale of the current DDL Exporter service instance.
	 *
	 * @return the locale of the current service instance
	 */
	public Locale getLocale();

	/**
	 * Returns the locale of the current DDL Exporter service instance.
	 *
	 * @param locale the locale used to retrieve the localized values of the
	 *        record
	 */
	public void setLocale(Locale locale);

}