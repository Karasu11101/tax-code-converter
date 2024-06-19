package it.forcina.esercizio_medialogic.commons;

import java.time.Instant;

public record ErrorResponse(String message, String statusCode, Instant timestamp) {}
