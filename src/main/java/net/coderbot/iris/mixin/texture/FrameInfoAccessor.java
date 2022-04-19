package net.coderbot.iris.mixin.texture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net/minecraft/client/renderer/texture/TextureAtlasSprite$FrameInfo")
public interface FrameInfoAccessor {
	@Accessor
	int getTime();
}
