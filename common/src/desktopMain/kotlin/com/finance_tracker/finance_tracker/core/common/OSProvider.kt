package com.finance_tracker.finance_tracker.core.common

object OSProvider {
    private val OSName = System.getProperty("os.name").lowercase()
    private val isWindows = OSName.indexOf("win") >= 0
    private val isMac = OSName.indexOf("mac") >= 0
    private val isUnix = OSName.indexOf("nix") >= 0 ||
            OSName.indexOf("nux") >= 0 ||
            OSName.indexOf("aix") > 0
    private var isSolaris = OSName.indexOf("sunos") >= 0

    val os = when {
        isWindows -> OS.Windows
        isMac -> OS.Mac
        isUnix -> OS.Unix
        isSolaris -> OS.Solaris
        else -> error("Undefined operation system")
    }
}

enum class OS {
    Windows,
    Mac,
    Unix,
    Solaris
}