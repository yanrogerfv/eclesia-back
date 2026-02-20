package imdl.eclesia.domain.event;

import imdl.eclesia.domain.Levita;
import imdl.eclesia.service.utils.events.LogAction;

import java.util.UUID;

public class LevitaEvent {
    private final Levita levita;
    private final LogAction action;
    private final String user;

    public LevitaEvent( Levita levita, LogAction action, String user) {
        this.levita = levita;
        this.action = action;
        this.user = user;
    }

    public LogAction getAction() {
        return action;
    }

    public String getUser() {
        return user;
    }

    public Levita getLevita() {
        return levita;
    }

}

