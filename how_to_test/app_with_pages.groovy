definition(
        name: "AppWithPages",
        namespace: "hubitat_ci_example",
        author: "Artem Tokmakov",
        description: "A couple of pages that use inputs",
        iconUrl: "",
        iconX2Url: "",
        iconX3Url: ""
)

preferences {
    page name: "main"
    page name: "page2"
}

def main() {
    return dynamicPage(name: "main", title: "Main page", install: true, nextPage: "page2") {
        section {
            input "inputOnPage1", "text", title: "Input on page1: inputOnPage1 = ${inputOnPage1}, inputOnPage2 = ${inputOnPage2}", required: false
        }
    }
}

def page2() {
    return dynamicPage(name: "page2", title: "Page 2", install: true) {
        section {
            input "inputOnPage2", "number", title: "Input on page2: inputOnPage1 = ${inputOnPage1}, inputOnPage2 = ${inputOnPage2}", required: false
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