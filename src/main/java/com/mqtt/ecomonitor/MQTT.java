package com.mqtt.ecomonitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Date;

public class MQTT {
    private static String topic = "VoSens/12_line/pressure";
    private static int qos = 1;
    private static String broker = "tcp://91.238.230.148:1883";
    private static String clientID = "JavaSample";
    private static String content = null;
    MemoryPersistence persistence = new MemoryPersistence();
    private static final Logger log = LogManager.getLogger(MQTT.class);

    public void sendMessage (double payload){
        try {
            MqttClient client = new MqttClient(broker,clientID,persistence);
            MqttConnectOptions conOptions = new MqttConnectOptions();
            conOptions.setCleanSession(true);
            log.debug("Соедеямся с брокером: " + broker);
            client.connect(conOptions);
            log.debug("Соединились");
            log.debug("Публикуем сообщение " + (content=String.valueOf(payload)));
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            client.publish(topic,message);
            log.debug("Сообщение опубликовали");
            client.disconnect();
            log.debug("Разъединились");
        }

        catch (MqttException e){
            log.error("reason " + e.getReasonCode());
            log.error("msg " + e.getMessage());
            log.error("loc " + e.getLocalizedMessage());
            log.error("cause " + e.getCause());
            log.error("excep " + e);
            log.error(e);
        }

    }
}
