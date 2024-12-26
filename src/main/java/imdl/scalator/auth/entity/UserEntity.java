package imdl.scalator.auth.entity;

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
    @OneToOne
    private RoleEntity role;
    private UUID levitaId;
}
