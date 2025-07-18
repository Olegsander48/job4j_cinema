package ru.job4j.cinema.filters;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import ru.job4j.cinema.model.User;

class AuthorizationFilterTest {
    private AuthorizationFilter filter;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain chain;

    @BeforeEach
    void setUp() {
        filter = new AuthorizationFilter();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        chain = new MockFilterChain();
    }

    /**
     * Test case: Accessing a protected URL (/tickets/buy) without a user session should redirect to login.
     */
    @Test
    void whenAccessBuyWithoutUserThenRedirectToLogin() throws Exception {
        request.setRequestURI("/tickets/buy");
        filter.doFilter(request, response, chain);
        assertThat(response.getRedirectedUrl()).endsWith("/users/login");
    }

    /**
     * Test case: Accessing a protected URL (/tickets/buy) with a valid user session should be allowed.
     */
    @Test
    void whenAccessBuyWithUserThenAllow() throws Exception {
        request.setRequestURI("/tickets/buy");
        request.getSession().setAttribute("user", new User());
        filter.doFilter(request, response, chain);
        assertThat(response.getRedirectedUrl()).isNull();
    }

    /**
     * Test case: Accessing a public URL should be allowed without user session.
     */
    @Test
    void whenAccessPublicUrlThenAllow() throws Exception {
        request.setRequestURI("/sessions");
        filter.doFilter(request, response, chain);
        assertThat(response.getRedirectedUrl()).isNull();
    }
}