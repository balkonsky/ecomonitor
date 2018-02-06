package com.mqtt.ecomonitor;

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

    public void sendMessage (double payload){
        try {
            MqttClient client = new MqttClient(broker,clientID,persistence);
            MqttConnectOptions conOptions = new MqttConnectOptions();
            conOptions.setCleanSession(true);
            System.out.println((new Date(System.currentTimeMillis())) + " | " + "Connecting to broker: " + broker);
            client.connect(conOptions);
            System.out.println((new Date(System.currentTimeMillis())) + " | " + "Connected");
            System.out.println((new Date(System.currentTimeMillis())) + " | " + "Publishing message " + (content=String.valueOf(payload)));
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            client.publish(topic,message);
            System.out.println((new Date(System.currentTimeMillis())) + " | " + "Message published");
            client.disconnect();
            System.out.println((new Date(System.currentTimeMillis())) + " | " + "Disconected");
            //System.exit(0);
        }

        catch (MqttException e){
            System.out.println("reason " + e.getReasonCode());
            System.out.println("msg " + e.getMessage());
            System.out.println("loc " + e.getLocalizedMessage());
            System.out.println("cause " + e.getCause());
            System.out.println("excep " + e);
            e.printStackTrace();
        }

    }
}
