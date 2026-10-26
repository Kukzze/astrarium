package com.kukzze.astrarium;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
@Singleton
public class AstrariumDiscordService
{
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private final OkHttpClient okHttpClient;
	private final Gson gson;
	private final AstrariumConfig config;

	@Inject
	public AstrariumDiscordService(OkHttpClient okHttpClient, Gson gson, AstrariumConfig config)
	{
		this.okHttpClient = okHttpClient;
		this.gson = gson;
		this.config = config;
	}

	public void sendSessionCard(String playerName, int combatLevel, SessionTracker session)
	{
		String webhookUrl = config.discordWebhook().trim();
		if (webhookUrl.isEmpty() || !isValidWebhookUrl(webhookUrl))
		{
			return;
		}

		Map<String, Object> embed = new HashMap<>();
		embed.put("title", "📜 Astrarium Session Log \u2022 " + playerName);
		embed.put("description", "Adventurer Combat Level: **" + combatLevel + "** | Session Duration: **" + session.getSessionDurationString() + "**");
		embed.put("color", config.theme().getPrimaryColor().getRGB() & 0xFFFFFF);

		List<Map<String, Object>> fields = new ArrayList<>();

		// XP Field
		Map<String, Object> xpField = new HashMap<>();
		xpField.put("name", "🌟 Total XP Gained");
		xpField.put("value", String.format("%,d XP (%,d XP/hr)", session.getTotalXpGained(), session.getXpPerHour()));
		xpField.put("inline", true);
		fields.add(xpField);

		// Bosses Field
		Map<String, Object> bossField = new HashMap<>();
		bossField.put("name", "⚔️ Bosses & Raids");
		bossField.put("value", session.getBossKillsCount() + " completed");
		bossField.put("inline", true);
		fields.add(bossField);

		// Clues Field
		Map<String, Object> clueField = new HashMap<>();
		clueField.put("name", "📜 Clue Scrolls");
		clueField.put("value", session.getCluesCount() + " solved");
		clueField.put("inline", true);
		fields.add(clueField);

		// Collection Log Field
		if (session.getCollectionLogCount() > 0)
		{
			Map<String, Object> collField = new HashMap<>();
			collField.put("name", "📖 Collection Log");
			collField.put("value", session.getCollectionLogCount() + " new unlocks");
			collField.put("inline", true);
			fields.add(collField);
		}

		embed.put("fields", fields);

		Map<String, Object> footer = new HashMap<>();
		footer.put("text", "Astrarium for RuneLite \u2022 Built by Kukzze");
		embed.put("footer", footer);

		List<Map<String, Object>> embeds = new ArrayList<>();
		embeds.add(embed);

		Map<String, Object> payload = new HashMap<>();
		payload.put("username", "Astrarium Astrolabe");
		payload.put("embeds", embeds);

		String jsonString = gson.toJson(payload);

		Request request = new Request.Builder()
			.url(webhookUrl)
			.post(RequestBody.create(JSON, jsonString))
			.build();

		okHttpClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Call call, IOException e)
			{
				log.warn("Failed to dispatch Astrarium Discord session card: {}", e.getMessage());
			}

			@Override
			public void onResponse(Call call, Response response)
			{
				response.close();
			}
		});
	}

	private static boolean isValidWebhookUrl(String url)
	{
		return url.startsWith("https://discord.com/api/webhooks/")
			|| url.startsWith("https://discordapp.com/api/webhooks/");
	}
}
