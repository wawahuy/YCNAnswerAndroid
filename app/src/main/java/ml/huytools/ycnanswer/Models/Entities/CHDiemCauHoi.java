package ml.huytools.ycnanswer.Models.Entities;

import ml.huytools.ycnanswer.Core.Annotation.JsonName;
import ml.huytools.ycnanswer.Core.MVP.Entity;

public class CHDiemCauHoi extends Entity {

    @JsonName
    public int id;

    @JsonName
    public int diem;

    @JsonName
    public int thu_tu;

    @JsonName
    public boolean moc;

}
