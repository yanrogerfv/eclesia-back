package imdl.scalator.auth.entity;

import imdl.scalator.entity.LevitaEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "users", schema = "ldap")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String passcode;
    private UUID roleId;
    private UUID levitaId;
}
