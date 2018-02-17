package com.hgames.gdx.rng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import com.badlogic.gdx.math.RandomXS128;

import squidpony.squidmath.IRNG;

/**
 * An implementation of {@link IRNG} backed up by libgdx's random number
 * generator.
 * 
 * @author smelC
 */
public class GdxRNG implements IRNG {

	private final RandomXS128 delegate;

	/**
	 * A fresh RNG.
	 */
	public GdxRNG() {
		this(new RandomXS128());
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
	public int between(int min, int max) {
		final int result = delegate.nextInt(max - min) + min;
		assert min <= result && result < max;
		return result;
	}

	@Override
	public <T> T getRandomElement(T[] array) {
		return array.length == 0 ? null : array[nextInt(array.length)];
	}

	@Override
	public <T> T getRandomElement(List<T> list) {
		final int sz = list.size();
		return sz == 0 ? null : list.get(nextInt(sz));
	}

	@Override
	public <T> T getRandomElement(Collection<T> collection) {
		int sz = collection.size();
		if (sz == 0)
			return null;
		int idx = nextInt(sz);
		final Iterator<T> it = collection.iterator();
		while (0 < idx) {
			assert it.hasNext();
			it.next();
			idx--;
		}
		assert it.hasNext();
		return it.next();
	}

	@Override
	public <T> Iterable<T> getRandomStartIterable(final List<T> list) {
		// I initially contributed this code to SquidLib, so I just copy/pasted it
		final int sz = list.size();
		if (sz == 0)
			return Collections.<T>emptyList();

		/*
		 * Here's a tricky bit: Defining 'start' here means that every Iterator returned
		 * by the returned Iterable will have the same iteration order. In other words,
		 * if you use more than once the returned Iterable, you'll will see elements in
		 * the same order every time, which is desirable.
		 */
		final int start = nextInt(sz);

		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {

					int next = -1;

					@Override
					public boolean hasNext() {
						return next != start;
					}

					@Override
					public T next() {
						if (next == start)
							throw new NoSuchElementException("Iteration terminated; check hasNext() before next()");
						if (next == -1)
							/* First call */
							next = start;
						final T result = list.get(next);
						if (next == sz - 1)
							/*
							 * Reached the list's end, let's continue from the list's left.
							 */
							next = 0;
						else
							next++;
						return result;
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException("Remove is not supported from a randomStartIterable");
					}

					@Override
					public String toString() {
						return "RandomStartIterator at index " + next;
					}
				};
			}
		};
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
		return delegate.nextInt(bound);
	}

	@Override
	public long nextLong() {
		return delegate.nextLong();
	}

	@Override
	public <T> T[] shuffle(T[] elements) {
		final int sz = elements.length;
		final T[] result = Arrays.copyOf(elements, sz);
		for (int i = 0; i < sz; i++) {
			final int j = i + nextInt(sz - i);
			final T save = result[j];
			result[j] = result[i];
			result[i] = save;
		}
		return result;
	}

	@Override
	public <T> T[] shuffle(T[] elements, T[] dest) {
		assert elements != dest;
		if (dest.length != elements.length) {
			assert false;
			return dest;
		}
		for (int i = 0; i < elements.length; i++) {
			final int r = nextInt(i + 1);
			if (r != i)
				dest[i] = dest[r];
			dest[r] = elements[i];
		}
		return dest;

	}

	@Override
	public <T> ArrayList<T> shuffle(Collection<T> elements, ArrayList<T> buf) {
		final ArrayList<T> result;
		if (buf == null || !buf.isEmpty())
			result = new ArrayList<>(elements);
		else {
			result = buf;
			result.addAll(elements);
		}
		final int sz = result.size();
		for (int i = 0; i < sz; i++)
			Collections.swap(result, i + nextInt(sz - i), i);
		return result;
	}

	@Override
	public <T> void shuffleInPlace(T[] elements) {
		for (int i = elements.length - 1; i > 0; i--) {
			final int r = nextInt(i + 1);
			final T save = elements[r];
			elements[r] = elements[i];
			elements[i] = save;
		}
	}

	@Override
	public <T> void shuffleInPlace(List<T> elements) {
		assert !(elements instanceof LinkedList);
		final int sz = elements.size();
		for (int i = sz - 1; i > 0; i--) {
			final int j = nextInt(i + 1);
			/* Get */
			final T atJ = elements.get(j);
			final T atI = elements.get(i);
			/* Swap */
			elements.set(j, atI);
			elements.set(i, atJ);
		}
	}

}
