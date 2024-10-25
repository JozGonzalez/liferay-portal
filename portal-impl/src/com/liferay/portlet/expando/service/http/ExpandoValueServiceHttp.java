/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.expando.service.http;

import com.liferay.expando.kernel.service.ExpandoValueServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ExpandoValueServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExpandoValueServiceHttp {

	public static com.liferay.expando.kernel.model.ExpandoValue addValue(
			HttpPrincipal httpPrincipal, long companyId, String className,
			String tableName, String columnName, long classPK, Object data)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExpandoValueServiceUtil.class, "addValue",
				_addValueParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, className, tableName, columnName, classPK,
				data);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.expando.kernel.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.expando.kernel.model.ExpandoValue addValue(
			HttpPrincipal httpPrincipal, long companyId, String className,
			String tableName, String columnName, long classPK, String data)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExpandoValueServiceUtil.class, "addValue",
				_addValueParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, className, tableName, columnName, classPK,
				data);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.expando.kernel.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void addValues(
			HttpPrincipal httpPrincipal, long companyId, String className,
			String tableName, long classPK,
			java.util.Map<String, java.io.Serializable> attributeValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExpandoValueServiceUtil.class, "addValues",
				_addValuesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, className, tableName, classPK,
				attributeValues);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.Map<String, java.io.Serializable> getData(
			HttpPrincipal httpPrincipal, long companyId, String className,
			String tableName, java.util.Collection<String> columnNames,
			long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExpandoValueServiceUtil.class, "getData",
				_getDataParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, className, tableName, columnNames,
				classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.Map<String, java.io.Serializable>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.io.Serializable getData(
			HttpPrincipal httpPrincipal, long companyId, String className,
			String tableName, String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExpandoValueServiceUtil.class, "getData",
				_getDataParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, className, tableName, columnName,
				classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.io.Serializable)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.kernel.json.JSONObject getJSONData(
			HttpPrincipal httpPrincipal, long companyId, String className,
			String tableName, String columnName, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExpandoValueServiceUtil.class, "getJSONData",
				_getJSONDataParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, className, tableName, columnName,
				classPK);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.portal.kernel.json.JSONObject)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ExpandoValueServiceHttp.class);

	private static final Class<?>[] _addValueParameterTypes0 = new Class[] {
		long.class, String.class, String.class, String.class, long.class,
		Object.class
	};
	private static final Class<?>[] _addValueParameterTypes1 = new Class[] {
		long.class, String.class, String.class, String.class, long.class,
		String.class
	};
	private static final Class<?>[] _addValuesParameterTypes2 = new Class[] {
		long.class, String.class, String.class, long.class, java.util.Map.class
	};
	private static final Class<?>[] _getDataParameterTypes3 = new Class[] {
		long.class, String.class, String.class, java.util.Collection.class,
		long.class
	};
	private static final Class<?>[] _getDataParameterTypes4 = new Class[] {
		long.class, String.class, String.class, String.class, long.class
	};
	private static final Class<?>[] _getJSONDataParameterTypes5 = new Class[] {
		long.class, String.class, String.class, String.class, long.class
	};

}