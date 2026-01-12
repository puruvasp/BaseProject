package com.s2p.filters;

import com.s2p.core.CachedBodyHttpServletRequest;
import com.s2p.core.CachedBodyHttpServletResponse;
import com.s2p.model.ApiRequestLog;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestLoggingFilter extends OncePerRequestFilter
{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {

        // Pre-Processing (wrap request + response)
        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);
        CachedBodyHttpServletResponse cachedResponse = new CachedBodyHttpServletResponse(response);

        // Capturing Request Body
        String requestBody = new String(cachedRequest.getCachedBody(), request.getCharacterEncoding());

        // Persist initial request log (without response yet)
        ApiRequestLog requestLog = new ApiRequestLog();

        requestLog.setMethod(request.getMethod());
        requestLog.setUri(request.getRequestURI());
        requestLog.setHeaders(request.getHeaderNames().toString());
        requestLog.setBody(requestBody);

        // Write The Logic To Persist The API Request Log

        // Business Logic Flow
        try {
            filterChain.doFilter(cachedRequest, response);
        }finally {
            // Post Processing
            String responseBody = new String(cachedResponse.getCachedBody(), response.getCharacterEncoding());

        }




    }
}
