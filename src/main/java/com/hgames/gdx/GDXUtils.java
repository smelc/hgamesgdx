package com.hgames.gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * @author smelC
 */
public class GDXUtils {

	/**
	 * @return A 1x1 white texture, ready to be stretched and colored for being
	 *         drawn. Don't forget to {@link Texture#dispose()} it.
	 */
	public static Texture createTexture() {
		final Pixmap tmp = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		tmp.setColor(Color.WHITE);
		tmp.fill();
		final Texture texture = new Texture(1, 1, Pixmap.Format.RGBA8888);
		texture.draw(tmp, 0, 0);
		tmp.dispose();
		return texture;
	}

	/**
	 * Draw {@code texture} within the rectangle at (x,y) with the given width
	 * and height.
	 * 
	 * @param batch
	 * @param texture
	 * @param color
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void drawRectangle(Batch batch, Texture texture, /* @Nullable */ Color color, float x,
			float y, float width, float height) {
		final Color save = batch.getColor();
		if (color != null)
			batch.setColor(color);
		batch.draw(texture, x, y, width, height);
		batch.setColor(save);
	}

	/**
	 * @param batch
	 * @param texture
	 * @param color
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void drawRectangleFrame(Batch batch, Texture texture, /* @Nullable */ Color color, float x,
			float y, float width, float height) {
		/* Bottom line */
		drawRectangle(batch, texture, color, x, y, width, 1f);
		/* Top line */
		drawRectangle(batch, texture, color, x, y + height, width, 1);
		/* Left line */
		drawRectangle(batch, texture, color, x, y, 1, height);
		/* Right line */
		drawRectangle(batch, texture, color, x + width, y, 1, height);
	}

}
