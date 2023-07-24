/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageMagickUtil;
import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.awt.image.RenderedImage;

import java.io.IOException;

/**
 * @author Roberto Díaz
 */
public class ImageSelectorProcessor {

	public ImageSelectorProcessor(byte[] bytes) {
		_bytes = bytes;
	}

	public byte[] cropImage(String cropRegion)
		throws IOException, PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(cropRegion);

		int height = jsonObject.getInt("height");
		int width = jsonObject.getInt("width");
		int x = jsonObject.getInt("x");
		int y = jsonObject.getInt("y");

		if ((x > 0) || (y > 0) || (width > 0) || (height > 0)) {
			ImageBag imageBag = ImageToolUtil.read(_bytes);

			RenderedImage renderedImage = imageBag.getRenderedImage();

			renderedImage = ImageToolUtil.crop(
				renderedImage, height, width, x, y);

			return ImageToolUtil.getBytes(renderedImage, imageBag.getType());
		}

		return _bytes;
	}

	public byte[] scaleImage(int width) throws Exception {
		byte[] bytes = null;

		try {
			ImageBag imageBag = ImageToolUtil.read(_bytes);

			RenderedImage renderedImage = imageBag.getRenderedImage();

			renderedImage = ImageToolUtil.scale(renderedImage, width);

			bytes = ImageToolUtil.getBytes(renderedImage, imageBag.getType());
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioException);
			}
		}

		if ((bytes == null) && ImageMagickUtil.isEnabled()) {
			bytes = ImageMagickUtil.scale(
				_bytes, ImageTool.TYPE_PNG, width, width);
		}

		return bytes;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ImageSelectorProcessor.class);

	private final byte[] _bytes;

}