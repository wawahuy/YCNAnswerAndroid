package ml.huytools.ycnanswer.Models.Entities;

import ml.huytools.ycnanswer.Core.Annotation.JsonName;
import ml.huytools.ycnanswer.Core.MVP.Entity;

public class CategoriesEntity extends Entity {
    @JsonName
    public int id;

    @JsonName
    public String ten_linh_vuc;

    @JsonName
    public int soLuongCauHoi;
}
