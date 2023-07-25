/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";

const zodSchema = {
	accountCreator: z.object({
		agreeToTermsAndConditions: z.boolean(),
		companyName: z.string().nonempty({ message: "This field is required" }),
		emailAddress: z.string().email("Please fill in valid email"),
		extension: z.string().optional(),
		familyName: z.string().nonempty({ message: "This field is required" }),
		givenName: z.string(),
		industry: z.string().nonempty({ message: "This field is required" }),
		phone: z.object({
			code: z.string(),
			flag: z.string(),
		}),
		phoneNumber: z.string().nonempty(),
	}),

	newCustomer: z.object({
		accountBriefs: z.any().optional(),
		alternateName: z.string().optional(),
		currentPassword: z.string().optional(),
		emailAddress: z.string().email(),
		familyName: z.string(),
		givenName: z.string(),
		id: z.number().optional(),
		image: z.string().optional(),
		imageBlob: z.any().optional(),
		isCustomerAccount: z.boolean().optional(),
		isPublisherAccount: z.boolean().optional(),
		newsSubscription: z.boolean(),
		password: z.string().optional(),
	}),
};

export { zodResolver };

export default zodSchema;
