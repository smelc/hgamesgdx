package com.hgames.gdx.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

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
	 * @return A tint action: the sequence of two {@link ColorAction}
	 */
	public static SequenceAction tintAction(Actor tinted, Color color, float duration) {
		final Action go = Actions.color(color, duration * TINT_GO_RATIO);
		final Action back = Actions.color(tinted.getColor(), duration * TINT_BACK_RATIO);
		return Actions.sequence(go, back);
	}

	/**
	 * @param tinted
	 * @param x 
	 * @param y 
	 * @param interp 
	 * @param duration
	 * @return A slide and back: the sequence of two {@link MoveByAction}
	 */
	public static SequenceAction slidenbackAction(Actor tinted, float x, float y,
			Interpolation interp, float duration) {
		final Action go = Actions.moveBy(x, y, duration / 2, interp);
		final Action back = Actions.moveBy(-x, -y, duration / 2, interp);
		return Actions.sequence(go, back);
	}

}
