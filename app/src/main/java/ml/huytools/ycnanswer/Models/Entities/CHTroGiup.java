package ml.huytools.ycnanswer.Models.Entities;

import ml.huytools.ycnanswer.Core.Annotation.JsonName;
import ml.huytools.ycnanswer.Core.MVP.Entity;

public class CHTroGiup extends Entity {
    @JsonName
    public int id;

    @JsonName
    public int loai_tro_giup;

    @JsonName
    public int thu_tu;

    @JsonName
    public int credit;
}
