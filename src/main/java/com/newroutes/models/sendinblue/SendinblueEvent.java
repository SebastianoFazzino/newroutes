package com.newroutes.models.sendinblue;

import com.newroutes.enums.sendinblue.Template;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendinblueEvent {

    private String email;

    private Template template;

    private String params;
}
