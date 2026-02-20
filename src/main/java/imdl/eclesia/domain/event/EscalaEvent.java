package imdl.eclesia.domain.event;

import imdl.eclesia.domain.Escala;
import imdl.eclesia.service.utils.events.LogAction;

public class EscalaEvent {
    private final Escala escala;
    private final LogAction action;
    private final String user;

    public EscalaEvent(Escala escala, LogAction action, String user) {
        this.escala = escala;
        this.action = action;
        this.user = user;
    }

    public Escala getEscala() {
        return escala;
    }

    public LogAction getAction() {
        return action;
    }

    public String getUser() {
        return user;
    }
}
