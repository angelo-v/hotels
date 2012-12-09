package hotels



import grails.test.mixin.*
import grails.test.GrailsMock
import de.datenwissen.util.groovyrdf.core.RdfData
import de.datenwissen.util.groovyrdf.jena.JenaRdfData
import de.datenwissen.util.groovyrdf.jena.JenaRdfBuilder

@TestFor (HotelController)
@Mock (Hotel)
class HotelControllerTests {

    void testReturn404IfHotelDoesNotExist () {
        controller.show (5)
        assertEquals (response.status, 404)
    }

    void testRenderHotelAsJson () {
        def hotel = new Hotel (name:  'Testhotel', numberOfRooms: 42, basedNear: 'http://example.org/city').save(validate: false)

        controller.show (hotel.id)

        def json = response.json
        assertEquals (response.status, 200)
        assertNotNull(json)
        assertEquals ('Testhotel', json.name)
        assertEquals (42, json.numberOfRooms)
        assertEquals ('http://example.org/city', json.basedNear)
    }

    void testRenderHotelAsTurtle () {
        def hotel = new Hotel (name:  'Testhotel', numberOfRooms: 42, basedNear: 'http://example.org/city').save(validate: false)
        response.format = 'ttl'
        def grailsMock = mockHotelToRdf ()

        controller.show (hotel.id)

        assertEquals (response.status, 200)
        assertEquals ('text/turtle;charset=utf-8', response.contentType)
        assertEquals('<http://subject>\n      <http://predicate> "object" .\n', response.contentAsString)
        grailsMock.verify()
    }

    private GrailsMock mockHotelToRdf () {

        RdfData mockedRdfData = new JenaRdfBuilder().build {
            'http://subject' {
                'http://predicate' 'object'
            }
        }

        def grailsMock = mockFor (RdfService)
        grailsMock.demand.hotelToRdf (1..1) {Hotel h ->
            return mockedRdfData
        }
        controller.rdfService = (RdfService) grailsMock.createMock ()
        return grailsMock
    }
}
