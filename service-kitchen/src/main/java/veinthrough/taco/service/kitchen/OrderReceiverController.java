package veinthrough.taco.service.kitchen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import veinthrough.taco.messsaging.MessageReceiver;
import veinthrough.taco.model.Order;
import veinthrough.taco.utils.MethodLog;

@Profile({"jms-receiver", "rabbit-receiver"})
@Controller
@RequestMapping("/orders")
@Slf4j
public class OrderReceiverController {
    private MessageReceiver<Order> receiver;

    @Autowired
    public void setReceiver(MessageReceiver<Order> receiver) {
        this.receiver = receiver;

        log.debug(MethodLog.log(
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                "receiver", receiver.toString()
        ));
    }

    @GetMapping("/receive")
    public String receiveOrder(Model model) {
        Order order = receiver.receiveObject();

        log.debug(MethodLog.log(
                Thread.currentThread().getStackTrace()[1].getMethodName(),
                1,
                "Message received",
                "order", order.toString()));
        if (order != null) {
            model.addAttribute("order", order);
            return "receiveOrder";
        }
        return "noOrder";
    }
}
