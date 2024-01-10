package no.geir.avtaletjeneste.client

import no.geir.avtaletjeneste.domain.AvtaleStatus

interface FagsystemClient {
    fun opprettAvtale(kundenummer: String): String
    fun opprettKunde(fornavn:String, etternavn: String, adresse: String, epost: String): String
    @Throws(StatusOppdetringException::class)
    fun oppdaterStatus(status: AvtaleStatus): AvtaleStatus
}