package veinthrough.taco.messsaging;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
class MessagePrinter {
    <T> void printMessage(T message) {
        log.info(message.toString());
    }
}
