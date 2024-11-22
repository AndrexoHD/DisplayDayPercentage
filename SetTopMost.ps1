# Fenster immer im Vordergrund halten (ohne Fokus zu ändern)
Add-Type @"
using System;
using System.Runtime.InteropServices;
public class Window {
    [DllImport("user32.dll")]
    [return: MarshalAs(UnmanagedType.Bool)]
    public static extern bool SetWindowPos(IntPtr hWnd, IntPtr hWndInsertAfter, int X, int Y, int cx, int cy, uint uFlags);

    public static readonly IntPtr HWND_TOPMOST = new IntPtr(-1);
    public static readonly IntPtr HWND_NOTOPMOST = new IntPtr(-2);
    public const uint SWP_NOSIZE = 0x0001;
    public const uint SWP_NOMOVE = 0x0002;
    public const uint SWP_NOACTIVATE = 0x0010; // Kein Fokus setzen
    public const uint TOPMOST_FLAGS = SWP_NOSIZE | SWP_NOMOVE | SWP_NOACTIVATE;
}
"@

# Fenstertitel
$WindowTitle = "DisplayDayPercentage"

# Suche nach dem Fensterhandle
$hwnd = (Get-Process | Where-Object { $_.MainWindowTitle -eq $WindowTitle }).MainWindowHandle

# Überprüfen, ob Fenster gefunden wurde
if ($hwnd -eq 0) {
    Write-Output "Fenster mit Titel '$WindowTitle' wurde nicht gefunden."
    exit
}

# Fenster immer im Vordergrund halten
[Window]::SetWindowPos($hwnd, [Window]::HWND_TOPMOST, 0, 0, 0, 0, [Window]::TOPMOST_FLAGS)
Write-Output "Fenster '$WindowTitle' bleibt immer im Vordergrund."
