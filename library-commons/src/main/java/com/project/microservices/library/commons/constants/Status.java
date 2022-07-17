package com.project.microservices.library.commons.constants;

public enum Status {

    ACTIVE("Activo"),
    RECEIVED("Recibido"),
    AUTHORIZED("Autorizado"),
    IN_PROCESS("En proceso"),
    NOTIFIED("Notificado"),
    REJECTED("Rechazado"),
    RESERVED("Reservado"),
    EXPIRED("Expirado"),
    INACTIVE("Inactivo");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

}
