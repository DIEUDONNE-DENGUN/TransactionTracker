package org.iota.transactiontracking.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
  @Author:Dieudonne Takougang
  @Date: 13/10/2021
  @Description: set swagger configurations
 */
@Controller
@RequestMapping("/")
public class WebApplicationController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "index";
    }
}
