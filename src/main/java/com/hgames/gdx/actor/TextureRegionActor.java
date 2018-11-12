package com.hgames.gdx.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * An actor that draws a {@link TextureRegion}.
 * 
 * @author smelC
 */
public class TextureRegionActor extends Actor {

	/** The region to draw */
	public TextureRegion region;

	/**
	 * @param width
	 * @param height
	 * @param region
	 *            The texture to use. Should not be null.
	 */
	public TextureRegionActor(float width, float height, TextureRegion region) {
		this.region = region;
		setSize(width, height);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		/* Prepare restoring */
		final Color bcolor = batch.getColor();
		final float r = bcolor.r;
		final float g = bcolor.g;
		final float b = bcolor.b;
		final float a = bcolor.a;
		batch.setColor(getColor());
		batch.draw(region, getX(), getY(), getWidth(), getHeight());
		/* Restore */
		batch.setColor(r, g, b, a);
	}

}
