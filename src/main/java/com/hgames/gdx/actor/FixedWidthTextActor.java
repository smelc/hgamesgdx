package com.hgames.gdx.actor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Align;

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
public class FixedWidthTextActor<T extends Color> extends Widget {

	protected BitmapFont font;
	protected List<IColoredString<T>> text;
	/* Do not access directly. Use getTypesetText instead */
	private String[] typesetText;
	protected final IMarkup<T> markup;

	/** Whether text must be wrapped */
	protected boolean wrap = true;

	protected int align = Align.left;

	private float prefWidth = -1;
	private float prefHeight = -1;

	/**
	 * @param font
	 *            The font to use, or null to set it later.
	 * @param text
	 *            The text to display, or null to set it later.
	 * @param markup
	 *            The markup to use, or null.
	 */
	public FixedWidthTextActor(/* @Nullable */ BitmapFont font, /* @Nullable */ String text,
			IMarkup<T> markup) {
		this(font, text, null, markup);
	}

	/**
	 * @param font
	 *            The font to use, or null to set it later.
	 * @param text
	 *            The text to display, or null to set it later.
	 * @param color
	 *            The color of {@code text}, or null to use a default.
	 * @param markup
	 *            The markup to use, or null.
	 */
	public FixedWidthTextActor(/* @Nullable */ BitmapFont font, /* @Nullable */ String text, T color,
			IMarkup<T> markup) {
		this(font, toLICS(text, color), markup);
	}

	/**
	 * @param font
	 *            The font to use, or null to set it later.
	 * @param text
	 *            The text to display, or null to set it later.
	 * @param markup
	 *            The markup to use, or null.
	 */
	public FixedWidthTextActor(/* @Nullable */ BitmapFont font, /* @Nullable */ IColoredString<T> text,
			IMarkup<T> markup) {
		this(font, toList(text), markup);
	}

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
	}

	/**
	 * @param font
	 *            The font to use.
	 */
	public void setFont(BitmapFont font) {
		if (markup != null && font != null)
			font.getData().markupEnabled |= true;
		this.font = font;
	}

	/**
	 * @param text
	 *            The text to display. Each member at index > 0 of this list is
	 *            displayed after a new line after its predecessor.
	 */
	public void setText(List<IColoredString<T>> text) {
		this.text = text;
		invalidateTypesetText();
	}

	/**
	 * @param textToAdd
	 *            The text to add
	 */
	public void addText(IColoredString<T> textToAdd) {
		if (textToAdd == null)
			throw new NullPointerException();
		if (this.text == null)
			this.text = new ArrayList<IColoredString<T>>();
		this.text.add(textToAdd);
		invalidateTypesetText();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if (text == null)
			/* Nothing to do */
			return;

		if (font == null)
			throw new NullPointerException(
					"The font should be set when drawing a " + getClass().getSimpleName());

		final float x = getX();
		final float width = getWidth();
		final float y = getY();

		float height = getHeight();
		final float destx = x;
		final String[] fancytText = getTypesetText();
		final int bound = fancytText.length;
		for (int i = 0; i < bound; i++) {
			final String toDisplay = fancytText[i];
			final GlyphLayout glyph = font.draw(batch, toDisplay, destx, y + height, 0, toDisplay.length(),
					width, align, wrap);
			height -= glyph.height;
		}
	}

	@Override
	public void setWidth(float w) {
		prefWidth = w;
		super.setWidth(w);
	}

	@Override
	public float getPrefWidth() {
		return prefWidth;
	}

	@Override
	public float getHeight() {
		float h = super.getHeight();
		if (h == 0) {
			/* Need to compute it */
			h = getPrefHeight();
			super.setHeight(h);
		}

		return h;
	}

	@Override
	public float getPrefHeight() {
		if (prefHeight == -1) {
			/* Not computed */
			if (text == null || font == null)
				/* Cannot do */
				return 0;
			final float width = getWidth();
			if (width <= 0)
				/* Cannot do */
				return 0;

			float h = 0;
			final String[] fancyText = getTypesetText();
			final int bound = fancyText.length;
			final BitmapFontCache cache = font.getCache();
			cache.clear();
			for (int i = 0; i < bound; i++) {
				final String toDisplay = fancyText[i];
				final GlyphLayout glyph = cache.addText(toDisplay, 0, 0, 0, toDisplay.length(), width, align,
						wrap);
				h += glyph.height;
				cache.clear();
			}

			prefHeight = h;
		}

		return prefHeight;
	}

	@Override
	public boolean needsLayout() {
		return prefHeight < 0;
	}

	@Override
	public void invalidate() {
		prefHeight = -1;
	}

	@Override
	public void layout() {
		final float h = getPrefHeight();
		if (0 < h)
			setHeight(h);
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

	protected void invalidateTypesetText() {
		typesetText = null;
	}

	private static <T> List<IColoredString<T>> toLICS(String text, T color) {
		final List<IColoredString<T>> list = new ArrayList<IColoredString<T>>(1);
		list.add(IColoredString.Impl.create(text, color));
		return list;
	}

	private static <T> List<T> toList(T t) {
		final List<T> list = new ArrayList<T>(1);
		list.add(t);
		return list;
	}
}
