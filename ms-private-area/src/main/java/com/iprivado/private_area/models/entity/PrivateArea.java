package com.iprivado.private_area.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "private_area")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @NotNull(message = "Month cost private area can not be null")
    @DecimalMin(value = "0.00", message = "Month cost private can not be less than 0")
    @Column(name = "month_cost_private_area")
    private BigDecimal monthCostPrivateArea;

    @OneToMany(mappedBy = "privateArea", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PrivateContent> privateContent;

}
