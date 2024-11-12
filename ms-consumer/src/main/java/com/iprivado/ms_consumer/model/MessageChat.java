package com.iprivado.ms_consumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageChat {

    private String sender;

    private String receiver;

    private String body;

    private String timestamp;

}
