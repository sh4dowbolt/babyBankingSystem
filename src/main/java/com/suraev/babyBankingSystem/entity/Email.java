package com.suraev.babyBankingSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import io.swagger.v3.oas.annotations.media.Schema;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "email")
@Schema(description = "Email")  
public class Email implements Serializable{

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID", example = "1")
    private Long id;

    @Column(name = "email")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email address")
    @Schema(description = "Email", example = "test@test.com")
    private String email;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "User")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
}
