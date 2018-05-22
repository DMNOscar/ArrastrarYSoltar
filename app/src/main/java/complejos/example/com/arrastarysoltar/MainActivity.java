package complejos.example.com.arrastarysoltar;

import android.content.ClipData;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView cuboUno,cuboDos,cuboTres;
    private ImageView cajaCubos;
    private LinearLayout contenedorEliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cuboUno = (TextView) findViewById(R.id.CuboUno);
        cuboDos = (TextView) findViewById(R.id.CuboDos);
        cuboTres = (TextView) findViewById(R.id.CuboTres);
        cajaCubos = (ImageView) findViewById(R.id.CajaCubos);
        contenedorEliminar = (LinearLayout) findViewById(R.id.contenedorEliminar);

        cuboUno.setOnLongClickListener(longClickListener);
        cuboDos.setOnLongClickListener(longClickListener);
        cuboTres.setOnLongClickListener(longClickListener);

        contenedorEliminar.setVisibility(View.GONE);
        cajaCubos.setOnDragListener(dragListener);

    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            //view.setVisibility(View.INVISIBLE);
            contenedorEliminar.setVisibility(View.VISIBLE);
            animarRealtiveLayout(true);
            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {

            int dragEventAction = dragEvent.getAction();
            final View viewDrag = (View) dragEvent.getLocalState();

            switch (dragEventAction){

                case DragEvent.ACTION_DRAG_ENTERED:
                    //cajaCubos.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.cajaCubos));
/*
                    if (viewDrag.getId() == R.id.CuboUno){
                        cajaCubos.setText("Arrastrasre el: "+cuboUno.getText() );
                    }else if (viewDrag.getId() == R.id.CuboDos){
                        cajaCubos.setText("Arrastrasre el: "+cuboDos.getText() );
                    }else if (viewDrag.getId() == R.id.CuboTres){
                        cajaCubos.setText("Arrastrasre el: "+cuboTres.getText() );
                    }*/
                    contenedorEliminar.setVisibility(View.VISIBLE);

                    break;

                case DragEvent.ACTION_DRAG_EXITED:

                    break;

                case DragEvent.ACTION_DROP:

                    if (viewDrag.getId() == R.id.CuboUno){
                        cajaCubos.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.cuboUno));
                        cuboUno.setVisibility(View.INVISIBLE);
                    }else if (viewDrag.getId() == R.id.CuboDos){
                        cajaCubos.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.cuboDos));
                        cuboDos.setVisibility(View.INVISIBLE);
                    }else if (viewDrag.getId() == R.id.CuboTres){
                        cajaCubos.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.cuboTres));
                        cuboTres.setVisibility(View.INVISIBLE);

                    }
                    break;

                default:
             //       Toast.makeText(Ma/Activity.this, "No entro en ningun case", Toast.LENGTH_SHORT).show();
                    contenedorEliminar.setVisibility(View.VISIBLE);
                    break;
            }

            return true;
        }
    };


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

}
