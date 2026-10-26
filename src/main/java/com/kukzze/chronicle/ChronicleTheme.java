package com.kukzze.chronicle;

import java.awt.Color;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChronicleTheme
{
	SUNFIRE_GOLD(
		"Sunfire Gold",
		new Color(255, 185, 35),
		new Color(210, 130, 20),
		new Color(30, 24, 18),
		new Color(45, 32, 16),
		new Color(22, 18, 14),
		new Color(255, 170, 0)
	),
	ZAROSIAN_VOID(
		"Zarosian Void",
		new Color(185, 115, 255),
		new Color(135, 60, 210),
		new Color(24, 18, 32),
		new Color(38, 22, 54),
		new Color(16, 14, 24),
		new Color(170, 85, 255)
	),
	PRIFDDINAS_CRYSTAL(
		"Prifddinas Crystal",
		new Color(60, 225, 215),
		new Color(25, 165, 175),
		new Color(16, 28, 30),
		new Color(20, 48, 52),
		new Color(12, 20, 22),
		new Color(50, 210, 200)
	),
	MORYTANIA_BLOOD(
		"Morytania Blood",
		new Color(245, 65, 75),
		new Color(185, 35, 45),
		new Color(28, 16, 18),
		new Color(48, 18, 22),
		new Color(20, 12, 14),
		new Color(230, 50, 60)
	),
	RUNIC_SLATE(
		"Runic Slate",
		new Color(145, 185, 245),
		new Color(95, 135, 205),
		new Color(20, 24, 32),
		new Color(28, 38, 54),
		new Color(14, 18, 24),
		new Color(115, 165, 235)
	);

	private final String name;
	private final Color primaryColor;
	private final Color secondaryColor;
	private final Color cardBackground;
	private final Color headerGradientStart;
	private final Color headerGradientEnd;
	private final Color progressBarColor;

	@Override
	public String toString()
	{
		return name;
	}
}
