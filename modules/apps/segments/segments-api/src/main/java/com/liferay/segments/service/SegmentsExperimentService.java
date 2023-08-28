/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service;

import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.model.SegmentsExperiment;

import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for SegmentsExperiment. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface SegmentsExperimentService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsExperimentServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the segments experiment remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link SegmentsExperimentServiceUtil} if injection and service tracking are not available.
	 */
	public SegmentsExperiment addSegmentsExperiment(
			long segmentsExperienceId, long plid, String name,
			String description, String goal, String goalTarget,
			ServiceContext serviceContext)
		throws PortalException;

	public SegmentsExperiment deleteSegmentsExperiment(
			long segmentsExperimentId)
		throws PortalException;

	public SegmentsExperiment deleteSegmentsExperiment(
			String segmentsExperimentKey)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SegmentsExperiment fetchSegmentsExperiment(long groupId, long plid)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SegmentsExperiment fetchSegmentsExperiment(
			long segmentsExperienceId, long plid, int[] statuses)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SegmentsExperiment fetchSegmentsExperiment(
			long groupId, String segmentsExperimentKey)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SegmentsExperiment> getSegmentsExperienceSegmentsExperiments(
			long[] segmentsExperienceIds, long plid, int[] statuses, int start,
			int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SegmentsExperiment getSegmentsExperiment(long segmentsExperimentId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SegmentsExperiment getSegmentsExperiment(
			String segmentsExperimentKey)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #fetchSegmentsExperiment(long, long)}
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SegmentsExperiment> getSegmentsExperiments(
		long groupId, long plid);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SegmentsExperiment> getSegmentsExperiments(
		long segmentsExperienceId, long plid, int[] statuses,
		OrderByComparator<SegmentsExperiment> orderByComparator);

	public SegmentsExperiment runSegmentsExperiment(
			long segmentsExperimentId, double confidenceLevel,
			Map<Long, Double> segmentsExperienceIdSplitMap)
		throws PortalException;

	public SegmentsExperiment runSegmentsExperiment(
			String segmentsExperimentKey, double confidenceLevel,
			Map<String, Double> segmentsExperienceKeySplitMap)
		throws PortalException;

	public SegmentsExperiment updateSegmentsExperiment(
			long segmentsExperimentId, String name, String description,
			String goal, String goalTarget)
		throws PortalException;

	public SegmentsExperiment updateSegmentsExperimentStatus(
			long segmentsExperimentId, int status)
		throws PortalException;

	public SegmentsExperiment updateSegmentsExperimentStatus(
			long segmentsExperimentId, long winnerSegmentsExperienceId,
			int status)
		throws PortalException;

	public SegmentsExperiment updateSegmentsExperimentStatus(
			String segmentsExperimentKey, int status)
		throws PortalException;

	public SegmentsExperiment updateSegmentsExperimentStatus(
			String segmentsExperimentKey, String winnerSegmentsExperienceKey,
			int status)
		throws PortalException;

}