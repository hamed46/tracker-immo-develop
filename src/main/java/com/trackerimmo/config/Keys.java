package com.trackerimmo.config;

public class Keys {

	private Keys() {
	}

	public static final ConfigKey<Integer> NOTIFICATION_DEFAULT_TTL = new ConfigKey<>("notification.defaultTTL", 1800);

	public static final ConfigKey<Integer> NOTIFICATION_LONG_LIVE_TTL = new ConfigKey<>("notification.longLiveTTL",
			86400);

	public static final ConfigKey<String> LONG_LIVE_EVENTS = new ConfigKey<>("notification.longLiveEvents",
			"maintenance,unplug,fuelDrop");

	public static final ConfigKey<Integer> PRIVATE_REFRESH_FREQUENCY = new ConfigKey<>(
			"notification.RealtimeRefreshFrequency", 120);

	public static final ConfigKey<Double> ENGINE_POWER_COMPUTE_EPSILON = new ConfigKey<>(
			"schedule.EnginePowerComputeEpsilon", 0.1);

	public static final ConfigKey<String> GEOCODER_FORMAT = new ConfigKey<>("geocoder.format", "%h %r, %t, %s, %c");

	public static final ConfigKey<String> MEILI_SEARCh_URL = new ConfigKey<>("meiliSearch.url",
			"http://54.36.191.72:7701/");

	public static final ConfigKey<String> MEILI_SEARCh_TOKEN = new ConfigKey<>("meilisearch.token");

}
