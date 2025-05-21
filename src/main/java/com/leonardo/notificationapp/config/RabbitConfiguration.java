package com.leonardo.notificationapp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RabbitConfiguration {

    @Value("${rabbitmq.queue.pending.proposal}")
    private String pendingProposalQueue;

    @Value("${rabbitmq.queue.completed.proposal}")
    private String completedProposalQueue;

    @Bean
    public MessageConverter messageConverter() {
        log.info("Configuring Jackson2JsonMessageConverter for RabbitMQ");
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue pendingProposalQueue() {
        log.info("Creating queue: {}", pendingProposalQueue);
        return new Queue(pendingProposalQueue, true);
    }

    @Bean
    public Queue completedProposalQueue() {
        log.info("Creating queue: {}", completedProposalQueue);
        return new Queue(completedProposalQueue, true);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        log.info("Configuring RabbitTemplate with custom message converter");
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
