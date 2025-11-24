package com.osrsvoiceover;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("osrsvoiceover")
public interface VoiceoverConfig extends Config
{
	@ConfigItem(
		keyName = "enabled",
		name = "Enabled",
		description = "Is the plugin turned on?"
	)
	default boolean enabled()
	{
		return true;
	}
}
