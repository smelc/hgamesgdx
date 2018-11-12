package com.hgames.gdx.sound;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author smelC
 *
 */
public abstract class RecordStoreImpl implements RecordStore, Disposable {

	/** A map from parameters of {@link #get} to actual sounds */
	protected final Map<String, Sound> id2Sound;

	/** A fresh empty instance */
	public RecordStoreImpl() {
		this.id2Sound = new HashMap<String, Sound>();
	}

	@Override public void dispose() {
		for (Sound sound : id2Sound.values())
			sound.dispose();
	}
	

	@Override
	public /*@Nullable*/ Sound get(String identifier) {
		if (identifier == null) { errlog("Cannot play null sound identifier"); return null; }
		Sound result = id2Sound.get(identifier);
		if (result != null) /*Loaded already */ return result;
		final String path = path(identifier);
		final FileHandle handle = handle(path);
		if (!handle.exists()) /* Do not cache it */ return null;
		log("Loading " + path);
		result = Gdx.audio.newSound(handle);
		/* Cache it */
		id2Sound.put(identifier, result);
		return result;
	}

	protected void errlog(String string) { System.err.println(string); }

	/** @return the handle to {@code path} */
	protected FileHandle handle(String path) { return Gdx.files.internal(path); }

	protected void log(String string) { System.out.println(string); }

	/** @return Where {@code identifier} lives in the assets folder. Can be {@code identifier} itself. */
	protected abstract String path(String identifier);

}
