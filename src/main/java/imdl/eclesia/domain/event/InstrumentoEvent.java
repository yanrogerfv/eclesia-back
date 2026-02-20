package imdl.eclesia.domain.event;

import imdl.eclesia.domain.Instrumento;
import imdl.eclesia.service.utils.events.LogAction;

public class InstrumentoEvent {
    private final Instrumento instrumento;
    private final LogAction action;
    private final String user;

    public InstrumentoEvent(Instrumento instrumento, LogAction action, String user) {
        this.instrumento = instrumento;
        this.action = action;
        this.user = user;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public LogAction getAction() {
        return action;
    }

    public String getUser() {
        return user;
    }
}

