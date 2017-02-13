package com.hgames.gdx.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.Align;

/**
 * An actor that draws lines around another actor.
 * 
 * @author smelC
 * @param <T>
 *            The type of the contained {@link Actor}
 */
public class ActorLiner<T extends Actor> extends Container<T> {

	protected /* @Nullable */ ShapeRenderer renderer;

	protected final Color color;

	/**
	 * @param renderer
	 *            The renderer to use. If null a new one will get created when
	 *            necessary (in which you should take care of calling
	 *            {@link #dispose()}).
	 * @param contained
	 *            The {@link Actor} being wrapped.
	 * @param borders
	 *            The borders to draw. A disjunction of {@link Align#left},
	 *            {@link Align#right}, {@link Align#top}, and
	 *            {@link Align#bottom}.
	 * @param bsize
	 *            The size of the borders.
	 * @param color
	 *            The color of the borders.
	 */
	public ActorLiner(/* @Nullable */ ShapeRenderer renderer, T contained, int borders, float bsize,
			Color color) {
		this.renderer = renderer;
		if (contained == null)
			throw new NullPointerException();
		setActor(contained);
		if (color == null)
			throw new NullPointerException();
		this.color = color;

		final Value v = new Value.Fixed(bsize);
		if ((borders & Align.left) != 0)
			padLeft(v);
		if ((borders & Align.right) != 0)
			padRight(v);
		if ((borders & Align.bottom) != 0)
			padBottom(v);
		if ((borders & Align.top) != 0)
			padTop(v);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (renderer == null)
			renderer = new ShapeRenderer();
		batch.end();

		final Matrix4 m = batch.getTransformMatrix();
		renderer.setTransformMatrix(m);
		renderer.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		renderer.begin(ShapeType.Filled);

		final float x = getX();
		final float y = getY();
		final float w = getWidth();
		final float h = getHeight();

		final float l = getPadLeft();
		if (0 != l)
			renderer.rect(x, y, l, h);
		final float r = getPadRight();
		if (0 != r)
			renderer.rect(x + w - r, y, r, h);
		final float b = getPadBottom();
		if (0 != b)
			renderer.rect(x, y, w, b);
		final float t = getPadTop();
		if (0 != t)
			renderer.rect(x, y + h - t, w, t);

		renderer.end();

		batch.begin();
		super.draw(batch, parentAlpha);
	}

	/**
	 * Disposes the renderer that was automatically created (if any).
	 */
	public void dispose() {
		if (renderer != null)
			renderer.dispose();
	}

}
