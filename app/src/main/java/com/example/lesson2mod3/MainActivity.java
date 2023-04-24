package com.example.lesson2mod3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //создание полей для вывода нужных значений
    private TextView coordinatesOut;//окно вывода полей для координат
    private float x;//задание поля для координаты x
    private float y;//задание поля для координаты y
    private String sDown;//строка для записи координат нажатия
    private String sMove;//строка для записи координат движения
    private String sUp;//строка для записи координат отпускания

    //задание дополнительных полей координат кота шрёдингера
    private final float xCat = 500;//задание поля для координаты x
    private final float yCat = 500;//задание поля для координаты y
    private final float deltaCat = 50;//допустимая погрешность в нахождении кота

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //присваивание переменным активити элементов представления activity_main

        coordinatesOut = findViewById(R.id.coordinatesOut);

        //выполнение действий при нажатии кнопки
        coordinatesOut.setOnTouchListener(listener);
    }

    //обьект обработки касания экрана(слушатель)
    private View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            //в motionEvent пишутся координаты
            x = motionEvent.getX();//инициализация координат x
            y = motionEvent.getY();//инициализация координат y

            switch (motionEvent.getAction()){
                //метод getAction() считывает состояние касания(нажатие/движение/отпускание)
                case MotionEvent.ACTION_DOWN://нажатие
                    sDown  = "Нажатие: координата X = " + x + " , координата y = " + y;
                    sMove = "";
                    sUp = "";
                    break;
                case MotionEvent.ACTION_MOVE://движение
                    sMove = "Движение: координата X = " + x +", координата y = " + y;
                    //задание условия нахождения кота шрёдингера
                    if (x < (xCat+deltaCat) && x > (xCat - deltaCat) && y < (yCat + deltaCat) && y > (yCat - deltaCat)) {//если пользователь коснулся места нахождения кота
                        //размещаем тост(контекст,сообщение,длительность сообщения)
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.successful_search, Toast.LENGTH_SHORT);//инициализация
                       toast.setGravity(Gravity.LEFT,(int) (xCat-100), (int) (yCat-700) ); //задание позиции на экране(положение,смещение по оси x,смещение по оси y)
                        //помещение тоста в контейнер
                        LinearLayout toastContainer = (LinearLayout) toast.getView();
                        //добавление в тост картинки
                        ImageView cat = new ImageView(getApplicationContext()); //создание объекта картинки(контекст)
                       cat.setImageResource(R.drawable.found_cat); //добавление картинки из ресурсов
                       toastContainer.addView(cat,1); //добавление картинки под индексом 1 в имеющийся контейнер
                       toast.show(); //демонстрация тоста на экране
                    }
                    break;
                case MotionEvent.ACTION_UP://отпускание
                case MotionEvent.ACTION_CANCEL://внутренний сбой(аналогичен ACTION_UP)
                    sMove = "";
                    sUp = "Отпускание: координата X = " + x +", координата y = " + y;
            }

            //вывод на экран в три строки считанных значений координат
            coordinatesOut.setText(sDown + "\n" + sMove + "\n" + sUp);

            return true;//подтверждение нашей обработки событий
        }
    };
}