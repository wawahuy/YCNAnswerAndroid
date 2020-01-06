package ml.huytools.ycnanswer.Models.Entities;

import ml.huytools.ycnanswer.Core.Annotation.JsonName;
import ml.huytools.ycnanswer.Core.MVP.Entity;
import ml.huytools.ycnanswer.Core.MVP.EntityManager;

public class findConfigQuestionEntity extends Entity {
    @JsonName
    public int diem;

    @JsonName
    public int thu_tu;

    public boolean moc;
    public EntityManager<ConfigHelpEntity> helps;
}
