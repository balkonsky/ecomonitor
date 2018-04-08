package com.mqtt.ecomonitor;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class StartApp extends Thread {
    private static final Logger log = LogManager.getLogger(StartApp.class);

    public static void main(String[] args) {
        log.debug("Инициализируем поток для запросов раз в час к погодному API...");
        final Thread get_forecast_hour = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        RequestAPI requestAPI = new RequestAPI();
                        StringBuilder forecast = requestAPI.getForecast();
                        DAO dao = new DAO();
                        MQTT mqtt = new MQTT();

                        double new_pressure = requestAPI.getParsePressure(forecast);
                        log.info("Полученное давление = {}", new_pressure);
                        log.info("Отправляем данные на сервер");
                        dao.setPressure(new_pressure);
                        mqtt.sendMessage(new_pressure);

                        double new_air = requestAPI.getParseAir(forecast);
                        log.info("Полученное качество воздуха = {}", new_air);
                        log.info("Отправляем данные на сервер");
                        dao.setAir(new_air);
                        mqtt.sendMessage(new_air);

                        double new_hum = requestAPI.getParseHumidity(forecast);
                        log.info("Полученная влажность = {}", new_hum);
                        log.info("Отправляем данные на сервер");
                        dao.setHumidity(new_hum);
                        mqtt.sendMessage(new_hum);

                        double new_temp = requestAPI.getParseTemperature(forecast);
                        log.info("Полученная температура = {}", new_temp);
                        log.info("Отправляем данные на сервер");
                        dao.setTemperature(new_temp);
                        mqtt.sendMessage(new_temp);

                        log.info("Спим час");
                        Thread.sleep(3600000);
                        log.info("Проснулись спустя час");
                    } catch (Exception e) {
                        log.error("Непредвиденная ошибка: ", e);
                    }
                }
            }
        };
        log.debug("Поток для запросов раз в час к погодному API инициализирован");
        log.debug("Запускаем поток для запросов раз в час к погодному API: {}", get_forecast_hour.getName());
        get_forecast_hour.start();

        log.debug("Инициализируем поток для запросок к погодному API раз в две минуты...");
        final Thread get_forecast_two_min = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        RequestAPI requestAPI = new RequestAPI();
                        StringBuilder forecast = requestAPI.getForecast();
                        DAO dao = new DAO();
                        MQTT mqtt = new MQTT();

                        double new_pressure = requestAPI.getParsePressure(forecast);
                        double current_pressure = dao.getPressure();
                        double diff_pressure = Math.abs(new_pressure - current_pressure);
                        if (diff_pressure >= 100)

                        {
                            log.info("Полученное давление = {}, Текущее давление = {}, Разница = {}", new_pressure, current_pressure, diff_pressure);
                            log.info("Отправляем данные на сервер");
                            dao.setPressure(new_pressure);
                            mqtt.sendMessage(new_pressure);
                        } else

                        {
                            log.info("Полученное давление = {}, Текущее давление = {}, Разница = {}", new_pressure, current_pressure, diff_pressure);
                        }

                        double new_air = requestAPI.getParseAir(forecast);
                        double current_air = dao.getAir();
                        double diff_air = Math.abs(new_air - current_air);
                        if (diff_air >= 50)

                        {
                            log.info("Полученное качество воздуха = {}, Текущее качество воздуха = {}, Разница = {}", new_air, current_air, diff_air);
                            log.info("Отправляем данные на сервер");
                            dao.setAir(new_air);
                            mqtt.sendMessage(new_air);
                        } else

                        {
                            log.info("Полученное качество воздуха = {}, Текущее качество воздуха = {}, Разница = {}", new_air, current_air, diff_air);
                        }

                        double new_hum = requestAPI.getParseHumidity(forecast);
                        double current_hum = dao.getHumidity();
                        double diff_hum = Math.abs(new_hum - current_hum);
                        if (diff_hum >= 10)

                        {
                            log.info("Полученная влажность = {}, Текущая влажность = {}, Разница = {}", new_hum, current_hum, diff_hum);
                            log.info("Отправляем данные на сервер");
                            dao.setHumidity(new_hum);
                            mqtt.sendMessage(new_hum);
                        } else

                        {
                            log.info("Полученная влажность = {}, Текущая влажность = {}, Разница = {}", new_hum, current_hum, diff_hum);

                        }

                        double new_temp = requestAPI.getParseTemperature(forecast);
                        double current_temp = dao.getTemperature();
                        double diff_temp = Math.abs(new_temp - current_temp);
                        if (diff_temp >= 1)

                        {
                            log.info("Полученная температура = {}, Текущая температура = {}, Разница = {}", new_temp, current_temp, diff_temp);
                            log.info("Отправляем данные на сервер");
                            dao.setTemperature(new_temp);
                            mqtt.sendMessage(new_temp);
                        } else

                        {
                            log.info("Полученная температура = {}, Текущая температура = {}, Разница = {}", new_temp, current_temp, diff_temp);
                        }
                        log.info("Спим две минуты");
                        Thread.sleep(120000);
                        log.info("Проснулись спустя две минуты");
                    } catch (Throwable e) {
                        log.error("Неожиданная ошибка: ", e);
                    }
                }
            }
        };
        log.debug("Поток для запросок к погодному API раз в две минуты инициализован");
        log.debug("Запускаем поток для запросок к погодному API раз в две минуты: {}", get_forecast_two_min.getName());
        get_forecast_two_min.start();
    }
}


