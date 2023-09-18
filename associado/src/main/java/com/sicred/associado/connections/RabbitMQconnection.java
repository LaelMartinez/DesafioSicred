package com.sicred.associado.connections;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQconnection {
	private static final String NOME_EXCHANGE= "amq.direct"; 
	private AmqpAdmin amqpAdmin;
	
	public RabbitMQconnection(AmqpAdmin amqpAdmin) {
		this.amqpAdmin = amqpAdmin;
	}
	
	@SuppressWarnings("unused")
	private Queue fila(String nomeFila) {
		return new Queue(nomeFila, true, false, false);
	}
	
	private DirectExchange trocaDireta() {
		return new DirectExchange(NOME_EXCHANGE);
	}
	
	private Binding relacionamento(Queue fila, DirectExchange troca) {
		return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
	}
	
	@PostConstruct
	private void adiciona() {
		Queue filaBoleto = this.fila(RabbitmqConstantes.FILA_BOLETO);
		Queue filaAssociado = this.fila(RabbitmqConstantes.FILA_ASSOCIADO);
		
		DirectExchange troca = this.trocaDireta();
				
		Binding ligacaoAssociado = this.relacionamento(filaAssociado, troca);
		Binding ligacaoBoleto = this.relacionamento(filaBoleto, troca);
		
		this.amqpAdmin.declareQueue(filaBoleto);
		this.amqpAdmin.declareQueue(filaAssociado);
		
		this.amqpAdmin.declareExchange(troca);
		
		this.amqpAdmin.declareBinding(ligacaoAssociado);
		this.amqpAdmin.declareBinding(ligacaoBoleto);
	};
	
}
