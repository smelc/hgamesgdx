package com.hgames.gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import squidpony.squidmath.Coord;

/**
 * @author smelC
 */
public class GDXUtils {

	/**
	 * @param npd
	 * @return A copy of {@code npd}.
	 */
	public static NinePatchDrawable clone(NinePatchDrawable npd) {
		return new NinePatchDrawable(new NinePatch(npd.getPatch()));
	}

	/**
	 * @param red
	 * @param green
	 * @param blue
	 * @param opacity
	 *            The opacity, recall that 0 means transparency; while 255 means
	 *            opacity.
	 * @return A fresh color instance.
	 */
	public static Color newColor(int red, int green, int blue, int opacity) {
		final float r = red / 255f;
		final float g = green / 255f;
		final float b = blue / 255f;
		final float a = opacity / 255f;
		return new Color(r, g, b, a);
	}

	/**
	 * @param red
	 * @param green
	 * @param blue
	 * @return A fresh opaque color.
	 */
	public static Color newOpaqueColor(int red, int green, int blue) {
		return newColor(red, green, blue, 255);
	}

	/**
	 * @param v
	 * @return A uniform opaque color
	 */
	public static Color newGreyColor(int v) {
		return newColor(v, v, v, 255);
	}

	/**
	 * @param color
	 *            The color to use, or null for white.
	 * @return A 1x1 white texture, ready to be stretched and colored for being
	 *         drawn. Don't forget to {@link Texture#dispose()} it.
	 */
	public static Texture createTexture(/* @Nullable */ Color color) {
		final Pixmap tmp = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		tmp.setColor(color == null ? Color.WHITE : color);
		tmp.fill();
		final Texture texture = new Texture(1, 1, Pixmap.Format.RGBA8888);
		texture.draw(tmp, 0, 0);
		tmp.dispose();
		return texture;
	}

	/**
	 * Draw a frame around {@code actor}
	 * 
	 * @param batch
	 * @param texture
	 * @param color
	 * @param actor
	 */
	public static void drawActorFrame(Batch batch, Texture texture, /* @Nullable */ Color color, Actor actor) {
		drawRectangleFrame(batch, texture, color, actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
	}

	/**
	 * Draw a rectangle on {@code actor}
	 * 
	 * @param batch
	 * @param texture
	 * @param color
	 * @param actor
	 */
	public static void drawActorRectangle(Batch batch, Texture texture, /* @Nullable */ Color color, Actor actor) {
		drawRectangle(batch, texture, color, actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
	}

	/**
	 * Draw {@code texture} within the rectangle at (x,y) with the given width and
	 * height.
	 * 
	 * @param batch
	 * @param texture
	 * @param color
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void drawRectangle(Batch batch, Texture texture, /* @Nullable */ Color color, float x, float y,
			float width, float height) {
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
	public static void drawRectangleFrame(Batch batch, Texture texture, /* @Nullable */ Color color, float x, float y,
			float width, float height) {
		/* Bottom line */
		drawRectangle(batch, texture, color, x, y, width, 1f);
		/* Top line */
		drawRectangle(batch, texture, color, x, y + height, width, 1);
		/* Left line */
		drawRectangle(batch, texture, color, x, y, 1, height);
		/* Right line */
		drawRectangle(batch, texture, color, x + width, y, 1, height);
	}

	/**
	 * @param actor
	 * @param position
	 */
	public static void setPosition(Actor actor, Coord position) {
		if (actor != null && position != null) {
			actor.setX(position.x);
			actor.setY(position.y);
		}
	}
}
