package com.disconnected.marketplace.config; 
 
import org.springframework.amqp.core.DirectExchange; 
import org.springframework.amqp.rabbit.connection.ConnectionFactory; 
import org.springframework.amqp.rabbit.core.RabbitTemplate; 
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
@Configuration 
public class RabbitMQConfig { 
 
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class); 
 
    @Bean 
    public DirectExchange orderStatusExchange() { 
        logger.info("Creating DirectExchange bean for orderStatusExchange."); 
        return new DirectExchange("orderStatusExchange"); 
    } 
 
    @Bean 
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) { 
        try { 
            logger.info("Creating RabbitTemplate bean."); 
            return new RabbitTemplate(connectionFactory); 
        } catch (Exception e) { 
            logger.error("Error creating RabbitTemplate bean: ", e); 
            throw e; 
        } 
    } 
} 