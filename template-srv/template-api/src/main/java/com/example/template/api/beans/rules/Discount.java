package com.example.template.api.beans.rules;

import lombok.Data;

@Data
public class Discount {
    private Long quota;

    private Long threshold;
}
