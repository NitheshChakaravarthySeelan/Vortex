package com.chat.vortex.kafka.producer;

import com.chat.vortex.kafka.event.Event;

public interface Producer {

    void publish(Event event);

}
