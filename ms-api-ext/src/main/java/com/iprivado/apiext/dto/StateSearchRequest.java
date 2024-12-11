package com.iprivado.apiext.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iprivado.apiext.model.entity.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateSearchRequest implements Serializable {

    @JsonProperty("name")
    private String name;

    @JsonProperty("states")
    private List<State> states;

}
