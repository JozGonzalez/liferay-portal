/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ReactFlow, {
	Background,
	Connection,
	ConnectionMode,
	Controls,
	Edge,
	MiniMap,
	addEdge,
} from 'react-flow-renderer';

import {DefinitionNode} from '../DefinitionNode/DefinitionNode';
import {EmptyNode} from '../DefinitionNode/EmptyNode';

import './Diagram.scss';

import React, {useCallback} from 'react';

import DefaultEdge from '../Edges/DefaultEdge';
import SelfEdge from '../Edges/SelfEdge';
import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';

const NODE_TYPES = {
	emptyNode: EmptyNode,
	objectDefinition: DefinitionNode,
};

const EDGE_TYPES = {
	default: DefaultEdge,
	self: SelfEdge,
};

function DiagramBuilder({
	setShowModal,
}: {
	setShowModal: (value: boolean) => void;
}) {
	const [{elements}, dispatch] = useFolderContext();

	const emptyNode = [
		{
			data: {
				setShowModal,
			},
			id: 'empty',
			position: {
				x: 400,
				y: 400,
			},
			type: 'emptyNode',
		},
	];

	const onConnect = useCallback(
		(connection: Connection | Edge) => {
			const newElements = addEdge(connection, elements);

			dispatch({
				payload: {newElements},
				type: TYPES.SET_ELEMENTS,
			});
		},
		[dispatch, elements]
	);

	return (
		<div className="lfr-objects__model-builder-diagram-area">
			<ReactFlow
				connectionMode={ConnectionMode.Loose}
				edgeTypes={EDGE_TYPES}
				elements={elements.length ? elements : emptyNode}
				minZoom={0.1}
				nodeTypes={NODE_TYPES}
				onConnect={onConnect}
			>
				<Background size={1} />

				<Controls showInteractive={false} />

				<MiniMap />
			</ReactFlow>
		</div>
	);
}

export default DiagramBuilder;
