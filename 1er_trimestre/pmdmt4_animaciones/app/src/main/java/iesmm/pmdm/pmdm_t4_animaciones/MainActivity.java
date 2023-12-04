package iesmm.pmdm.pmdm_t4_animaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView icon = (ImageView) findViewById(R.id.image);
        Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.my_animation);
        icon.startAnimation(myAnimation);
    }

    public void death(View view) {
        Animation myAnimation = AnimationUtils.loadAnimation(this, R.anim.death_animation);
        ImageView image = (ImageView) findViewById(R.id.image);
        image.startAnimation(myAnimation);
    }
}