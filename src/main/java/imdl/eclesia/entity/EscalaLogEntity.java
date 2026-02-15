package imdl.eclesia.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "escala_log")
public class EscalaLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID logId;
    private UUID escalaId;
    private String description;
}
