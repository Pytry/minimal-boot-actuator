package llc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Keith Hoopes on 5/19/2015.
 * Copyright Bear River Mutual 2015.
 */
@Controller
@RequestMapping("/hello")
public class HelloWorldController {

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ModelAndView welcome(@PathVariable String name) {

        ModelAndView model = new ModelAndView();
        model.setViewName("hello");
        model.addObject("name", name == null ? "Person":name);

        return model;
    }
}
