package ml.huytools.ycnanswer.Core.Game.Graphics;

import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import ml.huytools.ycnanswer.Core.App;

public class Font {
    Typeface typeface;

    private Font(){
    }

    public static Font loadResource(int res){
        Font font = new Font();
        font.typeface = ResourcesCompat.getFont(App.getInstance().getContextApplication(), res);
        return font;
    }

    public Typeface getTypeFace(){
        return typeface;
    }
}
