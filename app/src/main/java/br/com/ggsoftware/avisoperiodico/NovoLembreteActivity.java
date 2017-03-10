package br.com.ggsoftware.avisoperiodico;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NovoLembreteActivity extends AppCompatActivity implements Constantes{

    EditText edtLembrete;
    RadioButton rdAlarme;
    RadioButton rdNotificacao;
    Spinner spinnerTipoPeriodicidade;
    EditText edtdataHoraInicio;
    EditText edtperiodicidade;
    View.OnTouchListener touchListener;
    SimpleDateFormat sdf;

    Calendar hoje;
    int ano;
    int mes;
    int dia;
    int hora;
    int minuto;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_lembrete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtLembrete = (EditText) findViewById(R.id.edtLembrete);
        edtdataHoraInicio = (EditText) findViewById(R.id.edtDataHora);
        edtperiodicidade = (EditText) findViewById(R.id.edtPeriodicidade);

        rdAlarme = (RadioButton) findViewById(R.id.rdAlarme);
        rdNotificacao = (RadioButton) findViewById(R.id.rdNotificacao);
        spinnerTipoPeriodicidade = (Spinner) findViewById(R.id.periodicidade_spinner);

        sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt-BR"));


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.periodicidade_array, android.R.layout.simple_spinner_item);


        spinnerTipoPeriodicidade.setAdapter(adapter);

        spinnerTipoPeriodicidade.setSelection(2);

        edtdataHoraInicio.setText(sdf.format(Calendar.getInstance().getTime()));

        touchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                edtdataHoraInicio.setOnTouchListener(null);

                Locale locale = getResources().getConfiguration().locale;
                Locale.setDefault(locale);

                hoje = Calendar.getInstance();
                ano = hoje.get(Calendar.YEAR);
                mes = hoje.get(Calendar.MONTH);
                dia = hoje.get(Calendar.DAY_OF_MONTH);


                hora = hoje.get(Calendar.HOUR_OF_DAY);
                minuto = hoje.get(Calendar.MINUTE);



                 datePickerDialog = new DatePickerDialog(NovoLembreteActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                //txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                hoje.set(Calendar.YEAR, year);
                                hoje.set(Calendar.MONTH, monthOfYear);
                                hoje.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                    timePickerDialog.show();

                            }

                            }, ano, mes, dia);



               timePickerDialog = new TimePickerDialog(NovoLembreteActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {


                                hoje.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                hoje.set(Calendar.MINUTE, minute);

                                edtdataHoraInicio.setText(sdf.format(hoje.getTime()));
                                edtdataHoraInicio.setOnTouchListener(touchListener);

                            }
                        }, hora, minuto, false);





                datePickerDialog.show();

                return true;
            }
        };

        edtdataHoraInicio.setOnTouchListener(touchListener);
    }


    public void onRadioButtonClicked (View v){
                if(v.getId() == R.id.rdAlarme){
                    rdNotificacao.setChecked(false);
                }else{
                    rdAlarme.setChecked(false);
                }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

public void agendar(View v) throws ParseException {

    LembreteDAO oLembreteDAO = new LembreteDAO(NovoLembreteActivity.this);

    LembreteVO oLembreteVO = new LembreteVO();

    oLembreteVO.setLembrete(edtLembrete.getText().toString());
    oLembreteVO.setDataHora(edtdataHoraInicio.getText().toString());

    oLembreteVO.setPeriodicidade(Integer.valueOf(edtperiodicidade.getText().toString()));
    oLembreteVO.setTipo(rdAlarme.isChecked() ? TIPO_LEMBRETE_ALARME : TIPO_LEMBRETE_NOTIFICACAO);
    oLembreteVO.setTipoPeriodicidade(spinnerTipoPeriodicidade.getSelectedItemPosition());

    oLembreteDAO.insert(oLembreteVO);


    Intent it = new Intent("EXECUTAR_ALARME");

    final int id = oLembreteDAO.proximoId();


    PendingIntent p = PendingIntent.getBroadcast(NovoLembreteActivity.this, id, it, 0);

    AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);

    Calendar horarioAlarme = Calendar.getInstance();

    horarioAlarme.setTime(sdf.parse(oLembreteVO.getDataHora()));

    long time = horarioAlarme.getTimeInMillis();

    alarme.setRepeating(AlarmManager.RTC_WAKEUP, time, 50000, p); //

    Toast.makeText(getApplicationContext(), "Lembrete Agendado com sucesso", Toast.LENGTH_SHORT).show();

    startActivity(new Intent(NovoLembreteActivity.this, MainActivity.class));
}

}

