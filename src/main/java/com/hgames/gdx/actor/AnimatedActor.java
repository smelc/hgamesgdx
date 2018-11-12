package com.hgames.gdx.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author smelC
 */
public class AnimatedActor extends Actor {

	/** The animation */
	public final Animation<TextureRegion> animation;

	private float stateTime;

	/**
	 * @param animation
	 */
	public AnimatedActor(Animation<TextureRegion> animation) {
		this.animation = animation;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		stateTime += Gdx.graphics.getDeltaTime();
		final TextureRegion frame = animation.getKeyFrame(stateTime);
		batch.setColor(getColor());
		batch.draw(frame, getX(), getY(), getWidth(), getHeight());
		batch.setColor(1, 1, 1, 1); // restore
	}

}
