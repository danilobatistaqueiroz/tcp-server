package com.labs.prova.validations;

import static com.labs.prova.validations.InputQueryResultEnum.WARNING;
import static com.labs.prova.validations.InputQueryResultEnum.END_OF_QUERY;
import static com.labs.prova.validations.InputQueryResultEnum.ERROR;

import org.apache.commons.lang3.StringUtils;

import com.labs.prova.exceptions.ClientInputException;

import lombok.Getter;

public class QueryBuilder {
    @Getter
    private InputQueryResultEnum result = InputQueryResultEnum.VALID;
    @Getter
    private String message = "";
    @Getter
    private boolean isQueryLength = true;
    private StringBuilder query = new StringBuilder();
    private StringBuilder queryLength = new StringBuilder();
    
    public InputQueryResultEnum validateInput(int character){
        if(character==-1) {
            message = "Invalid input!";
            result = WARNING;
        }
        return result;
    }
    public void validateQueryLength() {
        boolean isValidQueryLength = StringUtils.isNumeric(queryLength.toString());
        if(!isValidQueryLength) {
            message = "Invalid query length!";
            result = ERROR;
        }
    }
    public void validateQuery() {
        if(query.length()==0) {
            message = "Query invalid!";
            result = WARNING;
        }
    }
    public void validateEndOfQuery() {
        if(query.length()==Integer.valueOf(queryLength.toString())) {
            message = "";
            result = END_OF_QUERY;
        }
    }
    public String getQueryString() {
        return query.toString();
    }
    public void queryAppend(char character) throws ClientInputException {
       if(query.length()>1000) {
           throw new ClientInputException("Query too long!");
       }
       query.append(character); 
    }
    public void queryLengthAppend(char character) throws ClientInputException {
        if(character==(int)':') {
            isQueryLength=false;
        } else {
            if(queryLength.length()>4) {
                throw new ClientInputException("Query length too long!");
            }
            queryLength.append(character);
            validateQueryLength();
        }
    }
}