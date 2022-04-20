package net.minecraft.client.stream;

import org.apache.logging.log4j.*;
import tv.twitch.*;
import java.util.*;
import tv.twitch.chat.*;
import com.google.common.collect.*;

public class ChatController
{
    protected AuthToken field_153012_j;
    protected String field_153006_d;
    private static final Logger LOGGER;
    protected HashMap field_175998_i;
    protected ChatEmoticonData field_175996_m;
    protected ChatState field_153011_i;
    protected EnumEmoticonMode field_175997_k;
    protected String field_153007_e;
    protected int field_153015_m;
    protected String field_153004_b;
    protected ChatListener field_153003_a;
    protected Chat field_153008_f;
    protected IChatAPIListener field_175999_p;
    protected EnumEmoticonMode field_175995_l;
    protected Core field_175992_e;
    protected int field_175993_n;
    protected int field_175994_o;
    
    public void func_152990_a(final ChatListener field_153003_a) {
        this.field_153003_a = field_153003_a;
    }
    
    public void func_152994_a(final AuthToken field_153012_j) {
        this.field_153012_j = field_153012_j;
    }
    
    protected void func_152988_s() {
        if (this.field_175996_m == null) {
            this.field_175996_m = new ChatEmoticonData();
            final ErrorCode emoticonData = this.field_153008_f.getEmoticonData(this.field_175996_m);
            if (ErrorCode.succeeded(emoticonData)) {
                if (this.field_153003_a != null) {
                    this.field_153003_a.func_176021_d();
                }
            }
            else {
                this.func_152995_h("Error preparing emoticon data: " + ErrorCode.getString(emoticonData));
            }
        }
    }
    
    public boolean func_152986_d(final String s) {
        return this.func_175987_a(s, false);
    }
    
    public void func_175988_p() {
        if (this.func_153000_j() != ChatState.Uninitialized) {
            this.func_152993_m();
            if (this.func_153000_j() == ChatState.ShuttingDown) {
                while (this.func_153000_j() != ChatState.Uninitialized) {
                    Thread.sleep(200L);
                    this.func_152997_n();
                }
            }
        }
    }
    
    public EnumChannelState func_175989_e(final String s) {
        if (!this.field_175998_i.containsKey(s)) {
            return EnumChannelState.Disconnected;
        }
        return this.field_175998_i.get(s).func_176040_a();
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
    
    public void func_152997_n() {
        if (this.field_153011_i != ChatState.Uninitialized) {
            final ErrorCode flushEvents = this.field_153008_f.flushEvents();
            if (ErrorCode.failed(flushEvents)) {
                this.func_152995_h(String.format("Error flushing chat events: %s", ErrorCode.getString(flushEvents)));
            }
        }
    }
    
    public void func_152984_a(final String field_153006_d) {
        this.field_153006_d = field_153006_d;
    }
    
    protected void func_152995_h(final String s) {
        ChatController.LOGGER.error(TwitchStream.STREAM_MARKER, "[Chat controller] {}", new Object[] { s });
    }
    
    protected void func_152996_t() {
        if (this.field_175996_m != null) {
            final ErrorCode clearEmoticonData = this.field_153008_f.clearEmoticonData();
            if (ErrorCode.succeeded(clearEmoticonData)) {
                this.field_175996_m = null;
                if (this.field_153003_a != null) {
                    this.field_153003_a.func_176024_e();
                }
            }
            else {
                this.func_152995_h("Error clearing emoticon data: " + ErrorCode.getString(clearEmoticonData));
            }
        }
    }
    
    protected void func_175985_a(final ChatState field_153011_i) {
        if (field_153011_i != this.field_153011_i) {
            this.field_153011_i = field_153011_i;
            if (this.field_153003_a != null) {
                this.field_153003_a.func_176017_a(field_153011_i);
            }
        }
    }
    
    public void func_152998_c(final String field_153004_b) {
        this.field_153004_b = field_153004_b;
    }
    
    public boolean func_175991_l(final String s) {
        if (this.field_153011_i != ChatState.Initialized) {
            return false;
        }
        if (!this.field_175998_i.containsKey(s)) {
            this.func_152995_h("Not in channel: " + s);
            return false;
        }
        return this.field_175998_i.get(s).func_176034_g();
    }
    
    public ChatController() {
        this.field_153003_a = null;
        this.field_153004_b = "";
        this.field_153006_d = "";
        this.field_153007_e = "";
        this.field_175992_e = null;
        this.field_153008_f = null;
        this.field_153011_i = ChatState.Uninitialized;
        this.field_153012_j = new AuthToken();
        this.field_175998_i = new HashMap();
        this.field_153015_m = 128;
        this.field_175997_k = EnumEmoticonMode.None;
        this.field_175995_l = EnumEmoticonMode.None;
        this.field_175996_m = null;
        this.field_175993_n = 500;
        this.field_175994_o = 2000;
        this.field_175999_p = (IChatAPIListener)new IChatAPIListener() {
            final ChatController this$0;
            
            public void chatEmoticonDataDownloadCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.func_152988_s();
                }
            }
            
            public void chatShutdownCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    final ErrorCode shutdown = this.this$0.field_175992_e.shutdown();
                    if (ErrorCode.failed(shutdown)) {
                        this.this$0.func_152995_h(String.format("Error shutting down the Twitch sdk: %s", ErrorCode.getString(shutdown)));
                    }
                    this.this$0.func_175985_a(ChatState.Uninitialized);
                }
                else {
                    this.this$0.func_175985_a(ChatState.Initialized);
                    this.this$0.func_152995_h(String.format("Error shutting down Twith chat: %s", errorCode));
                }
                if (this.this$0.field_153003_a != null) {
                    this.this$0.field_153003_a.func_176022_e(errorCode);
                }
            }
            
            public void chatInitializationCallback(final ErrorCode errorCode) {
                if (ErrorCode.succeeded(errorCode)) {
                    this.this$0.field_153008_f.setMessageFlushInterval(this.this$0.field_175993_n);
                    this.this$0.field_153008_f.setUserChangeEventInterval(this.this$0.field_175994_o);
                    this.this$0.func_153001_r();
                    this.this$0.func_175985_a(ChatState.Initialized);
                }
                else {
                    this.this$0.func_175985_a(ChatState.Uninitialized);
                }
                if (this.this$0.field_153003_a != null) {
                    this.this$0.field_153003_a.func_176023_d(errorCode);
                }
            }
        };
        this.field_175992_e = Core.getInstance();
        if (this.field_175992_e == null) {
            this.field_175992_e = new Core((CoreAPI)new StandardCoreAPI());
        }
        this.field_153008_f = new Chat((ChatAPI)new StandardChatAPI());
    }
    
    protected boolean func_175987_a(final String s, final boolean b) {
        if (this.field_153011_i != ChatState.Initialized) {
            return false;
        }
        if (this.field_175998_i.containsKey(s)) {
            this.func_152995_h("Already in channel: " + s);
            return false;
        }
        if (s != null && !s.equals("")) {
            final ChatChannelListener chatChannelListener = new ChatChannelListener(s);
            this.field_175998_i.put(s, chatChannelListener);
            final boolean func_176038_a = chatChannelListener.func_176038_a(b);
            if (!func_176038_a) {
                this.field_175998_i.remove(s);
            }
            return func_176038_a;
        }
        return false;
    }
    
    public boolean func_175984_n() {
        if (this.field_153011_i != ChatState.Uninitialized) {
            return false;
        }
        this.func_175985_a(ChatState.Initializing);
        final ErrorCode initialize = this.field_175992_e.initialize(this.field_153006_d, (String)null);
        if (ErrorCode.failed(initialize)) {
            this.func_175985_a(ChatState.Uninitialized);
            this.func_152995_h(String.format("Error initializing Twitch sdk: %s", ErrorCode.getString(initialize)));
            return false;
        }
        this.field_175995_l = this.field_175997_k;
        final HashSet<ChatTokenizationOption> set = new HashSet<ChatTokenizationOption>();
        switch (ChatController$2.$SwitchMap$net$minecraft$client$stream$ChatController$EnumEmoticonMode[this.field_175997_k.ordinal()]) {
            case 1: {
                set.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_NONE);
                break;
            }
            case 2: {
                set.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_URLS);
                break;
            }
            case 3: {
                set.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_TEXTURES);
                break;
            }
        }
        final ErrorCode initialize2 = this.field_153008_f.initialize((HashSet)set, this.field_175999_p);
        if (ErrorCode.failed(initialize2)) {
            this.field_175992_e.shutdown();
            this.func_175985_a(ChatState.Uninitialized);
            this.func_152995_h(String.format("Error initializing Twitch chat: %s", ErrorCode.getString(initialize2)));
            return false;
        }
        this.func_175985_a(ChatState.Initialized);
        return true;
    }
    
    public boolean func_152993_m() {
        if (this.field_153011_i != ChatState.Initialized) {
            return false;
        }
        final ErrorCode shutdown = this.field_153008_f.shutdown();
        if (ErrorCode.failed(shutdown)) {
            this.func_152995_h(String.format("Error shutting down chat: %s", ErrorCode.getString(shutdown)));
            return false;
        }
        this.func_152996_t();
        this.func_175985_a(ChatState.ShuttingDown);
        return true;
    }
    
    public boolean func_175986_a(final String s, final String s2) {
        if (this.field_153011_i != ChatState.Initialized) {
            return false;
        }
        if (!this.field_175998_i.containsKey(s)) {
            this.func_152995_h("Not in channel: " + s);
            return false;
        }
        return this.field_175998_i.get(s).func_176037_b(s2);
    }
    
    protected void func_153001_r() {
        if (this.field_175995_l != EnumEmoticonMode.None && this.field_175996_m == null) {
            final ErrorCode downloadEmoticonData = this.field_153008_f.downloadEmoticonData();
            if (ErrorCode.failed(downloadEmoticonData)) {
                this.func_152995_h(String.format("Error trying to download emoticon data: %s", ErrorCode.getString(downloadEmoticonData)));
            }
        }
    }
    
    public boolean func_175990_d(final String s) {
        return this.field_175998_i.containsKey(s) && this.field_175998_i.get(s).func_176040_a() == EnumChannelState.Connected;
    }
    
    public ChatState func_153000_j() {
        return this.field_153011_i;
    }
    
    public class ChatChannelListener implements IChatChannelListener
    {
        protected LinkedList field_176045_e;
        protected EnumChannelState field_176047_c;
        protected String field_176048_a;
        protected List field_176044_d;
        protected ChatBadgeData field_176043_g;
        protected boolean field_176046_b;
        final ChatController this$0;
        protected LinkedList field_176042_f;
        
        public boolean func_176037_b(final String s) {
            if (this.field_176047_c != EnumChannelState.Connected) {
                return false;
            }
            final ErrorCode sendMessage = this.this$0.field_153008_f.sendMessage(this.field_176048_a, s);
            if (ErrorCode.failed(sendMessage)) {
                this.this$0.func_152995_h(String.format("Error sending chat message: %s", ErrorCode.getString(sendMessage)));
                return false;
            }
            return true;
        }
        
        protected void func_176033_j() {
            if (this.field_176043_g != null) {
                final ErrorCode clearBadgeData = this.this$0.field_153008_f.clearBadgeData(this.field_176048_a);
                if (ErrorCode.succeeded(clearBadgeData)) {
                    this.field_176043_g = null;
                    if (this.this$0.field_153003_a != null) {
                        this.this$0.field_153003_a.func_176020_d(this.field_176048_a);
                    }
                }
                else {
                    this.this$0.func_152995_h("Error releasing badge data: " + ErrorCode.getString(clearBadgeData));
                }
            }
        }
        
        private void func_176030_k() {
            if (this.field_176047_c != EnumChannelState.Disconnected) {
                this.func_176035_a(EnumChannelState.Disconnected);
                this.func_176036_d(this.field_176048_a);
                this.func_176033_j();
            }
        }
        
        public boolean func_176038_a(final boolean field_176046_b) {
            this.field_176046_b = field_176046_b;
            final ErrorCode ttv_EC_SUCCESS = ErrorCode.TTV_EC_SUCCESS;
            ErrorCode errorCode;
            if (field_176046_b) {
                errorCode = this.this$0.field_153008_f.connectAnonymous(this.field_176048_a, (IChatChannelListener)this);
            }
            else {
                errorCode = this.this$0.field_153008_f.connect(this.field_176048_a, this.this$0.field_153004_b, this.this$0.field_153012_j.data, (IChatChannelListener)this);
            }
            if (ErrorCode.failed(errorCode)) {
                this.this$0.func_152995_h(String.format("Error connecting: %s", ErrorCode.getString(errorCode)));
                this.func_176036_d(this.field_176048_a);
                return false;
            }
            this.func_176035_a(EnumChannelState.Connecting);
            this.func_176041_h();
            return true;
        }
        
        public boolean func_176034_g() {
            switch (ChatController$2.$SwitchMap$net$minecraft$client$stream$ChatController$EnumChannelState[this.field_176047_c.ordinal()]) {
                case 1:
                case 2: {
                    final ErrorCode disconnect = this.this$0.field_153008_f.disconnect(this.field_176048_a);
                    if (ErrorCode.failed(disconnect)) {
                        this.this$0.func_152995_h(String.format("Error disconnecting: %s", ErrorCode.getString(disconnect)));
                        return false;
                    }
                    this.func_176035_a(EnumChannelState.Disconnecting);
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
        
        public void func_176032_a(final String s) {
            if (this.this$0.field_175995_l == EnumEmoticonMode.None) {
                this.field_176045_e.clear();
                this.field_176042_f.clear();
            }
            else {
                if (this.field_176045_e.size() > 0) {
                    final ListIterator listIterator = this.field_176045_e.listIterator();
                    while (listIterator.hasNext()) {
                        if (listIterator.next().userName.equals(s)) {
                            listIterator.remove();
                        }
                    }
                }
                if (this.field_176042_f.size() > 0) {
                    final ListIterator listIterator2 = this.field_176042_f.listIterator();
                    while (listIterator2.hasNext()) {
                        if (listIterator2.next().displayName.equals(s)) {
                            listIterator2.remove();
                        }
                    }
                }
            }
            if (this.this$0.field_153003_a != null) {
                this.this$0.field_153003_a.func_176019_a(this.field_176048_a, s);
            }
        }
        
        public EnumChannelState func_176040_a() {
            return this.field_176047_c;
        }
        
        public void chatClearCallback(final String s, final String s2) {
            this.func_176032_a(s2);
        }
        
        public void chatChannelRawMessageCallback(final String s, final ChatRawMessage[] array) {
            while (0 < array.length) {
                this.field_176045_e.addLast(array[0]);
                int n = 0;
                ++n;
            }
            if (this.this$0.field_153003_a != null) {
                this.this$0.field_153003_a.func_180605_a(this.field_176048_a, array);
            }
            while (this.field_176045_e.size() > this.this$0.field_153015_m) {
                this.field_176045_e.removeFirst();
            }
        }
        
        public void chatChannelTokenizedMessageCallback(final String s, final ChatTokenizedMessage[] array) {
            while (0 < array.length) {
                this.field_176042_f.addLast(array[0]);
                int n = 0;
                ++n;
            }
            if (this.this$0.field_153003_a != null) {
                this.this$0.field_153003_a.func_176025_a(this.field_176048_a, array);
            }
            while (this.field_176042_f.size() > this.this$0.field_153015_m) {
                this.field_176042_f.removeFirst();
            }
        }
        
        protected void func_176036_d(final String s) {
            if (this.this$0.field_153003_a != null) {
                this.this$0.field_153003_a.func_180607_b(s);
            }
        }
        
        public void chatBadgeDataDownloadCallback(final String s, final ErrorCode errorCode) {
            if (ErrorCode.succeeded(errorCode)) {
                this.func_176039_i();
            }
        }
        
        public void chatChannelUserChangeCallback(final String s, final ChatUserInfo[] array, final ChatUserInfo[] array2, final ChatUserInfo[] array3) {
            int n = 0;
            while (0 < array2.length) {
                final int index = this.field_176044_d.indexOf(array2[0]);
                if (index >= 0) {
                    this.field_176044_d.remove(index);
                }
                ++n;
            }
            while (0 < array3.length) {
                final int index2 = this.field_176044_d.indexOf(array3[0]);
                if (index2 >= 0) {
                    this.field_176044_d.remove(index2);
                }
                this.field_176044_d.add(array3[0]);
                ++n;
            }
            while (0 < array.length) {
                this.field_176044_d.add(array[0]);
                ++n;
            }
            if (this.this$0.field_153003_a != null) {
                this.this$0.field_153003_a.func_176018_a(this.field_176048_a, array, array2, array3);
            }
        }
        
        public void chatStatusCallback(final String s, final ErrorCode errorCode) {
            if (!ErrorCode.succeeded(errorCode)) {
                this.this$0.field_175998_i.remove(s);
                this.func_176030_k();
            }
        }
        
        protected void func_176039_i() {
            if (this.field_176043_g == null) {
                this.field_176043_g = new ChatBadgeData();
                final ErrorCode badgeData = this.this$0.field_153008_f.getBadgeData(this.field_176048_a, this.field_176043_g);
                if (ErrorCode.succeeded(badgeData)) {
                    if (this.this$0.field_153003_a != null) {
                        this.this$0.field_153003_a.func_176016_c(this.field_176048_a);
                    }
                }
                else {
                    this.this$0.func_152995_h("Error preparing badge data: " + ErrorCode.getString(badgeData));
                }
            }
        }
        
        protected void func_176041_h() {
            if (this.this$0.field_175995_l != EnumEmoticonMode.None && this.field_176043_g == null) {
                final ErrorCode downloadBadgeData = this.this$0.field_153008_f.downloadBadgeData(this.field_176048_a);
                if (ErrorCode.failed(downloadBadgeData)) {
                    this.this$0.func_152995_h(String.format("Error trying to download badge data: %s", ErrorCode.getString(downloadBadgeData)));
                }
            }
        }
        
        protected void func_176031_c(final String s) {
            if (this.this$0.field_153003_a != null) {
                this.this$0.field_153003_a.func_180606_a(s);
            }
        }
        
        public void chatChannelMembershipCallback(final String s, final ChatEvent chatEvent, final ChatChannelInfo chatChannelInfo) {
            switch (ChatController$2.$SwitchMap$tv$twitch$chat$ChatEvent[chatEvent.ordinal()]) {
                case 1: {
                    this.func_176035_a(EnumChannelState.Connected);
                    this.func_176031_c(s);
                    break;
                }
                case 2: {
                    this.func_176030_k();
                    break;
                }
            }
        }
        
        public ChatChannelListener(final ChatController this$0, final String field_176048_a) {
            this.this$0 = this$0;
            this.field_176048_a = null;
            this.field_176046_b = false;
            this.field_176047_c = EnumChannelState.Created;
            this.field_176044_d = Lists.newArrayList();
            this.field_176045_e = new LinkedList();
            this.field_176042_f = new LinkedList();
            this.field_176043_g = null;
            this.field_176048_a = field_176048_a;
        }
        
        protected void func_176035_a(final EnumChannelState field_176047_c) {
            if (field_176047_c != this.field_176047_c) {
                this.field_176047_c = field_176047_c;
            }
        }
    }
    
    public interface ChatListener
    {
        void func_180606_a(final String p0);
        
        void func_176023_d(final ErrorCode p0);
        
        void func_176018_a(final String p0, final ChatUserInfo[] p1, final ChatUserInfo[] p2, final ChatUserInfo[] p3);
        
        void func_176019_a(final String p0, final String p1);
        
        void func_176024_e();
        
        void func_176021_d();
        
        void func_180605_a(final String p0, final ChatRawMessage[] p1);
        
        void func_176017_a(final ChatState p0);
        
        void func_176016_c(final String p0);
        
        void func_180607_b(final String p0);
        
        void func_176025_a(final String p0, final ChatTokenizedMessage[] p1);
        
        void func_176022_e(final ErrorCode p0);
        
        void func_176020_d(final String p0);
    }
    
    public enum ChatState
    {
        Uninitialized("Uninitialized", 0);
        
        private static final ChatState[] $VALUES;
        
        Initializing("Initializing", 1), 
        ShuttingDown("ShuttingDown", 3), 
        Initialized("Initialized", 2);
        
        static {
            $VALUES = new ChatState[] { ChatState.Uninitialized, ChatState.Initializing, ChatState.Initialized, ChatState.ShuttingDown };
        }
        
        private ChatState(final String s, final int n) {
        }
    }
    
    public enum EnumEmoticonMode
    {
        None("None", 0), 
        Url("Url", 1), 
        TextureAtlas("TextureAtlas", 2);
        
        private static final EnumEmoticonMode[] $VALUES;
        
        private EnumEmoticonMode(final String s, final int n) {
        }
        
        static {
            $VALUES = new EnumEmoticonMode[] { EnumEmoticonMode.None, EnumEmoticonMode.Url, EnumEmoticonMode.TextureAtlas };
        }
    }
    
    public enum EnumChannelState
    {
        private static final EnumChannelState[] $VALUES;
        
        Disconnected("Disconnected", 4), 
        Created("Created", 0), 
        Disconnecting("Disconnecting", 3), 
        Connected("Connected", 2), 
        Connecting("Connecting", 1);
        
        private EnumChannelState(final String s, final int n) {
        }
        
        static {
            $VALUES = new EnumChannelState[] { EnumChannelState.Created, EnumChannelState.Connecting, EnumChannelState.Connected, EnumChannelState.Disconnecting, EnumChannelState.Disconnected };
        }
    }
}
