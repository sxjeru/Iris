package net.coderbot.iris.mixin.texture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.mojang.blaze3d.platform.NativeImage;

import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;

@Mixin(TextureAtlasSprite.class)
public interface TextureAtlasSpriteAccessor {
	@Accessor("mainImage")
	NativeImage[] getMainImage();

	@Accessor("x")
	int getX();

	@Accessor("y")
	int getY();

	@Invoker("<init>")
	static TextureAtlasSprite callInit(TextureAtlas atlas, TextureAtlasSprite.Info info, int mipLevel, int atlasWidth, int atlasHeight, int x, int y, NativeImage image) {
		return null;
	}

}
