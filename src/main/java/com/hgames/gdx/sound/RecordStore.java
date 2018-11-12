package com.hgames.gdx.sound;

import com.badlogic.gdx.audio.Sound;

/**
 * An interface to retrieve sounds.
 * 
 * @author smelC
 * @see RecordStoreImpl The default implementation
 */
public interface RecordStore {

	/**
	 * @param identifier
	 *            The identifier. Can be null, but this is an error that is logged.
	 * @return The sound corresponding to {@code identifier}. Maybe fresh maybe not.
	 *         Or null if not found.
	 */
	public /* @Nullable */ Sound get(/* @Nullable */ String identifier);

}
