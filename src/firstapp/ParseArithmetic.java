
package firstapp;

/**
 *
 * @author User
 */

public class ParseArithmetic {
    
    private TokenStack operatorStack = new TokenStack();
    private TokenStack valueStack = new TokenStack();
    private Boolean error = false;
    private double result;
    
    public ParseArithmetic (String expressions) {
        if (expressions.length() > 0) {
            String characters[] = expressions.split(" ");

            Token tokens[] = new Token[characters.length];
            for (int x = 0; x< characters.length;x++) {
                tokens[x] = new Token(characters[x]);
            }

            for (int index = 0; index < tokens.length; index++) {

                Token currToken = tokens[index];
                int currTokenType = currToken.getType();
                int currTokenPrecedence = currToken.getPrecedence();
                Boolean operatorStackEmpty = operatorStack.isEmpty();

                switch (currTokenType) {
                    case Token.NUMBER:
                        valueStack.push(currToken);
                        break;
                    case Token.OPERATOR:
                        if (operatorStackEmpty || currTokenPrecedence > operatorStack.top().getPrecedence()) {
                            operatorStack.push(currToken);
                        }else {
                            while (!operatorStack.isEmpty() && currTokenPrecedence <= operatorStack.top().getPrecedence()) {
                                Token operatorToProcess = operatorStack.top();
                                operatorStack.pop();
                                processOperation(operatorToProcess);
                            }
                            operatorStack.push(currToken);
                        }   
                        break;
                    case Token.L_PARENTHESIS:
                        operatorStack.push(currToken);
                        break;
                    case Token.R_PARENTHESIS:
                        while (!operatorStackEmpty && operatorStack.top().getType() == Token.OPERATOR) {
                            Token operatorToProcess = operatorStack.top();
                            operatorStack.pop();
                            processOperation(operatorToProcess);
                        }   
                        if (!operatorStack.isEmpty() && operatorStack.top().getType() == Token.L_PARENTHESIS) {
                            operatorStack.pop();
                        }else {
                            PopUpWindow.showPopUp("Error occured, bad arithmetric expression");
                            error = true;
                        }
                        break;
                    default:
                        break;
                }
            }

            while (!operatorStack.isEmpty() && operatorStack.top().getType() == Token.OPERATOR) {
                Token operatorToProcess = operatorStack.top();
                operatorStack.pop();
                processOperation(operatorToProcess);
            }

            if (!error) {
                Token calcResult = valueStack.top();
                valueStack.pop();
                //Just in case error happen, although I formatted most of the expression correctly.....
                if (!operatorStack.isEmpty() || !valueStack.isEmpty()) {
                    PopUpWindow.showPopUp("Error occured, bad arithmetric expression");
                }else {
                    this.result = calcResult.getValue();
                }
            }
        
        }
    }
    
    public Double getResult () {
        if (result != Double.NaN)
            return result;
        else {
            return Double.NaN;
        }
    }
    
    public void processOperation (Token operator) {
       Token value1 = null, value2 = null;
       if (valueStack.isEmpty()) {
           error = true;
           PopUpWindow.showPopUp("Error occured, bad arithmetric expression");
           return;
       }else {
           value2 = valueStack.top();
           valueStack.pop();
       }
       if (valueStack.isEmpty()) {
            PopUpWindow.showPopUp("Error occured, bad arithmetric expression");
           error = true;
           return;
       }else {
           value1 = valueStack.top();
           valueStack.pop();
       }
       Token result = operator.executeOperation(value1.getValue(), value2.getValue());
       valueStack.push(result);
    }
}