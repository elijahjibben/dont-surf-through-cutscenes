package com.jibben.dontsurfthroughcutscenes;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.Perspective;
import net.minecraft.world.GameMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DontSurfThroughCutscenes implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("dont-surf-through-cutscenes");
	private Perspective previousPerspective = Perspective.FIRST_PERSON;


	@Override
	public void onInitialize() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null) return;

			if (client.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) {
				if (client.options.getPerspective() != Perspective.FIRST_PERSON) {
					previousPerspective = client.options.getPerspective();
					client.options.setPerspective(Perspective.FIRST_PERSON);
				}
			} else if (client.options.getPerspective() == Perspective.FIRST_PERSON && previousPerspective != Perspective.FIRST_PERSON) {
				client.options.setPerspective(previousPerspective);
				previousPerspective = Perspective.FIRST_PERSON;
			}
		});
	}
}