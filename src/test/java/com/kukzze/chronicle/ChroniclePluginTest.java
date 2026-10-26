package com.kukzze.chronicle;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ChroniclePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ChroniclePlugin.class);
		RuneLite.main(args);
	}
}
