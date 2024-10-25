/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.internal.dao.sql.transformer;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Michael Bowerman
 * @author Preston Crary
 */
public class SQLFunctionTransformer {

	public SQLFunctionTransformer(
		String functionPrefix, String replacementPrefix,
		String replacementDelimiter, String replacementSuffix) {

		if (!StringUtil.isUpperCase(functionPrefix) ||
			!functionPrefix.endsWith(StringPool.OPEN_PARENTHESIS)) {

			throw new IllegalArgumentException(functionPrefix);
		}

		if (replacementDelimiter.contains(functionPrefix)) {
			throw new IllegalArgumentException(replacementDelimiter);
		}

		if (replacementSuffix.contains(functionPrefix)) {
			throw new IllegalArgumentException(replacementSuffix);
		}

		_functionPrefix = functionPrefix;
		_replacementPrefix = replacementPrefix;
		_replacementDelimiter = replacementDelimiter;
		_replacementSuffix = replacementSuffix;
	}

	public String transform(String sql) {
		int pos = sql.indexOf(_functionPrefix);

		if (pos < 0) {
			return sql;
		}

		StringBuilder sb = new StringBuilder(sql);

		while (pos >= 0) {
			sb.replace(pos, pos + _functionPrefix.length(), _replacementPrefix);

			int next = pos + _replacementPrefix.length();

			int openParentheses = 1;

			while (true) {
				if (next == sb.length()) {
					throw new IllegalArgumentException(
						"Unclosed function in: " + sql);
				}

				char c = sb.charAt(next);

				if (c == CharPool.COMMA) {
					if (openParentheses == 1) {
						sb.replace(next, next + 1, _replacementDelimiter);

						next = next + _replacementDelimiter.length();

						continue;
					}
				}
				else if (c == CharPool.CLOSE_PARENTHESIS) {
					openParentheses--;

					if (openParentheses == 0) {
						break;
					}
				}
				else if (c == CharPool.OPEN_PARENTHESIS) {
					openParentheses++;
				}
				else if (c == CharPool.APOSTROPHE) {
					next = sb.indexOf(StringPool.APOSTROPHE, next + 1);

					while ((next >= 0) && _isEscaped(sb, next)) {
						next = sb.indexOf(StringPool.APOSTROPHE, next + 1);
					}

					if (next < 0) {
						throw new IllegalArgumentException(
							"Unclosed string literal in: " + sql);
					}
				}

				next++;
			}

			sb.replace(next, next + 1, _replacementSuffix);

			pos = sb.indexOf(
				_functionPrefix, pos + _replacementPrefix.length());
		}

		return sb.toString();
	}

	private boolean _isEscaped(StringBuilder sb, int index) {
		boolean escaped = false;

		while (sb.charAt(--index) == CharPool.BACK_SLASH) {
			escaped = !escaped;
		}

		return escaped;
	}

	private final String _functionPrefix;
	private final String _replacementDelimiter;
	private final String _replacementPrefix;
	private final String _replacementSuffix;

}