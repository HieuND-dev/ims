package be_im_interview_management.controller;

import be_im_interview_management.enums.Constant;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: HieuND64
 * Date Time: 7/27/2024 9:38 PM
 */
@RestController
public class HelloController {

    @Secured(Constant.ADMIN_ROLE)
    @GetMapping("/")
    public String hello() {
        return "Hello, World!";
    }
}
