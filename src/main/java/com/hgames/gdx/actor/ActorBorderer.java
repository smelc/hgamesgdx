package com.hgames.gdx.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Value;

import squidpony.squidgrid.gui.gdx.UIUtil;
import squidpony.squidgrid.gui.gdx.UIUtil.CornerStyle;

/**
 * An actor that draws 4 lines around another actor, possibly with some
 * {@link CornerStyle style}.
 * 
 * @author smelC
 * @param <T>
 *            The type of the contained {@link Actor}
 * @see ActorLiner
 */
public class ActorBorderer<T extends Actor> extends Container<T> {

	protected /* @Nullable */ ShapeRenderer renderer;
	protected final CornerStyle cstyle;
	protected final float bsize;

	protected final Color color;

	/** The offset when drawing the border. Typically left to 0. */
	public float xoffset;

	/** The offset when drawing the border. Typically left to 0. */
	public float yoffset;

	/**
	 * @param renderer
	 *            The renderer to use. If null a new one will get created when
	 *            necessary (in which you should take care of calling
	 *            {@link #dispose()}).
	 * @param contained
	 *            The {@link Actor} being wrapped.
	 * @param cstyle
	 *            The style of the border.
	 * @param bsize
	 *            The size of the borders.
	 * @param color
	 *            The color of the borders.
	 */
	public ActorBorderer(/* @Nullable */ ShapeRenderer renderer, T contained, CornerStyle cstyle, float bsize,
			Color color) {
		this.renderer = renderer;
		if (contained == null)
			throw new NullPointerException();
		setActor(contained);
		this.cstyle = cstyle;
		this.bsize = bsize;
		if (color == null)
			throw new NullPointerException();
		this.color = color;

		final Value v = new Value.Fixed(bsize);
		pad(v, v, v, v);
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

		UIUtil.drawMarginsAround(renderer, x + xoffset, y + yoffset, w, h, bsize, color, cstyle, 1f, 1f);

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
