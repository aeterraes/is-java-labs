package org.example.kotiki.config;

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

@Configuration
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Value("${spring.rabbitmq.queue.cat.save}")
    private String saveCatQueue;

    @Value("${spring.rabbitmq.queue.cat.delete}")
    private String deleteCatQueue;

    @Value("${spring.rabbitmq.queue.cat.update}")
    private String updateCatQueue;

    @Value("${spring.rabbitmq.queue.cat.findByColor}")
    private String findByColorCatQueue;

    @Value("${spring.rabbitmq.queue.cat.findByBreed}")
    private String findByBreedCatQueue;

    @Value("${spring.rabbitmq.queue.cat.findByName}")
    private String findByNameCatQueue;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Bean
    public Queue saveCatQueue() {
        return new Queue(saveCatQueue, false);
    }

    @Bean
    public Queue deleteCatQueue() {
        return new Queue(deleteCatQueue, false);
    }

    @Bean
    public Queue updateCatQueue() {
        return new Queue(updateCatQueue, false);
    }

    @Bean
    public Queue findByColorCatQueue() {
        return new Queue(findByColorCatQueue, false);
    }

    @Bean
    public Queue findByBreedCatQueue() {
        return new Queue(findByBreedCatQueue, false);
    }

    @Bean
    public Queue findByNameCatQueue() {
        return new Queue(findByNameCatQueue, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding saveCatBinding(Queue saveCatQueue, TopicExchange exchange) {
        return BindingBuilder.bind(saveCatQueue).to(exchange).with(String.valueOf(saveCatQueue));
    }

    @Bean
    public Binding deleteCatBinding(Queue deleteCatQueue, TopicExchange exchange) {
        return BindingBuilder.bind(deleteCatQueue).to(exchange).with(String.valueOf(deleteCatQueue));
    }

    @Bean
    public Binding updateCatBinding(Queue updateCatQueue, TopicExchange exchange) {
        return BindingBuilder.bind(updateCatQueue).to(exchange).with(String.valueOf(updateCatQueue));
    }

    @Bean
    public Binding findByColorCatBinding(Queue findByColorCatQueue, TopicExchange exchange) {
        return BindingBuilder.bind(findByColorCatQueue).to(exchange).with(String.valueOf(findByColorCatQueue));
    }

    @Bean
    public Binding findByBreedCatBinding(Queue findByBreedCatQueue, TopicExchange exchange) {
        return BindingBuilder.bind(findByBreedCatQueue).to(exchange).with(String.valueOf(findByBreedCatQueue));
    }

    @Bean
    public Binding findByNameCatBinding(Queue findByNameCatQueue, TopicExchange exchange) {
        return BindingBuilder.bind(findByNameCatQueue).to(exchange).with(String.valueOf(findByNameCatQueue));
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