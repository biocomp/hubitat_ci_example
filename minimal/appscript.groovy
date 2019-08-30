definition(
    name: "MyApp",
    namespace: "hubitat_ci_example",
    author: "Artem Tokmakov",
    description: "Minimal app",
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