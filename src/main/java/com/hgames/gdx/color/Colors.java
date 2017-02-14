package com.hgames.gdx.color;

import com.badlogic.gdx.graphics.Color;

import squidpony.ColoredStringList;
import squidpony.panel.IColoredString;

/**
 * @author smelC
 */
public class Colors {

	/**
	 * @param input
	 * @param nullReplacer
	 *            The color by which to replace {@code null} in {@code input}.
	 *            You should likely give something, because null is usually
	 *            interpreted as white.
	 * @param affectAlpha
	 *            Whether alpha should be changed.
	 * @return A monochromatic grey version of {@code input}.
	 */
	public static IColoredString<Color> greify(IColoredString<Color> input,
			/* @Nullable */ Color nullReplacer, boolean affectAlpha) {
		final IColoredString.Impl<Color> result = IColoredString.Impl.create();
		for (IColoredString.Bucket<Color> bucket : input) {
			final String text = bucket.getText();
			if (text == null || text.isEmpty())
				continue;
			final Color changed;
			final Color inThere = bucket.getColor();
			if (inThere == null)
				changed = nullReplacer;
			else {
				final float sum = inThere.r + inThere.g + inThere.b + (affectAlpha ? inThere.a : 0);
				final float mean = sum / (3 + (affectAlpha ? 1 : 0));
				changed = new Color(mean, mean, mean, affectAlpha ? mean : inThere.a);
			}
			result.append(text, changed);
		}
		return result;
	}

	/**
	 * @param input
	 * @param nullReplacer
	 *            The color by which to replace {@code null} in {@code input}.
	 *            You should likely give something, because null is usually
	 *            interpreted as white.
	 * @param affectAlpha
	 *            Whether alpha should be changed.
	 * @return A monochromatic grey version of {@code input}.
	 */
	public static ColoredStringList<Color> greify(ColoredStringList<Color> input,
			/* @Nullable */ Color nullReplacer, boolean affectAlpha) {
		final int sz = input.size();
		final ColoredStringList<Color> result = ColoredStringList.create(sz);
		for (int i = 0; i < sz; i++)
			result.add(greify(input.get(i), nullReplacer, affectAlpha));
		return result;
	}
}
