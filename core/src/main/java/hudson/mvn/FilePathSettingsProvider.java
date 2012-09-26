package hudson.mvn;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.TaskListener;
import hudson.util.IOUtils;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 * @author Dominik Bartholdi (imod)
 */
public class FilePathSettingsProvider extends SettingsProvider {

    private final String path;

    @DataBoundConstructor
    public FilePathSettingsProvider(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public FilePath configure(AbstractBuild<?, ?> project, TaskListener listener) {
        if (StringUtils.isEmpty(path))
            return null;
        if (IOUtils.isAbsolute(path)) {
            return new FilePath(new File(path));
        } else {
            FilePath mrSettings = project.getModuleRoot().child(path);
            FilePath wsSettings = project.getWorkspace().child(path);
            try {
                if (!wsSettings.exists() && mrSettings.exists()) {
                    wsSettings = mrSettings;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return wsSettings;
        }
    }

    @Extension(ordinal = 10)
    public static class DescriptorImpl extends SettingsProviderDescriptor {

        @Override
        public String getDisplayName() {
            return "File in project workspace";
        }
    }
}
