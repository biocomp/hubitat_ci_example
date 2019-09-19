package me.biocomp.hubitat_ci_example

import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import spock.lang.Specification


class HowToTestDeviceWithInlineScript extends
        Specification
{
    def "You can have script code inside the test"() {
        setup:
            // Creating sandbox object, but instead of loading script from a file, 
            // contents are directly provided.
            HubitatDeviceSandbox sandbox = new HubitatDeviceSandbox("""
metadata {
    definition(name: "Min Driver", namespace: "hubitat_ci", author: "biocomp") {
        capability "Actuator"
    }

    preferences() {
        input "Input", "number", title: "Val"
    }
}

Map parse(String s) {}
            """)

        expect:
            // Compile, construct script object, and initialize metadata
            sandbox.run()
    }
}