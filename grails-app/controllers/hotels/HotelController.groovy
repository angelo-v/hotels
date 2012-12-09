package hotels

import grails.converters.JSON
import de.datenwissen.util.groovyrdf.core.RdfDataFormat
import de.datenwissen.util.groovyrdf.core.RdfData

class HotelController {

    RdfService rdfService

    def show (long id) {
        def hotel = Hotel.get (id)
        if (!hotel) {
            render (status: 404)
            return
        }
        withFormat {
            ttl {
                RdfData rdfData = rdfService.hotelToRdf (hotel)
                renderTurtle (rdfData)
            }
            json {
                render hotel as JSON
            }
        }

    }

    def list () {
        // TODO test
        def hotels = Hotel.list()
        withFormat {
            ttl {
                RdfData rdfData = rdfService.hotelsToRdf (hotels)
                renderTurtle (rdfData)
            }
            json {
                render hotels as JSON
            }

        }
    }

    private void renderTurtle (RdfData rdfData) {
        StringWriter out = new StringWriter ()
        rdfData?.write (out, RdfDataFormat.TURTLE)
        render (contentType: 'text/turtle', text: out.toString ())
    }

}
