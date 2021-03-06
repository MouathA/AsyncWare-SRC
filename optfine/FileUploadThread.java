package optfine;

import java.util.*;

public class FileUploadThread extends Thread
{
    private byte[] content;
    private IFileUploadListener listener;
    private String urlString;
    private Map headers;
    
    public byte[] getContent() {
        return this.content;
    }
    
    public String getUrlString() {
        return this.urlString;
    }
    
    @Override
    public void run() {
        HttpUtils.post(this.urlString, this.headers, this.content);
        this.listener.fileUploadFinished(this.urlString, this.content, null);
    }
    
    public IFileUploadListener getListener() {
        return this.listener;
    }
    
    public FileUploadThread(final String urlString, final Map headers, final byte[] content, final IFileUploadListener listener) {
        this.urlString = urlString;
        this.headers = headers;
        this.content = content;
        this.listener = listener;
    }
}
