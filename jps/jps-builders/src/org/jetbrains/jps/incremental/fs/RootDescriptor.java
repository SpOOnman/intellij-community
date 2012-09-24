package org.jetbrains.jps.incremental.fs;

import com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.builders.BuildRootDescriptor;
import org.jetbrains.jps.builders.BuildTarget;
import org.jetbrains.jps.incremental.ModuleBuildTarget;

import java.io.File;

/**
* @author Eugene Zhuravlev
*         Date: 1/3/12
*/
public final class RootDescriptor extends BuildRootDescriptor {
  @NotNull
  public final File root;
  @NotNull
  public final ModuleBuildTarget target;
  public final boolean isTestRoot;
  public final boolean isGeneratedSources;
  public final boolean isTemp;

  public RootDescriptor(@NotNull File root,
                        @NotNull ModuleBuildTarget target,
                        boolean isTestRoot,
                        boolean isGenerated,
                        boolean isTemp) {
    this.root = root;
    this.target = target;
    this.isTestRoot = isTestRoot;
    this.isGeneratedSources = isGenerated;
    this.isTemp = isTemp;
  }

  @Override
  public String toString() {
    return "RootDescriptor{" +
           "target='" + target + '\'' +
           ", root=" + root +
           ", test=" + isTestRoot +
           ", generated=" + isGeneratedSources +
           '}';
  }

  @Override
  public String getRootId() {
    return FileUtil.toSystemIndependentName(root.getPath());
  }

  @Override
  public File getRootFile() {
    return root;
  }

  @Override
  public BuildTarget getTarget() {
    return target;
  }
}