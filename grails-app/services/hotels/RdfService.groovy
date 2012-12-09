package hotels

import de.datenwissen.util.groovyrdf.core.RdfData
import de.datenwissen.util.groovyrdf.jena.JenaRdfBuilder

class RdfService {

    def grailsApplication

    /**
     * @param hotel The hotel instance to describe via RDF
     * @return RDF data describing the given hotel
     */
    RdfData hotelToRdf (Hotel hotel) {
        new JenaRdfBuilder ().build {
            "${grailsApplication.config.grails.serverURL}/hotel/${hotel.id}#it" {
                a 'http://purl.org/acco/ns#Hotel'
                'http://purl.org/goodrelations/v1#name' hotel.name
                'http://purl.org/acco/ns#numberOfRooms' hotel.numberOfRooms
                'http://xmlns.com/foaf/0.1/based_near' {
                    "$hotel.basedNear" {}
                }
            }
        }
    }

    RdfData hotelsToRdf (List<Hotel> hotels) {
        new JenaRdfBuilder ().build {
            hotels.each { Hotel hotel ->
                "${grailsApplication.config.grails.serverURL}/hotel/${hotel.id}#it" {
                    a 'http://purl.org/acco/ns#Hotel'
                    'http://purl.org/goodrelations/v1#name' hotel.name
                }
            }
        }
    }
}
