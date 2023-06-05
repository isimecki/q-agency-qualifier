package hr.igor.simecki.q_agencyqualifier.web;

import hr.igor.simecki.q_agencyqualifier.dto.EmailDto;
import hr.igor.simecki.q_agencyqualifier.dto.EmailSearchDto;
import hr.igor.simecki.q_agencyqualifier.model.Importance;
import hr.igor.simecki.q_agencyqualifier.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class WebController {

    public static final String HTTP_REQUEST_HEADER_REFERER = "Referer";
    public static final String MODEL_ATTRIBUTE_EMAIL_SEARCH = "emailSearch";
    public static final String MODEL_ATTRIBUTE_RESULT = "result";
    public static final String MODEL_ATTRIBUTE_IMPORTANCES = "importances";
    public static final String MODEL_ATTRIBUTE_EMAIL = "email";
    private final EmailService emailService;

    @Autowired
    public WebController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping({"/", "/main"})
    public String mainPage(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_IMPORTANCES, Arrays.stream(Importance.values()).map(Importance::name).collect(Collectors.toList()));
        model.addAttribute(MODEL_ATTRIBUTE_EMAIL, new EmailDto());
        return "main";
    }

    @RequestMapping("/history")
    public String history(Model model) {
        model.addAttribute(MODEL_ATTRIBUTE_EMAIL_SEARCH, new EmailSearchDto());
        return "history";
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@Valid @ModelAttribute(MODEL_ATTRIBUTE_EMAIL) EmailDto emailDto, BindingResult result, HttpServletRequest request) {
        if (!result.hasErrors()) {
            emailService.saveEmail(emailDto.toEmail());
        }
        String referer = request.getHeader(HTTP_REQUEST_HEADER_REFERER);
        return "redirect:" + referer;
    }

    @PostMapping("/fetchEmails")
    public String fetchEmails(@Valid @ModelAttribute(MODEL_ATTRIBUTE_EMAIL_SEARCH) EmailSearchDto searchDto, BindingResult result, Model model) {
        model.addAllAttributes(result.getModel());
        model.addAttribute(MODEL_ATTRIBUTE_EMAIL_SEARCH, new EmailSearchDto());
        model.addAttribute(MODEL_ATTRIBUTE_RESULT, emailService.fetchEmails(searchDto.getSearchString()).stream().map(EmailDto::fromEmail).collect(Collectors.toList()));
        return "history";
    }
}
