package imdl.eclesia.entity;

import imdl.eclesia.service.utils.events.TipoLog;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@Table(name = "log")
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID logId;
    private UUID referenciaId;
    @Enumerated(EnumType.STRING)
    private TipoLog tipoLog;
    private String action;
    private String description;
    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private JsonNode object;
}
