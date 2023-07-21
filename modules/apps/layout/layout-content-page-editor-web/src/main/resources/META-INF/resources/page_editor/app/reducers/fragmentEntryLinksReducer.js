/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ADD_FRAGMENT_ENTRY_LINKS,
	ADD_FRAGMENT_ENTRY_LINK_COMMENT,
	ADD_ITEM,
	CHANGE_MASTER_LAYOUT,
	DELETE_FRAGMENT_ENTRY_LINK_COMMENT,
	DELETE_ITEM,
	DUPLICATE_ITEM,
	EDIT_FRAGMENT_ENTRY_LINK_COMMENT,
	UPDATE_COLLECTION_DISPLAY_COLLECTION,
	UPDATE_EDITABLE_VALUES,
	UPDATE_FORM_ITEM_CONFIG,
	UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION,
	UPDATE_FRAGMENT_ENTRY_LINK_CONTENT,
	UPDATE_PREVIEW_IMAGE,
} from '../actions/types';
import {BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/backgroundImageFragmentEntryProcessor';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/editableFragmentEntryProcessor';

export const INITIAL_STATE = {};

export default function fragmentEntryLinksReducer(
	fragmentEntryLinks = INITIAL_STATE,
	action
) {
	switch (action.type) {
		case ADD_ITEM: {
			const newFragmentEntryLinks = {};

			if (action.fragmentEntryLinkIds) {
				action.fragmentEntryLinkIds.forEach((fragmentEntryLinkId) => {
					newFragmentEntryLinks[fragmentEntryLinkId] = {
						...fragmentEntryLinks[fragmentEntryLinkId],
						removed: false,
					};
				});

				return {
					...fragmentEntryLinks,
					...newFragmentEntryLinks,
				};
			}

			return fragmentEntryLinks;
		}

		case ADD_FRAGMENT_ENTRY_LINKS: {
			const newFragmentEntryLinks = {};

			action.fragmentEntryLinks.forEach((fragmentEntryLink) => {
				newFragmentEntryLinks[
					fragmentEntryLink.fragmentEntryLinkId
				] = fragmentEntryLink;
			});

			return {
				...fragmentEntryLinks,
				...newFragmentEntryLinks,
			};
		}

		case ADD_FRAGMENT_ENTRY_LINK_COMMENT: {
			const fragmentEntryLink =
				fragmentEntryLinks[action.fragmentEntryLinkId];

			const {comments = []} = fragmentEntryLink;

			let nextComments;

			if (action.parentCommentId) {
				nextComments = comments.map((comment) =>
					comment.commentId === action.parentCommentId
						? {
								...comment,
								children: [
									...(comment.children || []),
									action.fragmentEntryLinkComment,
								],
						  }
						: comment
				);
			}
			else {
				nextComments = [...comments, action.fragmentEntryLinkComment];
			}

			return {
				...fragmentEntryLinks,
				[action.fragmentEntryLinkId]: {
					...fragmentEntryLink,
					comments: nextComments,
				},
			};
		}
		case CHANGE_MASTER_LAYOUT: {
			const nextFragmentEntryLinks = {
				...(action.fragmentEntryLinks || {}),
			};

			Object.entries(fragmentEntryLinks).forEach(
				([fragmentEntryLinkId, fragmentEntryLink]) => {
					if (!fragmentEntryLink.masterLayout) {
						nextFragmentEntryLinks[
							fragmentEntryLinkId
						] = fragmentEntryLink;
					}
				}
			);

			return nextFragmentEntryLinks;
		}

		case DELETE_ITEM: {
			const newFragmentEntryLinks = {};

			if (action.fragmentEntryLinkIds) {
				action.fragmentEntryLinkIds.forEach((fragmentEntryLinkId) => {
					newFragmentEntryLinks[fragmentEntryLinkId] = {
						...fragmentEntryLinks[fragmentEntryLinkId],
						removed: true,
					};
				});

				return {
					...fragmentEntryLinks,
					...newFragmentEntryLinks,
				};
			}

			return fragmentEntryLinks;
		}

		case DELETE_FRAGMENT_ENTRY_LINK_COMMENT: {
			const fragmentEntryLink =
				fragmentEntryLinks[action.fragmentEntryLinkId];

			const {comments = []} = fragmentEntryLink;

			let nextComments;

			if (action.parentCommentId) {
				nextComments = comments.map((comment) =>
					comment.commentId === action.parentCommentId
						? {
								...comment,
								children: comment.children.filter(
									(childComment) =>
										childComment.commentId !==
										action.commentId
								),
						  }
						: comment
				);
			}
			else {
				nextComments = comments.filter(
					(comment) => comment.commentId !== action.commentId
				);
			}

			return {
				...fragmentEntryLinks,
				[action.fragmentEntryLinkId]: {
					...fragmentEntryLink,
					comments: nextComments,
				},
			};
		}

		case DUPLICATE_ITEM: {
			const nextFragmentEntryLinks = {...fragmentEntryLinks};

			action.addedFragmentEntryLinks.forEach((fragmentEntryLink) => {
				nextFragmentEntryLinks[
					fragmentEntryLink.fragmentEntryLinkId
				] = fragmentEntryLink;
			});

			return nextFragmentEntryLinks;
		}

		case EDIT_FRAGMENT_ENTRY_LINK_COMMENT: {
			const fragmentEntryLink =
				fragmentEntryLinks[action.fragmentEntryLinkId];

			const {comments = []} = fragmentEntryLink;

			let nextComments;

			if (action.parentCommentId) {
				nextComments = comments.map((comment) =>
					comment.commentId === action.parentCommentId
						? {
								...comment,
								children: comment.children.map((childComment) =>
									childComment.commentId ===
									action.fragmentEntryLinkComment.commentId
										? action.fragmentEntryLinkComment
										: childComment
								),
						  }
						: comment
				);
			}
			else {
				nextComments = comments.map((comment) =>
					comment.commentId ===
					action.fragmentEntryLinkComment.commentId
						? {...comment, ...action.fragmentEntryLinkComment}
						: comment
				);
			}

			return {
				...fragmentEntryLinks,
				[action.fragmentEntryLinkId]: {
					...fragmentEntryLink,
					comments: nextComments,
				},
			};
		}

		case UPDATE_COLLECTION_DISPLAY_COLLECTION:
			return {
				...fragmentEntryLinks,
				...Object.fromEntries(
					action.fragmentEntryLinks.map((fragmentEntryLink) => [
						fragmentEntryLink.fragmentEntryLinkId,
						{
							...fragmentEntryLinks[
								fragmentEntryLink.fragmentEntryLinkId
							],
							...fragmentEntryLink,
						},
					])
				),
			};

		case UPDATE_EDITABLE_VALUES:
			return {
				...fragmentEntryLinks,
				[action.fragmentEntryLinkId]: {
					...fragmentEntryLinks[action.fragmentEntryLinkId],
					content: action.content,
					editableValues: action.editableValues,
				},
			};

		case UPDATE_FORM_ITEM_CONFIG: {
			const newFragmentEntryLinks = action.addedFragmentEntryLinks
				? {...action.addedFragmentEntryLinks}
				: {};

			if (action.removedFragmentEntryLinkIds) {
				action.removedFragmentEntryLinkIds.forEach(
					(fragmentEntryLinkId) => {
						newFragmentEntryLinks[fragmentEntryLinkId] = {
							...fragmentEntryLinks[fragmentEntryLinkId],
							removed: true,
						};
					}
				);
			}

			if (action.restoredFragmentEntryLinkIds) {
				action.restoredFragmentEntryLinkIds.forEach(
					(fragmentEntryLinkId) => {
						newFragmentEntryLinks[fragmentEntryLinkId] = {
							...fragmentEntryLinks[fragmentEntryLinkId],
							removed: false,
						};
					}
				);
			}

			return {
				...fragmentEntryLinks,
				...newFragmentEntryLinks,
			};
		}

		case UPDATE_FRAGMENT_ENTRY_LINK_CONFIGURATION:
			return {
				...fragmentEntryLinks,
				[action.fragmentEntryLinkId]: {
					...fragmentEntryLinks[action.fragmentEntryLinkId],
					configuration: action.fragmentEntryLink.configuration,
					content: action.fragmentEntryLink.content,
					editableTypes: action.fragmentEntryLink.editableTypes,
					editableValues: action.fragmentEntryLink.editableValues,
				},
			};

		case UPDATE_FRAGMENT_ENTRY_LINK_CONTENT: {
			const fragmentEntryLink =
				fragmentEntryLinks[action.fragmentEntryLinkId];

			let collectionContent = fragmentEntryLink.collectionContent || {};

			if (
				action.collectionContentId !== null &&
				action.collectionContentId !== undefined
			) {
				collectionContent = {...collectionContent};

				collectionContent[action.collectionContentId] = action.content;
			}

			return {
				...fragmentEntryLinks,
				[action.fragmentEntryLinkId]: {
					...fragmentEntryLinks[action.fragmentEntryLinkId],
					collectionContent,
					content: action.content,
				},
			};
		}

		case UPDATE_PREVIEW_IMAGE: {
			const getUpdatedEditableValues = (editableValues = {}) =>
				Object.entries(editableValues).map(([key, value]) => [
					key,
					Object.fromEntries(
						Object.entries(value).map(([key, value]) => [
							key,
							typeof value === 'object' &&
							value.url &&
							value.fileEntryId
								? {...value, url: action.previewURL}
								: value,
						])
					),
				]);

			const newFragmentEntryLinks = action.contents.map(
				({content, fragmentEntryLinkId}) => {
					const {editableValues} = fragmentEntryLinks[
						fragmentEntryLinkId
					];

					return [
						fragmentEntryLinkId,
						{
							...fragmentEntryLinks[fragmentEntryLinkId],
							content,
							editableValues: {
								...editableValues,
								[BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR]: Object.fromEntries(
									getUpdatedEditableValues(
										editableValues[
											BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR
										]
									)
								),
								[EDITABLE_FRAGMENT_ENTRY_PROCESSOR]: Object.fromEntries(
									getUpdatedEditableValues(
										editableValues[
											EDITABLE_FRAGMENT_ENTRY_PROCESSOR
										]
									)
								),
							},
						},
					];
				}
			);

			return {
				...fragmentEntryLinks,
				...Object.fromEntries(newFragmentEntryLinks),
			};
		}

		default:
			return fragmentEntryLinks;
	}
}
