package org.example.owners.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Value("${spring.rabbitmq.queue.owner.save}")
    private String saveOwnerQueue;

    @Value("${spring.rabbitmq.queue.owner.delete}")
    private String deleteOwnerQueue;

    @Value("${spring.rabbitmq.queue.owner.update}")
    private String updateOwnerQueue;

    @Value("${spring.rabbitmq.queue.owner.getById}")
    private String getOwnerByIdQueue;

    @Value("${spring.rabbitmq.queue.owner.getAll}")
    private String getAllOwnersQueue;

    @Value("${spring.rabbitmq.queue.owner.findByName}")
    private String getOwnerByNameQueue;

    @Value("${spring.rabbitmq.queue.owner.findByCatId}")
    private String findAllCatsByOwnerIdQueue;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Bean
    public Queue saveOwnerQueue() {
        return new Queue(saveOwnerQueue, false);
    }

    @Bean
    public Queue deleteOwnerQueue() {
        return new Queue(deleteOwnerQueue, false);
    }

    @Bean
    public Queue updateOwnerQueue() {
        return new Queue(updateOwnerQueue, false);
    }

    @Bean
    public Queue getOwnerByIdQueue() {
        return new Queue(getOwnerByIdQueue, false);
    }

    @Bean
    public Queue getAllOwnersQueue() {
        return new Queue(getAllOwnersQueue, false);
    }

    @Bean
    public Queue getOwnerByNameQueue() {
        return new Queue(getOwnerByNameQueue, false);
    }

    @Bean
    public Queue findAllCatsByOwnerIdQueue() {
        return new Queue(findAllCatsByOwnerIdQueue, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding saveOwnerBinding(Queue saveOwnerQueue, TopicExchange exchange) {
        return BindingBuilder.bind(saveOwnerQueue).to(exchange).with(String.valueOf(saveOwnerQueue));
    }

    @Bean
    public Binding deleteOwnerBinding(Queue deleteOwnerQueue, TopicExchange exchange) {
        return BindingBuilder.bind(deleteOwnerQueue).to(exchange).with(String.valueOf(deleteOwnerQueue));
    }

    @Bean
    public Binding updateOwnerBinding(Queue updateOwnerQueue, TopicExchange exchange) {
        return BindingBuilder.bind(updateOwnerQueue).to(exchange).with(String.valueOf(updateOwnerQueue));
    }

    @Bean
    public Binding getOwnerByIdBinding(Queue getOwnerByIdQueue, TopicExchange exchange) {
        return BindingBuilder.bind(getOwnerByIdQueue).to(exchange).with(String.valueOf(getOwnerByIdQueue));
    }

    @Bean
    public Binding getAllOwnersBinding(Queue getAllOwnersQueue, TopicExchange exchange) {
        return BindingBuilder.bind(getAllOwnersQueue).to(exchange).with(String.valueOf(getAllOwnersQueue));
    }

    @Bean
    public Binding getOwnerByNameBinding(Queue getOwnerByNameQueue, TopicExchange exchange) {
        return BindingBuilder.bind(getOwnerByNameQueue).to(exchange).with(String.valueOf(getOwnerByNameQueue));
    }

    @Bean
    public Binding findAllCatsByOwnerIdBinding(Queue findAllCatsByOwnerIdQueue, TopicExchange exchange) {
        return BindingBuilder.bind(findAllCatsByOwnerIdQueue).to(exchange).with(String.valueOf(findAllCatsByOwnerIdQueue));
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setMessageConverter(jsonMessageConverter());
        factory.setDefaultRequeueRejected(false);
        return factory;
    }
}