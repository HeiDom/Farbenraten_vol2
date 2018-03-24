package de.dome.farbenerraten.Util;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by Dominik on 04.09.2017.
 */
public class AnimationUtil {

    /**
     * Erstellt eine Animation
     * @return gibt ein Animations Objekt zurück
     */
    public static AlphaAnimation getAnimation(Animation.AnimationListener animationListener){
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(1000);
        animation.setAnimationListener(animationListener);

        return animation;
    }
}
