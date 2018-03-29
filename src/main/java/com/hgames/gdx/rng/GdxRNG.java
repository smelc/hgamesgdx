package com.hgames.gdx.rng;

import java.io.Serializable;
import java.util.ArrayList;

import com.badlogic.gdx.math.RandomXS128;
import com.hgames.lib.collection.list.Lists;
import com.hgames.rhogue.rng.AbstractRNG;

import squidpony.squidmath.IRNG;

/**
 * An implementation of {@link IRNG} backed up by libgdx's random number
 * generator.
 * 
 * @author smelC
 */
@SuppressWarnings("serial") // this class is NOT serializable
public class GdxRNG extends AbstractRNG {

	private final RandomXS128 delegate;

	/**
	 * A fresh RNG.
	 */
	public GdxRNG() {
		this(new RandomXS128());
	}

	/**
	 * A fresh RNG using the given seed.
	 * 
	 * @param seed
	 */
	public GdxRNG(long seed) {
		this(new RandomXS128(seed));
	}

	/**
	 * A fresh RNG using the given seeds.
	 * 
	 * @param seed1
	 *            The first part of the seed
	 * @param seed2
	 *            The second part of the seed
	 */
	public GdxRNG(long seed1, long seed2) {
		this(new RandomXS128(seed1, seed2));
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

	@Override
	public Serializable toSerializable() {
		/* Matching code should be tagged (GdxRNG-SERIAL) */
		final ArrayList<Long> result = Lists.newArrayList();
		result.add(Long.valueOf(delegate.getState(0)));
		result.add(Long.valueOf(delegate.getState(1)));
		return result;
	}

}
