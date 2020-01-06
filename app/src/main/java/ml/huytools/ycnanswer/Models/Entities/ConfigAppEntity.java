package ml.huytools.ycnanswer.Models.Entities;

import ml.huytools.ycnanswer.Core.Annotation.JsonName;
import ml.huytools.ycnanswer.Core.MVP.Entity;

public class ConfigAppEntity extends Entity {
    @JsonName
    public int co_hoi_sai;

    @JsonName
    public int thoi_gian_tra_loi;
}
