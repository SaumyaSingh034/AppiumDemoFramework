package base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class DriverManagerEmulator {

    public static AndroidDriver driver;
    private static AppiumDriverLocalService service;
    public static String emulator =  "Pixel_8a";

    private static final String ANDROID_HOME = "C:\\Users\\sausingh31\\AppData\\Local\\Android\\Sdk";

    public static AndroidDriver initializeDriver() throws IOException, URISyntaxException, InterruptedException {

        if (driver == null) {

            restartADB();

            String emulatorName = getFirstAVD();
            if (emulatorName == null) {
                throw new RuntimeException("No AVD found. Please create an Android Virtual Device.");
            }

            // Start emulator
            startEmulator();

           // waitForDevice();

            //  Start Appium server
            service = new AppiumServiceBuilder()
                    .withAppiumJS(new File("C:\\Users\\sausingh31\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                    .withIPAddress("127.0.0.1")
                    .usingPort(4723)
                    .build();
            service.start();

            // 6️⃣ Start driver
            UiAutomator2Options options = new UiAutomator2Options();
            options.setDeviceName(emulator);
            options.setAutomationName("UiAutomator2");
            options.setApp("C:\\Users\\sausingh31\\IdeaProjects\\AppiumDemoFramework\\src\\test\\java\\resources\\ApiDemos-debug.apk");

            driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);
        }

        return driver;
    }

    private static void restartADB() {
        try {
            String adbPath = ANDROID_HOME + "\\platform-tools\\adb.exe";
            new ProcessBuilder(adbPath, "kill-server").start().waitFor();
            new ProcessBuilder(adbPath, "start-server").start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getFirstAVD() {
        try {
            String emulatorPath = ANDROID_HOME + "\\emulator\\emulator.exe";
            Process p = new ProcessBuilder(emulatorPath, "-list-avds").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            List<String> avds = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    avds.add(line.trim());
                }
            }
            p.waitFor();
            if (avds.size() > 0) {
                System.out.println("First available emulator: " + avds.get(0));
                return avds.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void startEmulator() throws IOException, InterruptedException {
        String emulatorPath = ANDROID_HOME + "\\emulator\\emulator.exe";

        ProcessBuilder pb = new ProcessBuilder(
                emulatorPath,
                "-avd", emulator,
                "-no-audio"
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();


        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[EMULATOR] " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        long startTime = System.currentTimeMillis();
        long timeout = 120000;
        boolean booted = false;
        String adbPath = ANDROID_HOME + "\\platform-tools\\adb.exe";

        while (!booted && System.currentTimeMillis() - startTime < timeout) {
            Process bootProc = new ProcessBuilder(adbPath, "shell", "getprop", "sys.boot_completed").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(bootProc.getInputStream()));
            String bootStatus = reader.readLine();
            if ("1".equals(bootStatus)) {
                booted = true;
            } else {
                Thread.sleep(1000); // wait 1 sec before next check
            }
        }

        if (!booted) {
            throw new RuntimeException("Emulator did not boot in 2 minutes.");
        }

        System.out.println("Emulator is online!");
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        if (service != null) {
            service.stop();
        }

        // Kill emulator
        try {
            String adbPath = ANDROID_HOME + "\\platform-tools\\adb.exe";
            ProcessBuilder pb = new ProcessBuilder(adbPath, "emu", "kill");
            pb.start();
            System.out.println("Emulator shutdown command sent.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
