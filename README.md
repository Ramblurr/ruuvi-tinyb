# ruuvi-tinyb

This is a small proof of concept showing how to read
[RuuviTags](https://ruuvi.com/ruuvitag-specs/) with Java. It uses the Intel
[tinyb](https://github.com/intel-iot-devkit/tinyb) library, which supports ARM
and x86 Linux platforms. No hcitool, hcidump in site.

### Caveats

Tinyb works fine on modern linux systems, but it is all but abandonware (
see [#168](https://github.com/intel-iot-devkit/tinyb/issues/168)).

The Linux kernel bluez driver suppresses multiple advertisements sent from the same BLE device. As of
this writing, this cannot be changed.  
See [this thread](https://marc.info/?l=linux-bluetooth&m=158225950522806&w=2) in the linux-bluetooth mailing list. - [dhalbert's comment](https://github.com/hbldh/bleak/issues/235#issue-648529883) on GitHub.

There is a workaround (see linked github issue), but it requires root privileges. 

## Usage

### Build TinyB

You'll need a build environment setup (gcc, cmake, glib2-devel, etc).

```
git clone https://github.com/intel-iot-devkit/tinyb.git
mkdir build 
cd build
cmake .. -DBUILDJAVA=ON
make
make install
```

This will install the native library into /usr/local/lib64 and the jar into
/usr/local/lib/java.

### Get ruuvitag-common

[ruuvitag-common](https://github.com/Scrin/ruuvitag-common-java) is a little
library that parses the binary payload emitted by the tags. You can get the jar
from [the releases
page](https://github.com/Scrin/ruuvitag-common-java/releases).

### Run the example

The example is no frills. No maven, no gradle. None of that. Just two jars.

Add them to your classpath with your IDE. Compile and run Main.


