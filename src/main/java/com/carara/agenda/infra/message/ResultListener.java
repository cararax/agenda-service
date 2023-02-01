package com.carara.agenda.infra.message;

import com.carara.agenda.infra.message.response.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpIllegalStateException;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Log4j2(topic = "ResultListener")
public class ResultListener {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange resultExchange;

    @Value("${rabbitmq.result.routingkey}")
    private String resulRoutingKey;

    ObjectMapper newObjectMapper = new ObjectMapper();

    public Result listen(Long agendaId) throws JsonProcessingException {
        log.info("Requesting result for agendaId: " + agendaId);
        String response = (String) template.convertSendAndReceive(resultExchange.getName(), resulRoutingKey, agendaId);

        if (response == null) {
            log.info("Result not found for agendaId: " + agendaId);
            throw new AmqpIllegalStateException("Impossible to calculate result, try again later.");
        }

        Result result= newObjectMapper.readValue(response, Result.class);
        log.info("Result found for agendaId: " + agendaId + " - " + result.toString());
        return result;
    }
}
