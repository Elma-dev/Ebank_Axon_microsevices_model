package dev.elma.demo.commonapi.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class BaseEvent {
    @Getter private String id;
}
