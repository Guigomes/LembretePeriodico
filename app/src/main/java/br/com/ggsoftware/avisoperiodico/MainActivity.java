package br.com.ggsoftware.avisoperiodico;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements Constantes {


    List<LembreteVO> lLembreteVO = new ArrayList<>();

    LinearLayout content;
    TextView empty;
    LembreteDAO oLembreteDAO;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sdf = new SimpleDateFormat("dd/MM/yyyy ' às ' HH:mm", new Locale("pt-BR"));

        content = (LinearLayout) findViewById(R.id.content_main);
        empty = (TextView) findViewById(R.id.empty);

        oLembreteDAO = new LembreteDAO(MainActivity.this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NovoLembreteActivity.class);
                startActivity(intent);
            }
        });


        lLembreteVO = oLembreteDAO.listar();
        if (lLembreteVO != null && lLembreteVO.size() > 0) {

            empty.setVisibility(View.GONE);
            for (LembreteVO oLembreteVO : lLembreteVO) {
                addItem(oLembreteVO);
            }
        }


    }

    private void addItem(LembreteVO oLembreteVO) {


        ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.item_lembrete, null, false);

        String textoHorario = oLembreteVO.getTipo() == TIPO_LEMBRETE_ALARME ? "Alarme" : "Notificação";

        textoHorario += " a cada " + oLembreteVO.getPeriodicidade() + " " + getTextoTipoPeriodicidade(oLembreteVO.getTipoPeriodicidade(), oLembreteVO.getPeriodicidade()) + " a partir do dia " + oLembreteVO.getDataHora();

        ((TextView) newView.findViewById(R.id.txtData)).setText(textoHorario);

        ((TextView) newView.findViewById(R.id.txtLembrete)).setText(oLembreteVO.getLembrete().toString());
/*
        newView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView t = (TextView) newView.findViewById(R.id.txtIdBD);

                AlarmeDadosDAO alarmeDadosDAO = new AlarmeDadosDAO(getApplicationContext());
                AlarmeDadosVO alarmeDadosVO = alarmeDadosDAO.buscaPorId(Integer.parseInt(t.getText().toString()));
                alarmeDadosDAO.delete(alarmeDadosVO);
                mContainerView.removeView(newView);

                if (mContainerView.getChildCount() == 0) {
                    empty.setVisibility(View.VISIBLE);
                }
            }
        });
*/

        content.addView(newView, 0);
    }


    private String getTextoTipoPeriodicidade(Integer tipoPeriodicidade, Integer periodicidade) {

        String textoTipoPeriodicidade = null;

        if (tipoPeriodicidade == TIPO_PERIODICIDADE_SEGUNDOS)
            textoTipoPeriodicidade =  "segundos";

        if (tipoPeriodicidade == TIPO_PERIODICIDADE_MINUTOS)
            textoTipoPeriodicidade =  "minutos";

        if (tipoPeriodicidade == TIPO_PERIODICIDADE_HORAS)
            textoTipoPeriodicidade =  "horas";

        if (tipoPeriodicidade == TIPO_PERIODICIDADE_DIAS)
            textoTipoPeriodicidade =  "dias";

        if (tipoPeriodicidade == TIPO_PERIODICIDADE_MESES)
            textoTipoPeriodicidade =  "meses";




        return textoTipoPeriodicidade;
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


}

