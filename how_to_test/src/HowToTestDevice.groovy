package me.biocomp.hubitat_ci_example

import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import spock.lang.Specification

class HowToTestDevice extends
        Specification
{
    // Creating a sandbox object for device script from file.
    // At this point, script object is not created.
    // Using Hubitat**Device**Sandbox for app scripts.
    HubitatDeviceSandbox sandbox = new HubitatDeviceSandbox(new File("device_script.groovy"))

    def "Basic validation"() {
        expect:
            // Compile, construct script object, and initialize metadata
            final def script = sandbox.run()

            // Call method defined in the script
            script.parse("Message") == [source: "Message", result: 42]
    }
}