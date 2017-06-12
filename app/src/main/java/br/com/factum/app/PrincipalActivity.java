package br.com.factum.app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import br.com.factum.app.database.DataBaseHelper;
import br.com.factum.app.model.Estudo;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewFlipper view_flipper;
    CountDownTimer timer;
    Context context;
    String dataFormatada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        //PEGAR DATA DO CELULAR
        String dataAtual = DateFormat.getDateInstance().format(new Date());
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone(dataAtual));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        dataFormatada = String.format("%02d/%02d/%d", day, month+1, year);
        Log.d("DATA", dataFormatada);

        //Invocando o método que ativa a notificação
        mostrarNotificacao();

        view_flipper= (ViewFlipper) findViewById(R.id.view_flipper);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Animações de entrada e saida
        Animation animacaoEntrada = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        animacaoEntrada.setDuration(2000);

        Animation animacaoSaida = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        animacaoSaida.setDuration(2000);

        view_flipper.setOutAnimation(animacaoSaida);
        view_flipper.setInAnimation(animacaoEntrada);

        //Timer para ele ficar passando sozinho
        timer = new CountDownTimer(5000, 20) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                try{
                    //função que passa de slide
                    nextSlide();

                }catch(Exception e){
                    Log.e("Error", "Error: " + e.toString());
                }
            }
        }.start();
    }
    private void nextSlide(){
        view_flipper.showNext();
        timer.start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_estudo) {
            Intent intent = new Intent(this, EstudoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_simulado) {
            Intent intent = new Intent(this, EscolherSimuladoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_cont) {
            Intent intent = new Intent(this, PendentesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mat) {
            Intent intent = new Intent(this, MaterialActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_video) {

        } else if (id == R.id.nav_com) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void abrirEstudo(View view) {
        Intent intent = new Intent(this, EstudoActivity.class);
        startActivity(intent);
    }

    public void abrirPendentes(View view) {
        Intent intent = new Intent(this, PendentesActivity.class);
        startActivity(intent);
    }
    public void mostrarNotificacao(){
        SQLiteDatabase db = new DataBaseHelper(context).getReadableDatabase();

        String select = "SELECT estudo._id, estudo.data, material.materia, material.assunto FROM tbl_estudo as estudo INNER JOIN tbl_material as material ON estudo.material=material._id";
        //String select = "SELECT * from tbl_estudo";

        Cursor cursor = db.rawQuery(select, null);
        if(cursor.getCount()> 0){
            cursor.moveToFirst();
            for(int i = 0 ; i <cursor.getCount(); i++){
                //TODO: Pegar data do celular e comparar com as do banco
                String dataBanco = cursor.getString(1);
                String materiaBnaco = cursor.getString(2);
                String assuntoBanco = cursor.getString(3);
                Log.d("DATA2", dataFormatada);
                Log.d("DATA3", dataBanco);
                int idEstudo = cursor.getInt(0);
                if(dataBanco.equals(dataFormatada)){
                    Log.d("Quero saber", "Entrou");
                    android.support.v4.app.NotificationCompat.Builder nConstrutor =
                            new NotificationCompat.Builder(this)
                            .setSmallIcon(android.R.drawable.ic_dialog_alert)
                            .setContentTitle("Estudo marcado para hoje")
                            .setContentText(materiaBnaco + " - " + assuntoBanco);

                    Intent intent = new Intent(this, DetalhesEstudoActivity.class);
                    intent.putExtra("idEstudo", idEstudo);

                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

                    // Adds the Intent that starts the Activity to the top of the stack
                    stackBuilder.addNextIntent(intent);


                    PendingIntent pendingIntent =
                            stackBuilder.getPendingIntent(
                                    0,
                                    PendingIntent.FLAG_CANCEL_CURRENT
                            );
                    Integer nId=1;
                    nConstrutor.setContentIntent(pendingIntent);

                    NotificationManager nManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    nManager.notify(nId, nConstrutor.build());
                }







                //if(cursor.ge)
                cursor.moveToNext();
            }
        }else{
        }
        cursor.close();
    }
}
