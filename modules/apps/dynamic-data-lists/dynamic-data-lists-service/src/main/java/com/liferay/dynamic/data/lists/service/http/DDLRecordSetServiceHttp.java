/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.service.http;

import com.liferay.dynamic.data.lists.service.DDLRecordSetServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>DDLRecordSetServiceUtil</code> service
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
public class DDLRecordSetServiceHttp {

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			addRecordSet(
				HttpPrincipal httpPrincipal, long groupId, long ddmStructureId,
				String recordSetKey,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				int minDisplayRows, int scope,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordSetServiceUtil.class, "addRecordSet",
				_addRecordSetParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, ddmStructureId, recordSetKey, nameMap,
				descriptionMap, minDisplayRows, scope, serviceContext);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecordSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static void deleteRecordSet(
			HttpPrincipal httpPrincipal, long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordSetServiceUtil.class, "deleteRecordSet",
				_deleteRecordSetParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordSetId);

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

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			fetchRecordSet(HttpPrincipal httpPrincipal, long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordSetServiceUtil.class, "fetchRecordSet",
				_fetchRecordSetParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordSetId);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecordSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			getRecordSet(HttpPrincipal httpPrincipal, long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordSetServiceUtil.class, "getRecordSet",
				_getRecordSetParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordSetId);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecordSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.lists.model.DDLRecordSet> getRecordSets(
			HttpPrincipal httpPrincipal, long[] groupIds) {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordSetServiceUtil.class, "getRecordSets",
				_getRecordSetsParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupIds);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.lists.model.DDLRecordSet>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.lists.model.DDLRecordSet> search(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			String keywords, int scope, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecordSet>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordSetServiceUtil.class, "search",
				_searchParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, keywords, scope, start, end,
				orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.lists.model.DDLRecordSet>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.dynamic.data.lists.model.DDLRecordSet> search(
			HttpPrincipal httpPrincipal, long companyId, long groupId,
			String name, String description, int scope, boolean andOperator,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecordSet>
					orderByComparator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordSetServiceUtil.class, "search",
				_searchParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, name, description, scope,
				andOperator, start, end, orderByComparator);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.dynamic.data.lists.model.DDLRecordSet>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long groupId,
		String keywords, int scope) {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordSetServiceUtil.class, "searchCount",
				_searchCountParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, keywords, scope);

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

	public static int searchCount(
		HttpPrincipal httpPrincipal, long companyId, long groupId, String name,
		String description, int scope, boolean andOperator) {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordSetServiceUtil.class, "searchCount",
				_searchCountParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, groupId, name, description, scope,
				andOperator);

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

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			updateMinDisplayRows(
				HttpPrincipal httpPrincipal, long recordSetId,
				int minDisplayRows,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordSetServiceUtil.class, "updateMinDisplayRows",
				_updateMinDisplayRowsParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordSetId, minDisplayRows, serviceContext);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecordSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			updateRecordSet(
				HttpPrincipal httpPrincipal, long recordSetId,
				com.liferay.dynamic.data.mapping.storage.DDMFormValues
					settingsDDMFormValues)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordSetServiceUtil.class, "updateRecordSet",
				_updateRecordSetParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordSetId, settingsDDMFormValues);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecordSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			updateRecordSet(
				HttpPrincipal httpPrincipal, long recordSetId,
				long ddmStructureId,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				int minDisplayRows,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordSetServiceUtil.class, "updateRecordSet",
				_updateRecordSetParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, recordSetId, ddmStructureId, nameMap, descriptionMap,
				minDisplayRows, serviceContext);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecordSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSet
			updateRecordSet(
				HttpPrincipal httpPrincipal, long groupId, long ddmStructureId,
				String recordSetKey,
				java.util.Map<java.util.Locale, String> nameMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				int minDisplayRows,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				DDLRecordSetServiceUtil.class, "updateRecordSet",
				_updateRecordSetParameterTypes12);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, groupId, ddmStructureId, recordSetKey, nameMap,
				descriptionMap, minDisplayRows, serviceContext);

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

			return (com.liferay.dynamic.data.lists.model.DDLRecordSet)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDLRecordSetServiceHttp.class);

	private static final Class<?>[] _addRecordSetParameterTypes0 = new Class[] {
		long.class, long.class, String.class, java.util.Map.class,
		java.util.Map.class, int.class, int.class,
		com.liferay.portal.kernel.service.ServiceContext.class
	};
	private static final Class<?>[] _deleteRecordSetParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _fetchRecordSetParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getRecordSetParameterTypes3 = new Class[] {
		long.class
	};
	private static final Class<?>[] _getRecordSetsParameterTypes4 =
		new Class[] {long[].class};
	private static final Class<?>[] _searchParameterTypes5 = new Class[] {
		long.class, long.class, String.class, int.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchParameterTypes6 = new Class[] {
		long.class, long.class, String.class, String.class, int.class,
		boolean.class, int.class, int.class,
		com.liferay.portal.kernel.util.OrderByComparator.class
	};
	private static final Class<?>[] _searchCountParameterTypes7 = new Class[] {
		long.class, long.class, String.class, int.class
	};
	private static final Class<?>[] _searchCountParameterTypes8 = new Class[] {
		long.class, long.class, String.class, String.class, int.class,
		boolean.class
	};
	private static final Class<?>[] _updateMinDisplayRowsParameterTypes9 =
		new Class[] {
			long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateRecordSetParameterTypes10 =
		new Class[] {
			long.class,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues.class
		};
	private static final Class<?>[] _updateRecordSetParameterTypes11 =
		new Class[] {
			long.class, long.class, java.util.Map.class, java.util.Map.class,
			int.class, com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _updateRecordSetParameterTypes12 =
		new Class[] {
			long.class, long.class, String.class, java.util.Map.class,
			java.util.Map.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}