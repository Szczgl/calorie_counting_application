package com.calories.end.services.edamam;

import java.util.List;

public class EdamamResponse {
    private List<EdamamParsed> parsed;

    public List<EdamamParsed> getParsed() {
        return parsed;
    }

    public void setParsed(List<EdamamParsed> parsed) {
        this.parsed = parsed;
    }
}
