package com.edwinacubillos.exsql;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String url = "jdbc:mysql://inemjose.edu.co:3306/inemjose_colegio";
    String user = "inemjose";
    String password = "inem@jose*2012";

    String resultado = "";

    Button bConectar;
    TextView tData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bConectar = (Button) findViewById(R.id.bConectar);
        tData = (TextView) findViewById(R.id.tData);

        bConectar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        new conexion().execute();
    }

    private class conexion extends AsyncTask<Void, Void, Void>{

        public conexion() {
            super();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try{
                Log.i("Mensaje", "Conectando a base de datos...");
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, password);
                Log.i("Mensaje", "Conexión establecida  ...");
            Statement  estado = con.createStatement();
            ResultSet rs = estado.executeQuery("SELECT * FROM `Futbol`");
            while(rs.next()){
                resultado = resultado +"\t"+ rs.getString("nombre");
            }
            }catch (SQLException ex) {
                Log.i("Mensaje","Error de mysql");
            } catch (Exception e) {
                Log.i("Mensaje"," e.printStackTrace()");
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
          //  Toast.makeText(getApplicationContext(), "Iniciando Conexión", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
           // Toast.makeText(getApplicationContext(), "Termina Conexión", Toast.LENGTH_SHORT).show();
            tData.setText(resultado.toString());
        }
    }


}
