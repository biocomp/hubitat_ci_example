package me.biocomp.hubitat_ci_example

import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import me.biocomp.hubitat_ci.util.CapturingLog
import spock.lang.Specification

class UsingCapturingLog extends Specification
{
    def "There's a helper CapturingLog class that can be used"()
    {
        given:
            // CapturingLog used here
            final def log = new CapturingLog()
            final def api = Mock(DeviceExecutor){
                _*getLog() >> log
            }

            final def script = new HubitatDeviceSandbox(new File("device_script.groovy")).run(api: api)

        when:
            script.logSomething()

        then:
            log.records[0] == new Tuple(CapturingLog.Level.info, "Logging some info")
            log.records[1] == new Tuple(CapturingLog.Level.error, "That's an error!")
    }
}
