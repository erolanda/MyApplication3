package me.clandestina.myapplication3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final int SETUP_CONNECTION = 1;
    private TcpClient cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner Operadores=(Spinner) findViewById(R.id.Operacion);
        ArrayAdapter<CharSequence> spinAdapter=ArrayAdapter.createFromResource(
                this, R.array.Oper, R.layout.spinner_style);
        spinAdapter.setDropDownViewResource(R.layout.spinner_style);
        Operadores.setAdapter(spinAdapter);
        cliente = new TcpClient();
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
            Intent intent1=new Intent(this,settings.class);
            //startActivity(intent1);
            startActivityForResult(intent1, SETUP_CONNECTION);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SETUP_CONNECTION) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if(data.getExtras().containsKey("host")){
                    if(data.getExtras().containsKey("port")){
                        String host = data.getStringExtra("host");
                        int port = data.getIntExtra("port",80);
                        cliente.connect(getApplicationContext(),host,port);
                    }
                }
            }
        }
    }

    public void showToast(String mensaje) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, mensaje, duration);
        toast.show();
    }

    public void comprobar(View v){
        if(cliente.getConnected()) {
            cliente.send("Algo");
        }
    }
}
