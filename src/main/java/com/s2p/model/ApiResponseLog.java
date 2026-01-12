package com.s2p.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(
        name = "ApiResponseLog",
        description = "Entity that logs API response details including status, body, duration, and linked request."
)
public class ApiResponseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apiResponseId;

    private int status;

    @Column(columnDefinition = "TEXT")
    private String body;

    @CurrentTimestamp
    private LocalDateTime timestamp;

    private Long durationMs;

    private String host;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = ApiRequestLog.class)
    @JoinColumn(name = "api_response_id", referencedColumnName = "apiResponseId")
    private ApiRequestLog requestLog;
}
