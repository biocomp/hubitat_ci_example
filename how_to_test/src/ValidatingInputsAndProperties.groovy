package me.biocomp.hubitat_ci_example

import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.app.preferences.HRef
import me.biocomp.hubitat_ci.app.preferences.Input
import me.biocomp.hubitat_ci.app.preferences.Paragraph
import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import spock.lang.Specification

class ValidatingInputsAndProperties extends Specification
{
    def "You can validate data generated during app script's setup"()
    {
        given:
            final def script = new HubitatAppSandbox(new File("more_complex_app.groovy")).run()

        expect:
            // Check definition
            script.getProducedDefinition().description == "A little more complex app"
            script.getProducedDefinition().name == "ComplexApp"

            // Call mapped actions
            script.getProducedMappings()['/p'].actions.GET(123, "I'm a request") == "handlerForGet called with params = '123', request = 'I'm a request'"
            script.getProducedMappings()['/p/null'].actions.PUT("I'm params", "Another request") == "handlerForPut2 called with params = 'I'm params', request = 'Another request'"

            // Check preferences
            // Using readName() which will return name regardless of whether it was passed separately or inside 'options'
            script.getProducedPreferences().pages[0].readName() == "page3" // The only non-dynamic page
            script.getProducedPreferences().dynamicPages[0].readName() == "main"
            script.getProducedPreferences().dynamicPages[1].readName() == "page2"


            // All pages in order of definition:
            script.getProducedPreferences().allPages[0].readName() == "main"
            script.getProducedPreferences().allPages[1].readName() == "page2"
            script.getProducedPreferences().allPages[2].readName() == "page3"


            // main page, section "About this app"
            script.getProducedPreferences().allPages[0].sections[0].title == "About This App"
            (script.getProducedPreferences().allPages[0].sections[0].children[0] as Paragraph).text == "Some huge help text"

            // main page, section "Menu"
            script.getProducedPreferences().allPages[0].sections[1].title == "Menu"

            // Note: nextPageName will be null, if it was put into 'options'. Then it would need to be read through options.
            (script.getProducedPreferences().allPages[0].sections[1].children[0] as HRef).nextPageName == "page2"
            (script.getProducedPreferences().allPages[0].sections[1].children[0] as HRef).options.description == "Tap to see page 2"
            (script.getProducedPreferences().allPages[0].sections[1].children[0] as HRef).options.params == [refresh: true]


            // page2, section "Timeout"
            script.getProducedPreferences().allPages[1].sections[0].title == "Timeout"

            // readName() and readType() will always return name and type whether or not they were put into a map or provided separately
            (script.getProducedPreferences().allPages[1].sections[0].children[0] as Input).readName() == "timer"
            (script.getProducedPreferences().allPages[1].sections[0].children[0] as Input).readType() == "number"
            (script.getProducedPreferences().allPages[1].sections[0].children[0] as Input).options.title == "A timeout"

            // page2, section "Times to check"
            script.getProducedPreferences().allPages[1].sections[1].title == "Times to check"
            (script.getProducedPreferences().allPages[1].sections[1].children[0] as Input).readName() == "checkFrequency"
            (script.getProducedPreferences().allPages[1].sections[1].children[0] as Input).options.required == false
            (script.getProducedPreferences().allPages[1].sections[1].children[0] as Input).options.options == [[1: "5 Minutes"],
                                                                                                               [2: "1 Hour"],
                                                                                                               [3: "3 Hours"]]

            // page3, unnamed section
            (script.getProducedPreferences().allPages[2].sections[0].children[0] as Input).readName() == "in31"
    }

    def "You can validate data generated during device script's setup"()
    {
        given:
            final def script = new HubitatDeviceSandbox(new File("more_complex_device.groovy")).run()

        expect:
            script.getProducedPreferences()[0].readName() == "Input1"
            script.getProducedPreferences()[0].readType() == "text"

            script.getProducedPreferences()[1].readName() == "tempOffset"
            script.getProducedPreferences()[1].readType() == "number"
            script.getProducedPreferences()[1].options == [title: "Degrees", description: "Adjust temperature by this many degrees", range: "*..*", displayDuringSetup: false]

            script.getProducedDefinition().options.name == "My more complex driver"
            script.getProducedDefinition().options.namespace == "hubitat_ci_example"

            script.getProducedDefinition().capabilities == ["Actuator", "Button", "Switch"]
    }
}