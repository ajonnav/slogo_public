package parser;

import java.util.function.BinaryOperator;

public enum Operator {
    PRODUCT((n1, n2) -> n1 * n2),
    DIFFERENCE((n1, n2) -> n1-n2),
    SUM((n1, n2) -> n1 + n2),
    QUOTIENT((n1,n2) -> n1 / n2),
    REMAINDER((n1,n2) -> n1 % n2),
    POWER((n1,n2) -> Math.pow(n1, n2)),
    NOTEQUAL((n1,n2) -> {
        if(n1 != n2) {
            return 1.0;
        }
        return 0.0;
    }),
    EQUAL((n1,n2) -> {
        if(n1 == n2){
          return 1.0;  
        }
        return 0.0;
    });

    private final BinaryOperator<Double> operation;

    private Operator(BinaryOperator<Double> operation) {
        this.operation = operation;
    }

    public double operate(double n1, double n2) {
        return operation.apply(n1, n2);
    }
}