package ml.huytools.ycnanswer.Models;


import ml.huytools.ycnanswer.Commons.Annotation.JsonName;
import ml.huytools.ycnanswer.Commons.Model;

public class CauHoi extends Model {

    @JsonName("id")
    private String id;

    public CauHoi(){
        id = "1";
    }

}
