# Examples of [hubitat_ci](https://github.com/biocomp/hubitat_ci) usage
[![Build Status](https://biocomp.visualstudio.com/HubitatCiRelease/_apis/build/status/hubitat_ci_example%20build?branchName=master)](https://biocomp.visualstudio.com/HubitatCiRelease/_build/latest?definitionId=12&branchName=master)

# Contents
[Minimal](minimal) - this folder has minimal [build file](minimal/build.gradle) that runs a couple of [tests](minimal/src/test.groovy) against dummy [app script](minimal/appscript.groovy).

[HowToTestDevice.groovy](how_to_test/src/HowToTestDevice.groovy) - bare minimum test for a device driver.

[HowToTestDeviceWithInlineScript.groovy](how_to_test/src/HowToTestDeviceWithInlineScript.groovy) - script can be provided as a string in the test, as opposed to loading from file.

[Mocking.groovy](how_to_test/src/Mocking.groovy) - various examples of mocking APIs, script functions, user inputs and so on.

[UsingCapturingLog.groovy](how_to_test/src/UsingCapturingLog.groovy) - there's a helper log class that can be used instead of mock.

[DisablingValidations.groovy](how_to_test/src/DisablingValidations.groovy) - most of validations can be disabled.

[ValidatingInputsAndProperties.groovy](how_to_test/src/ValidatingInputsAndProperties.groovy) - most of information generated during script setup can be read back.
