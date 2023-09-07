/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import {Link} from 'react-router-dom';
import {useEffect, useState} from 'react';

let oAuth2Client;

try {
	oAuth2Client = Liferay.OAuth2Client.FromUserAgentApplication(
		'liferay-jethr0-etc-spring-boot-oauth-application-user-agent'
	);
}
catch (error) {
	console.error(error);
}

function JobQueue() {
	const [jobQueue, setJobQueue] = useState(null);

	useEffect(() => {
		oAuth2Client
			?.fetch('/jobs/queue')
			.then((response) => response.text())
			.then((jobQueue) => {
				setJobQueue(JSON.parse(jobQueue));
			})
			// eslint-disable-next-line no-console
			.catch((error) => console.log(error));
	}, []);

	if (!jobQueue) {
		return (
			<div>Loading...</div>
		);
	}

	return (<ClayTable borderedColumns responsiveSize="sm">
		<ClayTable.Head>
			<ClayTable.Row>
				<ClayTable.Cell headingCell>ID</ClayTable.Cell>

				<ClayTable.Cell headingCell>Name</ClayTable.Cell>

				<ClayTable.Cell headingCell>Priority</ClayTable.Cell>

				<ClayTable.Cell headingCell>Create Date</ClayTable.Cell>

				<ClayTable.Cell headingCell>Start Date</ClayTable.Cell>

				<ClayTable.Cell headingCell>State</ClayTable.Cell>

				<ClayTable.Cell headingCell>Type</ClayTable.Cell>

				<ClayTable.Cell headingCell>Builds</ClayTable.Cell>
			</ClayTable.Row>
		</ClayTable.Head>
		<ClayTable.Body>
			{jobQueue && jobQueue.map((job) => {
				return (
					<ClayTable.Row key={job.id}>
						<ClayTable.Cell headingCell>
							<Link
								title={job.id}
								to={'/jobs/' + job.id}
							>
								{job.id}
							</Link>
						</ClayTable.Cell>

						<ClayTable.Cell>{job.name}</ClayTable.Cell>

						<ClayTable.Cell>{job.priority}</ClayTable.Cell>

						<ClayTable.Cell>
							{job.dateCreated}
						</ClayTable.Cell>

						<ClayTable.Cell>{job.startDate}</ClayTable.Cell>

						<ClayTable.Cell>
							{job.state.name}
						</ClayTable.Cell>

						<ClayTable.Cell>{job.type.name}</ClayTable.Cell>

						<ClayTable.Cell>
							{job.totalBuilds}
						</ClayTable.Cell>
					</ClayTable.Row>
				);
			})}
		</ClayTable.Body>
	</ClayTable>);
}

export default JobQueue;
