package no.geir.avtaletjeneste.mock

import no.geir.avtaletjeneste.client.BrevtjenesteClient
import no.geir.avtaletjeneste.domain.AvtaleInfo
import no.geir.avtaletjeneste.domain.UtsendelseStatus
import org.springframework.stereotype.Component

@Component
class BrevtjenesteClientMock: BrevtjenesteClient {

    override fun sendAvtaleTilKunde(avtale: AvtaleInfo, kundenummer: String): UtsendelseStatus {
        return UtsendelseStatus.SENDT
    }
}

