package net.coderbot.iris.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ShaderLoadErrorScreen extends Screen {
	private final Component info;
	private final Exception e;

	public ShaderLoadErrorScreen(Component title, Component info, Exception e) {
		super(title);
		this.info = info;
		this.e = e;
	}

	@Override
	public void init(Minecraft minecraft, int i, int j) {
		super.init(minecraft, i, j);
		this.addButton(new Button(this.width / 2 - 116, this.height / 2 + 62 + -16, 114, 20, Component.nullToEmpty("Return to menu"), arg -> {
			// Reset the whole screen
			minecraft.setScreen(new ShaderPackScreen(null));
		}));
		this.addButton(new Button(this.width / 2 + 2, this.height / 2 + 62 + -16, 114, 20, Component.nullToEmpty("Copy error"), arg -> {
			StringWriter writer = new StringWriter();
			try (PrintWriter printWriter = new PrintWriter(writer)) {
				e.printStackTrace(printWriter);
				String errorString = writer.toString();
				minecraft.keyboardHandler.setClipboard(errorString);
			}
		}));
	}

	@Override
	public void render(PoseStack poseStack, int i, int j, float f) {
		this.fillGradient(poseStack, 0, 0, width, height, -1073741824, -1073741824);

		drawCenteredString(poseStack, this.font, title, (int)(this.width * 0.5), (this.height - 166) / 2 + 8, 0xFFFFFF);
		drawCenteredString(poseStack, this.font, info, (int)(this.width * 0.5), (this.height - 140) / 2 + 8, 0xFFFFFF);

		super.render(poseStack, i, j, f);
	}
}
