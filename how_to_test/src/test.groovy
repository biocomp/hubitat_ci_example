
// ###[DOC START]how_to_test.md|## Testing an app|This happens when `sandbox.run()` method is executed.
package me.biocomp.hubitat_ci_example

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification

class HowToTestDevice extends
        Specification
{
    HubitatDeviceSandbox sandbox = new HubitatDeviceSandbox(new File("device_script.groovy"))

    def "Basic validation"() {
        expect:
            sandbox.run() // Runs and validates definition() and preferences().
    }
}

class HowToTestDeviceWithInlineScript extends
        Specification
{
    def "You can have script code inside the test"() {
        setup:
            HubitatDeviceSandbox sandbox = new HubitatDeviceSandbox("""
metadata {
    definition(name: "Min Driver", namespace: "hubitat_ci", author: "biocomp") {
        capability "Actuator"
    }

    preferences() {
        input "Input", "number", title: "Val"
    }
}

def parse() {}
            """)

        expect:
            sandbox.run()
    }
}

class Mocking extends Specification
{
    def "Mocking calls to Hubitat APIs"()
    {
        setup:
            AppExecutor executorApi = Mock{
                1*getHubUID() >> "Mocked UID" // Method mocked here
            }

            def script = new HubitatAppSandbox(new File("app_script.groovy"))
                .run(api: executorApi)

        expect:
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
            script.methodThatCallsPrivateMethod() == "Mocked data!"
    }
}

class DisablingValidations extends Specification
{
    def "You can disable most built-in validations"()
    {
        expect:
            new HubitatAppSandbox(new File("bad_app_script.groovy"))
                    .run(validationFlags:
                            [Flags.AllowNullEnumInputOptions, // Enum should have 'options', but doesn't in this case
                             Flags.AllowNullListOptions, // Same reason - for enum
                             Flags.DontValidateDefinition]) // definition() is completely omitted in this script
    }

    def "Don't run init at all"()
    {
        expect:
            new HubitatAppSandbox(new File("bad_app_script.groovy"))
                    .run(validationFlags: [Flags.DontRunScript])
    }
}