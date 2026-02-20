package imdl.eclesia.service.utils.events;

import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

public class Log {
    private UUID referenciaId;
    private TipoLog tipoLog;
    private String action;
    private String description;
    private JsonNode object;

    public Log() {}

    public Log(UUID referenciaId, TipoLog tipoLog, String action, String description, JsonNode object) {
        this.referenciaId = referenciaId;
        this.tipoLog = tipoLog;
        this.action = action;
        this.description = description;
        this.object = object;
    }

    public UUID getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(UUID referenciaId) {
        this.referenciaId = referenciaId;
    }

    public TipoLog getTipoLog() {
        return tipoLog;
    }

    public void setTipoLog(TipoLog tipoLog) {
        this.tipoLog = tipoLog;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JsonNode getObject() {
        return object;
    }

    public void setObject(JsonNode object) {
        this.object = object;
    }
}
