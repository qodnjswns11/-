package com.example.calculrator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean isfirstinput = true; // 입력 값이 처음 입력되는지를 체크
    double resultNumber = 0; // 계산된 결과 값을 저장하는 변수
    char operator = '+'; // 입력된 연산자를 저장하는 변수

    final String CLEAR_INPUT_TEXT = "0";
    TextView resultText;

    Button num0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = findViewById(R.id.result_text);

    }

    // AC, CE, BS, . 이 실행되었을때 메소드
    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.all_clear_button:
                resultNumber = 0;
                operator = '+';
                setClearText(CLEAR_INPUT_TEXT);
                break;

            case R.id.clear_entry_button:
                setClearText(CLEAR_INPUT_TEXT);
                break;

            case R.id.back_space_button:
                if (resultText.getText().toString().length() > 1) {
                    String getResultText = resultText.getText().toString();
                    String subString =  getResultText.substring(0, getResultText.length() - 1);
                    resultText.setText(subString);
                } else {
                    setClearText(CLEAR_INPUT_TEXT);
                }
                break;

            case R.id.decimal_button:
                if(isfirstinput){
                    resultText.setTextColor(0xFF000000);
                    resultText.setText("0.");
                    isfirstinput= false;
                }else {
                    if (resultText.getText().toString().contains(".")) {
                        Toast.makeText(getApplicationContext(), "이미 소수점이 존재합니다", Toast.LENGTH_SHORT).show();
                    } else {
                        resultText.append(".");
                    }
                    break;
                }
        }
    }

    // 0번 ~ 9번 입력되었을때 설정되는 메소드
    public void numButtonClick(View view) {
        Button getButton = findViewById(view.getId());
        if (isfirstinput) {
            resultText.setTextColor(0xFF000000);
            resultText.setText(getButton.getText().toString());
            isfirstinput = false;
        } else {
            if (resultText.getText().toString().equals("0")) {
                Toast.makeText(getApplicationContext(), "0으로 시작하는 정수는 없습니다", Toast.LENGTH_SHORT).show();
                setClearText(CLEAR_INPUT_TEXT);
                return;
            } else
                resultText.append(getButton.getText().toString());
        }
    }

    // 입력된 숫자를 클리어 시켜주는 메소드
    public void setClearText(String clearText) {
        isfirstinput = true;
        resultText.setTextColor(0xFF666666);
        resultText.setText(clearText);
    }

    // 사칙연산을 해서 값을 반환해주는  메소드
    public double DouCal(double result, double lastNum, char operator) {
        if (operator == '+') {
            result = result + lastNum;
        } else if (operator == '-') {
            result = result - lastNum;
        } else if (operator == '/') {
            result = result / lastNum;
        } else if (operator == '*') {
            result = result * lastNum;
        }
        return result;
    }

    // 연산자가 클릭 되었을때 실행되는 메소드
    public void operatorClick(View view) {
        Button getButton = findViewById(view.getId());

        if (view.getId() == R.id.result_button) {
            if (isfirstinput) {
                resultNumber = 0;
                operator = '+';
                setClearText("0");
                // TODO: 다음에 실수형 계산기 만들때 윈도우 계산기 처럼 =을 두번 이상 누를때 실행방법과 같이 구현할것
            } else {
                resultNumber = DouCal(resultNumber, Double.parseDouble(resultText.getText().toString()), operator);
                resultText.setText(String.valueOf(resultNumber));
                isfirstinput = true;
            }

        } else {
            if (isfirstinput) {
                operator = getButton.getText().toString().charAt(0);
            } else {
                double lastNum = Double.parseDouble(resultText.getText().toString());
                resultNumber = DouCal(resultNumber, lastNum, operator);
                operator = getButton.getText().toString().charAt(0);
                resultText.setText(String.valueOf(resultNumber));
                isfirstinput = true;
            }
        }
    }
}