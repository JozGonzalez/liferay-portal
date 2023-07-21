/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.web.internal.model;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class Replenishment {

	public Replenishment(
		long commerceInventoryReplenishmentItemId, String warehouse,
		String date, int quantity) {

		_commerceInventoryReplenishmentItemId =
			commerceInventoryReplenishmentItemId;
		_warehouse = warehouse;
		_date = date;
		_quantity = quantity;
	}

	public long getCommerceInventoryReplenishmentItemId() {
		return _commerceInventoryReplenishmentItemId;
	}

	public String getDate() {
		return _date;
	}

	public int getQuantity() {
		return _quantity;
	}

	public String getWarehouse() {
		return _warehouse;
	}

	private final long _commerceInventoryReplenishmentItemId;
	private final String _date;
	private final int _quantity;
	private final String _warehouse;

}