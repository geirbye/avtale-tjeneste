package no.geir.avtaletjeneste.client

import no.geir.avtaletjeneste.domain.AvtaleInfo
import no.geir.avtaletjeneste.domain.UtsendelseStatus

interface BrevtjenesteClient {
    fun sendAvtaleTilKunde(avtale: AvtaleInfo, kundenummer: String): UtsendelseStatus
}