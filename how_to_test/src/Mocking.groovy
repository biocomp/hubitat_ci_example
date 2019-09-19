package me.biocomp.hubitat_ci_example

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.app.preferences.Input
import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import spock.lang.Specification

/**
* Various ways of mocking APIs, settings, methods and inputs. 
*/
class Mocking extends Specification
{
    def "Mocking calls to Hubitat APIs"()
    {
        setup:
            AppExecutor executorApi = Mock{
                // Method will be called 1 time and will return 'Mocked UID'
                1*getHubUID() >> "Mocked UID" // Method mocked here
            }

            def script = new HubitatAppSandbox(new File("app_script.groovy"))
                    .run(api: executorApi)

        expect:
            // Calling method defined in the script
            script.myHubUIdMethod() == "Mocked UID"
    }

    def "Mocking calls to internal scripts methods "()
    {
        setup:
            def script = new HubitatAppSandbox(new File("app_script.groovy"))
                    .run(customizeScriptBeforeRun: {
                        script->
                            script.getMetaClass().scriptPrivateInternalMethod =
                                    { return "Mocked data!" } // Method mocked here
                    })

        expect:
            // Calling method defined in the script
            script.methodThatCallsPrivateMethod() == "Mocked data!"
    }

    def "Mocking app/device state"()
    {
        setup:
            // Defining state map
            def state = [val1:42, val2:"Some value2"] 

            // This is example for device
            DeviceExecutor executorApi = Mock{
                _*getState() >> state // State mocked here
            }

            def script = new HubitatDeviceSandbox(new File("device_script.groovy"))
                    .run(api: executorApi)

        expect:
            // Calling methods defined in the script
            script.readSomeState("val1") == 42
            script.readSomeState("val2") == "Some value2"
            script.readSomeState("invalid") == null
    }

    def "Mocking of inputs can also be done via 'userSettingValues' parameter for device"()
    {
        setup:
            // Providing 'userSettingValues', which is a map of input name to input value
            def script = new HubitatDeviceSandbox(new File("more_complex_device.groovy"))
                    .run(userSettingValues: [Input1: "Provided value", tempOffset: 42])

        expect:
            script.Input1 == "Provided value"
            script.tempOffset == 42
    }

    def "Mocking of inputs can also be done via 'userSettingValues' parameter for app"()
    {
        setup:
            final def sandbox = new HubitatAppSandbox(new File("app_with_pages.groovy"))

        when: "Using simple version of userSettingValues - same input values on every page"
            // Passing a map of input name to input value
            final def script = sandbox.run(userSettingValues: [inputOnPage1: "Provided value", inputOnPage2: 42])
            final def scriptPages = script.getProducedPreferences().allPages
        then:
            (scriptPages[0].sections[0].children[0] as Input).options.title == "Input on page1: inputOnPage1 = Provided value, inputOnPage2 = 42"
            (scriptPages[1].sections[0].children[0] as Input).options.title == "Input on page2: inputOnPage1 = Provided value, inputOnPage2 = 42"

        when: "Using per page version of userSettingValues - customized input values on every page"
            // Here, inputOnPage1 is still same for all pages
            // inputOnPage2 is given a map, where '_' key is applied to all pages, and then if page name is specified, that value is only applied to that page.
            // Here, page1 will get default value 42, and page2 will get custom value 45
            final def script2 = sandbox.run(userSettingValues: [inputOnPage1: "Provided value", inputOnPage2: [_: 42, page2: 45]])
            final def script2Pages = script2.getProducedPreferences().allPages

        then:
            (script2Pages[0].sections[0].children[0] as Input).options.title == "Input on page1: inputOnPage1 = Provided value, inputOnPage2 = 42"
            (script2Pages[1].sections[0].children[0] as Input).options.title == "Input on page2: inputOnPage1 = Provided value, inputOnPage2 = 45" // Note 45
    }
}