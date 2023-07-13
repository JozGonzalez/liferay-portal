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

package com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.admin.account.dto.v1_0.Account;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.webserver.WebServerServletToken;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.account.model.AccountEntry",
	service = DTOConverter.class
)
public class AccountDTOConverter
	implements DTOConverter<AccountEntry, Account> {

	@Override
	public String getContentType() {
		return Account.class.getSimpleName();
	}

	@Override
	public Account toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		AccountEntry accountEntry;

		if ((Long)dtoConverterContext.getId() == -1) {
			User user = dtoConverterContext.getUser();

			if (user == null) {
				user = _userLocalService.getUserById(
					PrincipalThreadLocal.getUserId());
			}

			accountEntry = _accountEntryLocalService.getGuestAccountEntry(
				user.getCompanyId());
		}
		else {
			accountEntry = _accountEntryLocalService.getAccountEntry(
				(Long)dtoConverterContext.getId());
		}

		ExpandoBridge expandoBridge = accountEntry.getExpandoBridge();

		return new Account() {
			{
				active = _toCommerceAccountActive(accountEntry.getStatus());
				customFields = expandoBridge.getAttributes();
				dateCreated = accountEntry.getCreateDate();
				dateModified = accountEntry.getModifiedDate();
				defaultBillingAccountAddressId =
					accountEntry.getDefaultBillingAddressId();
				defaultShippingAccountAddressId =
					accountEntry.getDefaultShippingAddressId();
				emailAddresses = new String[] {accountEntry.getEmailAddress()};
				externalReferenceCode = accountEntry.getExternalReferenceCode();
				id = accountEntry.getAccountEntryId();
				logoId = accountEntry.getLogoId();
				logoURL = StringBundler.concat(
					"/image/organization_logo?img_id=",
					accountEntry.getLogoId(), "&t=",
					_webServerServletToken.getToken(accountEntry.getLogoId()))
				name = accountEntry.getName();
				root =
					accountEntry.getParentAccountEntryId() ==
						AccountConstants.PARENT_ACCOUNT_ENTRY_ID_DEFAULT;
				taxId = accountEntry.getTaxIdNumber();
				type = _toCommerceAccountType(accountEntry.getType());
			}
		};
	}

	private boolean _toCommerceAccountActive(int accountEntryStatus) {
		if (accountEntryStatus == WorkflowConstants.STATUS_APPROVED) {
			return true;
		}

		return false;
	}

	private Integer _toCommerceAccountType(String accountEntryType) {
		if (Objects.equals(
				accountEntryType,
				AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS)) {

			return _ACCOUNT_TYPE_BUSINESS;
		}
		else if (Objects.equals(
					accountEntryType,
					AccountConstants.ACCOUNT_ENTRY_TYPE_GUEST)) {

			return _ACCOUNT_TYPE_GUEST;
		}
		else if (Objects.equals(
					accountEntryType,
					AccountConstants.ACCOUNT_ENTRY_TYPE_PERSON)) {

			return _ACCOUNT_TYPE_PERSONAL;
		}

		return _ACCOUNT_TYPE_GUEST;
	}

	private static final int _ACCOUNT_TYPE_BUSINESS = 2;

	private static final int _ACCOUNT_TYPE_GUEST = 0;

	private static final int _ACCOUNT_TYPE_PERSONAL = 1;

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WebServerServletToken _webServerServletToken;

}