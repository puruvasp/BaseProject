package com.s2p.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(
        name = "ApiRequestLog",
        description = "Entity that logs API request details including method, URI, headers, body, caller info, and links to the response."
)
public class ApiRequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apiRequestId;

    private String method;

    private String uri;

    @Column(columnDefinition = "TEXT")
    private String headers;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    private String callerName;

    @OneToOne
    @JoinColumn(
            name = "api_response_id",
            referencedColumnName = "apiResponseId",
            nullable = false
    )
    private ApiResponseLog responseLog;
}
