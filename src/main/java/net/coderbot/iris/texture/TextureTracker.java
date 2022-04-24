package net.coderbot.iris.texture;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.coderbot.iris.Iris;
import net.coderbot.iris.gl.state.StateUpdateNotifiers;
import net.coderbot.iris.pipeline.WorldRenderingPipeline;
import net.coderbot.iris.texture.pbr.PBRTextureManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import org.jetbrains.annotations.Nullable;

public class TextureTracker {
	public static final TextureTracker INSTANCE = new TextureTracker();

	private static Runnable setShaderTextureListener;

	static {
		StateUpdateNotifiers.setShaderTextureNotifier = listener -> setShaderTextureListener = listener;
	}

	// Using the nullary ctor or 0 causes errors
	private final ObjectArrayList<AbstractTexture> textures = new ObjectArrayList<>(ObjectArrayList.DEFAULT_INITIAL_CAPACITY);

	private boolean lockSetShaderTextureCallback;

	private TextureTracker() {
	}

	public void trackTexture(int id, AbstractTexture texture) {
		if (id >= textures.size()) {
			textures.size(id + 1);
		}
		textures.set(id, texture);
	}

	@Nullable
	public AbstractTexture getTexture(int id) {
		if (id < textures.size()) {
			return textures.get(id);
		}
		return null;
	}

	public void onSetShaderTexture(int unit, int id) {
		if (lockSetShaderTextureCallback) {
			return;
		}
		if (unit == 0) {
			lockSetShaderTextureCallback = true;
			if (setShaderTextureListener != null) {
				setShaderTextureListener.run();
			}
			WorldRenderingPipeline pipeline = Iris.getPipelineManager().getPipelineNullable();
			if (pipeline != null) {
				pipeline.onSetShaderTexture0(id);
			}
			lockSetShaderTextureCallback = false;
		}
	}

	public void onDeleteTexture(int id) {
		if (id < textures.size()) {
			textures.set(id, null);
			PBRTextureManager.INSTANCE.onDeleteTexture(id);
		}
	}
}
