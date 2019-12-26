package ml.huytools.ycnanswer.Models;

import ml.huytools.ycnanswer.Core.Annotation.JsonName;
import ml.huytools.ycnanswer.Core.MVP.Model;

public class CHDiemCauHoi extends Model {

    @JsonName
    public int id;

    @JsonName
    public int diem;

    @JsonName
    public int thu_tu;

    @JsonName
    public boolean moc;

}
