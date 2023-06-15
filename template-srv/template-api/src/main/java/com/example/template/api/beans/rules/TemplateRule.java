package com.example.template.api.beans.rules;

import lombok.Data;

@Data
public class TemplateRule {
    private Discount discount;

    private Integer limitation;

    private Long deadline;
}
