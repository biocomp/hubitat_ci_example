package me.biocomp.hubitat_ci_example

import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.Log
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import spock.lang.Specification

class Test extends
        Specification
{
    HubitatAppSandbox sandbox = new HubitatAppSandbox(new File("appscript.groovy"))

    def "Basic validation"() {
        expect:
            sandbox.run() // Runs and validates definition() and preferences().
    }

    def "installed() logs 'Num'"() {
        setup:
            // Create mock log
            def log = Mock(Log)

            // Make AppExecutor return the mock log
            AppExecutor api = Mock{ _ * getLog() >> log }

            // Parse, construct script object, run validations
            def script = sandbox.run(api: api, userSettingValues: [Num: 42])

        when:
            // Run installed() method on script object.
            script.installed()

        then:
            // Expect that log.info() was called with this string
            1 * log.info("Installed with number = 42")
    }
}