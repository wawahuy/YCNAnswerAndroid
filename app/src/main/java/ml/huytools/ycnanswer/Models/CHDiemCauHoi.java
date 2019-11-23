package ml.huytools.ycnanswer.Models;

import ml.huytools.ycnanswer.Commons.Annotation.JsonName;
import ml.huytools.ycnanswer.Commons.Model;

public class CHDiemCauHoi extends Model {

    @JsonName
    public int id;

    @JsonName
    public int diem;

    @JsonName
    public int thu_tu;

}
