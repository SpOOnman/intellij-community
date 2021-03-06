/*
 * Copyright 2000-2013 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.dvcs;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.WindowManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author Kirill Likhodedov
 */
public class DvcsUtil {

  public static void installStatusBarWidget(@NotNull Project project, @NotNull StatusBarWidget widget) {
    StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
    if (statusBar != null) {
      statusBar.addWidget(widget, "after " + (SystemInfo.isMac ? "Encoding" : "InsertOverwrite"), project);
    }
  }

  public static void removeStatusBarWidget(@NotNull Project project, @NotNull StatusBarWidget widget) {
    StatusBar statusBar = WindowManager.getInstance().getStatusBar(project);
    if (statusBar != null) {
      statusBar.removeWidget(widget.ID());
    }
  }

}
