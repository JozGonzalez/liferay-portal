/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

import java.io.IOException;

import java.util.concurrent.TimeoutException;

/**
 * @author Michael Hashimoto
 */
public interface Host {

	public void cleanUpServices();

	public String getName();

	public class HostService {

		public void cleanUpService() {
			try {
				System.out.println("Clean up " + _name);

				Process process = JenkinsResultsParserUtil.executeBashCommands(
					_cleanUpCommand);

				System.out.println(
					JenkinsResultsParserUtil.readInputStream(
						process.getInputStream()));
			}
			catch (IOException | TimeoutException exception) {
				throw new RuntimeException(exception);
			}
		}

		protected HostService(String name, String cleanUpCommand) {
			_name = name;
			_cleanUpCommand = cleanUpCommand;
		}

		private final String _cleanUpCommand;
		private final String _name;

	}

}