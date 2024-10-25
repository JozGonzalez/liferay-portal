/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.jdbc;

import com.liferay.portal.kernel.internal.dao.jdbc.LimitedInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Blob;
import java.sql.SQLException;

/**
 * @author Shuyang Zhou
 */
public class OutputBlob implements Blob {

	public OutputBlob(InputStream inputStream, long length) {
		if (inputStream == null) {
			throw new IllegalArgumentException("Input stream is null");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length is less than 0");
		}

		_inputStream = inputStream;
		_length = length;
	}

	@Override
	public void free() throws SQLException {
		try {
			_inputStream.close();
		}
		catch (IOException ioException) {
			throw new SQLException(ioException.getMessage());
		}

		_inputStream = null;
	}

	@Override
	public InputStream getBinaryStream() {
		return _inputStream;
	}

	@Override
	public InputStream getBinaryStream(long pos, long length)
		throws SQLException {

		if (pos < 1) {
			throw new SQLException("Position is less than 1");
		}

		long offset = pos - 1;

		if ((offset >= _length) || ((offset + length) >= _length)) {
			throw new SQLException("Invalid range");
		}

		try {
			return new LimitedInputStream(_inputStream, offset, length);
		}
		catch (IOException ioException) {
			throw new SQLException(ioException.getMessage());
		}
	}

	@Override
	public byte[] getBytes(long pos, int length) throws SQLException {
		if (pos < 1) {
			throw new SQLException("Position is less than 1");
		}

		if (length < 0) {
			throw new SQLException("Length is less than 0");
		}

		byte[] bytes = new byte[length];

		try {
			int newLength = 0;

			int read = -1;

			while ((newLength < length) &&
				   ((read = _inputStream.read(
					   bytes, newLength, length - newLength)) != -1)) {

				newLength += read;
			}

			if (newLength < length) {
				byte[] newBytes = new byte[newLength];

				System.arraycopy(bytes, 0, newBytes, 0, newLength);

				bytes = newBytes;
			}
		}
		catch (IOException ioException) {
			throw new SQLException(ioException.getMessage());
		}

		return bytes;
	}

	@Override
	public long length() {
		return _length;
	}

	@Override
	public long position(Blob pattern, long start) {
		throw new UnsupportedOperationException();
	}

	@Override
	public long position(byte[] pattern, long start) {
		throw new UnsupportedOperationException();
	}

	@Override
	public OutputStream setBinaryStream(long pos) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int setBytes(long pos, byte[] bytes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int setBytes(long pos, byte[] bytes, int offset, int length) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void truncate(long length) {
		throw new UnsupportedOperationException();
	}

	private InputStream _inputStream;
	private final long _length;

}