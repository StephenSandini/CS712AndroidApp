package com.example.greetingcard

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainToSecondUiAutomatorTest {
    private lateinit var device: UiDevice

    private val appLabel = "CS712AndroidApp"

    private val startExplicitButtonText = "Start Activity Explicitly"

    private val expectedChallengeLines = listOf(
        "* Device Fragmentation",
        "* OS Fragmentation",
        "* Unstable and Dynamic Environment",
        "* Rapid Changes",
        "* Tool Support",
        "* Low Tolerance",
        "* Low Security and Privacy Awareness"
    )

    @Before
    fun setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun launchFromHome_clickExplicit_verifySecondActivityShowsChallenge() {
        //Launch App From Home
        device.pressHome()

        val launchPkg = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launchPkg).depth(0)),5_000)

        device.swipe(
            device.displayWidth / 2,
            device.displayHeight - 10,
            device.displayWidth / 2,
            device.displayHeight / 4,
            15
        )
        device.waitForIdle()


        //Tap Launcher Icon
        val icon = device.findObject(UiSelector().textContains(appLabel))
        assertTrue("Could not find app icon with label containing: $appLabel",icon.exists())
        icon.clickAndWaitForNewWindow()

        //Deal with Allow Notifications Prompt that started showing up in the testing
        handleNotificationPermission()

        //Click Button Explicit Intent
        val startBtn = device.wait(Until.findObject(By.text(startExplicitButtonText)), 5_000)
        assertTrue("Could not find button: $startExplicitButtonText", startBtn!= null)
        startBtn!!.click()

        //Verify Second Activity Screen
        val header = device.wait(Until.findObject(By.text("Mobile Software Engineering Challenges")),5_000)
        assertTrue("SecondActivity header not found.",header != null)

        val foundAny = expectedChallengeLines.any { line -> device.hasObject(By.text(line))}
        assertTrue("No expected challenge bullet found. Checked: $expectedChallengeLines", foundAny)


    }

    private fun handleNotificationPermission(timeoutMs: Long=4_000) {
        device.waitForIdle()

        val allowResIds = listOf(
            "com.android.permissioncontroller:id/permission_allow_button",
            "com.android.permissioncontroller:id/permission_allow_foreground_only_button",
            "com.android.permissioncontroller:id/permission_allow_one_time_button"
        )

        for(rid in allowResIds) {
            val btn = device.wait(Until.findObject(By.res(rid)),timeoutMs)
            if(btn != null) {
                btn.click()
                device.waitForIdle()
                return
            }
        }

        val allowByText = device.wait(
            Until.findObject(By.textContains("(?i)allow|allow notifications")),
            timeoutMs
        )

        if(allowByText != null){
            allowByText.click()
            device.waitForIdle()
            return
        }
    }

}