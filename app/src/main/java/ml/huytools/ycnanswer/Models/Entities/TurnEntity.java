package ml.huytools.ycnanswer.Models.Entities;

import ml.huytools.ycnanswer.Core.Annotation.JsonName;
import ml.huytools.ycnanswer.Core.MVP.Entity;

public class TurnEntity extends Entity {

    @JsonName
    public int id;

    @JsonName
    public int nguoichoi_id;

    @JsonName
    public int socau;

    @JsonName
    public int diem;

    @JsonName
    public String ngaygio;

}
