package com.intellij.openapi.externalSystem;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.externalSystem.build.ExternalSystemBuildManager;
import com.intellij.openapi.externalSystem.model.ProjectSystemId;
import com.intellij.openapi.externalSystem.service.ParametersEnhancer;
import com.intellij.openapi.externalSystem.service.project.ExternalSystemProjectResolver;
import com.intellij.openapi.externalSystem.service.remote.ExternalSystemExecutionSettings;
import com.intellij.openapi.externalSystem.settings.AbstractExternalSystemLocalSettings;
import com.intellij.openapi.externalSystem.settings.AbstractExternalSystemSettings;
import com.intellij.openapi.externalSystem.settings.ExternalSystemSettingsListener;
import com.intellij.openapi.project.Project;
import com.intellij.util.Function;
import org.jetbrains.annotations.NotNull;

/**
 * IntelliJ external systems integration is built using GoF Bridge pattern, i.e. 'external-system' module defines
 * external system-specific extension (current interface) and an api which is used by all extensions. Most of the codebase
 * is built on top of that api and provides generic actions like 'sync ide project with external project'; 'import library
 * dependencies which are configured at external system but not at the ide' etc.
 * <p/>
 * That makes it relatively easy to add a new external system integration.
 * 
 * @author Denis Zhdanov
 * @since 4/4/13 4:05 PM
 */
public interface ExternalSystemManager<
  SettingsListener extends ExternalSystemSettingsListener,
  Settings extends AbstractExternalSystemSettings<SettingsListener, Settings>,
  LocalSettings extends AbstractExternalSystemLocalSettings<LocalSettings>,
  ExecutionSettings extends ExternalSystemExecutionSettings>
  extends ParametersEnhancer
{
  
  ExtensionPointName<ExternalSystemManager> EP_NAME = ExtensionPointName.create("com.intellij.externalSystemManager");
  
  /**
   * @return    id of the external system represented by the current manager
   */
  @NotNull
  ProjectSystemId getSystemId();

  /**
   * Allows to answer if target external system is ready to use for the given project.
   * <p/>
   * A negative answer might be returned if, say, target external system config path is not defined at the given ide project.
   * 
   * @param project  target ide project
   * @return         <code>true</code> if target external system is configured for the given ide project;
   *                 <code>false</code> otherwise
   */
  boolean isReady(@NotNull Project project);
  
  /**
   * @return    a strategy which can be queried for external system settings to use with the given project
   */
  @NotNull
  Function<Project, Settings> getSettingsProvider();

  /**
   * @return    a strategy which can be queried for external system local settings to use with the given project
   */
  @NotNull
  Function<Project, LocalSettings> getLocalSettingsProvider();

  /**
   * @return    a strategy which can be queried for external system execution settings to use with the given project
   */
  @NotNull
  Function<Project, ExecutionSettings> getExecutionSettingsProvider();

  /**
   * Allows to retrieve information about {@link ExternalSystemProjectResolver project resolver} to use for the target external
   * system.
   * <p/>
   * <b>Note:</b> we return a class instance instead of resolver object here because there is a possible case that the resolver
   * is used at external (non-ide) process, so, it needs information which is enough for instantiating it there. That implies
   * the requirement that target resolver class is expected to have a no-args constructor
   * 
   * @return  class of the project resolver to use for the target external system
   */
  @NotNull
  Class<? extends ExternalSystemProjectResolver<ExecutionSettings>> getProjectResolverClass();

  /**
   * @return    class of the build manager to use for the target external system
   * @see #getProjectResolverClass()
   */
  Class<? extends ExternalSystemBuildManager<ExecutionSettings>> getBuildManagerClass();
}
