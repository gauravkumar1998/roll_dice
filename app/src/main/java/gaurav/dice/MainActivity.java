package gaurav.dice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);
        btn=findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter some value.", Toast.LENGTH_SHORT).show();
                }
                else {
                    int times = Integer.parseInt(et.getText().toString().trim());
                    new BackgroundProcess().execute(times);
                }
            }
        });
    }

    public class BackgroundProcess extends AsyncTask<Integer,Integer,String>{

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog=new ProgressDialog(MainActivity.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setMax(Integer.parseInt(et.getText().toString().trim()));
            dialog.show();
        }

        @Override
        protected String doInBackground(Integer... params) {

            int one=0, two=0, three=0, four=0, five=0, six=0, randomNo;
            Random random=new Random();
            String result;
            double current=0,previous=0;

            for (int i=0;i<params[0];i++){
                current=(double)i/params[0];
                if (current-previous>=0.02){
                    publishProgress(i);
                    previous=current;
                }
                randomNo=random.nextInt(6)+1;
                switch (randomNo){
                    case 1:one++;
                        break;
                    case 2:two++;
                        break;
                    case 3:three++;
                        break;
                    case 4:four++;
                        break;
                    case 5:five++;
                        break;
                    default:six++;
                }
            }
            result="Results: \n1: "+one+"\n2: "+two+"\n3: "+three+"\n4: "+four+"\n5: "+five+"\n6: "+six;

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            dialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            tv.setText(s);
            Toast.makeText(MainActivity.this, "Process done.", Toast.LENGTH_SHORT).show();
        }
    }
}
