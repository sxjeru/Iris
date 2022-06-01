package net.coderbot.iris.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.coderbot.iris.Iris;
import net.coderbot.iris.gl.IrisRenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(RenderSystem.class)
public class MixinRenderSystem {
	@Shadow
	private static Matrix4f savedProjectionMatrix;

	@Shadow
	private static PoseStack modelViewStack;

	@Inject(method = "initRenderer", at = @At("RETURN"), remap = false)
	private static void iris$onRendererInit(int debugVerbosity, boolean alwaysFalse, CallbackInfo ci) {
		Iris.onRenderSystemInit();
	}

	@Inject(method = "setProjectionMatrix", at = @At("HEAD"))
	private static void iris$onSetProjectionMatrix(Matrix4f arg, CallbackInfo ci) {
		IrisRenderSystem.setProjectionInverse(arg);
	}

	@Inject(method = "_restoreProjectionMatrix", at = @At("HEAD"))
	private static void iris$onSetProjectionMatrix2(CallbackInfo ci) {
		IrisRenderSystem.setProjectionInverse(savedProjectionMatrix);
	}

	@Inject(method = "applyModelViewMatrix", at = @At(value = "HEAD"))
	private static void iris$onApplyModelViewMatrix(CallbackInfo ci) {
		IrisRenderSystem.setModelViewInverse(modelViewStack.last().pose());
	}
}
