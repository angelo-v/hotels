class UrlMappings {

    static mappings = {


        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/hotel/$id" (resource: "hotel")

        "/" (controller: 'hotel', action: 'list')
        "500" (view: '/error')
    }
}
