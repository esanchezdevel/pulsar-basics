package com.esanchez.devel.pulsar;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

public class PulsarConsumer {

	private static final String PULSAR_HOST = "pulsar://localhost:6650";
	private static final String PULSAR_TOPIC = "test-topic";
	private static final String PULSAR_SUBSCRIPTION = "test-consumer-subscription";
	
	public static void main(String[] args) {
		System.out.println("Pulsar Consumer");
		System.out.println("~~~~~~~~~~~~~~~");
		System.out.println();
		
		PulsarClient client = null;
		try {
			client = PulsarClient.builder()
			        .serviceUrl(PULSAR_HOST)
			        .build();
		} catch (PulsarClientException e) {
			System.out.println("Error initializing Pulsar Client");
			e.printStackTrace();
		}
		
		if (client != null) {
			Consumer<String> consumer = null;
			try {
				consumer = client.newConsumer(Schema.STRING)
				        .topic(PULSAR_TOPIC)
				        .subscriptionName(PULSAR_SUBSCRIPTION)
				        .subscribe();
				
				while (true) {
					  // Wait for a message
					  Message<String> msg = consumer.receive();

					  try {
					      System.out.println("Message received: " + new String(msg.getData()));

					      // Acknowledge the message
					      consumer.acknowledge(msg);
					  } catch (Exception e) {
						  System.out.println("Message failed to process. Redeliver later");
					      consumer.negativeAcknowledge(msg);
					  }
					}
			} catch (PulsarClientException e) {
				System.out.println("Error consuming message");
				e.printStackTrace();
			} finally {
				try {
					if (consumer != null) {
						consumer.close();
					}
					
					if (client != null) {
						client.close();
					}
				} catch (PulsarClientException e) {
					System.out.println("Error closing Pulsar Producer and Client");
					e.printStackTrace();
				}
			}
		}
	}
}
