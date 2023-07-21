/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEventListener} from '@liferay/frontend-js-react-web';
import {setSessionValue} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import LayoutReports from './components/LayoutReports';
import {StoreContextProvider} from './context/StoreContext';

import '../css/main.scss';
import SidebarHeader from './components/SidebarHeader';
import {ConstantsContextProvider} from './context/ConstantsContext';

export default function App(props) {
	const {isPanelStateOpen} = props;

	const layoutReportsPanelToggle = document.getElementById(
		`layoutReportsPanelToggleId`
	);

	const layoutReportsPanelId = document.getElementById(
		`layoutReportsPanelId`
	);

	const sidenavInstance = Liferay.SideNavigation.instance(
		layoutReportsPanelToggle
	);

	if (isPanelStateOpen) {
		layoutReportsPanelToggle.setAttribute('aria-pressed', true);
	}

	const handleKeydownPanel = (event) => {
		if (event.key === 'Escape') {
			sidenavInstance.toggle();
		}
	};

	useEffect(() => {
		sidenavInstance.on('open.lexicon.sidenav', () => {
			setSessionValue(
				'com.liferay.layout.reports.web_layoutReportsPanelState',
				'open'
			);

			layoutReportsPanelToggle.setAttribute('aria-pressed', true);
			layoutReportsPanelId.focus();
		});

		sidenavInstance.on('closed.lexicon.sidenav', () => {
			setSessionValue(
				'com.liferay.layout.reports.web_layoutReportsPanelState',
				'closed'
			);

			layoutReportsPanelToggle.setAttribute('aria-pressed', false);
			layoutReportsPanelToggle.focus();
		});

		Liferay.once('screenLoad', () => {
			Liferay.SideNavigation.destroy(layoutReportsPanelToggle);
		});
	}, [layoutReportsPanelToggle, layoutReportsPanelId, sidenavInstance]);

	const [eventTriggered, setEventTriggered] = useState(false);

	useEventListener(
		'keydown',
		handleKeydownPanel,
		false,
		layoutReportsPanelId
	);

	useEventListener(
		'mouseenter',
		() => setEventTriggered(true),
		{once: true},
		layoutReportsPanelToggle
	);

	useEventListener(
		'focus',
		() => setEventTriggered(true),
		{once: true},
		layoutReportsPanelToggle
	);

	return (
		<ConstantsContextProvider constants={props}>
			<StoreContextProvider>
				<SidebarHeader />

				<SidebarBody>
					<LayoutReports eventTriggered={eventTriggered} />
				</SidebarBody>
			</StoreContextProvider>
		</ConstantsContextProvider>
	);
}

const SidebarBody = ({children}) => (
	<div className="sidebar-body">{children}</div>
);
