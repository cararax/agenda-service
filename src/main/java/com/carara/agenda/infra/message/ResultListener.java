package com.carara.agenda.infra.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

    public void listen(Long agendaId) throws JsonProcessingException {
        log.info(" [x] Requesting result for " + agendaId);

        String reponse = (String) template.convertSendAndReceive(resultExchange.getName(), resulRoutingKey, agendaId);
//        List<Vote> voteList= newObjectMapper.readValue(reponse, List.class);

        log.info(" [.] Returned ");
    }
}
