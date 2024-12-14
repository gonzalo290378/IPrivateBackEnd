package com.iprivado.apiext.controller;

import com.iprivado.apiext.dto.StateSearchRequest;
import com.iprivado.apiext.model.entity.State;
import com.iprivado.apiext.services.StateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RequestMapping("/api/v1/states")
public class StateController {

    @Autowired
    private StateService stateService;

    @PostMapping("/search")
    public ResponseEntity<List<State>> searchStates(@RequestBody StateSearchRequest request) {
        String name = request.getName();
        List<State> states = request.getStates();
        List<State> result = stateService.searchStates(name, states);
        return ResponseEntity.ok(result);
    }

}
