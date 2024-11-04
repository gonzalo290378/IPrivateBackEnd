package com.ms_users.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms_users.models.PrivateContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivateAreaDTO implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("isEnabled")
    private Boolean isEnabled;

    @JsonProperty("monthCostPrivateArea")
    private BigDecimal monthCostPrivateArea;

    @JsonProperty("privateContent")
    private List<PrivateContentDTO> privateContentDTO;


}
