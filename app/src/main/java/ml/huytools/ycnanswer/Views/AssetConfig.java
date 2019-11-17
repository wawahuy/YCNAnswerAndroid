package ml.huytools.ycnanswer.Views;

public final class AssetConfig {

    //// Asset Bang Vi tri Cau Hoi
    public static final int LEVEL_QUESTION_WIDTH_FRAME = 170;
    public static final int LEVEL_QUESTION_HEIGHT_FRAME = 306;
    public static final int[][] LEVEL_QUESTION_MAP_FRAME = {
            {0, 1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10, 11},
            {12, 13, 14, -1, -1, -1}
    };


    public static final float COUNT_DOWN_WIDTH_FRAME = 64.5f;
    public static final float COUNT_DOWN_HEIGHT_FRAME = 63.5f;
    public static final int[][] COUNT_DOWN_MAP_FRAME1 = {
            {0, 1, 2, 3},
            {4, 5, 6, 7},
            {8, 9, 10, 11},
            {12, 13, 14, 15}
    };

    public static final int[][] COUNT_DOWN_MAP_FRAME2 = {
            {0, 1, 2, -1},
            {5, 3, 4, -1},
            {9, 6, 7, 8},
            {13, 10, 11, 12}
    };
}
