package complejos.example.com.arrastarysoltar;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView cuboUno,cuboDos,cuboTres,cajaCubos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cuboUno = (TextView) findViewById(R.id.CuboUno);
        cuboDos = (TextView) findViewById(R.id.CuboDos);
        cuboTres= (TextView) findViewById(R.id.CajaCubos);

        cuboUno.setOnLongClickListener(longClickListener);
        cuboDos.setOnLongClickListener(longClickListener);
        cuboTres.setOnLongClickListener(longClickListener);

       // cajaCubos.setOnDragListener(dragListener);

    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {

            int dragEventAction = dragEvent.getAction();

            switch (dragEventAction){

                case DragEvent.ACTION_DRAG_ENTERED:
                    final View viewDrag = (View) dragEvent.getLocalState();

                    if (viewDrag.getId() == R.id.CuboUno){
                        cajaCubos.setText("Arrastrasre el: "+cuboTres.getText() );
                    }
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    break;

                case DragEvent.ACTION_DROP:
                    break;
            }

            return false;
        }
    };

}
