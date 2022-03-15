package com.assigment.suretime;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class HomePageController {

    @GetMapping("")
    public RedirectView homePage() {
        return new RedirectView("swagger-ui/");
    }


//    @GetMapping({"api-docs/", "api-docs", "docs", "docs/"})
//    public RedirectView apiDocs(){
//        return new RedirectView("/v2/api-docs");
//    }
}
