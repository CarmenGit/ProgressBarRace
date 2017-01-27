package es.cice.progressbarrace;

import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ProgressBar[] pBarList;
    public static final int SET_PROGRESS_MAX=1;
    public static final int SET_PROGRESS_NIVEL=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TypedArray pBarArray=getResources().obtainTypedArray(R.array.progressBarIds);
        pBarList= new ProgressBar[pBarArray.length()];
        for (int i = 0; i < pBarList.length; i++) {

            pBarList[i] = (ProgressBar) findViewById(pBarArray.getResourceId(i,0));
            pBarList[i].setMax((int)getResources().getInteger(R.integer.pBarMax));
        }
    }


    public void startRace() {
        ProgressBarRaceAsyntask[] pBartasks = new ProgressBarRaceAsyntask[pBarList.length];
        for (int i = 0; i < pBartasks.length;i++){
            pBartasks[i].execute();
        }
    }

    /**
     * Maneja la progresión de un ProgressBar. La actividad maneja un grupo de ProgressBar identificados porun
     * índice [0...max]. El primer genérico del AsynTask determina el indice del Progressbar que va a controlar
     * El segundo genérico se usa para señalar el progreso del Progressbar bajo control.
     * El último genérico, el valor final del AsyncTask cuando haya concluido su trabajo, señala el índice del
     * ProgressBar que ha terminado.
     */

    public class ProgressBarRaceAsyntask extends AsyncTask<Integer,Integer,Integer> {
        private int pBarIndex;


        @Override
        protected Integer doInBackground(Integer... params) {
            if(params.length==0)
               return null;
            pBarIndex=params[0];
            Random rnd=new Random();
            int progress=0;
            while(progress<R.integer.pBarMax){
                int avance=rnd.nextInt(10);
                publishProgress(avance);
                int sleep=rnd.nextInt(100);
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return pBarIndex;

        }



        @Override
        protected void onProgressUpdate(Integer... values) {
            pBarList[pBarIndex].setProgress(values[0]);

        }


        @Override
        protected void onPostExecute(Integer integer) {
            pBarList[pBarIndex].setVisibility(View.GONE);
        }
    }
}
