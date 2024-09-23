package imdl.auth.entity;

import imdl.scalator.entity.LevitaEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    private UUID id;
    private String username;
    private String passcode;
    private RoleEntity role;
    @JoinColumn(name = "levita_id")
    private LevitaEntity levita;
}
