package optfine;

public class FileDownloadThread extends Thread
{
    private String urlString;
    private IFileDownloadListener listener;
    
    public IFileDownloadListener getListener() {
        return this.listener;
    }
    
    @Override
    public void run() {
        this.listener.fileDownloadFinished(this.urlString, HttpUtils.get(this.urlString), null);
    }
    
    public String getUrlString() {
        return this.urlString;
    }
    
    public FileDownloadThread(final String urlString, final IFileDownloadListener listener) {
        this.urlString = null;
        this.listener = null;
        this.urlString = urlString;
        this.listener = listener;
    }
}
