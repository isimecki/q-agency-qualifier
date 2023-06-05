package hr.igor.simecki.q_agencyqualifier.web;

import hr.igor.simecki.q_agencyqualifier.dto.EmailDto;
import hr.igor.simecki.q_agencyqualifier.dto.EmailSearchDto;
import hr.igor.simecki.q_agencyqualifier.repository.EmailRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class WebControllerTest {

    @Autowired
    public EmailRepository emailRepository;

    @Autowired
    public MockMvc mockMvc;

    @Test
    public void test_main_initiatesValidView() throws Exception {

        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(result -> assertTrue(result.getModelAndView().getModel().containsKey("email")))
                .andExpect(result -> assertEquals("main", result.getModelAndView().getViewName()));
    }

    @Test
    public void test_history_initiatesValidView() throws Exception {
        this.mockMvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(result -> assertTrue(result.getModelAndView().getModel().containsKey("emailSearch")))
                .andExpect(result -> assertEquals("history", result.getModelAndView().getViewName()));
    }

    @Test
    @Sql(value = "classpath:test-db-scripts/clear-emails.sql", executionPhase = BEFORE_TEST_METHOD)
    public void test_sendEmail_savesToDB() throws Exception {
        HttpServletRequest mockedServletRequest = Mockito.mock(HttpServletRequest.class);
        when(mockedServletRequest.getHeader(matches("Referer"))).thenReturn("origin");
        EmailDto emailDto = new EmailDto();
        emailDto.setContent("tets");
        emailDto.setFrom("test@tets.test");
        emailDto.setTo("test@tets.test");
        emailDto.setSubject("test");
        emailDto.setContent("test");
        this.mockMvc.perform(post("/sendEmail")
                        .flashAttr("email", emailDto)
                        .header("Referer", "origin"))
                .andExpect(result -> assertEquals("redirect:origin", result.getModelAndView().getViewName()));
        assertNotEquals(0, StreamSupport.stream(emailRepository.findAll().spliterator(), false).count());
    }

    @Test
    public void test_fetchEmails_returnsData() throws Exception {
        this.mockMvc.perform(post("/fetchEmails")
                        .flashAttr("emailSearch", new EmailSearchDto())
                        .header("Referer", "origin"))
                .andExpect(result -> assertEquals("history", result.getModelAndView().getViewName()));
    }
}
