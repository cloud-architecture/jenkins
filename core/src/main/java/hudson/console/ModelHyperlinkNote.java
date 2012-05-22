package hudson.console;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.ModelObject;
import hudson.model.Run;
import hudson.model.User;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link HyperlinkNote} that links to a {@linkplain ModelObject model object},
 * which in the UI gets rendered with context menu and etc.
 *
 * @author Kohsuke Kawaguchi
 * @since 1.464
 */
public class ModelHyperlinkNote extends HyperlinkNote {
    public ModelHyperlinkNote(String url, int length) {
        super(url, length);
    }

    @Override
    protected String extraAttributes() {
        return " class='model-link'";
    }

    public static String encodeTo(User u) {
        return encodeTo(u,u.getDisplayName());
    }

    public static String encodeTo(User u, String text) {
        return encodeTo('/'+u.getUrl(),text);
    }

    public static String encodeTo(Item item) {
        return encodeTo(item,item.getDisplayName());
    }

    public static String encodeTo(Item item, String text) {
        return encodeTo('/'+item.getUrl(),text);
    }

    public static String encodeTo(Run r) {
        return encodeTo('/'+r.getUrl(),r.getDisplayName());
    }
    
    public static String encodeTo(String url, String text) {
        try {
            return new ModelHyperlinkNote(url,text.length()).encode()+text;
        } catch (IOException e) {
            // impossible, but don't make this a fatal problem
            LOGGER.log(Level.WARNING, "Failed to serialize "+ModelHyperlinkNote.class,e);
            return text;
        }
    }

    @Extension
    public static class DescriptorImpl extends HyperlinkNote.DescriptorImpl {
        public String getDisplayName() {
            return "Hyperlinks to models";
        }
    }
    
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(ModelHyperlinkNote.class.getName());
}
