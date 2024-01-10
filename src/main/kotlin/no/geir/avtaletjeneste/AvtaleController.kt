package no.geir.avtaletjeneste

import no.geir.avtaletjeneste.client.BrevtjenesteClient
import no.geir.avtaletjeneste.client.FagsystemClient
import no.geir.avtaletjeneste.domain.AvtaleInfo
import no.geir.avtaletjeneste.domain.AvtaleStatus
import no.geir.avtaletjeneste.domain.AvtaleStatusEnum
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AvtaleController(val fagsystem: FagsystemClient, val brevtjeneste: BrevtjenesteClient) {

    @PostMapping(value = ["/avtale"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun opprettAvtale(@RequestBody avtaleInfo: AvtaleInfo): ResponseEntity<Any> {

        val kundenummer = fagsystem.opprettKunde(avtaleInfo.fornavn, avtaleInfo.etternavn, avtaleInfo.adresse, avtaleInfo.epost)
        val avtaleNummer = fagsystem.opprettAvtale(kundenummer)
        val utsendelseStatus = brevtjeneste.sendAvtaleTilKunde(avtaleInfo, kundenummer)
        val avtaleStatus = fagsystem.oppdaterStatus(AvtaleStatus(avtaleNummer, AvtaleStatusEnum.AVTALE_SENDT))

        return ResponseEntity.status(HttpStatus.CREATED).body(avtaleStatus)

    }

}