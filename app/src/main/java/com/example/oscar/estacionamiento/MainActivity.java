package com.example.oscar.estacionamiento;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {
    TextView txtPago;
    EditText editHora, editFraccion;
    TimePicker time1, time2;
    Button botonCalcular;


    double valorHora, valorFraccion, aux = 0;
    int mTime1, mTime2, hTime1, hTime2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ////////////////////////////////////////////////////////////////////
        txtPago = (TextView)findViewById(R.id.txtPago);
        editHora = (EditText)findViewById(R.id.editHora);
        editFraccion = (EditText)findViewById(R.id.editFraccion);
        botonCalcular = (Button)findViewById(R.id.botonCalcular);
        time1 = (TimePicker) findViewById(R.id.time2);
        time2 = (TimePicker)findViewById(R.id.time3);
        //Para que podamos visualizar las horas en 24Hrs
        time1.setIs24HourView(true);
        time2.setIs24HourView(true);
        ;
        //Funcion de clickear el boton
        botonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valorHora = Double.parseDouble(editHora.getText().toString());
                valorFraccion = Double.parseDouble(editFraccion.getText().toString());

                //Ingresar los valores del reloj
                hTime1 = time1.getCurrentHour();
                mTime1 = time1.getCurrentMinute();
                hTime2 = time2.getCurrentHour();
                mTime2 = time2.getCurrentMinute();
                ///////////////////////////Logica para cobrar///////////////////////////
                int diferencia = 0;
                int diferenciaF = 0;

                if ( hTime1 != hTime2 ) {
                    if ( hTime1 < hTime2 ) {

                        diferencia  = hTime2 - hTime1;
                        if( mTime1 == mTime2 ){
                            aux = Igual( diferencia );
                        }
                        if ( mTime1 < mTime2 ){
                            aux =  Mayor( diferencia );
                        }
                        if ( mTime1 > mTime2 ){
                            aux = Menor( diferencia );
                        }
                    } else {
                        diferenciaF = 24 - (hTime1 - hTime2);

                        if( mTime1 == mTime2 ){
                            aux = Igual( diferenciaF );
                        }
                        if ( mTime1 < mTime2 ){
                            aux =  Mayor( diferenciaF );
                        }
                        if ( mTime1 > mTime2 ){
                            aux = Menor( diferenciaF );
                        }
                    }

                } else {
                    aux = valorHora;
                }
                txtPago.setText( ""+ aux );
            }
        });
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public double Igual( int horaDiferencia ){
        if ( horaDiferencia == 1 ) {
            aux = valorHora;
        } else {
            double auxFraccionMinutos = valorFraccion * ( ( horaDiferencia - 1 ) * 4 );
            aux = valorHora + auxFraccionMinutos;
        }
        return aux;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public double Mayor( int horaDiferencia ){
        int auxMinutoTime1 = mTime1;
        double auxFraccionMinutos = 0;
        if( horaDiferencia == 1 ){
            for ( int i = 1; i <= 4 ; i++ ){
                auxMinutoTime1 = auxMinutoTime1 + 15;
                if( auxMinutoTime1 >= mTime2 ) {
                    auxFraccionMinutos = valorFraccion * i;
                    i = 5;
                }
            }
            aux = valorHora + auxFraccionMinutos;
        } else {
            double auxFraccionHoras = valorFraccion * ( ( horaDiferencia - 1 ) * 4 );
            for ( int i = 1; i <= 4 ; i++ ){
                auxMinutoTime1 = auxMinutoTime1 + 15;

                if( auxMinutoTime1 >= mTime2 ) {
                    auxFraccionMinutos = valorFraccion * i;
                    i = 5;
                }
            }
            aux = valorHora + auxFraccionMinutos + auxFraccionHoras;
        }
        return aux;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public double Menor( int horaDiferencia ){
        if( horaDiferencia == 1 ){
            aux = valorHora;
        } else {
            int auxMinutos = ( mTime2 + 60 ) - mTime1 ;
            double auxFraccionMinutos = 0;
            int horasReales = horaDiferencia - 1;

            for ( int i = 1; i <= 4 ; i++ ){
                auxMinutos = auxMinutos - 15;

                if( auxMinutos < 0 ) {
                    auxFraccionMinutos = valorFraccion * i;
                    i = 5;
                }
            }
            double auxFraccionHoras = valorFraccion * ( (horasReales - 1) * 4 );
            aux = valorHora + auxFraccionMinutos + auxFraccionHoras;
        }
        return aux;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
