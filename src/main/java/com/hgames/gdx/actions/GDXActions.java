package com.hgames.gdx.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Builder atop {@link Actions}.
 * 
 * @author smelC
 */
public class GDXActions {

	/** Percentage of duration to go to alpha 1 when tinting */
	public static final float TINT_GO_RATIO = 0.3f;
	/** Percentage of duration to go to alpha 0 when tinting */
	public static final float TINT_BACK_RATIO = (1 - TINT_GO_RATIO);

	/**
	 * @param tinted
	 * @param color
	 * @param duration
	 * @return A tint action.
	 */
	public static Action tintAction(Actor tinted, Color color, float duration) {
		final Action go = Actions.color(color, duration * TINT_GO_RATIO);
		final Action back = Actions.color(tinted.getColor(), duration * TINT_BACK_RATIO);
		final Action action = Actions.sequence(go, back);
		return action;
	}

}
