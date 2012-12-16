package hotels

import de.datenwissen.util.groovyrdf.core.RdfData
import grails.test.mixin.TestFor

@TestFor (RdfService)
class RdfServiceTests {

    void testHotelToRdf () {
        config.grails.serverURL = 'http://hotels.example'
        def hotel = new Hotel(
                name: 'Testhotel',
                numberOfRooms: 123,
                basedNear: 'http://example.com/Berlin'
        )
        hotel.id = 42

        RdfData rdfData = service.hotelToRdf(hotel)

        assertNotNull('rdfData should not be null', rdfData)
        def hotelResource = rdfData.'http://hotels.example/hotel/42#it'
        assertEquals('http://purl.org/acco/ns#Hotel', hotelResource.type)
        assertEquals('Testhotel', hotelResource.'http://purl.org/goodrelations/v1#name')
        assertEquals(123, hotelResource.'http://purl.org/acco/ns#numberOfRooms')
        assertEquals('http://example.com/Berlin', hotelResource.'http://xmlns.com/foaf/0.1/based_near'?.uri)
    }

    void testHotelsToRdf () {
        config.grails.serverURL = 'http://hotels.example'
        def hotel1 = new Hotel(
                name: 'Testhotel',
                numberOfRooms: 123,
                basedNear: 'http://example.com/Berlin'
        )
        hotel1.id = 42
        def hotel2 = new Hotel(
                name: 'Testhotel 2',
                numberOfRooms: 456,
                basedNear: 'http://example.com/Hamburg'
        )
        hotel2.id = 21

        RdfData rdfData = service.hotelsToRdf([hotel1, hotel2])

        assertNotNull('rdfData should not be null', rdfData)
        def hotelResource = rdfData.'http://hotels.example/hotel/42#it'
        assertEquals('http://purl.org/acco/ns#Hotel', hotelResource.type)
        assertEquals('Testhotel', hotelResource.'http://purl.org/goodrelations/v1#name')
        assertNull(hotelResource.'http://purl.org/acco/ns#numberOfRooms')
        assertNull(hotelResource.'http://xmlns.com/foaf/0.1/based_near')

        def hotelResource2 = rdfData.'http://hotels.example/hotel/21#it'
        assertEquals('http://purl.org/acco/ns#Hotel', hotelResource2.type)
        assertEquals('Testhotel 2', hotelResource2.'http://purl.org/goodrelations/v1#name')
        assertNull(hotelResource2.'http://purl.org/acco/ns#numberOfRooms')
        assertNull(hotelResource2.'http://xmlns.com/foaf/0.1/based_near')
    }
}
