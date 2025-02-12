package imdl.eclesia.domain.input;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MusicasIdsInput {
    private List<UUID> musicasIds;
}
