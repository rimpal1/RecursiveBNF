import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * BNF: Programming assignment # 2:
 * Generating and evaluate expression tree from given input string following provided BNF.
 * Student Name: Rimpalbahen Suthar
 * <p>
 * BNF:
 * <expression>  ::=  <factor>  * <expression>   |   <factor>  /  <expression>   |   <factor>
 * <factor>  :==  <term> + <factor>  |  <term> - <factor>  |  <term>
 * <term>  ::=  {  <expression>  }  |  <literal>
 * <literal>  ::=  0|1|2|3|4|5|6|7|8|9
 */

class RecursiveBNF {
    private static int pointer = 0;
    private static Object token;
    private static String input;

    private static Double evalExp(ExpTreeType expTree) {

        if (expTree != null) {
            if (expTree.getLeftNode() != null && expTree.getRightNode() != null) {
                double rightChild = evalExp(expTree.getRightNode());
                double leftChild = evalExp(expTree.getLeftNode());
                if (expTree.getRoot().equals('*')) {
                    return leftChild * rightChild;
                } else if (expTree.getRoot().equals('/')) {
                    return leftChild / rightChild;
                } else if (expTree.getRoot().equals('+')) {
                    return leftChild + rightChild;
                } else if (expTree.getRoot().equals('-')) {
                    return leftChild - rightChild;
                }
                return 0.0d;
            } else {
                return Double.parseDouble(String.valueOf(expTree.getRoot()));
            }
        }
        return 0.0;
    }

    private static ExpTreeType expression() {
        ExpTreeType newFactorTree = new ExpTreeType();
        newFactorTree = factor();
        ExpTreeType expressionTree = new ExpTreeType();

        if (token.equals('#')) {
            return expressionTree = newFactorTree;
        } else if (token.equals('*') || token.equals('/')) {
            Object token = RecursiveBNF.token;
            ExpTreeType expressionToTree = new ExpTreeType();
            getToken();
            expressionToTree = expression();
            expressionTree.setRoot(token);
            expressionTree.setLeftNode(newFactorTree);
            expressionTree.setRightNode(expressionToTree);
            System.out.println("The ROOT of expression tree is: " + token);
            System.out.println("The left node of expression is: " + expressionTree.getLeftNode().toString());
            System.out.println("The right node of expression is: " + expressionTree.getRightNode().toString());
            //the expression tree representation starts from end to top, the root node will be the last node printed.
        } else {
            expressionTree = newFactorTree;
        }
        return expressionTree;
    }


    private static ExpTreeType factor() {
        ExpTreeType factorTree;
        factorTree = term();
        ExpTreeType termTree = new ExpTreeType();
        if (token.equals('+') || token.equals('-')) {
            Object token = RecursiveBNF.token;
            ExpTreeType termToTree = new ExpTreeType();
            getToken();
            termToTree = factor();
            termTree.setRoot(token);
            termTree.setLeftNode(factorTree);
            termTree.setRightNode(termToTree);
            System.out.println("The ROOT of factor tree is: " + token);
            System.out.println("The left node of factor is: " + termTree.getLeftNode().toString());
            System.out.println("The right node of factor is: " + termTree.getRightNode().toString());
            //the expression tree representation starts from end to top, the root node will be the last node printed.
        } else if (token.equals('#')) {
            return termTree = factorTree;
        } else {
            termTree = factorTree;
        }

        return termTree;
    }

    private static ExpTreeType term() {
        ExpTreeType termTree = new ExpTreeType();

        if (token.equals('{')) {
            ExpTreeType termToTree = new ExpTreeType();
            getToken();
            termToTree = expression();
            if (token.equals('}'))
                getToken();
            termTree = termToTree;
        } else if (token.toString().startsWith("[0-9]")) {
            ExpTreeType literalTree = new ExpTreeType();
            literalTree = literal();
            if (token.equals('#')) {
                termTree = literalTree;
                return termTree;
            }
            termTree = literalTree;
        } else {
            ExpTreeType literalTree = new ExpTreeType();
            literalTree = literal();
            if (token.equals('#')) {
                termTree = literalTree;
                return termTree;
            }
            termTree = literalTree;
        }

        return termTree;
    }

    private static ExpTreeType literal() {
        ExpTreeType literalToken = new ExpTreeType();

        literalToken.setRoot(token);
        getToken();
        literalToken.setLeftNode(null);
        literalToken.setRightNode(null);
        return literalToken;
    }

    public static Object getToken() {
        if (pointer < input.length()) {
            if (Character.isDigit(input.charAt(pointer))) {
                StringBuilder sb = new StringBuilder();
                for (int i = pointer; i < input.length(); i++) {
                    if (input.substring(i, i + 1).matches("[0-9]")) {
                        sb.append(input.charAt(i));
                        continue;
                    }
                    pointer = i;
                    return token = sb.toString();
                }
                pointer = input.length();
                return token = sb.toString();
            } else {
                token = input.charAt(pointer);
                pointer++;
                return token;
            }
        } else {
            token = '#';
            return token;
        }
    }

    public static void main(String[] args) {
        Double result = 0.0;
        DecimalFormat formatResult = new DecimalFormat("0.00");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a numeric expression adhering to provided BNF: ");
        input = scanner.nextLine();
        System.out.println("The provided input expression: " + input);
        getToken();
        ExpTreeType expTree;
        expTree = expression();
        result = evalExp(expTree);
        System.out.println("The result of the expression: " + formatResult.format(result));
    }
}