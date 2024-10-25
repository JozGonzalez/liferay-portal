/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.vm.amazon;

/**
 * @author Kiyoshi Lee
 */
public class CentOS7AmazonVM extends AmazonVM {

	protected CentOS7AmazonVM(
		String awsAccessKeyId, String awsSecretAccessKey, String instanceId) {

		super(awsAccessKeyId, awsSecretAccessKey, instanceId);
	}

	protected CentOS7AmazonVM(
		String awsAccessKeyId, String awsSecretAccessKey, String instanceType,
		String keyName) {

		super(
			awsAccessKeyId, awsSecretAccessKey, "ami-b1a59fd1", instanceType,
			keyName);
	}

	protected CentOS7AmazonVM(
		String awsAccessKeyId, String awsSecretAccessKey, String imageId,
		String instanceType, String keyName) {

		super(
			awsAccessKeyId, awsSecretAccessKey, imageId, instanceType, keyName);
	}

}