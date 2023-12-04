package iesmm.pmdm.pmdm_t4_animaciones_sprites;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {

    private ImageView imagen;

    private Animation animation;

    private AnimationDrawable sprites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagen = (ImageView) this.findViewById(R.id.imageView);
        imagen.setOnClickListener(this);

        Button button = (Button) this.findViewById(R.id.boton);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageView) {
            loadAnimation(v, R.anim.rotacion);
        } else if (v.getId() == R.id.boton) {
            loadFrameAnimation(imagen, sprites);
        }
    }

    private void loadAnimation(View view, int res) {
        // 1. cargamos la animación
        animation = AnimationUtils.loadAnimation(this, res);

        // 2. vinculación del escuchador
        animation.setAnimationListener(this);

        // 3. comenzar la animación
        view.startAnimation(animation);
    }

    private void loadFrameAnimation(ImageView imagen, AnimationDrawable sprites) {
        imagen.setBackgroundResource(R.drawable.sprites);

        sprites = (AnimationDrawable) imagen.getBackground();

        sprites.start();
    }

    @Override
    public void onAnimationStart(Animation animation) {
        Toast.makeText(this, "empieza", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Toast.makeText(this, "termina", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}