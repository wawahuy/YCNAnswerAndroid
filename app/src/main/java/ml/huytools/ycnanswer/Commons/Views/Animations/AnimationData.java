package ml.huytools.ycnanswer.Commons.Views.Animations;

import android.content.Context;
import android.content.res.Resources;

import java.util.LinkedList;

import ml.huytools.ycnanswer.Commons.Annotation.JsonName;
import ml.huytools.ycnanswer.Commons.App;
import ml.huytools.ycnanswer.Commons.Math.Vector2D;
import ml.huytools.ycnanswer.Commons.Model;
import ml.huytools.ycnanswer.Commons.ModelManager;
import ml.huytools.ycnanswer.Commons.Resource;
import ml.huytools.ycnanswer.Commons.Views.Image;

/***
 *
 * Cấu trúc:
 *  {
 *      "image": [
 *          {
 *              "type" : "...",     /// resource|storage
 *              "path" : "..."      /// R.[...].[name] | /a/b.type
 *              "frames" : [
 *  *               {"x": ..., "y": ..., "w": ..., "h": ...},   // frames pos 0
 *                    ....                                        // frames pos n
 *  *               ]
 *          }
 *      ]
 *      ,
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
    public ModelManager<Action> actions;

    @JsonName(type = JsonName.Type.ModelManager, clazz = Image.class)
    public ModelManager<Image> images;


    public  static AnimationData CreateByResource(int resFileJson){

        /// Tạo Model  với Resource
        String json = Resource.readRawTextFile(resFileJson);
        AnimationData animationData = Model.ParseJson(AnimationData.class, json);

        /// Tải resource tương ứng
        animationData.init();

        return animationData;
    }

    public void init(){
        /// Load image
        for(Image image:images){
            image.init();
        }
    }

    public boolean check(){
        /// Kiểm tra & lấy danh sách các image ID
        LinkedList<String> imageIds = new LinkedList<>();
        for(Image image:images){
            if(!image.isLoaded){
                return false;
            }
            imageIds.add(image.id);
        }

        /// Kiểm tra các Actions
        for(Action action:actions){
            // Kiểm tra các frame có ID hợp lệ hay không
            for(Action.Frame frame:action.frames){
                boolean check = false;
                for(String imageID:imageIds){
                    if(imageID.equals(frame.imgID)){
                        check = true;
                        break;
                    }
                }

                if(!check){
                    return false;
                }
            }
        }
        return true;
    }



    public static class Image extends Model {
        @JsonName
        public String id;

        @JsonName
        public String type; /// resource

        @JsonName
        public String path;

        @JsonName
        public float scaleX = 1.0f;

        @JsonName
        public float scaleY = 1.0f;

        @JsonName(type = JsonName.Type.ModelManager, clazz = Image.Frame.class)
        public ModelManager<Image.Frame> frames;

        ///
        private boolean isLoaded = false;

        private ml.huytools.ycnanswer.Commons.Views.Image image;

        /// Frames
        public static class Frame extends Model {
            @JsonName
            public float x;

            @JsonName
            public float y;

            @JsonName
            public float w;

            @JsonName
            public float h;

            ///
            private ml.huytools.ycnanswer.Commons.Views.Image imageCrop;

            public ml.huytools.ycnanswer.Commons.Views.Image getImageCrop(){
                return imageCrop;
            }
        }

        private void init(){
            /// init image resource
            switch (type){
                case "resource":
                    int r = Resource.getResourceID(path);
                    image = ml.huytools.ycnanswer.Commons.Views.Image.LoadByResource(r);
                    if(scaleX != 1.0f || scaleY != 1.0f){
                        image = image.scale(new Vector2D(scaleX, scaleY));
                    }
                    break;
            }

            isLoaded = image!=null && image.getBitmap() != null;
            if(!isLoaded){
                return;
            }

            /// init crop image to mini - frames
            for(Image.Frame f:frames){
                float x = App.convertDpToPixel(f.x)*scaleX;
                float y = App.convertDpToPixel(f.y)*scaleY;
                float w = App.convertDpToPixel(f.w)*scaleX;
                float h = App.convertDpToPixel(f.h)*scaleY;
                f.imageCrop = image.crop(new Vector2D(x, y), new Vector2D(w, h));
            }
        }


        public ml.huytools.ycnanswer.Commons.Views.Image getImage() {
            return image;
        }
    }

    public static class Action extends Model {
        @JsonName
        public String name;

        @JsonName
        public int time;

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
