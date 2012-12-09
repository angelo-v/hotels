import hotels.Hotel

class BootStrap {

    def init = { servletContext ->

        // Fiktive Beispieldaten

        // Berlin
        new Hotel(name: 'Hotel Fiktiva', numberOfRooms: 200, basedNear: 'http://sws.geonames.org/2950159/').save (failOnError: true)
        new Hotel(name: 'Hotel Imaginär', numberOfRooms: 120, basedNear: 'http://sws.geonames.org/2950159/').save (failOnError: true)

        // Hamburg
        new Hotel(name: 'Hotel Irrealis', numberOfRooms: 100, basedNear: 'http://sws.geonames.org/2911298/').save (failOnError: true)

        // München
        new Hotel(name: 'Pension Erf und En', numberOfRooms: 5, basedNear: 'http://sws.geonames.org/2867714/').save (failOnError: true)
        new Hotel(name: 'Gasthof aus Gedacht', numberOfRooms: 10, basedNear: 'http://sws.geonames.org/2867714/').save (failOnError: true)
    }

    def destroy = {
    }

}
