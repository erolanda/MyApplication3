package me.clandestina.myapplication3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class settings extends AppCompatActivity {
    //private TcpClient cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
       // cliente = new TcpClient();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

    public void connectToServer(View view){
        String host = ((EditText)findViewById(R.id.inputIP)).getText().toString();
        int port = Integer.parseInt(((EditText) findViewById(R.id.inputPort)).getText().toString());
        Intent i = getIntent();
        i.putExtra("host",host);
        i.putExtra("port",port);
        setResult(RESULT_OK,i);
        finish();
        //showToast(host+"/n"+port);
        //cliente.connect(getApplicationContext(), host, port);
    }

    public void showToast(String mensaje){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, mensaje, duration);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        if(cliente.getConnected()){
            Intent data = new Intent();
            setResult(RESULT_OK, data);
        }
    }*/
}
