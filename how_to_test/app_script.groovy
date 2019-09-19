definition(
    name: "MyApp",
    namespace: "hubitat_ci_example",
    author: "Artem Tokmakov",
    description: "Simple app",
    iconUrl: "",
    iconX2Url: "",
    iconX3Url: ""
)

preferences{
    section{
        input(
            name: "Num", 
            type: "number", 
            title: "Title",
            description: "Just an input",
            required: true)
    }
}

def installed()
{
    log.info("Installed with number = ${Num}")
}

def myHubUIdMethod()
{
    return getHubUID()
}

private def scriptPrivateInternalMethod()
{
    return "Some real data"
}

def methodThatCallsPrivateMethod()
{
    return scriptPrivateInternalMethod()
}