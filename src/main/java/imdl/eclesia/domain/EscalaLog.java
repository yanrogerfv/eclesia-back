package imdl.eclesia.domain;

import java.util.UUID;

public class EscalaLog {
    private UUID logId;
    private UUID escalaId;
    private String description;

    public UUID getLogId() {
        return logId;
    }

    public void setLogId(UUID logId) {
        this.logId = logId;
    }

    public UUID getEscalaId() {
        return escalaId;
    }

    public void setEscalaId(UUID escalaId) {
        this.escalaId = escalaId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
