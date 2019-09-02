metadata {
    definition(name: "Min Driver", namespace: "hubitat_ci", author: "biocomp") {
        capability "Actuator"
    }

    preferences() {
        input "Input", "number", title: "Val"
    }
}

def parse() {}

def readSomeState(def name)
{
    return state."${name}"
}