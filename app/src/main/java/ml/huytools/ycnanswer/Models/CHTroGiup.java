package ml.huytools.ycnanswer.Models;

import ml.huytools.ycnanswer.Commons.Annotation.JsonName;
import ml.huytools.ycnanswer.Commons.Model;

public class CHTroGiup extends Model {
    @JsonName
    public int id;

    @JsonName
    public int loai_tro_giup;

    @JsonName
    public int thu_tu;

    @JsonName
    public int credit;
}
