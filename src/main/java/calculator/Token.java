package calculator;
import java.util.ArrayList;
/**
 *
 * @author User
 */
public class Token {
    public static final int NONE = -1,
                            NUMBER = 0,
                            OPERATOR = 1,
                            L_PARENTHESIS = 2,
                            R_PARENTHESIS = 3;
    
    private int type;
    private double value;
    private char operator;
    private int precedence;
    
    public Token () {
        type = NONE;
    }
    
    //when a math operation is finished we need to create a new object to store that value while following the Token class
    //data format
    public Token (double value) {
        this.value = value;
    }
    
    int getType() { return type; }
    double getValue() { return value; }
    int getPrecedence() { return precedence; }
    
    //when parsing the arithmetric, specify the type, precedence and operator or value for the token
    public Token (String character) {
        char token = character.charAt(0);
        switch (character) {
            case "+":
               type = OPERATOR;
               //changing the string to a char instead of a string
               operator = token;
               precedence = 1;
               break;
            case "-":
                type = OPERATOR;
                operator = token;
                precedence = 1;
                break;
            case "x":
                type = OPERATOR;
                operator = token;
                precedence = 2;
                break;
            case "/":
                type = OPERATOR;
                operator = token;
                precedence = 2;
                break;
            case "(":
                type = L_PARENTHESIS;
                break;
            case ")":
                type = R_PARENTHESIS;
                break;
            default:
                type = NUMBER;
                try {
                    value = Double.parseDouble(character);
                } catch (NumberFormatException ex) {
                    type = NONE;
                }
        }
    }
    
    //execute the math operation
    Token executeOperation(double operand1, double operand2) {
        double result = 0;
        switch(operator) {
            case '+':
               result = operand1 + operand2;
               break;
            case '-':
               result = operand1 - operand2;
               break;
            case 'x':
               result = operand1 * operand2;
               break;
            case '/':
               result = operand1 / operand2;
               break;
       }
       return new Token(result);
    }
}

//stack class for the operator and values 
class TokenStack {
    private ArrayList<Token> tokens = new ArrayList<Token>();
    
    public Token top () {
        return tokens.get(tokens.size() - 1);
    }
    
    public void push (Token newToken) {
        tokens.add(newToken);
    }
    
    public void pop () {
        tokens.remove(tokens.size() - 1);
    }
    
    public Boolean isEmpty() {
        return tokens.isEmpty();
    }
}