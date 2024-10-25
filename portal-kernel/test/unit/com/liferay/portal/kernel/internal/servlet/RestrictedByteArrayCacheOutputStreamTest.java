/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.internal.servlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.internal.servlet.RestrictedByteArrayCacheOutputStream.FlushPreAction;
import com.liferay.portal.kernel.io.DummyOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.IOException;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class RestrictedByteArrayCacheOutputStreamTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testBlockWrite() throws IOException {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		RestrictedByteArrayCacheOutputStream
			restrictedByteArrayCacheOutputStream =
				new RestrictedByteArrayCacheOutputStream(
					unsyncByteArrayOutputStream, 9, 19, null);

		byte[] bytes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

		restrictedByteArrayCacheOutputStream.write(bytes);

		Assert.assertFalse(restrictedByteArrayCacheOutputStream.isOverflowed());
		Assert.assertEquals(10, restrictedByteArrayCacheOutputStream.index);
		Assert.assertEquals(10, restrictedByteArrayCacheOutputStream.size());
		Assert.assertArrayEquals(
			bytes, restrictedByteArrayCacheOutputStream.toByteArray());

		byte[] unsafeGetByteArray =
			restrictedByteArrayCacheOutputStream.unsafeGetByteArray();

		Assert.assertEquals(
			Arrays.toString(unsafeGetByteArray), 18, unsafeGetByteArray.length);

		for (int i = 0; i < 10; i++) {
			Assert.assertEquals(bytes[i], unsafeGetByteArray[i]);
		}

		ByteBuffer byteBuffer =
			restrictedByteArrayCacheOutputStream.unsafeGetByteBuffer();

		Assert.assertEquals(10, byteBuffer.remaining());

		byte[] byteBufferArray = byteBuffer.array();

		for (int i = 0; i < 10; i++) {
			Assert.assertEquals(bytes[i], byteBufferArray[i]);
		}

		restrictedByteArrayCacheOutputStream.write(bytes);

		Assert.assertTrue(restrictedByteArrayCacheOutputStream.isOverflowed());
		Assert.assertNull(restrictedByteArrayCacheOutputStream.cache);
		Assert.assertEquals(-1, restrictedByteArrayCacheOutputStream.index);
		Assert.assertEquals(-1, restrictedByteArrayCacheOutputStream.size());
		Assert.assertArrayEquals(
			ArrayUtil.append(bytes, bytes),
			unsyncByteArrayOutputStream.toByteArray());

		restrictedByteArrayCacheOutputStream.write(bytes);

		Assert.assertArrayEquals(
			ArrayUtil.append(bytes, bytes, bytes),
			unsyncByteArrayOutputStream.toByteArray());

		restrictedByteArrayCacheOutputStream.write(bytes, 0, 0);

		Assert.assertArrayEquals(
			ArrayUtil.append(bytes, bytes, bytes),
			unsyncByteArrayOutputStream.toByteArray());
	}

	@Test
	public void testConstructor() {

		// Default initial cache size

		DummyOutputStream dummyOutputStream = new DummyOutputStream();

		int cacheCapacity = 1024;

		FlushPreAction flushPreAction = new FlushPreAction() {

			@Override
			public void beforeFlush() {
			}

		};

		RestrictedByteArrayCacheOutputStream
			restrictedByteArrayCacheOutputStream =
				new RestrictedByteArrayCacheOutputStream(
					dummyOutputStream, cacheCapacity, flushPreAction);

		Assert.assertSame(
			dummyOutputStream,
			restrictedByteArrayCacheOutputStream.outputStream);
		Assert.assertEquals(
			cacheCapacity, restrictedByteArrayCacheOutputStream.cacheCapacity);
		Assert.assertEquals(
			cacheCapacity,
			restrictedByteArrayCacheOutputStream.getCacheCapacity());
		Assert.assertSame(
			flushPreAction,
			restrictedByteArrayCacheOutputStream.flushPreAction);
		Assert.assertEquals(
			32, restrictedByteArrayCacheOutputStream.cache.length);
		Assert.assertEquals(0, restrictedByteArrayCacheOutputStream.index);
		Assert.assertEquals(0, restrictedByteArrayCacheOutputStream.size());

		// Customized initial cache size

		restrictedByteArrayCacheOutputStream =
			new RestrictedByteArrayCacheOutputStream(
				dummyOutputStream, cacheCapacity / 2, cacheCapacity,
				flushPreAction);

		Assert.assertSame(
			dummyOutputStream,
			restrictedByteArrayCacheOutputStream.outputStream);
		Assert.assertEquals(
			cacheCapacity, restrictedByteArrayCacheOutputStream.cacheCapacity);
		Assert.assertEquals(
			cacheCapacity,
			restrictedByteArrayCacheOutputStream.getCacheCapacity());
		Assert.assertSame(
			flushPreAction,
			restrictedByteArrayCacheOutputStream.flushPreAction);
		Assert.assertEquals(
			cacheCapacity / 2,
			restrictedByteArrayCacheOutputStream.cache.length);
		Assert.assertEquals(0, restrictedByteArrayCacheOutputStream.index);
		Assert.assertEquals(0, restrictedByteArrayCacheOutputStream.size());

		// Initial cache size is larger than cache capacity

		try {
			new RestrictedByteArrayCacheOutputStream(
				dummyOutputStream, cacheCapacity, cacheCapacity / 2,
				flushPreAction);

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				StringBundler.concat(
					"Initial cache size ", cacheCapacity,
					" is larger than cache capacity ", cacheCapacity / 2),
				illegalArgumentException.getMessage());
		}
	}

	@Test
	public void testReset() throws IOException {
		RestrictedByteArrayCacheOutputStream
			restrictedByteArrayCacheOutputStream =
				new RestrictedByteArrayCacheOutputStream(
					new DummyOutputStream(), 32, null);

		for (int i = 0; i < 10; i++) {
			restrictedByteArrayCacheOutputStream.write(i);
		}

		Assert.assertEquals(10, restrictedByteArrayCacheOutputStream.index);

		restrictedByteArrayCacheOutputStream.reset();

		Assert.assertEquals(0, restrictedByteArrayCacheOutputStream.index);
	}

	@Test
	public void testWrite() throws IOException {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		final AtomicBoolean flushed = new AtomicBoolean();

		FlushPreAction flushPreAction = new FlushPreAction() {

			@Override
			public void beforeFlush() {
				flushed.set(true);
			}

		};

		RestrictedByteArrayCacheOutputStream
			restrictedByteArrayCacheOutputStream =
				new RestrictedByteArrayCacheOutputStream(
					unsyncByteArrayOutputStream, 10, 27, flushPreAction);

		StringBundler sb = new StringBundler(26);

		for (int i = 'a'; i <= 'z'; i++) {
			restrictedByteArrayCacheOutputStream.write(i);

			sb.append((char)i);
		}

		Assert.assertFalse(restrictedByteArrayCacheOutputStream.isOverflowed());
		Assert.assertFalse(flushed.get());
		Assert.assertEquals(26, restrictedByteArrayCacheOutputStream.index);
		Assert.assertEquals(26, restrictedByteArrayCacheOutputStream.size());

		String expectedStringResult = sb.toString();

		byte[] expectedBytesResult = expectedStringResult.getBytes(
			Charset.forName("ASCII"));

		Assert.assertArrayEquals(
			expectedBytesResult,
			restrictedByteArrayCacheOutputStream.toByteArray());

		byte[] unsafeGetByteArray =
			restrictedByteArrayCacheOutputStream.unsafeGetByteArray();

		Assert.assertEquals(
			Arrays.toString(unsafeGetByteArray), 27, unsafeGetByteArray.length);

		for (int i = 0; i < 26; i++) {
			Assert.assertEquals(expectedBytesResult[i], unsafeGetByteArray[i]);
		}

		ByteBuffer byteBuffer =
			restrictedByteArrayCacheOutputStream.unsafeGetByteBuffer();

		Assert.assertEquals(26, byteBuffer.remaining());

		byte[] byteBufferArray = byteBuffer.array();

		for (int i = 0; i < 26; i++) {
			Assert.assertEquals(expectedBytesResult[i], byteBufferArray[i]);
		}

		for (int i = '0'; i <= '9'; i++) {
			restrictedByteArrayCacheOutputStream.write(i);
			sb.append((char)i);
		}

		Assert.assertTrue(restrictedByteArrayCacheOutputStream.isOverflowed());
		Assert.assertTrue(flushed.get());
		Assert.assertNull(restrictedByteArrayCacheOutputStream.cache);
		Assert.assertEquals(-1, restrictedByteArrayCacheOutputStream.index);
		Assert.assertEquals(-1, restrictedByteArrayCacheOutputStream.size());
		Assert.assertEquals(
			sb.toString(), unsyncByteArrayOutputStream.toString());

		try {
			restrictedByteArrayCacheOutputStream.toByteArray();

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
			Assert.assertEquals(
				"Cache overflowed", illegalStateException.getMessage());
		}

		try {
			restrictedByteArrayCacheOutputStream.unsafeGetByteArray();

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
			Assert.assertEquals(
				"Cache overflowed", illegalStateException.getMessage());
		}

		try {
			restrictedByteArrayCacheOutputStream.unsafeGetByteBuffer();

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
			Assert.assertEquals(
				"Cache overflowed", illegalStateException.getMessage());
		}

		try {
			restrictedByteArrayCacheOutputStream.reset();

			Assert.fail();
		}
		catch (IllegalStateException illegalStateException) {
			Assert.assertEquals(
				"Cache overflowed", illegalStateException.getMessage());
		}

		flushed.set(false);

		restrictedByteArrayCacheOutputStream.flush();

		Assert.assertFalse(flushed.get());
	}

}