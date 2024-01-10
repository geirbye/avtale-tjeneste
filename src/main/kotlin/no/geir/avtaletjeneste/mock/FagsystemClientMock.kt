package no.geir.avtaletjeneste.mock

import no.geir.avtaletjeneste.client.FagsystemClient
import no.geir.avtaletjeneste.client.StatusOppdetringException
import no.geir.avtaletjeneste.domain.AvtaleStatus
import no.geir.avtaletjeneste.domain.AvtaleStatusEnum
import org.springframework.stereotype.Component

@Component
class FagsystemClientMock: FagsystemClient {

    override fun opprettAvtale(kundenummer: String): String {
        return "A-123456"
    }

    override fun opprettKunde(fornavn:String, etternavn: String, adresse: String, epost: String): String {
        return "K-123456"
    }

    override fun oppdaterStatus(status: AvtaleStatus): AvtaleStatus {
        return status
    }

}

