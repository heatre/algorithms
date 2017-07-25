package com.swin.fundamentals;

import com.swin.utils.StringUtils;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Dijkstra(迪杰斯特拉)双栈算术表达式求值算法
 * Created by Swin on 2016/8/31.
 */
public class DijkstraExpressionEvaluate {
    private double evaluate(String str) {
        double rnt = -1;
        Stack<String> ops = new Stack<>();
        Stack<Double> vals = new Stack<>();
        String s = StringUtils.replaceBlank(str);
        for (int i = 0; i < s.length(); i++) {
            String t = s.substring(i, i + 1);
            if (t.equals("(")) ;//忽略左括号
            else if (t.equals("+") || t.equals("-") || t.equals("*") || t.equals("/"))
                ops.push(t);
            else if (t.equals(")")) {
                String op = ops.pop();
                Double val = vals.pop();
                if (op.equals("+"))
                    val = vals.pop() + val;
                else if (op.equals("-"))
                    val = vals.pop() - val;
                else if (op.equals("*"))
                    val = vals.pop() * val;
                else if (op.equals("/"))
                    val = vals.pop() / val;
                vals.push(val);
            } else {
                vals.push(Double.parseDouble(t));
            }

        }
        rnt = vals.pop();
        return rnt;
    }

    public static void main(String[] args) {
        String s = "(1+((2+3)*(4*5)))";
        DijkstraExpressionEvaluate dee = new DijkstraExpressionEvaluate();
        double val = dee.evaluate(s);
        StdOut.println("val:"+val);
        StdOut.println(System.getProperty("user.dir"));
    }
}
