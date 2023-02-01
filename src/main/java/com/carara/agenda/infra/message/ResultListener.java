package com.carara.agenda.infra.message;

import com.carara.agenda.infra.message.response.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpIllegalStateException;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class ResultListener {

    //CLIENT
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange resultExchange;

    @Value("${rabbitmq.result.routingkey}")
    private String resulRoutingKey;

    ObjectMapper newObjectMapper = new ObjectMapper();

    public Result listen(Long agendaId) throws JsonProcessingException {
        log.info(" [1] Requesting result for agenda " + agendaId);
        String response = (String) template.convertSendAndReceive(resultExchange.getName(), resulRoutingKey, agendaId);

        if (response == null) {
            throw new AmqpIllegalStateException("Impossible to calculate result, try again later.");
        }

        Result result= newObjectMapper.readValue(response, Result.class);
        log.info(" [8] Returned , data: " + result.toString());
        return result;
    }
}
