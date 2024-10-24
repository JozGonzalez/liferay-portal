/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.markdown.converter.internal.pegdown.serializer;

import com.liferay.knowledge.base.markdown.converter.internal.pegdown.ast.PicWithCaptionNode;
import com.liferay.petra.string.StringBundler;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pegdown.LinkRenderer;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.ast.HeaderNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.ParaNode;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.TextNode;

/**
 * Provides a visitor implementation for printing HTML for pictures with
 * captions, "side-bars", and in-line images.
 *
 * @author James Hinkey
 */
public class LiferayToHtmlSerializer extends ToHtmlSerializer {

	public LiferayToHtmlSerializer(LinkRenderer linkRenderer) {
		super(linkRenderer);
	}

	@Override
	public void visit(HeaderNode node) {
		boolean anchorInserted = false;

		if (node.getLevel() != 1) {
			List<Node> childNodes = node.getChildren();

			if (!childNodes.isEmpty()) {
				StringBundler sb = new StringBundler();

				for (Node child : childNodes) {
					if (child instanceof TextNode) {
						TextNode textNode = (TextNode)child;

						sb.append(textNode.getText());
					}
				}

				Matcher matcher = _headerIdPattern.matcher(sb.toString());

				if (matcher.find()) {
					String match = matcher.group(1);

					printer.print(
						StringBundler.concat(
							"<a href=\"#", match, "\" id=\"", match, "\">"));

					anchorInserted = true;
				}
			}
		}

		super.visit(node);

		if (anchorInserted) {
			printer.print("</a>");
		}
	}

	@Override
	public void visit(ParaNode node) {
		boolean printParagraphTag = true;

		List<Node> childNodes = node.getChildren();

		for (Node childNode : childNodes) {
			List<Node> grandchildNodes = childNode.getChildren();

			for (Node grandchildNode : grandchildNodes) {
				if (grandchildNode instanceof TextNode) {
					TextNode textNode = (TextNode)grandchildNode;

					String text = textNode.getText();

					if (text.equals("+$$$") || text.equals("$$$")) {
						visitChildren(node);

						printParagraphTag = false;
					}
				}
			}
		}

		if (printParagraphTag) {
			printTag(node, "p");
		}
	}

	public void visit(PicWithCaptionNode picWithCaptionNode) {
		_print(picWithCaptionNode);
	}

	@Override
	public void visit(SuperNode superNode) {
		if (superNode instanceof PicWithCaptionNode) {
			visit((PicWithCaptionNode)superNode);
		}
		else {
			visitChildren(superNode);
		}
	}

	@Override
	public void visit(TextNode node) {
		String text = node.getText();

		if (text.equals("+$$$")) {
			printer.print("<div class=\"sidebar\">");
			printer.print("<div class=\"sidebar-image\"></div>");
			printer.print("<div class=\"sidebar-text\">");
		}
		else if (text.equals("$$$")) {
			printer.print("</div></div>");
		}
		else if (abbreviations.isEmpty()) {
			printer.print(text);
		}
		else {
			printWithAbbreviations(text);
		}
	}

	private void _print(PicWithCaptionNode picWithCaptionNode) {
		printer.print("<p><img src=\"");
		printer.print(picWithCaptionNode.getSrc());
		printer.print("\" alt=\"");
		printer.print(picWithCaptionNode.getAlt());
		printer.print("\" /><p class=\"caption\">");

		visitChildren(picWithCaptionNode);

		printer.print("</p>");
	}

	private static final Pattern _headerIdPattern = Pattern.compile(
		"\\[\\]\\(id=([^\\s]+?)\\)");

}