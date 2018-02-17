package com.hgames.gdx.rng;

import com.badlogic.gdx.math.RandomXS128;
import com.hgames.rhogue.rng.AbstractRNG;

import squidpony.squidmath.IRNG;

/**
 * An implementation of {@link IRNG} backed up by libgdx's random number
 * generator.
 * 
 * @author smelC
 */
public class GdxRNG extends AbstractRNG {

	private final RandomXS128 delegate;

	/**
	 * A fresh RNG.
	 */
	public GdxRNG() {
		this(new RandomXS128());
	}

	/**
	 * A fresh RNG.
	 * 
	 * @param seed
	 */
	public GdxRNG(long seed) {
		this(new RandomXS128(seed));
	}

	/**
	 * A RNG that uses the given delegate.
	 * 
	 * @param delegate
	 */
	public GdxRNG(RandomXS128 delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean nextBoolean() {
		return delegate.nextBoolean();
	}

	@Override
	public float nextFloat() {
		return delegate.nextFloat();
	}

	@Override
	public int nextInt() {
		return delegate.nextInt();
	}

	@Override
	public int nextInt(int bound) {
		assert 0 <= bound;
		return bound == 0 ? 0 : delegate.nextInt(bound);
	}

	@Override
	public long nextLong() {
		return delegate.nextLong();
	}

}
