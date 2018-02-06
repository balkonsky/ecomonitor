package com.mqtt.ecomonitor;


import java.util.Date;

public class StartApp {
    public static void main(String[] args) {

        try {
            while (true) {
                RequestAPI requestAPI = new RequestAPI();
                StringBuilder forecast = requestAPI.getForecast();
                DAO dao = new DAO();
                MQTT mqtt = new MQTT();

                if (Math.abs(requestAPI.getParsePressure(forecast) - dao.getPressure()) >= 1) {
                    System.out.println((new Date(System.currentTimeMillis())) + " | " + "Изменилось давление на " + Math.abs(requestAPI.getParsePressure(forecast) - dao.getPressure()));
                    dao.setPressure(requestAPI.getParsePressure(forecast));
                    mqtt.sendMessage(requestAPI.getParsePressure(forecast));
                } else {
                    System.out.println((new Date(System.currentTimeMillis())) + " | " + requestAPI.getParsePressure(forecast) + " " + dao.getPressure() + " текущая разница " + Math.abs(requestAPI.getParsePressure(forecast) - dao.getPressure()));
                }

                if (Math.abs(requestAPI.getParseAir(forecast) - dao.getAir()) >= 1) {
                    System.out.println((new Date(System.currentTimeMillis()))+ " | " + "Изменилось качество воздуха на " + Math.abs(requestAPI.getParseAir(forecast) - dao.getAir()));
                    dao.setAir(requestAPI.getParseAir(forecast));
                    mqtt.sendMessage(requestAPI.getParseAir(forecast));
                } else {
                    System.out.println((new Date(System.currentTimeMillis())) + " | " + requestAPI.getParseAir(forecast) + " " + dao.getAir() + " текущая разница " + Math.abs(requestAPI.getParseAir(forecast) - dao.getAir()));
                }

                if (Math.abs(requestAPI.getParseHumidity(forecast) - dao.getHumidity()) >= 1) {
                    System.out.println((new Date(System.currentTimeMillis())) + " | " + "Изменилась влажность на " + Math.abs(requestAPI.getParseHumidity(forecast) - dao.getHumidity()));
                    dao.setHumidity(requestAPI.getParseHumidity(forecast));
                    mqtt.sendMessage(requestAPI.getParseHumidity(forecast));
                } else {
                    System.out.println((new Date(System.currentTimeMillis())) + " | " + requestAPI.getParseHumidity(forecast) + " " + dao.getHumidity() + " текущая разница " + (Math.abs(requestAPI.getParseHumidity(forecast) - dao.getHumidity())));

                }

                if (Math.abs(requestAPI.getParseTemperature(forecast) - dao.getTemperature()) >= 1) {
                    System.out.println((new Date(System.currentTimeMillis())) + " | " + "Изменилась температура на " + Math.abs(requestAPI.getParseTemperature(forecast) - dao.getTemperature()));
                    dao.setTemperature(requestAPI.getParseTemperature(forecast));
                    mqtt.sendMessage(requestAPI.getParseTemperature(forecast));
                } else {
                    System.out.println((new Date(System.currentTimeMillis())) + " | " + requestAPI.getParseTemperature(forecast) + " " + dao.getTemperature() + " текущая разница " + Math.abs(requestAPI.getParseTemperature(forecast) - dao.getTemperature()));
                }
                System.out.println((new Date(System.currentTimeMillis())) + " | " + "Спим две минуты");
                Thread.sleep(120000);
                System.out.println((new Date(System.currentTimeMillis())) + " | " + "Проснулись");
            }
        }
        catch (Throwable e){
            System.out.println(e);
        }
    }

}
