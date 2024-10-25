/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.expando.service.http;

import com.liferay.expando.kernel.service.ExpandoColumnServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>ExpandoColumnServiceUtil</code> service
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
public class ExpandoColumnServiceHttp {

	public static com.liferay.expando.kernel.model.ExpandoColumn addColumn(
			HttpPrincipal httpPrincipal, long tableId, String name, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExpandoColumnServiceUtil.class, "addColumn",
				_addColumnParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, tableId, name, type);

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

			return (com.liferay.expando.kernel.model.ExpandoColumn)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.expando.kernel.model.ExpandoColumn addColumn(
			HttpPrincipal httpPrincipal, long tableId, String name, int type,
			Object defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExpandoColumnServiceUtil.class, "addColumn",
				_addColumnParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, tableId, name, type, defaultData);

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

			return (com.liferay.expando.kernel.model.ExpandoColumn)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteColumn(HttpPrincipal httpPrincipal, long columnId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExpandoColumnServiceUtil.class, "deleteColumn",
				_deleteColumnParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, columnId);

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

	public static com.liferay.expando.kernel.model.ExpandoColumn
			fetchExpandoColumn(HttpPrincipal httpPrincipal, long columnId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExpandoColumnServiceUtil.class, "fetchExpandoColumn",
				_fetchExpandoColumnParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, columnId);

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

			return (com.liferay.expando.kernel.model.ExpandoColumn)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.expando.kernel.model.ExpandoColumn updateColumn(
			HttpPrincipal httpPrincipal, long columnId, String name, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExpandoColumnServiceUtil.class, "updateColumn",
				_updateColumnParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, columnId, name, type);

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

			return (com.liferay.expando.kernel.model.ExpandoColumn)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.expando.kernel.model.ExpandoColumn updateColumn(
			HttpPrincipal httpPrincipal, long columnId, String name, int type,
			Object defaultData)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExpandoColumnServiceUtil.class, "updateColumn",
				_updateColumnParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, columnId, name, type, defaultData);

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

			return (com.liferay.expando.kernel.model.ExpandoColumn)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.expando.kernel.model.ExpandoColumn
			updateTypeSettings(
				HttpPrincipal httpPrincipal, long columnId, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				ExpandoColumnServiceUtil.class, "updateTypeSettings",
				_updateTypeSettingsParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, columnId, typeSettings);

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

			return (com.liferay.expando.kernel.model.ExpandoColumn)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ExpandoColumnServiceHttp.class);

	private static final Class<?>[] _addColumnParameterTypes0 = new Class[] {
		long.class, String.class, int.class
	};
	private static final Class<?>[] _addColumnParameterTypes1 = new Class[] {
		long.class, String.class, int.class, Object.class
	};
	private static final Class<?>[] _deleteColumnParameterTypes2 = new Class[] {
		long.class
	};
	private static final Class<?>[] _fetchExpandoColumnParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _updateColumnParameterTypes4 = new Class[] {
		long.class, String.class, int.class
	};
	private static final Class<?>[] _updateColumnParameterTypes5 = new Class[] {
		long.class, String.class, int.class, Object.class
	};
	private static final Class<?>[] _updateTypeSettingsParameterTypes6 =
		new Class[] {long.class, String.class};

}