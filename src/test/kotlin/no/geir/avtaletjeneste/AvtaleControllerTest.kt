package no.geir.avtaletjeneste

import no.geir.avtaletjeneste.client.BrevtjenesteClient
import no.geir.avtaletjeneste.client.FagsystemClient
import no.geir.avtaletjeneste.domain.AvtaleInfo
import no.geir.avtaletjeneste.domain.AvtaleStatus
import no.geir.avtaletjeneste.domain.AvtaleStatusEnum
import no.geir.avtaletjeneste.domain.UtsendelseStatus
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(AvtaleController::class)
class AvtaleControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var fagsystemClient: FagsystemClient

    @MockBean
    private lateinit var brevtjenesteClient: BrevtjenesteClient

    @Test
    fun `opprett_ny_avtale_suksessfullt`() {

        `when`(fagsystemClient.opprettKunde("Geir","Bye","Oslo", "geir@epost.no")).thenReturn("K-123456")
        `when`(fagsystemClient.opprettAvtale("K-123456")).thenReturn("A-123456")
        `when`(brevtjenesteClient.sendAvtaleTilKunde(AvtaleInfo("Geir","Bye","Oslo", "geir@epost.no"),"K-123456")).thenReturn(UtsendelseStatus.SENDT)
        `when`(fagsystemClient.oppdaterStatus( AvtaleStatus("A-123456", AvtaleStatusEnum.AVTALE_SENDT))).thenReturn( AvtaleStatus("A-123456", AvtaleStatusEnum.AVTALE_SENDT))

        mvc.post("/avtale") {
            contentType = MediaType.APPLICATION_JSON
            content = "{\"fornavn\":\"Geir\",\"etternavn\":\"Bye\",\"adresse\":\"Oslo\",\"epost\":\"geir@epost.no\"}"
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json("{\"avtaleNummer\": \"A-123456\",\"status\": \"AVTALE_SENDT\"}") }
        }
    }
}