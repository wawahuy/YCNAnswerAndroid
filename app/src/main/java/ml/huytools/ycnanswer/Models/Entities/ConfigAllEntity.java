package ml.huytools.ycnanswer.Models.Entities;

import ml.huytools.ycnanswer.Core.Annotation.JsonName;
import ml.huytools.ycnanswer.Core.MVP.Entity;
import ml.huytools.ycnanswer.Core.MVP.EntityManager;

public class ConfigAllEntity extends Entity {
    @JsonName(type= JsonName.Type.Model, clazz = ConfigAppEntity.class)
    public ConfigAppEntity cau_hinh_app;

    @JsonName(type= JsonName.Type.ModelManager, clazz = ConfigHelpEntity.class)
    public EntityManager<ConfigHelpEntity> cau_hinh_tro_giup;

    @JsonName(type= JsonName.Type.ModelManager, clazz = ConfigQuestionEntity.class)
    public EntityManager<ConfigQuestionEntity> cau_hinh_cau_hoi;
}
