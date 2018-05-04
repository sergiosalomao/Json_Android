package sistema.com.br.json;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.Touch;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String myJson = "";
    String URLJson = "http://www.json-generator.com/api/json/get/bULVPpfmtK?indent=2";
    String nome,sobrenome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
    }
     //essa classe foi adicionada para conectar com a internet
       private class JasonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try{
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);
                }
                return buffer.toString();

            }catch (MalformedURLException e)
            {
                e.printStackTrace();
            }catch (IOException e)
            {
                e.printStackTrace();
            }finally
            {
                if (connection != null)
                    connection.disconnect();
                try
                {
                    if (reader != null)
                    {
                        reader.close();
                    }
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
           Log.i("MEU LOG",result);
            try {
                JSONObject json = new JSONObject( result );
                 nome = json.getString( "nome" );
                 sobrenome = json.getString( "sobrenome" );


            }catch (JSONException e ){

            }

            super.onPostExecute(result);
        }
    }
    //-----------------------------------------------------------------------

    public void onClick (View view){
            myJson += "{";
            myJson += "\"nome\":\"Leandro\",";
            myJson += "\"sobrenome\":\"Lopes\",";
            myJson += "\"idade\":36";
            myJson += "}";

        try {
                JSONObject arquivojson = new JSONObject( myJson );
                String nome = arquivojson.getString( "nome" );
                String sobrenome = arquivojson.getString( "sobrenome" );
                Toast.makeText( this, nome + " " + sobrenome, Toast.LENGTH_SHORT ).show();

            } catch (JSONException e) {
                Toast.makeText( this, "Error!!!!!", Toast.LENGTH_SHORT ).show();
            }
        }


    public void onClickRemote (View view) {
        new JasonTask().execute( URLJson );
        Toast.makeText( MainActivity.this, "Remoto: " + sobrenome + " " + sobrenome, Toast.LENGTH_SHORT ).show();
    }


}

