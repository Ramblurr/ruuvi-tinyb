package outskirtslabs;

import fi.tkgwf.ruuvi.common.bean.RuuviMeasurement;
import fi.tkgwf.ruuvi.common.parser.impl.AnyDataFormatParser;
import tinyb.BluetoothDevice;
import tinyb.BluetoothManager;

import java.util.List;
import java.util.Map;

public final class Main {
    static boolean running = true;
    private final AnyDataFormatParser parser = new AnyDataFormatParser();

    private RuuviMeasurement parseMeasurement(BluetoothDevice device) {
        Map<Short, byte[]> manufacturerData = device.getManufacturerData();
        for (Map.Entry<Short, byte[]> entry : manufacturerData.entrySet()) {
            byte[] data = new byte[entry.getValue().length + 2];
            data[0] = (byte) (entry.getKey() & 0xFF);
            data[1] = (byte) (entry.getKey() >>> 8);

            System.arraycopy(entry.getValue(), 0, data, 2, entry.getValue().length);
            return parser.parse(data);
        }
        return null;
    }

    private void run() throws InterruptedException {
        BluetoothManager manager = BluetoothManager.getBluetoothManager();
        boolean discoveryStarted = manager.startDiscovery();
        if (!discoveryStarted)
            throw new RuntimeException("could not start discovery");


        while (running) {
            List<BluetoothDevice> devices = manager.getDevices();
            for (BluetoothDevice device : devices) {
                RuuviMeasurement result = parseMeasurement(device);
                if (result != null)
                    System.out.println(device.getAddress() + " " + result);
            }
            Thread.sleep(1000);
        }
    }

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

}
