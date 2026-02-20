package imdl.eclesia.service.mapper;

import imdl.eclesia.service.utils.events.Log;
import imdl.eclesia.entity.LogEntity;
import com.fasterxml.jackson.databind.JsonNode;

public class LogMapper {
    public static LogEntity domainToEntity(Log log) {
        LogEntity entity = new LogEntity();
        entity.setReferenciaId(log.getReferenciaId());
        entity.setTipoLog(log.getTipoLog());
        entity.setAction(log.getAction());
        entity.setDescription(log.getDescription());
        entity.setObject(log.getObject());
        return entity;
    }
}
