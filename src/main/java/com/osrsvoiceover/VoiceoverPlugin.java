package com.osrsvoiceover;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "OSRS Voiceover"
)
public class VoiceoverPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private VoiceoverConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.debug("OSRS Voiceover started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.debug("OSRS Voiceover stopped!");
	}

	@Subscribe
	public void onChatMessage(ChatMessage message) {
        if (config.enabled()) {
            if (message.getType() == ChatMessageType.DIALOG) {
                String msg = message.getMessage();
                System.out.println(msg);
            }
        }
    }

	@Provides
    VoiceoverConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(VoiceoverConfig.class);
	}
}
