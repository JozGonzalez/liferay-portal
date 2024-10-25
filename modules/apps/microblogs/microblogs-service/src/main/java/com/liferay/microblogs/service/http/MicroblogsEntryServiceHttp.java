/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.service.http;

import com.liferay.microblogs.service.MicroblogsEntryServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>MicroblogsEntryServiceUtil</code> service
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
public class MicroblogsEntryServiceHttp {

	public static com.liferay.microblogs.model.MicroblogsEntry
			addMicroblogsEntry(
				HttpPrincipal httpPrincipal, long userId, String content,
				int type, long parentMicroblogsEntryId, int socialRelationType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MicroblogsEntryServiceUtil.class, "addMicroblogsEntry",
				_addMicroblogsEntryParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, content, type, parentMicroblogsEntryId,
				socialRelationType, serviceContext);

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

			return (com.liferay.microblogs.model.MicroblogsEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.microblogs.model.MicroblogsEntry
			deleteMicroblogsEntry(
				HttpPrincipal httpPrincipal, long microblogsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MicroblogsEntryServiceUtil.class, "deleteMicroblogsEntry",
				_deleteMicroblogsEntryParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, microblogsEntryId);

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

			return (com.liferay.microblogs.model.MicroblogsEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
			getMicroblogsEntries(
				HttpPrincipal httpPrincipal, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MicroblogsEntryServiceUtil.class, "getMicroblogsEntries",
				_getMicroblogsEntriesParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, start, end);

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
				<com.liferay.microblogs.model.MicroblogsEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
			getMicroblogsEntries(
				HttpPrincipal httpPrincipal, String assetTagName, int start,
				int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MicroblogsEntryServiceUtil.class, "getMicroblogsEntries",
				_getMicroblogsEntriesParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetTagName, start, end);

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
				<com.liferay.microblogs.model.MicroblogsEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getMicroblogsEntriesCount(HttpPrincipal httpPrincipal)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MicroblogsEntryServiceUtil.class, "getMicroblogsEntriesCount",
				_getMicroblogsEntriesCountParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(methodKey);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getMicroblogsEntriesCount(
			HttpPrincipal httpPrincipal, String assetTagName)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MicroblogsEntryServiceUtil.class, "getMicroblogsEntriesCount",
				_getMicroblogsEntriesCountParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, assetTagName);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.microblogs.model.MicroblogsEntry
			getMicroblogsEntry(
				HttpPrincipal httpPrincipal, long microblogsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MicroblogsEntryServiceUtil.class, "getMicroblogsEntry",
				_getMicroblogsEntryParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, microblogsEntryId);

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

			return (com.liferay.microblogs.model.MicroblogsEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
			getUserMicroblogsEntries(
				HttpPrincipal httpPrincipal, long microblogsEntryUserId,
				int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MicroblogsEntryServiceUtil.class, "getUserMicroblogsEntries",
				_getUserMicroblogsEntriesParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, microblogsEntryUserId, start, end);

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
				<com.liferay.microblogs.model.MicroblogsEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List<com.liferay.microblogs.model.MicroblogsEntry>
			getUserMicroblogsEntries(
				HttpPrincipal httpPrincipal, long microblogsEntryUserId,
				int type, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MicroblogsEntryServiceUtil.class, "getUserMicroblogsEntries",
				_getUserMicroblogsEntriesParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, microblogsEntryUserId, type, start, end);

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
				<com.liferay.microblogs.model.MicroblogsEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getUserMicroblogsEntriesCount(
			HttpPrincipal httpPrincipal, long microblogsEntryUserId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MicroblogsEntryServiceUtil.class,
				"getUserMicroblogsEntriesCount",
				_getUserMicroblogsEntriesCountParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, microblogsEntryUserId);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static int getUserMicroblogsEntriesCount(
			HttpPrincipal httpPrincipal, long microblogsEntryUserId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MicroblogsEntryServiceUtil.class,
				"getUserMicroblogsEntriesCount",
				_getUserMicroblogsEntriesCountParameterTypes10);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, microblogsEntryUserId, type);

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static com.liferay.microblogs.model.MicroblogsEntry
			updateMicroblogsEntry(
				HttpPrincipal httpPrincipal, long microblogsEntryId,
				String content, int socialRelationType,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				MicroblogsEntryServiceUtil.class, "updateMicroblogsEntry",
				_updateMicroblogsEntryParameterTypes11);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, microblogsEntryId, content, socialRelationType,
				serviceContext);

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

			return (com.liferay.microblogs.model.MicroblogsEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MicroblogsEntryServiceHttp.class);

	private static final Class<?>[] _addMicroblogsEntryParameterTypes0 =
		new Class[] {
			long.class, String.class, int.class, long.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};
	private static final Class<?>[] _deleteMicroblogsEntryParameterTypes1 =
		new Class[] {long.class};
	private static final Class<?>[] _getMicroblogsEntriesParameterTypes2 =
		new Class[] {int.class, int.class};
	private static final Class<?>[] _getMicroblogsEntriesParameterTypes3 =
		new Class[] {String.class, int.class, int.class};
	private static final Class<?>[] _getMicroblogsEntriesCountParameterTypes4 =
		new Class[] {};
	private static final Class<?>[] _getMicroblogsEntriesCountParameterTypes5 =
		new Class[] {String.class};
	private static final Class<?>[] _getMicroblogsEntryParameterTypes6 =
		new Class[] {long.class};
	private static final Class<?>[] _getUserMicroblogsEntriesParameterTypes7 =
		new Class[] {long.class, int.class, int.class};
	private static final Class<?>[] _getUserMicroblogsEntriesParameterTypes8 =
		new Class[] {long.class, int.class, int.class, int.class};
	private static final Class<?>[]
		_getUserMicroblogsEntriesCountParameterTypes9 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getUserMicroblogsEntriesCountParameterTypes10 = new Class[] {
			long.class, int.class
		};
	private static final Class<?>[] _updateMicroblogsEntryParameterTypes11 =
		new Class[] {
			long.class, String.class, int.class,
			com.liferay.portal.kernel.service.ServiceContext.class
		};

}