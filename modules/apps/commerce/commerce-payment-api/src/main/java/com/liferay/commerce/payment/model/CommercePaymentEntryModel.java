/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.AttachedModel;
import com.liferay.portal.kernel.model.AuditedModel;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.ShardedModel;

import java.math.BigDecimal;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the CommercePaymentEntry service. Represents a row in the &quot;CommercePaymentEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.commerce.payment.model.impl.CommercePaymentEntryModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.commerce.payment.model.impl.CommercePaymentEntryImpl</code>.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntry
 * @generated
 */
@ProviderType
public interface CommercePaymentEntryModel
	extends AttachedModel, AuditedModel, BaseModel<CommercePaymentEntry>,
			MVCCModel, ShardedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a commerce payment entry model instance should use the {@link CommercePaymentEntry} interface instead.
	 */

	/**
	 * Returns the primary key of this commerce payment entry.
	 *
	 * @return the primary key of this commerce payment entry
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this commerce payment entry.
	 *
	 * @param primaryKey the primary key of this commerce payment entry
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this commerce payment entry.
	 *
	 * @return the mvcc version of this commerce payment entry
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this commerce payment entry.
	 *
	 * @param mvccVersion the mvcc version of this commerce payment entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the commerce payment entry ID of this commerce payment entry.
	 *
	 * @return the commerce payment entry ID of this commerce payment entry
	 */
	public long getCommercePaymentEntryId();

	/**
	 * Sets the commerce payment entry ID of this commerce payment entry.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID of this commerce payment entry
	 */
	public void setCommercePaymentEntryId(long commercePaymentEntryId);

	/**
	 * Returns the company ID of this commerce payment entry.
	 *
	 * @return the company ID of this commerce payment entry
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this commerce payment entry.
	 *
	 * @param companyId the company ID of this commerce payment entry
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this commerce payment entry.
	 *
	 * @return the user ID of this commerce payment entry
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this commerce payment entry.
	 *
	 * @param userId the user ID of this commerce payment entry
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this commerce payment entry.
	 *
	 * @return the user uuid of this commerce payment entry
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this commerce payment entry.
	 *
	 * @param userUuid the user uuid of this commerce payment entry
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this commerce payment entry.
	 *
	 * @return the user name of this commerce payment entry
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this commerce payment entry.
	 *
	 * @param userName the user name of this commerce payment entry
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this commerce payment entry.
	 *
	 * @return the create date of this commerce payment entry
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this commerce payment entry.
	 *
	 * @param createDate the create date of this commerce payment entry
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this commerce payment entry.
	 *
	 * @return the modified date of this commerce payment entry
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this commerce payment entry.
	 *
	 * @param modifiedDate the modified date of this commerce payment entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the fully qualified class name of this commerce payment entry.
	 *
	 * @return the fully qualified class name of this commerce payment entry
	 */
	@Override
	public String getClassName();

	public void setClassName(String className);

	/**
	 * Returns the class name ID of this commerce payment entry.
	 *
	 * @return the class name ID of this commerce payment entry
	 */
	@Override
	public long getClassNameId();

	/**
	 * Sets the class name ID of this commerce payment entry.
	 *
	 * @param classNameId the class name ID of this commerce payment entry
	 */
	@Override
	public void setClassNameId(long classNameId);

	/**
	 * Returns the class pk of this commerce payment entry.
	 *
	 * @return the class pk of this commerce payment entry
	 */
	@Override
	public long getClassPK();

	/**
	 * Sets the class pk of this commerce payment entry.
	 *
	 * @param classPK the class pk of this commerce payment entry
	 */
	@Override
	public void setClassPK(long classPK);

	/**
	 * Returns the commerce channel ID of this commerce payment entry.
	 *
	 * @return the commerce channel ID of this commerce payment entry
	 */
	public long getCommerceChannelId();

	/**
	 * Sets the commerce channel ID of this commerce payment entry.
	 *
	 * @param commerceChannelId the commerce channel ID of this commerce payment entry
	 */
	public void setCommerceChannelId(long commerceChannelId);

	/**
	 * Returns the amount of this commerce payment entry.
	 *
	 * @return the amount of this commerce payment entry
	 */
	public BigDecimal getAmount();

	/**
	 * Sets the amount of this commerce payment entry.
	 *
	 * @param amount the amount of this commerce payment entry
	 */
	public void setAmount(BigDecimal amount);

	/**
	 * Returns the callback url of this commerce payment entry.
	 *
	 * @return the callback url of this commerce payment entry
	 */
	@AutoEscape
	public String getCallbackURL();

	/**
	 * Sets the callback url of this commerce payment entry.
	 *
	 * @param callbackURL the callback url of this commerce payment entry
	 */
	public void setCallbackURL(String callbackURL);

	/**
	 * Returns the currency code of this commerce payment entry.
	 *
	 * @return the currency code of this commerce payment entry
	 */
	@AutoEscape
	public String getCurrencyCode();

	/**
	 * Sets the currency code of this commerce payment entry.
	 *
	 * @param currencyCode the currency code of this commerce payment entry
	 */
	public void setCurrencyCode(String currencyCode);

	/**
	 * Returns the payment integration key of this commerce payment entry.
	 *
	 * @return the payment integration key of this commerce payment entry
	 */
	@AutoEscape
	public String getPaymentIntegrationKey();

	/**
	 * Sets the payment integration key of this commerce payment entry.
	 *
	 * @param paymentIntegrationKey the payment integration key of this commerce payment entry
	 */
	public void setPaymentIntegrationKey(String paymentIntegrationKey);

	/**
	 * Returns the payment integration type of this commerce payment entry.
	 *
	 * @return the payment integration type of this commerce payment entry
	 */
	public int getPaymentIntegrationType();

	/**
	 * Sets the payment integration type of this commerce payment entry.
	 *
	 * @param paymentIntegrationType the payment integration type of this commerce payment entry
	 */
	public void setPaymentIntegrationType(int paymentIntegrationType);

	/**
	 * Returns the payment status of this commerce payment entry.
	 *
	 * @return the payment status of this commerce payment entry
	 */
	public int getPaymentStatus();

	/**
	 * Sets the payment status of this commerce payment entry.
	 *
	 * @param paymentStatus the payment status of this commerce payment entry
	 */
	public void setPaymentStatus(int paymentStatus);

	/**
	 * Returns the redirect url of this commerce payment entry.
	 *
	 * @return the redirect url of this commerce payment entry
	 */
	@AutoEscape
	public String getRedirectURL();

	/**
	 * Sets the redirect url of this commerce payment entry.
	 *
	 * @param redirectURL the redirect url of this commerce payment entry
	 */
	public void setRedirectURL(String redirectURL);

	/**
	 * Returns the transaction code of this commerce payment entry.
	 *
	 * @return the transaction code of this commerce payment entry
	 */
	@AutoEscape
	public String getTransactionCode();

	/**
	 * Sets the transaction code of this commerce payment entry.
	 *
	 * @param transactionCode the transaction code of this commerce payment entry
	 */
	public void setTransactionCode(String transactionCode);

	@Override
	public CommercePaymentEntry cloneWithOriginalValues();

	public default String toXmlString() {
		return null;
	}

}