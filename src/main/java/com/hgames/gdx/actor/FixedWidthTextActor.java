package com.hgames.gdx.actor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.hgames.gdx.Constants;

import squidpony.panel.IColoredString;
import squidpony.panel.IMarkup;

/**
 * An actor that displays possibly colored text, by using a fixed width, and
 * adjusting its height accordingly. You should call {@link #setWidth(float)}
 * asap: ideally, do it after the text and the font have been set.
 * 
 * @author smelC
 * 
 * @param <T>
 *            The type of color.
 */
public class FixedWidthTextActor<T extends Color> extends Actor {

	protected BitmapFont font;
	protected List<IColoredString<T>> text;
	/* Do not access directly. Use getTypesetText instead */
	private String[] typesetText;
	protected final IMarkup<T> markup;

	/** Whether text must be wrapped */
	protected boolean wrap = true;

	protected int align = Align.left;

	protected boolean invalidHeight = true;

	/**
	 * @param font
	 *            The font to use, or null to set it later.
	 * @param text
	 *            The text to display, or null to set it later. Each member at
	 *            index > 0 of this list is displayed after a new line after its
	 *            predecessor.
	 * @param markup
	 *            The markup to use, or null.
	 */
	public FixedWidthTextActor(/* @Nullable */ BitmapFont font, /* @Nullable */ List<IColoredString<T>> text,
			IMarkup<T> markup) {
		if (markup != null && font != null)
			font.getData().markupEnabled |= true;
		this.font = font;
		this.text = text;
		this.markup = markup;
		trySetHeight();
	}

	/**
	 * @param font
	 *            The font to use.
	 */
	public void setFont(BitmapFont font) {
		if (markup != null && font != null)
			font.getData().markupEnabled |= true;
		this.font = font;
		trySetHeight();
	}

	/**
	 * @param text
	 *            The text to display. Each member at index > 0 of this list is
	 *            displayed after a new line after its predecessor.
	 */
	public void setText(List<IColoredString<T>> text) {
		this.text = text;
		invalidateTypesetText();
		trySetHeight();
	}

	/**
	 * @param text
	 *            The text to add
	 */
	public void addText(IColoredString<T> text) {
		if (text == null)
			throw new NullPointerException();
		if (this.text == null)
			this.text = new ArrayList<IColoredString<T>>();
		this.text.add(text);
		invalidateTypesetText();
		trySetHeight();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (text == null)
			/* Nothing to do */
			return;

		if (font == null)
			throw new NullPointerException(
					"The font should be set when drawing a " + getClass().getSimpleName());

		final boolean ok = trySetHeight();
		if (!ok)
			Gdx.app.log(Constants.TAG, "'s height is not set when drawing a " + getClass().getSimpleName()
					+ ", this isn't good.");

		final float x = getX();
		final float width = getWidth();

		float height = getHeight();
		final float destx = x;
		final String[] typesetText = getTypesetText();
		final int bound = typesetText.length;
		for (int i = 0; i < bound; i++) {
			final String toDisplay = typesetText[i];
			final GlyphLayout glyph = font.draw(batch, toDisplay, destx, height, 0, toDisplay.length(), width,
					align, wrap);
			height -= glyph.height;
		}
	}

	@Override
	public void setWidth(float w) {
		super.setWidth(w);
		trySetHeight();
	}

	protected String[] getTypesetText() {
		if (typesetText == null) {
			final int sz = text.size();
			typesetText = new String[sz];
			for (int i = 0; i < sz; i++) {
				final IColoredString<T> ics = text.get(i);
				typesetText[i] = markup == null ? ics.toString() : ics.presentWithMarkup(markup);
			}
		}
		return typesetText;
	}

	protected void invalidateHeight() {
		invalidHeight = true;
	}

	protected void invalidateTypesetText() {
		typesetText = null;
	}

	/** Whether the height could be set */
	private boolean trySetHeight() {
		if (!invalidHeight)
			return true;

		if (text == null || font == null)
			return false;
		final float width = getWidth();
		if (width == 0)
			/* Cannot do */
			return false;

		float result = 0;
		final String[] typesetText = getTypesetText();
		final int bound = typesetText.length;
		final BitmapFontCache cache = font.getCache();
		cache.clear();
		for (int i = 0; i < bound; i++) {
			final String toDisplay = typesetText[i];
			final GlyphLayout glyph = cache.addText(toDisplay, 0, 0, 0, toDisplay.length(), width, align,
					wrap);
			result += glyph.height;
			cache.clear();
		}

		invalidHeight = false;
		setHeight(result);
		return true;
	}
}
