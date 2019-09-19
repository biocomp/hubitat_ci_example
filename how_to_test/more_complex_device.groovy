metadata {
    definition(name: "My more complex driver", namespace: "hubitat_ci_example", author: "biocomp") {
        capability "Actuator"
        capability "Button"
        capability "Switch"
    }

    preferences() {
        input "Input1", "text", title: "Val"
        input "tempOffset", "number", title: "Degrees", description: "Adjust temperature by this many degrees", range: "*..*", displayDuringSetup: false
    }
}

Map parse(String message) {}

void on()
{}

void off()
{}