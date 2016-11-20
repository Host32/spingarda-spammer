package org.spingarda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Ivan on 19/11/2016.
 */
@Controller
@RequestMapping("/")
public class HelloController {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private BotSpam bot;

    @Autowired
    private Facebook facebook;

    @GetMapping
    public String helloFacebook(Model model) {
        if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
            return "redirect:/connect/facebook";
        }

        if (bot.isRunning()) {
            model.addAttribute("log", bot.getLog());
            return "running";
        }

        model.addAttribute("botParams", new BotParams());
        return "hello";
    }

    @GetMapping("/log")
    public String getLog(Model model) throws Exception {
        model.addAttribute("log", bot.getLog());
        return "running";
    }

    @PostMapping
    public String runSpam(Model model, @ModelAttribute BotParams params) throws Exception {
        model.addAttribute("log", bot.getLog());
        if (!bot.isRunning()) {
            bot.run(facebook, params);
        }
        return "running";
    }
}
