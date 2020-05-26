package com.labs.prova.validations;

import static com.labs.prova.validations.InputValidEnum.BREAK;
import static com.labs.prova.validations.InputValidEnum.CONTINUE;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

@Getter
public class InputValid {
    private InputValidEnum valid = InputValidEnum.VALID;
    private String message = "";
    
    public void validateInput(String inputLine, String query, int divisor){
        if (".".equals(inputLine)) {
            message = "Disconnected from the server!";
            valid = BREAK;
            return;
        }
        if(divisor<=0) {
            message = "Invalid input format!";
            valid = CONTINUE;
            return;
        }
        boolean isValidPayload = StringUtils.isNumeric(inputLine.substring(0,divisor));
        if(!isValidPayload) {
            message = "Payload invalid!";
            valid = CONTINUE;
            return;
        }
        if(query.equals("")) {
            message = "Query invalid!";
            valid = CONTINUE;
        }
    }
}