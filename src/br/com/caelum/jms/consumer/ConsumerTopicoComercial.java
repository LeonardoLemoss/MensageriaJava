package br.com.caelum.jms.consumer;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class ConsumerTopicoComercial {

	public static void main(String[] args) throws Exception{

		
		InitialContext context = new InitialContext();
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory"); 
		Connection connection = factory.createConnection();  
		connection.setClientID("comercial");
		
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
		
		Topic topico = (Topic) context.lookup("loja");
		
		MessageConsumer consumer = session.createDurableSubscriber(topico,"assinatura");
		
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message messagem) {
				
				TextMessage textMessage = (TextMessage)messagem;
				
				try {
					
					System.out.println("Recebendo mensagem : " + textMessage.getText());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
		
	}

}
