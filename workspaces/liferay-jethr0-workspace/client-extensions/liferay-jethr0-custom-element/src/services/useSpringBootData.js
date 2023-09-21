/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';

function useSpringBootData({setData, timeout = -1, urlPath}) {
	let oAuth2Client;

	try {
		oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication(
			'liferay-jethr0-etc-spring-boot-oauth-application-user-agent'
		);
	}
	catch (error) {
		console.error(error);
	}

	useEffect(() => {
		oAuth2Client
			?.fetch(urlPath)
			.then((response) => response.text())
			.then((data) => {
				setData(JSON.parse(data));
			})
			.catch((error) => {
				// eslint-disable-next-line no-console
				console.log(error);
			});
	}, [oAuth2Client, setData, urlPath]);

	if (timeout > 0) {
		setTimeout(() => {
			oAuth2Client
				?.fetch(urlPath)
				.then((response) => response.text())
				.then((data) => {
					setData(JSON.parse(data));
				})
				.catch((error) => {
					// eslint-disable-next-line no-console
					console.log(error);
				});
		}, timeout);
	}
}

export default useSpringBootData;
