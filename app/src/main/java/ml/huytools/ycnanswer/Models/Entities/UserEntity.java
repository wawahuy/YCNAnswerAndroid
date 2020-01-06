package ml.huytools.ycnanswer.Models.Entities;

import ml.huytools.ycnanswer.Core.Annotation.JsonName;
import ml.huytools.ycnanswer.Core.MVP.Entity;

public class UserEntity extends Entity {

    @JsonName
    public int id;

    @JsonName
    public String email;

    @JsonName
    public String tendangnhap;

    @JsonName
    public int diemcaonhat;

    @JsonName
    public int credit;

    @JsonName
    public String AvatarUrl;

}
