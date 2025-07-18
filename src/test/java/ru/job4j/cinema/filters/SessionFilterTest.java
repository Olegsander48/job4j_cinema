package ru.job4j.cinema.filters;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import ru.job4j.cinema.model.User;

class SessionFilterTest {
    private SessionFilter filter;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain chain;

    @BeforeEach
    void setUp() {
        filter = new SessionFilter();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        chain = new MockFilterChain();
    }

    /**
     * Test case: When there is no user in the session, a guest user should be added to the request.
     */
    @Test
    void whenNoUserInSessionThenGuestUserAdded() throws Exception {
        filter.doFilter(request, response, chain);
        User user = (User) request.getAttribute("user");
        assertThat(user).isNotNull();
        assertThat(user.getFullName()).isEqualTo("Гость");
    }

    /**
     * Test case: When a user exists in the session, the same user should be set in the request.
     */
    @Test
    void whenUserInSessionThenSameUserInRequest() throws Exception {
        User realUser = new User(1, "Test User", "test@mail.com", "pass");
        request.getSession().setAttribute("user", realUser);
        filter.doFilter(request, response, chain);
        User user = (User) request.getAttribute("user");
        assertThat(user).isEqualTo(realUser);
    }
}