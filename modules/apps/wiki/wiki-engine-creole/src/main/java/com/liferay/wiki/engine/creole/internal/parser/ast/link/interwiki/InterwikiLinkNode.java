/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.engine.creole.internal.parser.ast.link.interwiki;

import com.liferay.wiki.engine.creole.internal.parser.ast.link.LinkNode;
import com.liferay.wiki.engine.creole.internal.parser.visitor.ASTVisitor;

/**
 * @author Miguel Pastor
 */
public abstract class InterwikiLinkNode extends LinkNode {

	public InterwikiLinkNode() {
	}

	public InterwikiLinkNode(int token) {
		super(token);
	}

	public InterwikiLinkNode(int token, String title) {
		this(token);

		_title = title;
	}

	public InterwikiLinkNode(String title) {
		_title = title;
	}

	@Override
	public abstract void accept(ASTVisitor astVisitor);

	public String getTitle() {
		return _title;
	}

	public String getURL() {
		return getBaseURL() + _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	protected abstract String getBaseURL();

	private String _title;

}