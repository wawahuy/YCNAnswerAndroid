package ml.huytools.ycnanswer.Models.Entities;

import ml.huytools.ycnanswer.Core.Annotation.JsonName;
import ml.huytools.ycnanswer.Core.MVP.Entity;

public class CreditEntity extends Entity {

    @JsonName
    public int id;

    @JsonName
    public String tengoi;

    @JsonName
    public int credit;

    @JsonName
    public int sotien;
}
