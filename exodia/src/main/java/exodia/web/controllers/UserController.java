package exodia.web.controllers;

import exodia.domain.models.binding.UserLoginBindingModel;
import exodia.domain.models.binding.UserRegisterBindingModel;
import exodia.domain.models.service.UserServiceModel;
import exodia.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/register")
    public ModelAndView modelAndView(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") != null) {
            modelAndView.setViewName("redirect:/home");
        } else {
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute UserRegisterBindingModel bindingModel, ModelAndView modelAndView) {
        if (!bindingModel.getPassword().equals(bindingModel.getConfirmPassword())) {
            throw new IllegalArgumentException("Not a valid Password");
        }

        if (!this.userService.registerUser(this.modelMapper.map(bindingModel, UserServiceModel.class))) {
            throw new IllegalArgumentException("Cannot save USER");
        }

        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") != null) {
            modelAndView.setViewName("redirect:/home");
        } else {
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@ModelAttribute UserLoginBindingModel bindingModel, ModelAndView modelAndView,
                                  HttpSession session) {
        UserServiceModel model = this.userService.loginUser(this.modelMapper.map(bindingModel, UserServiceModel.class));

        if (model == null) {
            throw new IllegalArgumentException("Not a valid User or Password");
        }

        session.setAttribute("userId", model.getId());
        session.setAttribute("username", model.getUsername());

        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/login");
        } else {
            session.invalidate();
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

}
