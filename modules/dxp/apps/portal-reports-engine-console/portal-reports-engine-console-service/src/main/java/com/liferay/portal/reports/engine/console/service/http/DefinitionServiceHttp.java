/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.reports.engine.console.service.DefinitionServiceUtil;

/**
 * Provides the HTTP utility for the
 * <code>DefinitionServiceUtil</code> service
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
public class DefinitionServiceHttp {

	public static com.liferay.portal.reports.engine.console.model.Definition
			addDefinition(
				HttpPrincipal httpPrincipal, long groupId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				long sourceId, String reportParameters, String fileName,
				java.io.InputStream inputStream,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DefinitionServiceUtil.class, "addDefinition",
				_addDefinitionParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, nameMap, descriptionMap, sourceId,
				reportParameters, fileName, inputStream, serviceContext);

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

			return (com.liferay.portal.reports.engine.console.model.Definition)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.reports.engine.console.model.Definition
			deleteDefinition(HttpPrincipal httpPrincipal, long definitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DefinitionServiceUtil.class, "deleteDefinition",
				_deleteDefinitionParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, definitionId);

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

			return (com.liferay.portal.reports.engine.console.model.Definition)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.reports.engine.console.model.Definition
			getDefinition(HttpPrincipal httpPrincipal, long definitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DefinitionServiceUtil.class, "getDefinition",
				_getDefinitionParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, definitionId);

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

			return (com.liferay.portal.reports.engine.console.model.Definition)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.portal.reports.engine.console.model.Definition>
				getDefinitions(
					HttpPrincipal httpPrincipal, long groupId,
					String definitionName, String description, String sourceId,
					String reportName, boolean andSearch, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.reports.engine.console.model.
							Definition> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DefinitionServiceUtil.class, "getDefinitions",
				_getDefinitionsParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, definitionName, description, sourceId,
				reportName, andSearch, start, end, orderByComparator);

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

			return (java.util.List
				<com.liferay.portal.reports.engine.console.model.Definition>)
					returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getDefinitionsCount(
		HttpPrincipal httpPrincipal, long groupId, String definitionName,
		String description, String sourceId, String reportName,
		boolean andSearch) {

		try {
			MethodKey methodKey = new MethodKey(
				DefinitionServiceUtil.class, "getDefinitionsCount",
				_getDefinitionsCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, definitionName, description, sourceId,
				reportName, andSearch);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.portal.reports.engine.console.model.Definition
			updateDefinition(
				HttpPrincipal httpPrincipal, long definitionId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				long sourceId, String reportParameters, String fileName,
				java.io.InputStream inputStream,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DefinitionServiceUtil.class, "updateDefinition",
				_updateDefinitionParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, definitionId, nameMap, descriptionMap, sourceId,
				reportParameters, fileName, inputStream, serviceContext);

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

			return (com.liferay.portal.reports.engine.console.model.Definition)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefinitionServiceHttp.class);

	private static final Class<?>[] _addDefinitionParameterTypes0 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, long.class,
			String.class, String.class, java.io.InputStream.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteDefinitionParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getDefinitionParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getDefinitionsParameterTypes3 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			boolean.class, int.class, int.class,
			com.liferay.portal.kernel.util.OrderByComparator.class
		};
	private static final Class<?>[] _getDefinitionsCountParameterTypes4 =
		new Class[] {
			long.class, String.class, String.class, String.class, String.class,
			boolean.class
		};
	private static final Class<?>[] _updateDefinitionParameterTypes5 =
		new Class[] {
			long.class, java.util.Map.class, java.util.Map.class, long.class,
			String.class, String.class, java.io.InputStream.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}