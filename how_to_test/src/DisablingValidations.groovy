package me.biocomp.hubitat_ci_example

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import me.biocomp.hubitat_ci.util.CapturingLog
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Specification

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