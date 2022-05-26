package com.will;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Hello implements Serializable {
    private String message;
    private String description;
}
