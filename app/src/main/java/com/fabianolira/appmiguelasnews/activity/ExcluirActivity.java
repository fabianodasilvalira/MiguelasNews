package com.fabianolira.appmiguelasnews.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.fabianolira.appmiguelasnews.R;
import com.fabianolira.appmiguelasnews.json.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class ExcluirActivity extends AppCompatActivity {
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir);

        txt = findViewById(R.id.textExcluir);

        new NoticiasTask().execute("https://viacep.com.br/ws/01001000/json/");
    }

    public class NoticiasTask extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... strings) {
            return JsonUtils.retornaJsonDeGet(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject obj = new JSONObject(s);

                txt.setText(obj.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
