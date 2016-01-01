package me.clandestina.myapplication3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

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
        Button btn = (Button)findViewById(R.id.btnRes);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobar(v);
            }
        });
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
            if(validar()){
                EditText edtN1 = (EditText)findViewById(R.id.num1);
                int n1 = Integer.parseInt(edtN1.getText().toString());
                EditText edtN2 = (EditText)findViewById(R.id.num2);
                int n2 = Integer.parseInt(edtN2.getText().toString());
                EditText edtN3 = (EditText)findViewById(R.id.numRes);
                int res = Integer.parseInt(edtN3.getText().toString());
                Spinner sp = (Spinner)findViewById(R.id.Operacion);
                int op = sp.getSelectedItemPosition();
                //showToast(""+n1+op+n2+"="+res);
                //showToast(""+op);
                int correctAns = 0;
                if(op == 0){
                    correctAns = n1 + n2;
                }else{
                    correctAns = n1 - n2;
                }
                if(correctAns == res){
                    cliente.send(""+n1+sp.getSelectedItem().toString()+n2);
                }else{
                    edtN3.setText("");
                    new MaterialDialog.Builder(this)
                            .title("Error")
                            .content("Resultado incorrecto, intenta de nuevo")
                            .positiveText("Continuar")
                            .show();
                }
            }
        }else{
            new MaterialDialog.Builder(this)
                    .title("Error")
                    .content("No hay conexi[on con el servidor.")
                    .positiveText("Continuar")
                    .show();
        }
    }

    public boolean validar(){
        boolean valid = true;
        EditText edtN1 = (EditText)findViewById(R.id.num1);
        String num1 = edtN1.getText().toString();
        EditText edtN2 = (EditText)findViewById(R.id.num2);
        String num2 = edtN2.getText().toString();
        EditText edtN3 = (EditText)findViewById(R.id.numRes);
        String num3 = edtN3.getText().toString();
        if (num1.isEmpty() || Integer.parseInt(num1) > 999){
            new MaterialDialog.Builder(this)
                    .title("Error")
                    .content("Ingresa un numero entre 1 y 999.")
                    .positiveText("Continuar")
                    .show();
            valid = false;
        }
        if (num2.isEmpty() || Integer.parseInt(num1) > 999){
            new MaterialDialog.Builder(this)
                    .title("Error")
                    .content("Ingresa un numero entre 1 y 999.")
                    .positiveText("Continuar")
                    .show();
            valid = false;
        }
        if (num3.isEmpty()){
            new MaterialDialog.Builder(this)
                    .title("Error")
                    .content("Resultado no puede estar vacio")
                    .positiveText("Continuar")
                    .show();
            valid = false;
        }
        return valid;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cliente.disconnect(getApplicationContext());
    }
}
