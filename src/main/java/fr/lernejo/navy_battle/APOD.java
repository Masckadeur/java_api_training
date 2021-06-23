package fr.lernejo.navy_battle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class APOD {
    public final JsonNode id;
    public final JsonNode url;
    public final JsonNode message;

    public APOD(@JsonProperty("id") JsonNode id,
                @JsonProperty("url") JsonNode url,
                @JsonProperty("message") JsonNode message) {
        this.id = id;
        this.url = url;
        this.message = message;
    }

}
