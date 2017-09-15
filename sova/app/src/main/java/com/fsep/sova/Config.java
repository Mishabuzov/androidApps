package com.fsep.sova;

public class Config {
    public static final String SOVA_ENDPOINT_DEBUG = "http://stage.api.fsep-lab.ru/sova-server/";
    public static final String SOVA_ENDPOINT_RELEASE = SOVA_ENDPOINT_DEBUG;

    public static final String SOVA_STORAGE_ENDPOINT_DEBUG = "http://storage.fsep-lab.ru/";
    public static final String SOVA_STORAGE_ENDPOINT_RELEASE = SOVA_STORAGE_ENDPOINT_DEBUG;

    public static final String SOVA_WEBSOCKET_ENDPOINT = "ws://stage.api.fsep-lab.ru/sova-server/hello/websocket";

    public static final int COUNT_PER_PAGE = 20;
}
