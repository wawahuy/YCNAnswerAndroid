package ml.huytools.ycnanswer.Views.GameViews.Effects;

import ml.huytools.ycnanswer.Commons.Views.AbstractAnimation;
import ml.huytools.ycnanswer.Commons.Views.IRender;

public abstract class Effect extends AbstractAnimation implements IRender {
    public abstract  boolean canRemove();
}
