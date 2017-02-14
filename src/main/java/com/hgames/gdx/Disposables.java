package com.hgames.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author smelC
 */
public class Disposables {

	/**
	 * Disposes {@code renderer}, ignoring exceptions (I've seen ones that were
	 * harmless).
	 * 
	 * @param renderer
	 */
	public static void safeDisposeRenderer(ShapeRenderer renderer) {
		if (renderer == null || renderer.isDrawing())
			return;

		safeDispose(renderer);
	}

	/**
	 * Disposes {@code d}, ignoring exceptions (I've seen ones that were
	 * harmless).
	 * 
	 * @param d
	 */
	public static void safeDispose(Disposable d) {
		if (d == null)
			return;

		try {
			d.dispose();
		} catch (Throwable e) {
			if (Gdx.app != null) {
				Gdx.app.log(Constants.TAG,
						"Got a " + e.getClass().getSimpleName() + " when disposing an instance of "
								+ d.getClass().getSimpleName() + ", but ignoring it.");
			}
		}
	}

}
