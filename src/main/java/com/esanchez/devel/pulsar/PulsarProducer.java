package com.esanchez.devel.pulsar;

import java.util.HashSet;
import java.util.Set;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.common.policies.data.TenantInfo;
import org.apache.pulsar.common.policies.data.TenantInfoImpl;

public class PulsarProducer {
	
	private static final String PULSAR_ADMIN_HOST = "http://localhost:8080";
	private static final String PULSAR_ADMIN_ROLE = "admin-roles";
	private static final String PULSAR_CLUSTER = "standalone";
	private static final String PULSAR_HOST = "pulsar://localhost:6650";
	private static final String PULSAR_TENANT = "test-tenant";
	private static final String PULSAR_NAMESPACE = "test-namespace";
	private static final String PULSAR_TOPIC = "persistent://" + PULSAR_TENANT + "/" + PULSAR_NAMESPACE + "/test-topic";
	
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
			
			// Create tenant and namespace if they not exists
			try {
				PulsarAdmin admin = PulsarAdmin.builder()
						.serviceHttpUrl(PULSAR_ADMIN_HOST)
						.build();
				
				if (!admin.tenants().getTenants().contains(PULSAR_TENANT)) {
					
					Set<String> adminRoles = new HashSet<>();
					adminRoles.add(PULSAR_ADMIN_ROLE);
					
					// We have to use an already existing cluster. The default cluster is
					// "standalone"
					Set<String> clusters = new HashSet<>();
					clusters.add(PULSAR_CLUSTER);
					
					TenantInfo tenantInfo = new TenantInfoImpl(adminRoles, clusters);
					
					admin.tenants().createTenant(PULSAR_TENANT, tenantInfo);	    
				}
				
				if (!admin.namespaces().getNamespaces(PULSAR_TENANT).contains(PULSAR_NAMESPACE)) {
					admin.namespaces().createNamespace(PULSAR_TENANT + "/" + PULSAR_NAMESPACE);
				}
			} catch (Exception e) {
				System.out.println("Error requesting admin information");
				e.printStackTrace();
			}
			
			Producer<String> producer = null;
			try {
				// If no tenant and namespace are defined, the topic will be created in
				// tenant=public and namespace=default
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
