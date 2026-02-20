package imdl.eclesia.domain.event;

import imdl.eclesia.domain.Musica;
import imdl.eclesia.service.utils.events.LogAction;

public class MusicaEvent {
    private final Musica musica;
    private final LogAction action;
    private final String user;

    public MusicaEvent(Musica musica, LogAction action, String user) {
        this.musica = musica;
        this.action = action;
        this.user = user;
    }

    public Musica getMusica() {
        return musica;
    }

    public LogAction getAction() {
        return action;
    }

    public String getUser() {
        return user;
    }
}

