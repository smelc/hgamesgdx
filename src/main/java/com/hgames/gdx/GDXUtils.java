package com.hgames.gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

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

}
