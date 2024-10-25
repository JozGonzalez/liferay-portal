/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Container from './Container';
import Item from './Item';
import ItemList from './ItemList';
import ResultsBar from './ResultsBar';
import ResultsBarItem from './ResultsBarItem';
import Search from './Search';

import './ManagementToolbar.scss';

const ManagementToolbar: Partial<IProps> = {};

ManagementToolbar.Container = Container;
ManagementToolbar.Item = Item;
ManagementToolbar.ItemList = ItemList;
ManagementToolbar.ResultsBar = ResultsBar;
ManagementToolbar.ResultsBarItem = ResultsBarItem;
ManagementToolbar.Search = Search;

export default ManagementToolbar as IProps;

interface IProps {
	Container: typeof Container;
	Item: typeof Item;
	ItemList: typeof ItemList;
	ResultsBar: typeof ResultsBar;
	ResultsBarItem: typeof ResultsBarItem;
	Search: typeof Search;
}
