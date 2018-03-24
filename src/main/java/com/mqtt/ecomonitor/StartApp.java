package com.mqtt.ecomonitor;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StartApp {
    private static final Logger rootLog = LogManager.getRootLogger();
    private static final Logger log = LogManager.getLogger(StartApp.class);

    public static void main(String[] args) {
        try {
            while (true) {
                RequestAPI requestAPI = new RequestAPI();
                StringBuilder forecast = requestAPI.getForecast();
                DAO dao = new DAO();
                MQTT mqtt = new MQTT();

                if (Math.abs(requestAPI.getParsePressure(forecast) - dao.getPressure()) >= 1) {
                    log.info("Изменилось давление на " + Math.abs(requestAPI.getParsePressure(forecast) - dao.getPressure()));
                    dao.setPressure(requestAPI.getParsePressure(forecast));
                    mqtt.sendMessage(requestAPI.getParsePressure(forecast));
                } else {
                    log.info(requestAPI.getParsePressure(forecast) + " " + dao.getPressure() + " текущая разница " + Math.abs(requestAPI.getParsePressure(forecast) - dao.getPressure()));
                }

                if (Math.abs(requestAPI.getParseAir(forecast) - dao.getAir()) >= 1) {
                    log.info("Изменилось качество воздуха на " + Math.abs(requestAPI.getParseAir(forecast) - dao.getAir()));
                    dao.setAir(requestAPI.getParseAir(forecast));
                    mqtt.sendMessage(requestAPI.getParseAir(forecast));
                } else {
                    log.info(requestAPI.getParseAir(forecast) + " " + dao.getAir() + " текущая разница " + Math.abs(requestAPI.getParseAir(forecast) - dao.getAir()));
                }

                if (Math.abs(requestAPI.getParseHumidity(forecast) - dao.getHumidity()) >= 1) {
                    log.info("Изменилась влажность на " + Math.abs(requestAPI.getParseHumidity(forecast) - dao.getHumidity()));
                    dao.setHumidity(requestAPI.getParseHumidity(forecast));
                    mqtt.sendMessage(requestAPI.getParseHumidity(forecast));
                } else {
                    log.info(requestAPI.getParseHumidity(forecast) + " " + dao.getHumidity() + " текущая разница " + (Math.abs(requestAPI.getParseHumidity(forecast) - dao.getHumidity())));

                }

                if (Math.abs(requestAPI.getParseTemperature(forecast) - dao.getTemperature()) >= 1) {
                    log.info("Изменилась температура на " + Math.abs(requestAPI.getParseTemperature(forecast) - dao.getTemperature()));
                    dao.setTemperature(requestAPI.getParseTemperature(forecast));
                    mqtt.sendMessage(requestAPI.getParseTemperature(forecast));
                } else {
                    log.info(requestAPI.getParseTemperature(forecast) + " " + dao.getTemperature() + " текущая разница " + Math.abs(requestAPI.getParseTemperature(forecast) - dao.getTemperature()));
                }
                log.info("Спим две минуты");
                Thread.sleep(120000);
                log.info("Проснулись");
            }
        } catch (Throwable e) {
            log.error(e);
        }
    }

}
