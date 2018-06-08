package complejos.example.com.arrastarysoltar;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private TextView cuboUno, cuboDos, cuboTres;
    private ImageView cajaCubos;
    private LinearLayout contenedorEliminar;
    private ImageButton recargar;
    private Long tiempo;
    private boolean estaPresionado = false;
    private TextView txtContador;
    private float firstTouchX;
    private float firstTouchY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cuboUno = (TextView) findViewById(R.id.CuboUno);
        cuboDos = (TextView) findViewById(R.id.CuboDos);
        cuboTres = (TextView) findViewById(R.id.CuboTres);
        cajaCubos = (ImageView) findViewById(R.id.CajaCubos);
        contenedorEliminar = (LinearLayout) findViewById(R.id.contenedorEliminar);
        txtContador = (TextView) findViewById(R.id.txtContador);

        recargar = (ImageButton) findViewById(R.id.btnRecargar);

        firstTouchX= cuboUno.getX();
        firstTouchY= cuboUno.getY();

        recargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuboUno.setVisibility(View.VISIBLE);
                cuboDos.setVisibility(View.VISIBLE);
                cuboTres.setVisibility(View.VISIBLE);
            }
        });

        cuboUno.setOnTouchListener(this);
        cuboDos.setOnTouchListener(this);
        cuboTres.setOnTouchListener(this);

        contenedorEliminar.setVisibility(View.GONE);
        cajaCubos.setOnDragListener(dragListener);

    }

    /*View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            contenedorEliminar.setVisibility(View.VISIBLE);
            animarRealtiveLayout(true);
            return false;
        }
    };*/

    /*Este es el encargado de escuchar lo que pasa sobre el bot de basura XD*/

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {

            int dragEventAction = dragEvent.getAction();
            final View viewDrag = (View) dragEvent.getLocalState();

            switch (dragEventAction) {

                case DragEvent.ACTION_DRAG_ENTERED:

                    // ACCIÓN ARRASTRE ENTRADA

                    contenedorEliminar.setVisibility(View.VISIBLE);
                    // Toast.makeText(MainActivity.this, "ACTION_DRAG_ENTERED", Toast.LENGTH_SHORT).show();
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    //Cuando sale del contenedor

                    //contenedorEliminar.setVisibility(View.GONE);
                    break;

                case DragEvent.ACTION_DROP:

                    if (viewDrag.getId() == R.id.CuboUno) {
                        cajaCubos.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.cuboUno));
                        cuboUno.setVisibility(View.GONE);
                        EsconderBote();
                    } else if (viewDrag.getId() == R.id.CuboDos) {
                        cajaCubos.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.cuboDos));
                        cuboDos.setVisibility(View.GONE);
                        EsconderBote();
                    } else if (viewDrag.getId() == R.id.CuboTres) {
                        cajaCubos.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.cuboTres));
                        cuboTres.setVisibility(View.GONE);
                        EsconderBote();
                    }
                    break;

                default:
                    //       Toast.makeText(Ma/Activity.this, "No entro en ningun case", Toast.LENGTH_SHORT).show();
                    EsconderBote();

                    break;
            }

            return false;
        }
    };

    private void EsconderBote() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ejecutar despues de 2s = 2000ms
                contenedorEliminar.setVisibility(View.GONE);
            }
        }, 2000);

    }

    private void animarRealtiveLayout(boolean mostrar) {
        AnimationSet set = new AnimationSet(true);
        Animation animation;
        if (mostrar) {
            //desde la esquina inferior derecha a la superior izquierda
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        } else {    //desde la esquina superior izquierda a la esquina inferior derecha
            animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        }
        //duración en milisegundos
        animation.setDuration(500);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.25f);

        contenedorEliminar.setLayoutAnimation(controller);
        contenedorEliminar.startAnimation(animation);
    }

    /*
    *Metodo encargadp de escuhca rlo que pasa con los cubos
    */

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {

            /*Caundo el boton es precionado comienza la grabacion*/
            case MotionEvent.ACTION_DOWN:
                tiempo = System.currentTimeMillis();//Contamos el tiempo qe el boton es precionado

                break;

            case MotionEvent.ACTION_MOVE:

                cuboUno.setX( event.getX() + (cajaCubos.getWidth()-cuboUno.getWidth())*4);


                break;

            case MotionEvent.ACTION_UP:

                cuboUno.setX(firstTouchX);
                if (((Long) System.currentTimeMillis() - tiempo) > 1200) {
                    tiempo = null;
                } else {
                    Toast.makeText(this, "Deja precionado el boton", Toast.LENGTH_SHORT).show();
                }

                break;

            default:
                //cuboUno.setX(firstTouchX);
                break;

        }

        return true;
    }


    public class Contador extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            while (estaPresionado) {
                AumentarContador();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        private void AumentarContador() {
            int contador = 0;
            contador = contador + 1;
            txtContador.setText(String.valueOf(contador));
        }

    }

    /*
    case MotionEvent.ACTION_MOVE:

    if (firstTouchX > event.getX()) {
        //Hacia la izquierda
    } else {
        //Hacia la derecha
    }
    if (firstTouchY > event.getY()) {

    } else {
        //Hacia abajo
        cuboUno.setX(event.getX() - cajaCubos.getWidth() );
    }

                break;

            case MotionEvent.ACTION_UP:

            if (((Long) System.currentTimeMillis() - tiempo) > 1200) {

        cuboUno.setX(firstTouchX);
        tiempo = null;
        estaPresionado = false;
    } else {
        Toast.makeText(this, "Deja precionado el boton", Toast.LENGTH_SHORT).show();
    }cuboUno.setX(event.getX() - cajaCubos.getWidth() );

                break;

}

        return true;*/
}



