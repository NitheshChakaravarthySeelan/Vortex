package com.chat.vortex.gateway.service;

import org.apache.catalina.valves.JsonErrorReportValve;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;

import com.chat.vortex.shared.model.Packet;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WebSocketServiceImpl implements WebSocketService{
   
    private final ObjectMapper objectMapper;

    public WebSocketServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    // Using session we can create a websocketmessage to send over internet cause
    // browser does not have to intelligence to know that.
    @Override
    public Mono<Void> handle(WebSocketSession session) {

    Flux<Packet> output = 
        session.receive()
        // Extract it here
        .map(msg -> msg.getPayloadAsText())
        // Deserialize it here
        .map(payload ->  { try {
            return objectMapper.readValue(payload, Packet.class) } 
        catch (Exception e) {
            new JsonErrorReportValve();
        }});

    // validate packet

    // check permission
    

    // Save to db

    // publish to kafka 
    // We can just map out everything in the above syntax

    // Serialize the msg back
    String json = objectMapper.writeValueAsString(output);
    // convert it back to websocketmessage to send it via internet 
    WebSocketMessage response = session.textMessage(json);

    // TODO: I think we need to send to a pub / sub
    return session.send(Flux.just(response));
   }
}
