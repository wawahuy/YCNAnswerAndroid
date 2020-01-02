package ml.huytools.ycnanswer.Models.Entities;


import ml.huytools.ycnanswer.Core.Annotation.JsonName;
import ml.huytools.ycnanswer.Core.MVP.Entity;

public class QuestionEntity extends Entity {
    @JsonName
    public int id;

    @JsonName
    public String noidung;

    @JsonName
    public String phuongan_A;

    @JsonName
    public String phuongan_B;

    @JsonName
    public String phuongan_C;

    @JsonName
    public String phuongan_D;

    @JsonName
    public String dapan;
}
