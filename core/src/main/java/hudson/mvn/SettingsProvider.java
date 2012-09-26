package hudson.mvn;

import hudson.ExtensionPoint;
import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.model.TaskListener;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 * @author Dominik Bartholdi (imod)
 */
public abstract class SettingsProvider extends AbstractDescribableImpl<SettingsProvider> implements ExtensionPoint {

    /**
     * Configure maven launcher argument list with adequate settings path. Implementations should be aware that this method might get called multiple times during a build.
     * 
     * @param project
     * @return the filepath to the provided file. <code>null</code> if no settings will be provided.
     */
    public abstract FilePath configure(AbstractBuild<?, ?> project, TaskListener listener);

    public static SettingsProvider parseSettingsProvider(StaplerRequest req) throws Descriptor.FormException, ServletException {
        JSONObject settings = req.getSubmittedForm().getJSONObject("settings");
        if (settings == null) {
            return new DefaultSettingsProvider();
        }
        return req.bindJSON(SettingsProvider.class, settings);
    }

    /**
     * Convenience method handling all <code>null</code> checks. Provides the path on the (possible) remote settings file.
     * 
     * @param settings
     *            the provider to be used
     * @param build
     *            the active build
     * @param listener
     *            the listener of the current build
     * @return the path to the settings.xml
     */
    public static final FilePath getFilePathOppressed(SettingsProvider settings, AbstractBuild<?, ?> build, TaskListener listener) {
        FilePath settingsPath = null;
        if (settings != null) {
            settingsPath = settings.configure(build, listener);
        }
        return settingsPath == null ? null : settingsPath;
    }

    /**
     * Convenience method handling all <code>null</code> checks. Provides the path on the (possible) remote settings file.
     * 
     * @param settings
     *            the provider to be used
     * @param build
     *            the active build
     * @param listener
     *            the listener of the current build
     * @return the path to the settings.xml
     */
    public static final String getRemotePath(SettingsProvider settings, AbstractBuild<?, ?> build, TaskListener listener) {
        FilePath fp = getFilePathOppressed(settings, build, listener);
        return fp == null ? null : fp.getRemote();
    }

}
