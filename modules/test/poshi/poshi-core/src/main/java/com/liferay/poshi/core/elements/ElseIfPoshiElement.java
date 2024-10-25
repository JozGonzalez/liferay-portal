/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.core.elements;

import com.liferay.poshi.core.script.PoshiScriptParserException;
import com.liferay.poshi.core.util.StringUtil;

import java.util.List;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * @author Kenji Heigel
 */
public class ElseIfPoshiElement extends IfPoshiElement {

	@Override
	public PoshiElement clone(Element element) {
		if (isElementType(_ELEMENT_NAME, element)) {
			return new ElseIfPoshiElement(element);
		}

		return null;
	}

	@Override
	public PoshiElement clone(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		if (_isElementType(parentPoshiElement, poshiScript)) {
			return new ElseIfPoshiElement(parentPoshiElement, poshiScript);
		}

		return null;
	}

	@Override
	public String toPoshiScript() {
		StringBuilder sb = new StringBuilder();

		PoshiElement thenElement = (PoshiElement)element("then");

		sb.append(createPoshiScriptBlock(thenElement.getPoshiNodes()));

		return sb.toString();
	}

	protected ElseIfPoshiElement() {
		super(_ELEMENT_NAME);
	}

	protected ElseIfPoshiElement(Element element) {
		super(_ELEMENT_NAME, element);
	}

	protected ElseIfPoshiElement(List<Attribute> attributes, List<Node> nodes) {
		super(_ELEMENT_NAME, attributes, nodes);
	}

	protected ElseIfPoshiElement(
			PoshiElement parentPoshiElement, String poshiScript)
		throws PoshiScriptParserException {

		super(_ELEMENT_NAME, parentPoshiElement, poshiScript);
	}

	@Override
	protected String getPoshiScriptKeyword() {
		return "else if";
	}

	protected static final Pattern blockNamePattern;

	private boolean _isElementType(
		PoshiElement parentPoshiElement, String poshiScript) {

		if (ElseIfPoshiElement.class.equals(parentPoshiElement.getClass()) ||
			!(parentPoshiElement instanceof IfPoshiElement)) {

			return false;
		}

		return isValidPoshiScriptBlock(blockNamePattern, poshiScript);
	}

	private static final String _ELEMENT_NAME = "elseif";

	private static final String _POSHI_SCRIPT_KEYWORD = "else if";

	private static final String _POSHI_SCRIPT_KEYWORD_REGEX;

	static {
		_POSHI_SCRIPT_KEYWORD_REGEX = StringUtil.replace(
			_POSHI_SCRIPT_KEYWORD, " ", "[\\s]*");

		blockNamePattern = Pattern.compile(
			"^" + _POSHI_SCRIPT_KEYWORD_REGEX + BLOCK_NAME_PARAMETER_REGEX,
			Pattern.DOTALL);
	}

}