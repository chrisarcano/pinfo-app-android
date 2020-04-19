package com.cda.pinfo.app.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private Integer expenseId;
    private String expenseName;
    private Integer billingStartDay;
    private Integer billingEndDay;
    private Integer billingDueDay;
    private Character frequency;
    private Float estimatedAmount;
    private String notes;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private LocalDate dateEffective;
    private LocalDate dateExpiration;
}
