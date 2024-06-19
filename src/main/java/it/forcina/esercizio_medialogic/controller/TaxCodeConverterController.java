package it.forcina.esercizio_medialogic.controller;

import it.forcina.esercizio_medialogic.service.ConverterService;
import it.forcina.esercizio_medialogic.commons.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TaxCodeConverterController {

    private final ConverterService converter;

    @Autowired
    public TaxCodeConverterController(ConverterService converter) {
        this.converter = converter;
    }

    @GetMapping(value = "/taxCode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> convertTaxCode(@RequestParam("taxCode") String taxCode) {
        return ResponseEntity.status(HttpStatus.OK).body(converter.convertToBirthDate(taxCode));
    }
}
