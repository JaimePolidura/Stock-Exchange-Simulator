package es.jaime.gateway.orders;

import es.jaime.gateway._shared.infrastrocture.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public final class ExecuteOrderController extends Controller {
    @PostMapping("/executeorder")
    public void executeorder (){

    }
}
