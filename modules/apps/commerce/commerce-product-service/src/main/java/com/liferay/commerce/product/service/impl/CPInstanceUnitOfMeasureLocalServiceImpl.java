/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.exception.CPInstanceUnitOfMeasureIncrementalOrderQuantityException;
import com.liferay.commerce.product.exception.CPInstanceUnitOfMeasureKeyException;
import com.liferay.commerce.product.exception.CPInstanceUnitOfMeasureRateException;
import com.liferay.commerce.product.exception.CPInstanceUnitOfMeasureSkuException;
import com.liferay.commerce.product.exception.DuplicateCPInstanceUnitOfMeasureKeyException;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.base.CPInstanceUnitOfMeasureLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPInstanceUnitOfMeasure",
	service = AopService.class
)
public class CPInstanceUnitOfMeasureLocalServiceImpl
	extends CPInstanceUnitOfMeasureLocalServiceBaseImpl {

	@Override
	public CPInstanceUnitOfMeasure addCPInstanceUnitOfMeasure(
			long userId, long cpInstanceId, boolean active,
			BigDecimal incrementalOrderQuantity, String key,
			Map<Locale, String> nameMap, int precision, boolean primary,
			double priority, BigDecimal rate, String sku)
		throws PortalException {

		_validateCPInstanceUnitOfMeasureIncrementalOrderQuantity(
			incrementalOrderQuantity);
		_validateCPInstanceUnitOfMeasureKey(cpInstanceId, 0, key);
		_validateCPInstanceUnitOfMeasureRate(rate);
		_validateCPInstanceUnitOfMeasureSKU(sku);

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			cpInstanceUnitOfMeasurePersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		cpInstanceUnitOfMeasure.setCompanyId(user.getCompanyId());
		cpInstanceUnitOfMeasure.setUserId(user.getUserId());
		cpInstanceUnitOfMeasure.setUserName(user.getFullName());

		cpInstanceUnitOfMeasure.setCPInstanceId(cpInstanceId);
		cpInstanceUnitOfMeasure.setActive(active);
		cpInstanceUnitOfMeasure.setIncrementalOrderQuantity(
			_normalizeCPInstanceUnitOfMeasureBaseDecimalQuantity(
				incrementalOrderQuantity, precision));
		cpInstanceUnitOfMeasure.setKey(key);
		cpInstanceUnitOfMeasure.setNameMap(nameMap);
		cpInstanceUnitOfMeasure.setPrecision(precision);
		cpInstanceUnitOfMeasure.setPrimary(primary);
		cpInstanceUnitOfMeasure.setPriority(priority);
		cpInstanceUnitOfMeasure.setRate(rate);
		cpInstanceUnitOfMeasure.setSku(sku);

		if (cpInstanceUnitOfMeasure.isPrimary()) {
			_updatePrimary(
				cpInstanceUnitOfMeasure.getCPInstanceUnitOfMeasureId(),
				cpInstanceId);
		}

		return cpInstanceUnitOfMeasurePersistence.update(
			cpInstanceUnitOfMeasure);
	}

	@Override
	public CPInstanceUnitOfMeasure fetchCPInstanceUnitOfMeasure(
		long cpInstanceId, String key) {

		return cpInstanceUnitOfMeasurePersistence.fetchByC_K(cpInstanceId, key);
	}

	@Override
	public CPInstanceUnitOfMeasure getCPInstanceUnitOfMeasure(
			long cpInstanceId, String key)
		throws PortalException {

		return cpInstanceUnitOfMeasurePersistence.findByC_K(cpInstanceId, key);
	}

	@Override
	public List<CPInstanceUnitOfMeasure> getCPInstanceUnitOfMeasures(
		long cpInstanceId, int start, int end,
		OrderByComparator<CPInstanceUnitOfMeasure> orderByComparator) {

		return cpInstanceUnitOfMeasurePersistence.findByCPInstanceId(
			cpInstanceId, start, end, orderByComparator);
	}

	@Override
	public CPInstanceUnitOfMeasure updateCPInstanceUnitOfMeasure(
			long cpInstanceUnitOfMeasureId, long cpInstanceId, boolean active,
			BigDecimal incrementalOrderQuantity, String key,
			Map<Locale, String> nameMap, int precision, boolean primary,
			double priority, BigDecimal rate, String sku)
		throws PortalException {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			cpInstanceUnitOfMeasurePersistence.findByPrimaryKey(
				cpInstanceUnitOfMeasureId);

		_validateCPInstanceUnitOfMeasureIncrementalOrderQuantity(
			incrementalOrderQuantity);
		_validateCPInstanceUnitOfMeasureKey(
			cpInstanceId, cpInstanceUnitOfMeasureId, key);
		_validateCPInstanceUnitOfMeasureRate(rate);
		_validateCPInstanceUnitOfMeasureSKU(sku);

		cpInstanceUnitOfMeasure.setActive(active);
		cpInstanceUnitOfMeasure.setIncrementalOrderQuantity(
			_normalizeCPInstanceUnitOfMeasureBaseDecimalQuantity(
				incrementalOrderQuantity, precision));
		cpInstanceUnitOfMeasure.setKey(key);
		cpInstanceUnitOfMeasure.setNameMap(nameMap);
		cpInstanceUnitOfMeasure.setPrecision(precision);
		cpInstanceUnitOfMeasure.setPrimary(primary);
		cpInstanceUnitOfMeasure.setPriority(priority);
		cpInstanceUnitOfMeasure.setRate(rate);
		cpInstanceUnitOfMeasure.setSku(sku);

		if (cpInstanceUnitOfMeasure.isPrimary()) {
			_updatePrimary(
				cpInstanceUnitOfMeasure.getCPInstanceUnitOfMeasureId(),
				cpInstanceId);
		}

		return cpInstanceUnitOfMeasurePersistence.update(
			cpInstanceUnitOfMeasure);
	}

	private BigDecimal _normalizeCPInstanceUnitOfMeasureBaseDecimalQuantity(
		BigDecimal baseDecimalQuantity, int precision) {

		return baseDecimalQuantity.setScale(precision, RoundingMode.HALF_UP);
	}

	private void _updatePrimary(
		long cpInstanceUnitOfMeasureId, long cpInstanceId) {

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			cpInstanceUnitOfMeasurePersistence.fetchByC_P_First(
				cpInstanceId, true, null);

		if ((cpInstanceUnitOfMeasure != null) &&
			(cpInstanceUnitOfMeasure.getCPInstanceUnitOfMeasureId() !=
				cpInstanceUnitOfMeasureId)) {

			cpInstanceUnitOfMeasure.setPrimary(false);

			cpInstanceUnitOfMeasurePersistence.update(cpInstanceUnitOfMeasure);
		}
	}

	private void _validateCPInstanceUnitOfMeasureIncrementalOrderQuantity(
			BigDecimal incrementalOrderQuantity)
		throws PortalException {

		if (incrementalOrderQuantity == null) {
			throw new CPInstanceUnitOfMeasureIncrementalOrderQuantityException(
				"Incremental order quantity is mandatory");
		}

		if (incrementalOrderQuantity.compareTo(BigDecimal.ZERO) < 1) {
			throw new CPInstanceUnitOfMeasureIncrementalOrderQuantityException(
				"Incremental order quantity must be greater than 0");
		}
	}

	private void _validateCPInstanceUnitOfMeasureKey(
			long cpInstanceId, long cpInstanceUnitOfMeasureId, String key)
		throws PortalException {

		if (Validator.isNull(key)) {
			throw new CPInstanceUnitOfMeasureKeyException("Key is mandatory");
		}

		CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
			cpInstanceUnitOfMeasurePersistence.fetchByC_K(cpInstanceId, key);

		if ((cpInstanceUnitOfMeasure != null) &&
			(cpInstanceUnitOfMeasure.getCPInstanceUnitOfMeasureId() !=
				cpInstanceUnitOfMeasureId)) {

			throw new DuplicateCPInstanceUnitOfMeasureKeyException(
				"There is another commerce product instance unit of measure " +
					"with key " + key);
		}
	}

	private void _validateCPInstanceUnitOfMeasureRate(BigDecimal rate)
		throws PortalException {

		if (rate == null) {
			throw new CPInstanceUnitOfMeasureRateException("Rate is mandatory");
		}

		if (rate.compareTo(BigDecimal.ZERO) < 1) {
			throw new CPInstanceUnitOfMeasureRateException(
				"Rate quantity must be greater than 0");
		}
	}

	private void _validateCPInstanceUnitOfMeasureSKU(String sku)
		throws PortalException {

		if (Validator.isNull(sku)) {
			throw new CPInstanceUnitOfMeasureSkuException("SKU is mandatory");
		}
	}

	@Reference
	private UserLocalService _userLocalService;

}