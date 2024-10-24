/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.internal.dao.jdbc;

import java.io.IOException;
import java.io.InputStream;

/**
 * Skip the specified offset until it fails three times. This is used to prevent
 * reading an InputStream infinitely.
 *
 * @author Shuyang Zhou
 */
public class LimitedInputStream extends InputStream {

	public LimitedInputStream(InputStream inputStream, long offset, long length)
		throws IOException {

		if (offset < 0) {
			throw new IllegalArgumentException("Offset is less than 0");
		}

		if (length < 0) {
			throw new IllegalArgumentException("Length is less than 0");
		}

		_inputStream = inputStream;
		_length = length;

		int count = 0;

		while (offset > 0) {
			long skip = _inputStream.skip(offset);

			if (skip == 0) {
				if (++count >= _SKIP_RETRY_COUNT) {
					throw new IOException(
						"Most likely reached the end of the input stream");
				}
			}
			else {
				count = 0;
			}

			offset = offset - skip;
		}
	}

	@Override
	public int available() throws IOException {
		int available = _inputStream.available();

		int allowed = (int)(_length - _read);

		if (available > allowed) {
			return allowed;
		}

		return available;
	}

	@Override
	public void close() throws IOException {
		_inputStream.close();
	}

	@Override
	public void mark(int readLimit) {
	}

	@Override
	public boolean markSupported() {
		return false;
	}

	@Override
	public int read() throws IOException {
		if (_read >= _length) {
			return -1;
		}

		int read = _inputStream.read();

		if (read >= 0) {
			_read++;
		}

		return read;
	}

	@Override
	public int read(byte[] bytes) throws IOException {
		if (_read >= _length) {
			return -1;
		}

		int read = 0;

		if ((_read + bytes.length) > _length) {
			read = _inputStream.read(bytes, 0, (int)(_length - _read));
		}
		else {
			read = _inputStream.read(bytes);
		}

		_read += read;

		return read;
	}

	@Override
	public int read(byte[] bytes, int offset, int length) throws IOException {
		if (_read >= _length) {
			return -1;
		}

		int read = 0;

		if ((_read + length) > _length) {
			read = _inputStream.read(bytes, 0, (int)(_length - _read));
		}
		else {
			read = _inputStream.read(bytes, offset, length);
		}

		_read += read;

		return read;
	}

	@Override
	public void reset() {
	}

	@Override
	public long skip(long skip) throws IOException {
		long allowed = _length - _read;

		if (allowed < skip) {
			skip = allowed;
		}

		skip = _inputStream.skip(skip);

		_read += skip;

		return skip;
	}

	private static final int _SKIP_RETRY_COUNT = 3;

	private final InputStream _inputStream;
	private final long _length;
	private long _read;

}