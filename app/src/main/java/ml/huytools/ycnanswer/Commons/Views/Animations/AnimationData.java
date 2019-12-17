package ml.huytools.ycnanswer.Commons.Views.Animations;

import ml.huytools.ycnanswer.Commons.Annotation.JsonName;
import ml.huytools.ycnanswer.Commons.Model;
import ml.huytools.ycnanswer.Commons.ModelManager;

/***
 *
 * Cấu trúc:
 *  {
 *      "frames" : [
 *          {"x": ..., "y": ..., "w": ..., "h": ...},   // frames pos 0
 *          ....                                        // frames pos n
 *      ],
 *      "actions" : [
 *          {
 *              "name" : "idle",
 *              "position_frames" : [0, 1, 3, ...]
 *          },
 *          ....    // action different
 *      ]
 *  }
 */
public class AnimationData extends Model {
    @JsonName(type = JsonName.Type.ModelManager, clazz = Action.class)
    private ModelManager<Action> actions;

    @JsonName(type = JsonName.Type.ModelManager, clazz = Image.class)
    private ModelManager<Image> images;


    public static class Image extends Model {
        @JsonName
        public String id;

        @JsonName
        public String type; /// resource

        @JsonName
        public String path;

        @JsonName(type = JsonName.Type.ModelManager, clazz = Image.Frame.class)
        private ModelManager<Image.Frame> frames;

        public static class Frame extends Model {
            @JsonName
            public float x;

            @JsonName
            public float y;

            @JsonName
            public float w;

            @JsonName
            public float h;
        }
    }

    public static class Action extends Model {
        @JsonName
        public String name;

        @JsonName(type = JsonName.Type.ModelManager, clazz = Action.Frame.class)
        public ModelManager<Action.Frame> frames;

        public static class Frame extends Model {
            @JsonName
            public String imgID;

            @JsonName
            public int framePos;
        }
    }


}
