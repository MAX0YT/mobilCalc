package com.example.calcvalovpr23101;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String firstNum = "";
    private String operator = "";
    private String secondNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.resultDisplay);
    }

    public void onNumberClick(View view) {
        Button button = (Button) view;
        appendNumber(button.getText().toString());
    }

    public void onDecimalClick(View view) {
        appendDecimal();
    }

    public void onOperatorClick(View view) {
        Button button = (Button) view;
        setOperator(button.getText().toString());
    }

    public void onEqualsClick(View view) {
        calculate();
    }

    public void onACClick(View view) {
        clear();
    }

    public void onSignClick(View view) {
        backspace();
    }

    public void onPercentClick(View view) {
        applyPercent();
    }

    private void appendNumber(String number) {
        if (operator.isEmpty()) {
            firstNum += number;
        } else {
            secondNum += number;
        }
        updateDisplay();
    }

    private void appendDecimal() {
        if (operator.isEmpty()) {
            if (!firstNum.contains(".") && !firstNum.endsWith(".")) {
                firstNum += (firstNum.isEmpty() ? "0." : ".");
            }
        } else {
            if (!secondNum.contains(".") && !secondNum.endsWith(".")) {
                secondNum += (secondNum.isEmpty() ? "0." : ".");
            }
        }
        updateDisplay();
    }

    private void setOperator(String op) {
        if (firstNum.isEmpty()) {
            if (op.equals("-")){
                firstNum = "0";
                operator = "-";
                updateDisplay();
            }
            return;
        }
        operator = op;
        updateDisplay();
    }

    private void calculate() {
        if (operator.isEmpty() || secondNum.isEmpty()) {
            return;
        }
        try {
            double num1 = Double.parseDouble(firstNum);
            double num2 = Double.parseDouble(secondNum);
            double result = 0;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "×":
                    result = num1 * num2;
                    break;
                case "÷":
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
            }
            if (result == (long) result) {
                firstNum = String.valueOf((long) result);
            } else {
                firstNum = String.valueOf(result);
            }
            operator = "";
            secondNum = "";
            updateDisplay();
        } catch (NumberFormatException e) {
            display.setText("Error");
        }
    }

    private void clear() {
        firstNum = "";
        operator = "";
        secondNum = "";
        updateDisplay();
    }
    private void backspace() {
        if (!secondNum.isEmpty()) {
            // удаляем последний символ из второго числа
            secondNum = secondNum.substring(0, secondNum.length() - 1);
        } else if (!operator.isEmpty()) {
            // если второе число пустое — удаляем оператор
            operator = "";
        } else if (!firstNum.isEmpty()) {
            // удаляем последний символ из первого числа
            firstNum = firstNum.substring(0, firstNum.length() - 1);
        }
        updateDisplay();
    }
    private void toggleSign() {
        if (operator.isEmpty()) {
            if (!firstNum.isEmpty()) {
                if (firstNum.startsWith("-")) {
                    firstNum = firstNum.substring(1);
                } else {
                    firstNum = "-" + firstNum;
                }
            }
        } else {
            if (!secondNum.isEmpty()) {
                if (secondNum.startsWith("-")) {
                    secondNum = secondNum.substring(1);
                } else {
                    secondNum = "-" + secondNum;
                }
            }
        }
        updateDisplay();
    }

    private void applyPercent() {
        if (operator.isEmpty()) {
            if (!firstNum.isEmpty()) {
                try {
                    double num = Double.parseDouble(firstNum) / 100;
                    firstNum = String.valueOf(num);
                } catch (NumberFormatException e) {
                    // Ignore
                }
            }
        } else {
            if (!secondNum.isEmpty()) {
                try {
                    double num = Double.parseDouble(secondNum) / 100;
                    secondNum = String.valueOf(num);
                } catch (NumberFormatException e) {
                    // Ignore
                }
            }
        }
        updateDisplay();
    }

    private void updateDisplay() {
        StringBuilder text = new StringBuilder(firstNum);
        if (!operator.isEmpty()) {
            text.append(" ").append(operator);
            if (!secondNum.isEmpty()) {
                text.append(" ").append(secondNum);
            }
        }
        String displayText = text.toString();
        display.setText(displayText.isEmpty() ? "0" : displayText);
    }
}