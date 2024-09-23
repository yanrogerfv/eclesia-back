package imdl.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "role")
public class RoleEntity {
    private UUID id;
    private String role;
}
