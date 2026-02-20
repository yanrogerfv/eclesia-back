package imdl.eclesia.service.utils.events;

import imdl.eclesia.domain.event.LevitaEvent;
import imdl.eclesia.domain.event.EscalaEvent;
import imdl.eclesia.domain.event.MusicaEvent;
import imdl.eclesia.domain.event.InstrumentoEvent;
import imdl.eclesia.persistence.LogRepository;
import imdl.eclesia.service.mapper.LogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
public class LogListener {

    private final LogRepository logRepository;
    private final ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
    private final ObjectMapper mapper = new ObjectMapper();

    public LogListener(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @EventListener
    public void handleLevitaEvent(LevitaEvent event) {
        Log logEntry = new Log();
        logEntry.setReferenciaId(event.getLevita().getId());
        logEntry.setTipoLog(TipoLog.LEVITA);
        logEntry.setAction(event.getAction().getAcao());
        logEntry.setDescription(String.format(
                "%s %s %s às %s.",
                event.getUser(),
                event.getAction().getAcao(),
                event.getLevita().getNome(),
                LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        ));
        JsonNode obj = mapper.valueToTree(event.getLevita());
        logEntry.setObject(obj);
        log.info("Registrando log de Levita: {}", logEntry.getDescription());
        logRepository.save(LogMapper.domainToEntity(logEntry));
    }

    @EventListener
    public void handleEscalaEvent(EscalaEvent event) {
        Log logEntry = new Log();
        logEntry.setReferenciaId(event.getEscala().getId());
        logEntry.setTipoLog(TipoLog.ESCALA);
        logEntry.setAction(event.getAction().getAcao());
        logEntry.setDescription(String.format(
                "%s %s %s do dia %s às %s.",
                event.getUser(),
                event.getAction().getAcao(),
                event.getEscala().getTitulo(),
                event.getEscala().getData().toString(),
                LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        ));
        JsonNode obj = mapper.valueToTree(event.getEscala());
        logEntry.setObject(obj);
        log.info("Registrando log de Escala: {}", logEntry.getDescription());
        logRepository.save(LogMapper.domainToEntity(logEntry));
    }

    @EventListener
    public void handleMusicaEvent(MusicaEvent event) {
        Log logEntry = new Log();
        logEntry.setReferenciaId(event.getMusica().getId());
        logEntry.setTipoLog(TipoLog.MUSICA);
        logEntry.setAction(event.getAction().getAcao());
        logEntry.setDescription(String.format(
                "%s %s %s às %s.",
                event.getUser(),
                event.getAction().getAcao(),
                event.getMusica().getNome(),
                LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        ));
        JsonNode obj = mapper.valueToTree(event.getMusica());
        logEntry.setObject(obj);
        log.info("Registrando log de Música: {}", logEntry.getDescription());
        logRepository.save(LogMapper.domainToEntity(logEntry));
    }

    @EventListener
    public void handleInstrumentoEvent(InstrumentoEvent event) {
        Log logEntry = new Log();
        logEntry.setReferenciaId(UUID.randomUUID());
        logEntry.setTipoLog(TipoLog.INSTRUMENTO);
        logEntry.setAction(event.getAction().getAcao());
        logEntry.setDescription(String.format(
                "%s %s %s às %s.",
                event.getUser(),
                event.getAction().getAcao(),
                event.getInstrumento().getNome(),
                LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
        ));
        JsonNode obj = mapper.valueToTree(event.getInstrumento());
        logEntry.setObject(obj);
        log.info("Registrando log de Instrumento: {}", logEntry.getDescription());
        logRepository.save(LogMapper.domainToEntity(logEntry));
    }
}
