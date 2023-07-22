package com.example.calculator;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import org.mariuszgromada.math.mxparser.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button num0 = findViewById(R.id.num0);
        Button num1 = findViewById(R.id.num1);
        Button num2 = findViewById(R.id.num2);
        Button num3 = findViewById(R.id.num3);
        Button num4 = findViewById(R.id.num4);
        Button num5 = findViewById(R.id.num5);
        Button num6 = findViewById(R.id.num6);
        Button num7 = findViewById(R.id.num7);
        Button num8 = findViewById(R.id.num8);
        Button num9 = findViewById(R.id.num9);

        Button AC = findViewById(R.id.AC);
        Button bracket = findViewById(R.id.bracket);
        Button exponential = findViewById(R.id.exponential);
        Button divide = findViewById(R.id.divide);
        Button multiply = findViewById(R.id.multiply);
        Button subtraction = findViewById(R.id.subtractin);
        Button addition = findViewById(R.id.addition);
        Button equals = findViewById(R.id.equals);
        Button point = findViewById(R.id.point);
        Button backspace = findViewById(R.id.backspace);

        screen = findViewById(R.id.screen);

        screen.setShowSoftInputOnFocus(false);

        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("0");
            }
        });

        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("1");
            }
        });

        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("2");
            }
        });
        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("3");
            }
        });
        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("4");
            }
        });
        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("5");
            }
        });
        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("6");
            }
        });
        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("7");
            }
        });
        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("8");
            }
        });
        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("9");
            }
        });

        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("÷");
            }
        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("×");
            }
        });
        subtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("-");
            }
        });
        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("+");
            }
        });
        exponential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText("^");
            }
        });
        AC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen.setText("0");
            }
        });

        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateText(".");
            }
        });




        bracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursorPos = screen.getSelectionStart();
                int openPar = 0;
                int closedPar = 0;
                int numLen = screen.getText().length();
                for(int i = 0; i < cursorPos; i++){
                    if(screen.getText().toString().charAt(i) == '('){
                        openPar = openPar + 1;
                    }
                    if(screen.getText().toString().charAt(i) == ')'){
                        closedPar = closedPar + 1;
                    }
                }
                if(openPar == closedPar || screen.getText().toString().charAt(numLen - 1) == '('){
                    updateText("(");
                }
                if(closedPar < openPar && screen.getText().toString().charAt(numLen - 1) != '('){
                    updateText(")");
                }
                screen.setSelection(cursorPos + 1);

            }
        });

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cursorPos = screen.getSelectionStart();
                int numLen = screen.getText().length();
                if(cursorPos != 0 && numLen != 0){
                    SpannableStringBuilder selection = (SpannableStringBuilder) screen.getText();
                    selection.replace(cursorPos - 1, cursorPos,"");
                    screen.setText(selection);
                    screen.setSelection(cursorPos-1);
                }
            }
        });
        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userExp  = screen.getText().toString();

                userExp = userExp.replaceAll("÷","/");
                userExp = userExp.replaceAll("×","*");

                Expression exp = new Expression(userExp);

                String result = String.valueOf(exp.calculate());

                screen.setText(result);
                screen.setSelection(result.length());

            }
        });


    }





    private void updateText(String strToAdd) {
        int cursorPos = screen.getSelectionStart();
        String oldStr = screen.getText().toString();
        int strLength = oldStr.length();

        // Ensure the cursor position is within the valid range
        if (cursorPos < 0 || cursorPos > strLength) {
            cursorPos = strLength;
        }

        String leftStr = oldStr.substring(0, cursorPos);
        String rightStr = oldStr.substring(cursorPos);

        String newText;
        if (screen.getText().toString().equals("0")) {
            newText = strToAdd;
        } else {
            newText = leftStr + strToAdd + rightStr;
        }

        screen.setText(newText);

        // Set the cursor position based on the length of the added text
        int newCursorPosition = cursorPos + strToAdd.length();
        if (newCursorPosition < 0) {
            newCursorPosition = 0;
        } else if (newCursorPosition > newText.length()) {
            newCursorPosition = newText.length();
        }
        screen.setSelection(newCursorPosition);
    }
}






