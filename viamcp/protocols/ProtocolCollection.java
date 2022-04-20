package viamcp.protocols;

import com.viaversion.viaversion.api.protocol.version.*;

public enum ProtocolCollection
{
    R1_14_1("R1_14_1", 13, new ProtocolVersion(480, "1.14.1")), 
    R1_9_1("R1_9_1", 26, new ProtocolVersion(108, "1.9.1")), 
    R1_9_4("R1_9_4", 24, new ProtocolVersion(110, "1.9.3-1.9.4")), 
    R1_11_1("R1_11_1", 21, new ProtocolVersion(316, "1.11.1-1.11.2")), 
    R1_17_1("R1_17_1", 0, new ProtocolVersion(756, "1.17.1")), 
    R1_16("R1_16", 6, new ProtocolVersion(735, "1.16")), 
    R1_9("R1_9", 27, new ProtocolVersion(107, "1.9")), 
    R1_12_2("R1_12_2", 18, new ProtocolVersion(340, "1.12.2")), 
    R1_16_1("R1_16_1", 5, new ProtocolVersion(736, "1.16.1")), 
    R1_16_5("R1_16_5", 2, new ProtocolVersion(754, "1.16.4-1.16.5")), 
    R1_17("R1_17", 1, new ProtocolVersion(755, "1.17")), 
    R1_15("R1_15", 9, new ProtocolVersion(573, "1.15")), 
    R1_16_2("R1_16_2", 4, new ProtocolVersion(751, "1.16.2")), 
    R1_14_2("R1_14_2", 12, new ProtocolVersion(485, "1.14.2")), 
    R1_15_2("R1_15_2", 7, new ProtocolVersion(578, "1.15.2")), 
    R1_14("R1_14", 14, new ProtocolVersion(477, "1.14")), 
    R1_16_3("R1_16_3", 3, new ProtocolVersion(753, "1.16.3")), 
    R1_7("R1_7", 30, new ProtocolVersion(4, -1, "1.7-1.7.5 (May Not Work)", new VersionRange("1.7", 0, 5))), 
    R1_12_1("R1_12_1", 19, new ProtocolVersion(338, "1.12.1")), 
    R1_13_1("R1_13_1", 16, new ProtocolVersion(401, "1.13.1")), 
    R1_12("R1_12", 20, new ProtocolVersion(335, "1.12")), 
    R1_14_4("R1_14_4", 10, new ProtocolVersion(498, "1.14.4")), 
    R1_9_2("R1_9_2", 25, new ProtocolVersion(109, "1.9.2"));
    
    private static final ProtocolCollection[] $VALUES;
    
    R1_8("R1_8", 28, new ProtocolVersion(47, "1.8.x")), 
    R1_7_6("R1_7_6", 29, new ProtocolVersion(5, -1, "1.7.6-1.7.10 (May Not Work)", new VersionRange("1.7", 6, 10))), 
    R1_13_2("R1_13_2", 15, new ProtocolVersion(404, "1.13.2")), 
    R1_13("R1_13", 17, new ProtocolVersion(393, "1.13")), 
    R1_14_3("R1_14_3", 11, new ProtocolVersion(490, "1.14.3")), 
    R1_11("R1_11", 22, new ProtocolVersion(315, "1.11"));
    
    private ProtocolVersion version;
    
    R1_10("R1_10", 23, new ProtocolVersion(210, "1.10.x")), 
    R1_15_1("R1_15_1", 8, new ProtocolVersion(575, "1.15.1"));
    
    public static ProtocolVersion getProtocolById(final int n) {
        final ProtocolCollection[] values = values();
        while (0 < values.length) {
            final ProtocolCollection collection = values[0];
            if (collection.getVersion().getVersion() == n) {
                return collection.getVersion();
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    private ProtocolCollection(final String s, final int n, final ProtocolVersion version) {
        this.version = version;
    }
    
    static {
        $VALUES = new ProtocolCollection[] { ProtocolCollection.R1_17_1, ProtocolCollection.R1_17, ProtocolCollection.R1_16_5, ProtocolCollection.R1_16_3, ProtocolCollection.R1_16_2, ProtocolCollection.R1_16_1, ProtocolCollection.R1_16, ProtocolCollection.R1_15_2, ProtocolCollection.R1_15_1, ProtocolCollection.R1_15, ProtocolCollection.R1_14_4, ProtocolCollection.R1_14_3, ProtocolCollection.R1_14_2, ProtocolCollection.R1_14_1, ProtocolCollection.R1_14, ProtocolCollection.R1_13_2, ProtocolCollection.R1_13_1, ProtocolCollection.R1_13, ProtocolCollection.R1_12_2, ProtocolCollection.R1_12_1, ProtocolCollection.R1_12, ProtocolCollection.R1_11_1, ProtocolCollection.R1_11, ProtocolCollection.R1_10, ProtocolCollection.R1_9_4, ProtocolCollection.R1_9_2, ProtocolCollection.R1_9_1, ProtocolCollection.R1_9, ProtocolCollection.R1_8, ProtocolCollection.R1_7_6, ProtocolCollection.R1_7 };
    }
    
    public ProtocolVersion getVersion() {
        return this.version;
    }
}
