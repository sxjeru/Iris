package net.coderbot.iris.compat.sodium.mixin.pbr_animation;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.jellysquid.mods.sodium.client.render.texture.SpriteUtil;
import net.coderbot.iris.texture.pbr.PBRSpriteHolder;
import net.coderbot.iris.texture.pbr.TextureAtlasSpriteExtension;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextureAtlas.class)
public class MixinTextureAtlasSprite {
	@Inject(method = "getSprite", at = @At("RETURN"), remap = false)
	private void onTailMarkActive(ResourceLocation arg, CallbackInfoReturnable<TextureAtlasSprite> cir) {
		TextureAtlasSpriteExtension extension = (TextureAtlasSpriteExtension) cir.getReturnValue();
		if (extension.hasPBRHolder()) {
			PBRSpriteHolder pbrHolder = extension.getPBRHolder();
			TextureAtlasSprite normalSprite = pbrHolder.getNormalSprite();
			TextureAtlasSprite specularSprite = pbrHolder.getSpecularSprite();
			if (normalSprite != null) {
				SpriteUtil.markSpriteActive(normalSprite);
			}
			if (specularSprite != null) {
				SpriteUtil.markSpriteActive(specularSprite);
			}
		}
	}
}
