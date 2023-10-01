package com.esanchez.devel.pulsar;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

public class PulsarProducer {

	private static final String PULSAR_HOST = "pulsar://localhost:6650";
	private static final String PULSAR_TOPIC = "test-topic";
	
	public static void main(String[] args) {
		System.out.println("Pulsar Producer");
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
			Producer<String> producer = null;
			try {
				producer = client.newProducer(Schema.STRING)
				        .topic(PULSAR_TOPIC)
				        .create();
				
				// You can then send messages to the broker and topic you specified:
				String message = "This is a test message sent from JAVA client";
				producer.send(message);
				
				System.out.println("Message sent to Pulsar");
			} catch (PulsarClientException e) {
				System.out.println("Error producing message");
				e.printStackTrace();
			} finally {
				try {
					if (producer != null) {
						producer.close();
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
