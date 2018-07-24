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

	/**
	 * @param tinted
	 * @param color
	 * @param duration
	 * @return A tint action.
	 */
	public static Action tintAction(Actor tinted, Color color, float duration) {
		final Action go = Actions.color(color, duration * 0.3f);
		final Action back = Actions.color(tinted.getColor(), duration * 0.7f);
		final Action action = Actions.sequence(go, back);
		return action;
	}

}
