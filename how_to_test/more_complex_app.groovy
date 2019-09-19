definition(
        name: "ComplexApp",
        namespace: "hubitat_ci_example",
        author: "Artem Tokmakov",
        description: "A little more complex app",
        iconUrl: "",
        iconX2Url: "",
        iconX3Url: ""
)

preferences {
    page name: "main"
    page name: "page2"
    page(name: "page3", title: "Page 3 title") {
        section {
            input "in31", "time", title: "Input1, page 3", required: false
        }
    }
}

def main() {
    def pageProperties = [
        name: "main",
        title: "Main page",
        install: true,
        nextPage: "page2",
        uninstall: true
    ]

    def helpPage = "Some huge help text"

    return dynamicPage(pageProperties) {
        section("About This App") {
            paragraph helpPage
        }

        section("Menu") {
            href "page2", title: "Reference to page2", description: "Tap to see page 2", params: [refresh: true]
            href "page3", title: "Reference to page3", description: "Tap to see page 3"
        }
    }
}

def page2() {
    def pageProperties = [
            name: "page2",
            title: "Page 2",
            install: false,
            uninstall: false
    ]

    return dynamicPage(pageProperties) {
        section("Timeout") {
            input "timer", "number", title: "A timeout", required: false
        }

        section("Times to check") {
            input "checkFrequency", "enum", title: "Interval to check", required: false, options: [
                    [1: "5 Minutes"],
                    [2: "1 Hour"],
                    [3: "3 Hours"]
            ]
        }
    }
}


mappings{
    path('/p') {
        action: [
                'GET': 'handlerForGet',
                'PUT': 'handlerForPut'
        ]
    }
    path('/p/null') {
        action: [
                'PUT': 'handlerForPut2'
        ]
    }
}

def handlerForGet()
{
    return "handlerForGet called with params = '${params}', request = '${request}'"
}
def handlerForPut()
{}

def handlerForPut2()
{
    return "handlerForPut2 called with params = '${params}', request = '${request}'"
}