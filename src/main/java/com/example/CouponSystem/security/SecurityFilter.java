package com.example.CouponSystem.security;

import com.example.CouponSystem.enums.ClientType;
import com.example.CouponSystem.enums.ErrorMessage;
import com.example.CouponSystem.exception.AuthorizationException;
import com.example.CouponSystem.exception.ErrorResponse;
import com.example.CouponSystem.service.TokenService;
import com.example.CouponSystem.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String tokenHeader = request.getHeader("Authorization");
            if (tokenHeader == null || !tokenHeader.startsWith("Bearer")) {
                filterChain.doFilter(request, response); //if the request don't have a token,can go to controller if authorized in securityConfig!
                return;
            }

            final String token = tokenHeader.substring(7); // header starts with: Bearer someToken, so the token start after index 7
            String email = this.tokenService.getEmailFromToken(token);
            String clientType = this.tokenService.getClientTypeFromToken(token);

//         if the user is not an admin but contains admin in URL -> throw an exception
//         also if the user is admin but not contains admin in URL -> throes an exception
        if (clientType == null || !clientType.equals(ClientType.ADMIN.name()) && request.getRequestURI().contains("admin") || clientType.equals(ClientType.ADMIN.name()) && !request.getRequestURI().contains("admin")) {
            throw new AuthorizationException(ErrorMessage.NOT_AUTHORIZED);
        }

            if (email != null) {
                boolean isTokenExpirationValid = this.tokenService.isExpirationToken(token);
                if (isTokenExpirationValid) {
                    UserDetails userDetails = this.userService.loadUserByUsername(email);
                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken authentication
                                = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                new ArrayList<>()
                        );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
        catch (AuthorizationException e){
            ErrorResponse errorResponse = new ErrorResponse(117, ErrorMessage.NOT_AUTHORIZED.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(convertObjectToJson(errorResponse));
            return;
        }
        filterChain.doFilter(request, response);
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}

