package pro.jazzman.odmiana.entities;

import lombok.Data;

@Data
public class Healthcheck {
    private final String version;
    private final String status;
    private final String database;
}
