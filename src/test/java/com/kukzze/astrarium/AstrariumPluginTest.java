package com.kukzze.astrarium;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class AstrariumPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(AstrariumPlugin.class);
		RuneLite.main(args);
	}
}
