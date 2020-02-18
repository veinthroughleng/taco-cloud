package veinthrough.taco.messsaging;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class MessagePrinter {
    static <T> void printMessage(T message) {
        log.info(message.toString());
    }
}
