package me.biocomp.hubitat_ci_example

import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import spock.lang.Specification

class HowToTestDevice extends
        Specification
{
    // Creating a sandbox object for device script from file.
    // At this point, script object is not created.
    HubitatDeviceSandbox sandbox = new HubitatDeviceSandbox(new File("device_script.groovy"))

    def "Basic validation"() {
        expect:
            // Compile, construct script object, and initialize metadata
            sandbox.run() 
    }
}