package entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Expression {

    @JsonProperty("expression")
    private String expression;

    @JsonProperty("answer")
    private double answer;

    public Expression(String expression) {
        this.expression = expression;
    }

}
