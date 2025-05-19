package com.sbdemo

import grails.converters.JSON

class PersonController {

    def save(Person person) {
        if (person.hasErrors()) {
            render status: 400, text: "Binding failed"
            return
        }

        render([bound: [name: person.name, role: person.role]] as JSON)
    }
}
