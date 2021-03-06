metadata {
    definition(name: "Min Driver", namespace: "hubitat_ci", author: "biocomp") {
        capability "Actuator"
    }

    preferences() {
        input "Input", "number", title: "Val"
    }
}

Map parse(String message) { return [source: message, result: 42] }

def readSomeState(def name)
{
    return state."${name}"
}

def logSomething()
{
    log.info("Logging some info")
    log.error("That's an error!")
}