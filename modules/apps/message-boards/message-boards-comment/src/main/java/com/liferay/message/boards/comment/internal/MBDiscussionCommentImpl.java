/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.comment.internal;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBTreeWalker;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.comment.DiscussionComment;
import com.liferay.portal.kernel.comment.DiscussionCommentIterator;
import com.liferay.portal.kernel.comment.WorkflowableComment;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.model.RatingsStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class MBDiscussionCommentImpl
	extends MBCommentImpl implements DiscussionComment, WorkflowableComment {

	public MBDiscussionCommentImpl(
		MBMessage message, MBTreeWalker treeWalker,
		Map<Long, RatingsEntry> ratingsEntries,
		Map<Long, RatingsStats> ratingsStats) {

		super(message);

		_treeWalker = treeWalker;
		_ratingsEntries = ratingsEntries;
		_ratingsStats = ratingsStats;
	}

	@Override
	public List<DiscussionComment> getDescendantComments() {
		List<DiscussionComment> discussionComments = new ArrayList<>();

		DiscussionCommentIterator discussionCommentIterator =
			getThreadDiscussionCommentIterator();

		while (discussionCommentIterator.hasNext()) {
			discussionComments.add(discussionCommentIterator.next());
		}

		return discussionComments;
	}

	@Override
	public int getDescendantCommentsCount() {
		List<MBMessage> messages = _treeWalker.getChildren(getMessage());

		return messages.size();
	}

	@Override
	public DiscussionComment getParentComment() throws PortalException {
		MBMessage message = getMessage();

		long parentMessageId = message.getParentMessageId();

		if (parentMessageId == 0) {
			return null;
		}

		MBMessage parentMessage = MBMessageLocalServiceUtil.getMessage(
			parentMessageId);

		return new MBDiscussionCommentImpl(
			parentMessage, _treeWalker, _ratingsEntries, _ratingsStats);
	}

	@Override
	public RatingsEntry getRatingsEntry() {
		return _ratingsEntries.get(getCommentId());
	}

	@Override
	public RatingsStats getRatingsStats() {
		return _ratingsStats.get(getCommentId());
	}

	@Override
	public DiscussionCommentIterator getThreadDiscussionCommentIterator() {
		List<MBMessage> messages = _treeWalker.getMessages();

		int[] range = _treeWalker.getChildrenRange(getMessage());

		return new MBDiscussionCommentIterator(
			messages, range[0], range[1], _treeWalker);
	}

	@Override
	public DiscussionCommentIterator getThreadDiscussionCommentIterator(
		int from) {

		List<MBMessage> messages = _treeWalker.getMessages();

		int[] range = _treeWalker.getChildrenRange(getMessage());

		return new MBDiscussionCommentIterator(
			messages, from + 1, range[1], _treeWalker);
	}

	@Override
	public boolean isInTrash() {
		MBMessage message = getMessage();

		return message.isInTrash();
	}

	@Override
	public boolean isRoot() {
		MBMessage message = getMessage();

		return message.isRoot();
	}

	private final Map<Long, RatingsEntry> _ratingsEntries;
	private final Map<Long, RatingsStats> _ratingsStats;
	private final MBTreeWalker _treeWalker;

	private class MBDiscussionCommentIterator
		implements DiscussionCommentIterator {

		public MBDiscussionCommentIterator(
			List<MBMessage> messages, int from, int to,
			MBTreeWalker treeWalker) {

			_messages = messages;
			_from = from;
			_to = to;
			_treeWalker = treeWalker;
		}

		@Override
		public int getIndexPage() {
			return _from;
		}

		@Override
		public boolean hasNext() {
			if (_from < _to) {
				return true;
			}

			return false;
		}

		@Override
		public DiscussionComment next() {
			DiscussionComment discussionComment = new MBDiscussionCommentImpl(
				_messages.get(_from), _treeWalker, _ratingsEntries,
				_ratingsStats);

			_from++;

			return discussionComment;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		private int _from;
		private final List<MBMessage> _messages;
		private final int _to;
		private final MBTreeWalker _treeWalker;

	}

}