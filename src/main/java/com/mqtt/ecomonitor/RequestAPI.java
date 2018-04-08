package com.mqtt.ecomonitor;

import java.io.*;
import java.net.*;

import com.google.gson.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestAPI {
    private static final Logger log = LogManager.getLogger(RequestAPI.class);

    public StringBuilder getForecast() {
        StringBuilder sb = new StringBuilder();
        try {
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(5000).build();
            CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            CloseableHttpResponse response = client.execute(new HttpGet(" "));
            HttpEntity entity = response.getEntity();
            log.debug("Запрос к погодному API ");
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (entity != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        sb.append("\n");
                    }
                    log.debug("Получили успшеный ответ: {} ", response.getStatusLine());
                }
            } else {
                try {
                    log.error("Ошибка при подключении: " + response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
                    log.debug("Еще раз пытаемся обратиться к API, вдруг получится");
                    RequestConfig config_new = RequestConfig.custom()
                            .setConnectTimeout(5000)
                            .setConnectTimeout(5000)
                            .setConnectionRequestTimeout(5000)
                            .setSocketTimeout(5000).build();
                    CloseableHttpClient client_new = HttpClientBuilder.create().setDefaultRequestConfig(config_new).build();
                    CloseableHttpResponse response_new = client_new.execute(new HttpGet("https://api.darksky.net/forecast/4d756d88a5410c440849626517936408/59.902655,30.4847007?units=auto"));
                    HttpEntity entity_new = response_new.getEntity();
                    log.debug("Запрос к погодному API ");
                    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        if (entity_new != null) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(response_new.getEntity().getContent()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                sb.append(line);
                                sb.append("\n");
                            }
                            log.debug("Вот тепеь похоже получили успшеный ответ: {} ", response_new.getStatusLine());
                        }
                    } else {
                        log.error("Нет все так же безуспешно, получини ошибку: " + response_new.getStatusLine().getStatusCode() + " " + response_new.getStatusLine().getReasonPhrase());
                    }
                } catch (Exception cause) {
                    log.error("Ошибка при подключении: ", cause);
                }
            }
        } catch (Exception cause) {
            log.error("Ошибка при подключении: ", cause);
        }
        return sb;
    }

    public StringBuilder getForecastOld() {
        String query = "https://api.darksky.net/forecast/4d756d88a5410c440849626517936408/59.902655,30.4847007?units=auto";
        HttpURLConnection connection = null;
        StringBuilder sb = new StringBuilder();

        try {
            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
            } else {
                log.error("Faile, " + connection.getResponseCode() + " " + connection.getResponseMessage());
            }
        } catch (Throwable cause) {
            log.error(cause);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return sb;
    }

    public double getParsePressure(StringBuilder sb) {
        String json = new String(sb);
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(json).getAsJsonObject();

        String pressure = object.getAsJsonObject("currently")
                .get("pressure").toString();
        log.debug("Распарсили полученное значение давления: {}", ((Double.valueOf(pressure) * 0.7) + " mm Hg"));
        return (Double.valueOf(pressure) * 0.7);
    }

    public double getParseAir(StringBuilder sb) {
        String json = new String(sb);
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(json).getAsJsonObject();

        String air = object.getAsJsonObject("currently")
                .get("ozone").toString();
        log.debug("Распарсили полученное значение качества воздуха: {} ", (Double.valueOf(air) + " ppm"));
        return Double.valueOf(air);
    }

    public double getParseHumidity(StringBuilder sb) {
        String json = new String(sb);
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(json).getAsJsonObject();

        String humidity = object.getAsJsonObject("currently")
                .get("humidity").toString();
        log.debug("Распарсили полученное значение влажности: {}", ((Double.valueOf(humidity) * 100) + " %"));
        return (Double.valueOf(humidity) * 100);
    }

    public double getParseTemperature(StringBuilder sb) {
        String json = new String(sb);
        JsonParser parser = new JsonParser();
        JsonObject object = parser.parse(json).getAsJsonObject();

        String temperature = object.getAsJsonObject("currently")
                .get("temperature").toString();
        log.debug("Распарсили полученное значение: {}", (Double.valueOf(temperature) + " °C"));
        return Double.valueOf(temperature);

    }

}
